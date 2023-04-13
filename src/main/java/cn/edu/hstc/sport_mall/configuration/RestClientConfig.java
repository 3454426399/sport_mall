package cn.edu.hstc.sport_mall.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * ElasticSearch配置类，这里的配置会和和Spring的配置一同生效
 */
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    //通过注入elasticsearch对象，注入elasticsearchOperations，即elasticsearchRestTemplate，用于调用api操作es
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("127.0.0.1:9200")    //与elasticsearch服务器建立连接
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
