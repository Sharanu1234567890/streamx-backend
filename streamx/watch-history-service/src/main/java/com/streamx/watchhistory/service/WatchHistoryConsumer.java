package com.streamx.watchhistory.service;


import com.streamx.watchhistory.model.WatchHistory;
import com.streamx.watchhistory.model.WatchedEvent;
import com.streamx.watchhistory.repository.WatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WatchHistoryConsumer {

    private final WatchHistoryRepository repository;

    @KafkaListener(topics = "user.watched", groupId = "watch-history-group")
    public void consume(WatchedEvent event) {
        log.info("Consumed user.watched event: {}", event);

        WatchHistory history = WatchHistory.builder()
                .userId(event.getUserId())
                .videoKey(event.getVideoKey())
                .watchedAt(event.getWatchedAt())
                .build();

        repository.save(history);
        log.info("Saved watch history for user: {}", event.getUserId());
    }
}