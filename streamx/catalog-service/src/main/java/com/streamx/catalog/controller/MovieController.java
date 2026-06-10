package com.streamx.catalog.controller;

import com.streamx.catalog.dto.MovieDtos.*;
import com.streamx.catalog.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
@Tag(name = "Catalog", description = "Movie catalog management")
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/movies")
    @Operation(summary = "Add a new movie")
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody CreateMovieRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createMovie(request));
    }

    @GetMapping("/movies")
    @Operation(summary = "Get all movies")
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/movies/{id}")
    @Operation(summary = "Get movie by ID")
    public ResponseEntity<MovieResponse> getMovie(@PathVariable String id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping("/movies/genre/{genre}")
    @Operation(summary = "Get movies by genre")
    public ResponseEntity<List<MovieResponse>> getByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(movieService.getByGenre(genre));
    }

    @DeleteMapping("/movies/{id}")
    @Operation(summary = "Delete a movie")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}