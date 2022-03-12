package com.kevin.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Arrays;

public class EsDocDeleteBatch {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")
                ));

        // 查询文档-请求对象
        BulkRequest request = new BulkRequest();
        // 设置索引，设置文档的ID
        request.add(new DeleteRequest().index("user").id("1001"));
        request.add(new DeleteRequest().index("user").id("1002"));
        request.add(new DeleteRequest().index("user").id("1003"));
        // 向ES发送请求并获取响应
        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println("took:" + response.getTook());
        System.out.println("items:" + Arrays.toString(response.getItems()));

        esClient.close();
    }
}
