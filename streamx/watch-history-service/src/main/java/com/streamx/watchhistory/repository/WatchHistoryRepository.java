package com.streamx.watchhistory.repository;


import com.streamx.watchhistory.model.WatchHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WatchHistoryRepository extends MongoRepository<WatchHistory, String> {
    List<WatchHistory> findByUserId(String userId);
}