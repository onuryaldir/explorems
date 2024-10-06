package com.lokal.springboot_elastichsearch_application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class ElasticSearchController {

    @Autowired
    private ElasticSearchQuery elasticSearchQuery;

    @PostMapping("/createOrUpdateDocument")
    public ResponseEntity<Object> createOrUpdateDocument(@RequestBody Lokal lokal) throws IOException {
        String response = elasticSearchQuery.createOrUpdateDocument(lokal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getDocument")
    public ResponseEntity<Object> getDocumentById(@RequestParam String lokalId) throws IOException {
        Lokal lokal =  elasticSearchQuery.getDocumentById(lokalId);
        return new ResponseEntity<>(lokal, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDocument")
    public ResponseEntity<Object> deleteDocumentById(@RequestParam String lokalId) throws IOException {
        String response =  elasticSearchQuery.deleteDocumentById(lokalId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/searchDocumentByKey")
    public ResponseEntity<Object> createOrUpdateDocument(@RequestParam String word) throws IOException {
        List<Lokal> lokals = elasticSearchQuery.searchAllDocuments1(word);
        return new ResponseEntity<>(lokals, HttpStatus.OK);
    }
}