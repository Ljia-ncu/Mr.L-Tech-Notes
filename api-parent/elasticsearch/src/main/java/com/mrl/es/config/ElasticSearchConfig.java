package com.mrl.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: ElasticSearchConfig
 * @Description
 * @Author Mr.L
 * @Date 2020/12/5 16:31
 * @Version 1.0
 */
@Configuration
public class ElasticSearchConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        HttpHost.create("127.0.0.1:9200")
                )
        );
    }
}
