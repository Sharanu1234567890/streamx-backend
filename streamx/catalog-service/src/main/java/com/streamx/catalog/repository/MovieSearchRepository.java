package com.streamx.catalog.repository;


import com.streamx.catalog.model.MovieDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieSearchRepository extends ElasticsearchRepository<MovieDocument, String> {
    List<MovieDocument> findByTitleContainingOrDescriptionContainingOrDirectorContaining(
            String title, String description, String director
    );
}