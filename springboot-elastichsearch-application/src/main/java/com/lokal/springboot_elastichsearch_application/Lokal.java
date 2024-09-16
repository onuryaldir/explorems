package com.lokal.springboot_elastichsearch_application;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
@Getter
@Setter
@Document(indexName = "location_id")
public class Lokal {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "location_code")
    private String  location_code;

    @Field(type = FieldType.Text, name = "location_name")
    private String  location_name;

    @Field(type = FieldType.Text, name = "location_name_eng")
    private String location_name_eng;

    @Field(type = FieldType.Double, name = "latitude")
    private double latitude;

    @Field(type = FieldType.Double, name = "longitude")
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

    @Field(type = FieldType.Text, name = "g_comment")
    private String g_comment;

    @Field(type = FieldType.Double, name = "score")
    private double score;

    @Field(type = FieldType.Long, name = "indicator")
    private long indicator;

    @Field(type = FieldType.Boolean, name = "is_main")
    private boolean is_main;

    @Field(type = FieldType.Boolean, name = "is_conjunction_point")
    private boolean is_conjunction_point;

    @Field(type = FieldType.Boolean, name = "is_young_deleted")
    private boolean is_young_deleted;

    @Field(type = FieldType.Boolean, name = "is_boolean_deleted")
    private boolean is_boolean_deleted;

    @Field(type = FieldType.Boolean, name = "is_middle_deleted")
    private boolean is_middle_deleted;

    @Field(type = FieldType.Boolean, name = "is_food")
    private boolean is_food;

    @Field(type = FieldType.Boolean, name = "is_hidden_place")
    private boolean is_hidden_place;

    @Field(type = FieldType.Text, name = "description")
    private String description;


}