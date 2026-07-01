package com.streamx.notification.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchedEvent {
    private String userId;
    private String videoKey;
    private long watchedAt;
}