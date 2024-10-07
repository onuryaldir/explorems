package com.lokal.springboot_elastichsearch_application;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;

import co.elastic.clients.elasticsearch.core.search.Hit;
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

    private final String indexName = "istanbul";


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

    public List<Lokal> searchAllDocuments(String word) throws IOException {
        // Create the ElasticsearchClient instance


        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(indexName)
                .query(q -> q
                        .prefix(p -> p
                                .field("location_name")
                                .value(word)
                                .caseInsensitive(true)
                        )
                )
                .build();

        SearchResponse<Lokal> response = elasticsearchClient.search(searchRequest, Lokal.class);

        List<Lokal> lokalList = new ArrayList<>();
        for (Hit<Lokal> hit : response.hits().hits()) {
            Lokal lokal = hit.source();
            if (lokal != null) {
                lokalList.add(lokal);
            }
        }
        // Close the client

        return lokalList;

    }
    public  List<Lokal> getAllDocuments() throws IOException {
        // Initialize Elasticsearch client

        // Build the search request to get all documents in the index
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index(indexName)
                .query(q -> q
                        .matchAll(m -> m)  // Use match_all query to get all documents
                ).size(10000)
                .build();

        // Execute the search request
        SearchResponse<Lokal> response = elasticsearchClient.search(searchRequest, Lokal.class);

        // Parse and map the response to a list of Lokal objects
        List<Lokal> lokalList = new ArrayList<>();
        for (Hit<Lokal> hit : response.hits().hits()) {
            Lokal lokal = hit.source();
            if (lokal != null) {
                lokalList.add(lokal);
            }
        }

        // Close the client

        // Return the list of documents
        return lokalList;
    }

}