package com.streamx.catalog.service;

import com.streamx.catalog.dto.MovieDtos.*;
import com.streamx.catalog.model.Movie;
import com.streamx.catalog.model.MovieDocument;
import com.streamx.catalog.repository.MovieRepository;
import com.streamx.catalog.repository.MovieSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieSearchRepository movieSearchRepository;

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

        Movie saved = movieRepository.save(movie);

        MovieDocument doc = MovieDocument.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .genre(saved.getGenre())
                .director(saved.getDirector())
                .releaseYear(saved.getReleaseYear())
                .rating(saved.getRating())
                .cast(saved.getCast())
                .build();
        movieSearchRepository.save(doc);

        return toResponse(saved);
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

    public List<MovieResponse> search(String query) {
        return movieSearchRepository
                .findByTitleContainingOrDescriptionContainingOrDirectorContaining(query, query, query)
                .stream()
                .map(doc -> {
                    MovieResponse r = new MovieResponse();
                    r.setId(doc.getId());
                    r.setTitle(doc.getTitle());
                    r.setDescription(doc.getDescription());
                    r.setGenre(doc.getGenre());
                    r.setDirector(doc.getDirector());
                    r.setReleaseYear(doc.getReleaseYear());
                    r.setRating(doc.getRating());
                    r.setCast(doc.getCast());
                    return r;
                })
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