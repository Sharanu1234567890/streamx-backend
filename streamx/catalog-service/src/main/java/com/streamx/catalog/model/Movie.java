package com.streamx.catalog.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "movies")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Movie {

    @Id
    private String id;
    private String title;
    private String description;
    private String genre;
    private String director;
    private int releaseYear;
    private double rating;
    private String thumbnailUrl;
    private String videoKey;      // MinIO key — added Week 2
    private List<String> cast;
    private LocalDateTime createdAt;
}