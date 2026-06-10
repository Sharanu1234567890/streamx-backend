package com.streamx.catalog.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

public class MovieDtos {

    @Data
    public static class CreateMovieRequest {
        @NotBlank(message = "Title is required")
        private String title;
        private String description;
        @NotBlank(message = "Genre is required")
        private String genre;
        private String director;
        @NotNull
        private int releaseYear;
        private double rating;
        private String thumbnailUrl;
        private List<String> cast;
    }

    @Data
    public static class MovieResponse {
        private String id;
        private String title;
        private String description;
        private String genre;
        private String director;
        private int releaseYear;
        private double rating;
        private String thumbnailUrl;
        private List<String> cast;
    }
}
