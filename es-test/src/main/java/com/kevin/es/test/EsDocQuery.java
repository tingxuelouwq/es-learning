package com.kevin.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

public class EsDocQuery {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")
                ));

//        // 1. 查询所有文档
//        // 创建搜索请求对象
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//
//        // 构建查询的请求体
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        // 查询所有文档
//        sourceBuilder.query(QueryBuilders.matchAllQuery());
//        request.source(sourceBuilder);

//        // 2. 条件查询
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        request.source(sourceBuilder.query(QueryBuilders.termQuery("age", 30)));
//        request.source(sourceBuilder.query(QueryBuilders.matchQuery("name", "wangwu")));

//        // 3. 分页查询
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        request.source(sourceBuilder.query(QueryBuilders.matchAllQuery()));
//        sourceBuilder.from(0);  // (当前页码-1)*每页显示数据条数
//        sourceBuilder.size(2);
//        request.source(sourceBuilder);

//        // 4. 查询排序
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        request.source(sourceBuilder.query(QueryBuilders.matchAllQuery()));
//        sourceBuilder.sort("age", SortOrder.DESC);
//        request.source(sourceBuilder);

//        // 5. 过滤字段
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        request.source(sourceBuilder.query(QueryBuilders.matchAllQuery()));
//
//        String[] includes = {"name", "age"};
//        String[] excludes = {};
//        sourceBuilder.fetchSource(includes, excludes);
//
//        request.source(sourceBuilder);

//        // 6. 组合查询
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 30));
//        boolQueryBuilder.must(QueryBuilders.matchQuery("sex", "男"));
//        sourceBuilder.query(boolQueryBuilder);
//
//        request.source(sourceBuilder);

//        // 7. 范围查询
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//
//        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("age");
//        rangeQueryBuilder.gte(30);
//        rangeQueryBuilder.lte(40);
//        sourceBuilder.query(rangeQueryBuilder);
//
//        request.source(sourceBuilder);

//        // 8. 模糊查询
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//
//        FuzzyQueryBuilder fuzzyQueryBuilder = new FuzzyQueryBuilder("name", "wangwu")
//                .fuzziness(Fuzziness.ONE);
//        sourceBuilder.query(fuzzyQueryBuilder);
//
//        request.source(sourceBuilder);

//        // 9. 高亮查询
//        SearchRequest request = new SearchRequest();
//        request.indices("user");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//
//        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("name", "zhangsan");
//        sourceBuilder.query(termQueryBuilder);
//
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.preTags("<font color='red'>");
//        highlightBuilder.postTags("</font>");
//        highlightBuilder.field("name");
//        sourceBuilder.highlighter(highlightBuilder);
//
//        request.source(sourceBuilder);

        // 10. 聚合查询
        SearchRequest request = new SearchRequest();
        request.indices("user");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroupBy").field("age")
                .subAggregation(AggregationBuilders.sum("ageSum").field("age"));
        sourceBuilder.aggregation(aggregationBuilder);

        request.source(sourceBuilder);

        // 向ES发送请求并获取响应
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        // 打印响应结果
        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("isTimeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("maxSocre:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");

        esClient.close();
    }
}
