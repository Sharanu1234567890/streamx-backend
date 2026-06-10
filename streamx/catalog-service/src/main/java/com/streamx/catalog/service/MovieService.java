package com.streamx.catalog.service;


import com.streamx.catalog.dto.MovieDtos.*;
import com.streamx.catalog.model.Movie;
import com.streamx.catalog.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieResponse createMovie(CreateMovieRequest request) {
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .genre(request.getGenre())
                .director(request.getDirector())
                .releaseYear(request.getReleaseYear())
                .rating(request.getRating())
                .thumbnailUrl(request.getThumbnailUrl())
                .cast(request.getCast())
                .createdAt(LocalDateTime.now())
                .build();
        return toResponse(movieRepository.save(movie));
    }

    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll()
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    public MovieResponse getMovieById(String id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found: " + id));
        return toResponse(movie);
    }

    public List<MovieResponse> getByGenre(String genre) {
        return movieRepository.findByGenre(genre)
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteMovie(String id) {
        movieRepository.deleteById(id);
    }

    private MovieResponse toResponse(Movie movie) {
        MovieResponse r = new MovieResponse();
        r.setId(movie.getId());
        r.setTitle(movie.getTitle());
        r.setDescription(movie.getDescription());
        r.setGenre(movie.getGenre());
        r.setDirector(movie.getDirector());
        r.setReleaseYear(movie.getReleaseYear());
        r.setRating(movie.getRating());
        r.setThumbnailUrl(movie.getThumbnailUrl());
        r.setCast(movie.getCast());
        return r;
    }
}