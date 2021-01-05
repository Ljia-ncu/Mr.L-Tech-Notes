import com.alibaba.fastjson.JSON;
import com.mrl.es.ElasticSearchApplication;
import com.mrl.es.pojo.Goods;
import com.mrl.es.pojo.GoodsSearchAttr;
import com.mrl.es.pojo.SearchParam;
import com.mrl.es.pojo.SearchResponseVO;
import com.mrl.es.repository.GoodsRepository;
import com.mrl.es.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: AggTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/8 14:28
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticSearchApplication.class)
public class AggTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private SearchService searchService;

    @Test
    public void importData() {
        Goods goods = new Goods();
        goods.setSkuId(7456L);
        goods.setPic("http://oss.pic7456");
        goods.setTitle("Redmi Note 9 Pro 5G 一亿像素 骁龙750G 33W快充 120Hz刷新率 碧海星辰");
        goods.setSubtitle("6GB+128GB 游戏智能手机 小米 红米");
        goods.setOriPrice(new BigDecimal("1488.00"));
        goods.setCurPrice(new BigDecimal("1488.00"));
        goods.setSales(120000L);
        goods.setStock(200000L);
        goods.setStatus(1);
        goods.setOnSaleDate(new Date());
        goods.setBrandId(4425L);
        goods.setBrandName("小米");
        goods.setCategoryId(105L);
        goods.setCategoryName("手机");
        List<GoodsSearchAttr> list = new ArrayList<>();
        GoodsSearchAttr attr = new GoodsSearchAttr();
        attr.setAttrId(1L);
        attr.setAttrName("内存");
        attr.setAttrValue("64G");
        list.add(attr);
        GoodsSearchAttr attr2 = new GoodsSearchAttr();
        attr2.setAttrId(2L);
        attr2.setAttrName("屏幕");
        attr2.setAttrValue("7.4寸");
        list.add(attr2);
        goods.setAttrs(list);
        goodsRepository.save(goods);
    }

    @Test
    public void importData2() {
        Goods goods = new Goods();
        goods.setSkuId(9999L);
        goods.setPic("http://oss.pic9999");
        goods.setTitle("华为 HUAWEI Mate 30E Pro 5G麒麟990E SoC芯片 双4000万徕卡电影影像");
        goods.setSubtitle("8GB+128GB亮黑色全网通手机");
        goods.setOriPrice(new BigDecimal("6488.00"));
        goods.setCurPrice(new BigDecimal("6488.00"));
        goods.setSales(120000L);
        goods.setStock(200000L);
        goods.setStatus(1);
        goods.setOnSaleDate(new Date());
        goods.setBrandId(9527L);
        goods.setBrandName("华为");
        goods.setCategoryId(105L);
        goods.setCategoryName("手机");
        List<GoodsSearchAttr> list = new ArrayList<>();
        GoodsSearchAttr attr = new GoodsSearchAttr();
        attr.setAttrId(1L);
        attr.setAttrName("内存");
        attr.setAttrValue("128G");
        list.add(attr);
        GoodsSearchAttr attr2 = new GoodsSearchAttr();
        attr2.setAttrId(2L);
        attr2.setAttrName("屏幕");
        attr2.setAttrValue("7寸");
        list.add(attr2);
        goods.setAttrs(list);
        goodsRepository.save(goods);
    }

    @Test
    public void test() throws IOException {
        SearchParam param = new SearchParam();
        param.setCategoryId(new String[]{"105"});
        param.setBrandId(new String[]{"4425", "9527", "555"});
        param.setAttr(new String[]{"1:64G-128G", "2:7寸-7.4寸"});      //注意G大小写，keyword大小写敏感
        param.setKeyword("手机");
        param.setOrder("curPrice:asc");
        param.setPriceTo(10000);
        SearchResponseVO responseVO = searchService.search(param);
        System.out.println(JSON.toJSONString(responseVO));
    }

    public int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        dp[0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j - 1] + dp[j];
            }
        }
        return dp[n - 1];
    }
}
