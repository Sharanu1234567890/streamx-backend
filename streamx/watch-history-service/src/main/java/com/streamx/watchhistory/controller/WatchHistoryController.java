package com.streamx.watchhistory.controller;

import com.streamx.watchhistory.model.WatchHistory;
import com.streamx.watchhistory.repository.WatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class WatchHistoryController {

    private final WatchHistoryRepository repository;

    @GetMapping("/{userId}")
    public List<WatchHistory> getHistory(@PathVariable String userId) {
        return repository.findByUserId(userId);
    }
}