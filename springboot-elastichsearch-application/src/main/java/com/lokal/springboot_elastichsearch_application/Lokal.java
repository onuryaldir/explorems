package com.lokal.springboot_elastichsearch_application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
@Getter
@Setter
@Document(indexName = "istanbul")
@JsonIgnoreProperties(ignoreUnknown = true) // Optional, if you want to ignore other unknown fields
public class Lokal {

    @Id
    @JsonIgnore
    private String id;

    @Field(type = FieldType.Text, name = "location_id")
    private String location_id;

    @Field(type = FieldType.Text, name = "location_name")
    private String location_name;

    @Field(type = FieldType.Text, name = "location_name_eng")
    private String location_name_eng;

    @Field(type = FieldType.Double, name = "lat")
    private double lat;

    @Field(type = FieldType.Double, name = "long") // or `longitude` if you prefer
    @JsonProperty("long") // Maps the JSON field "long" to this field
    private double longitude;

    @Field(type = FieldType.Text, name = "definition")
    private String definition;

    @Field(type = FieldType.Text, name = "primary_theme")
    private String primary_theme;

    @Field(type = FieldType.Text, name = "secondary_theme")
    private String secondary_theme;

    @Field(type = FieldType.Text, name = "theme1")
    private String theme1;

    @Field(type = FieldType.Text, name = "theme2")
    private String theme2;

    @Field(type = FieldType.Double, name = "score")
    private double score;

    @Field(type = FieldType.Long, name = "indicator")
    private long indicator;

    @Field(type = FieldType.Boolean, name = "is_main")
    private int is_main;

    @Field(type = FieldType.Boolean, name = "is_conjunction_point")
    private int is_conjunction_point;

    @Field(type = FieldType.Boolean, name = "is_young_deleted")
    private int is_young_deleted;

    @Field(type = FieldType.Boolean, name = "is_boolean_deleted")
    private int is_boolean_deleted;

    @Field(type = FieldType.Boolean, name = "is_middle_deleted")
    private int is_middle_deleted;

    @Field(type = FieldType.Boolean, name = "is_food")
    private int is_food;

    @Field(type = FieldType.Boolean, name = "is_hidden_place")
    private int is_hidden_place;

    @Field(type = FieldType.Text, name = "description")
    private String description;

    @Field(type = FieldType.Text, name = "region")
    private String region;

    @Field(type = FieldType.Integer, name = "region_id")
    private int region_id;

    @Field(type = FieldType.Integer, name = "review")
    private int review;

}