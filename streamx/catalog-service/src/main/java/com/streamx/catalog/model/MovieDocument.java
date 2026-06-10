package com.streamx.catalog.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "movies")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MovieDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String genre;

    @Field(type = FieldType.Text)
    private String director;

    @Field(type = FieldType.Integer)
    private int releaseYear;

    @Field(type = FieldType.Double)
    private double rating;

    @Field(type = FieldType.Text)
    private List<String> cast;
}
