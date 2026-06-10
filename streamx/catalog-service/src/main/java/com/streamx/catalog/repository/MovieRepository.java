package com.streamx.catalog.repository;


import com.streamx.catalog.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByGenre(String genre);
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByReleaseYear(int year);
}