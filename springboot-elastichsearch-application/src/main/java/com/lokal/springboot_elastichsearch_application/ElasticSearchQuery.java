package com.lokal.springboot_elastichsearch_application;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.TopHits;
import org.elasticsearch.search.aggregations.metrics.TopHitsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ElasticSearchQuery {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private final String indexName = "lokals";


    public String createOrUpdateDocument(Lokal lokal) throws IOException {

        IndexResponse response = elasticsearchClient.index(i -> i
                .index(indexName)
                .id(lokal.getId())
                .document(lokal)
        );
        if (response.result().name().equals("Created")) {
            return new StringBuilder("Document has been successfully created.").toString();
        } else if (response.result().name().equals("Updated")) {
            return new StringBuilder("Document has been successfully updated.").toString();
        }
        return new StringBuilder("Error while performing the operation.").toString();
    }

    public Lokal getDocumentById(String lokalId) throws IOException {
        Lokal lokal = null;
        GetResponse<Lokal> response = elasticsearchClient.get(g -> g
                        .index(indexName)
                        .id(lokalId),
                Lokal.class
        );

        if (response.found()) {
            lokal = response.source();
            assert lokal != null;
            System.out.println("Lokal name " + lokal.getLocation_name());
        } else {
            System.out.println("Lokal not found");
        }

        return lokal;
    }

    public String deleteDocumentById(String lokalID) throws IOException {

        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(lokalID));

        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("Lokal with id " + deleteResponse.id() + " has been deleted.").toString();
        }
        System.out.println("Lokal not found");
        return new StringBuilder("Lokal with id " + deleteResponse.id() + " does not exist.").toString();

    }

    public List<Lokal> searchAllDocuments1(String word) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        List<Lokal> locations = new ArrayList<>();

        try {
            // Create the prefix query
            PrefixQueryBuilder prefixQuery = new PrefixQueryBuilder("location_name", word);

            // Create the boolean query
            BoolQueryBuilder boolQuery = new BoolQueryBuilder();
            boolQuery.filter(prefixQuery);

            // Create the top_hits aggregation
            TopHitsAggregationBuilder topHitsAggregation = AggregationBuilders
                    .topHits("top_location_hits")
                    .size(5);

            // Create the terms aggregation
            TermsAggregationBuilder termsAggregation = AggregationBuilders
                    .terms("location_name_suggestions")
                    .field("location_name.keyword")
                    .size(10)
                    .subAggregation(topHitsAggregation);

            // Build the search source
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(boolQuery);
            sourceBuilder.aggregation(termsAggregation);
            sourceBuilder.size(0);

            // Create the search request
            SearchRequest searchRequest = new SearchRequest("lokals");
            searchRequest.source(sourceBuilder);

            // Execute the search request
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);


            ObjectMapper mapper = new ObjectMapper();

            Terms terms = searchResponse.getAggregations().get("location_name_suggestions");
            for (Terms.Bucket bucket : terms.getBuckets()) {
                TopHits topHits = bucket.getAggregations().get("top_location_hits");
                for (SearchHit hit : topHits.getHits().getHits()) {
                    Lokal location = mapper.readValue(hit.getSourceAsString(), Lokal.class);
                    locations.add(location);
                }
            }

            // Process the response
            System.out.println(searchResponse);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the client
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return locations;
    }
}