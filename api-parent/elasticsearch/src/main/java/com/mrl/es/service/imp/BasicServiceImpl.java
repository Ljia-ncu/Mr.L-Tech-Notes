package com.mrl.es.service.imp;

import com.alibaba.fastjson.JSON;
import com.mrl.es.entity.Book;
import com.mrl.es.service.BasicService;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @ClassName: SearchServiceImpl
 * @Description demo
 * @Author Mr.L
 * @Date 2020/12/5 21:32
 * @Version 1.0
 */
@Service
public class BasicServiceImpl implements BasicService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void index() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("mrl");
        CreateIndexResponse createIndexResponse
                = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

        GetIndexRequest getRequest = new GetIndexRequest("mrl");
        boolean exist = restHighLevelClient.indices().exists(getRequest, RequestOptions.DEFAULT);

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("mrl");
        restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);

    }

    public void index2() throws IOException {
        IndexRequest indexRequest = new IndexRequest();
        Book book = new Book().setBookId(1L);
        indexRequest.id("1");
        indexRequest.timeout(TimeValue.timeValueMillis(500));
        indexRequest.source(JSON.toJSONString(book), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    public void get() throws IOException {
        GetRequest getRequest = new GetRequest("mrl", "1");
        getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        getRequest.storedFields("_none_");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        getResponse.getSourceAsString();
    }

    public void update() throws IOException {
        UpdateRequest request = new UpdateRequest("mrl", "1");
        Book book = new Book().setBookId(1L).setBookName("cc");
        request.doc(JSON.toJSONString(book), XContentType.JSON);
        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);

    }

    public void query() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("bookName", "mysql");
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);

        SearchRequest searchRequest = new SearchRequest("mrl");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            hit.getSourceAsMap();
        }
    }


}

