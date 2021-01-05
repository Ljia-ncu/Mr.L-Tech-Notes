package com.mrl.es.service.imp;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mrl.es.pojo.AggregationAttr;
import com.mrl.es.pojo.Goods;
import com.mrl.es.pojo.SearchParam;
import com.mrl.es.pojo.SearchResponseVO;
import com.mrl.es.service.SearchService;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: AggServiceImpl
 * @Description
 * @Author Mr.L
 * @Date 2020/12/6 18:39
 * @Version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public SearchResponseVO search(SearchParam searchParam) throws IOException {
        SearchRequest searchRequest = buildQueryDsl(searchParam);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchResponseVO responseVo = this.parseSearchResult(searchResponse);
        responseVo.setPageNum(searchParam.getPageNum());
        responseVo.setPageSize(searchParam.getPageSize());
        return responseVo;
    }

    private SearchResponseVO parseSearchResult(SearchResponse response) {
        SearchResponseVO responseVO = new SearchResponseVO();

        SearchHits hits = response.getHits();
        //总记录数
        responseVO.setTotal(hits.getTotalHits().value);

        //提取Goods & 高亮显示
        List<Goods> goods = new ArrayList<>();
        for (SearchHit hit : hits) {
            Goods tmp = JSON.parseObject(hit.getSourceAsString(), Goods.class);
            HighlightField titleField = hit.getHighlightFields().get("title");
            if (titleField != null) {
                tmp.setTitle(titleField.getFragments()[0].toString());
            }
            HighlightField subtitleField = hit.getHighlightFields().get("subtitle");
            if (subtitleField != null) {
                tmp.setSubtitle(subtitleField.getFragments()[0].toString());
            }
            goods.add(tmp);
        }
        responseVO.setGoods(goods);

        Map<String, Aggregation> aggregationMap = response.getAggregations().asMap();

        //解析brandIdAgg.bucket
        AggregationAttr brand = new AggregationAttr();
        brand.setName("brand");
        ParsedLongTerms brandIdAgg = (ParsedLongTerms) aggregationMap.get("brandIdAgg");
        List<String> brandValues = brandIdAgg.getBuckets().stream().map(bucket -> {
            Map<String, String> map = new HashMap<>();
            map.put("brandId", bucket.getKeyAsString());

            Map<String, Aggregation> brandNameMap = bucket.getAggregations().asMap();
            ParsedStringTerms brandNameAgg = (ParsedStringTerms) brandNameMap.get("brandNameAgg");
            map.put("brandName", brandNameAgg.getBuckets().get(0).getKeyAsString());

            return JSON.toJSONString(map);
        }).collect(Collectors.toList());
        brand.setValues(brandValues);
        responseVO.setBrand(brand);

        //解析categoryIdAgg.bucket
        AggregationAttr category = new AggregationAttr();
        category.setName("category");
        ParsedLongTerms categoryIdAgg = (ParsedLongTerms) aggregationMap.get("categoryIdAgg");
        List<String> categoryValues = categoryIdAgg.getBuckets().stream().map(bucket -> {
            Map<String, String> map = new HashMap<>();
            map.put("categoryId", bucket.getKeyAsString());

            Map<String, Aggregation> categoryNameMap = bucket.getAggregations().asMap();
            ParsedStringTerms categoryNameMapAgg = (ParsedStringTerms) categoryNameMap.get("categoryNameAgg");
            map.put("categoryName", categoryNameMapAgg.getBuckets().get(0).getKeyAsString());

            return JSON.toJSONString(map);
        }).collect(Collectors.toList());
        category.setValues(categoryValues);
        responseVO.setCategory(category);

        //解析Nested attrs
        ParsedNested attrsAgg = (ParsedNested) aggregationMap.get("attrsAgg");
        ParsedLongTerms attrsIdAgg = attrsAgg.getAggregations().get("attrsIdAgg");
        List<AggregationAttr> attrList = attrsIdAgg.getBuckets().stream().map(bucket -> {
            AggregationAttr tmp = new AggregationAttr();

            //attrsId
            tmp.setId(bucket.getKeyAsNumber().longValue());

            Map<String, Aggregation> attrsMap = bucket.getAggregations().asMap();

            //attrsName
            ParsedStringTerms attrsName = (ParsedStringTerms) attrsMap.get("attrsNameAgg");
            tmp.setName(attrsName.getBuckets().get(0).getKeyAsString());

            //attrsValue
            ParsedStringTerms attrsValue = (ParsedStringTerms) attrsMap.get("attrsValueAgg");
            List<String> attrsValueList = attrsValue.getBuckets().stream().map(Terms.Bucket::getKeyAsString).collect(Collectors.toList());
            tmp.setValues(attrsValueList);

            return tmp;
        }).collect(Collectors.toList());
        responseVO.setAttrs(attrList);
        return responseVO;
    }

    private SearchRequest buildQueryDsl(SearchParam searchParam) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //关键字匹配title or subTitle
        String keyword = searchParam.getKeyword();
        if (!StrUtil.isEmpty(keyword)) {
            BoolQueryBuilder keywordQuery = QueryBuilders.boolQuery();
            keywordQuery.should(QueryBuilders.matchQuery("title", keyword))
                    .should(QueryBuilders.matchQuery("subtitle", keyword));
            boolQueryBuilder.must(keywordQuery);
        }

        //状态过滤
        boolQueryBuilder.filter(QueryBuilders.termQuery("status", 1));

        //分类过滤
        String[] categoryId = searchParam.getCategoryId();
        if (null != categoryId && categoryId.length != 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("categoryId", categoryId));
        }

        //品牌过滤s
        String[] brandId = searchParam.getBrandId();
        if (null != brandId && brandId.length != 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId", brandId));
        }

        //nested attr 过滤
        String[] attrs = searchParam.getAttr();
        if (null != attrs && attrs.length != 0) {
            for (String attr : attrs) {
                String[] split = attr.split(":");
                String attrId = split[0];
                String[] attrValue = split[1].split("-");

                BoolQueryBuilder nestedQuery = QueryBuilders.boolQuery();
                BoolQueryBuilder subQuery = QueryBuilders.boolQuery();
                subQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                subQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValue));
                nestedQuery.must(QueryBuilders.nestedQuery("attrs", subQuery, ScoreMode.None));
                boolQueryBuilder.filter(nestedQuery);
            }
        }

        //价格区间过滤
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("curPrice");
        Integer priceFrom = searchParam.getPriceFrom();
        Integer priceTo = searchParam.getPriceTo();
        if (priceFrom != null) {
            rangeQueryBuilder.gte(priceFrom);
        }
        if (priceTo != null) {
            rangeQueryBuilder.lte(priceTo);
        }
        boolQueryBuilder.filter(rangeQueryBuilder);

        //整合boolQuery
        sourceBuilder.query(boolQueryBuilder);

        //分页
        Integer pageNum = searchParam.getPageNum();
        Integer pageSize = searchParam.getPageSize();
        sourceBuilder.from((pageNum - 1) * pageSize);
        sourceBuilder.size(pageSize);

        //排序
        String order = searchParam.getOrder();
        if (!StrUtil.isEmpty(order)) {
            String[] split = order.split(":");
            sourceBuilder.sort(split[0], "asc".equals(split[1]) ? SortOrder.ASC : SortOrder.DESC);
        }

        //高亮
        sourceBuilder.highlighter(new HighlightBuilder().field("title").field("subtitle").preTags("<em>").postTags("</em>"));

        //brand聚合
        sourceBuilder.aggregation(AggregationBuilders.terms("brandIdAgg").field("brandId")
                .subAggregation(AggregationBuilders.terms("brandNameAgg").field("brandName")));

        //分类聚合
        sourceBuilder.aggregation(AggregationBuilders.terms("categoryIdAgg").field("categoryId")
                .subAggregation(AggregationBuilders.terms("categoryNameAgg").field("categoryName")));

        //nested 聚合
        sourceBuilder.aggregation(AggregationBuilders.nested("attrsAgg", "attrs")
                .subAggregation(AggregationBuilders.terms("attrsIdAgg").field("attrs.attrId")
                        .subAggregation(AggregationBuilders.terms("attrsNameAgg").field("attrs.attrName"))
                        .subAggregation(AggregationBuilders.terms("attrsValueAgg").field("attrs.attrValue"))));

        String[] include = new String[]{"skuId", "pic", "title", "subtitle", "oriPrice", "curPrice", "sales", "stock", "onSaleDate"};
        sourceBuilder.fetchSource(include, null);

        SearchRequest searchRequest = new SearchRequest("goods");
        searchRequest.source(sourceBuilder);
        return searchRequest;
    }
}
