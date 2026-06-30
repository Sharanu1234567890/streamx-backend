package com.streamx.watchhistory.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "watch_history")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WatchHistory {
    @Id
    private String id;
    private String userId;
    private String videoKey;
    private long watchedAt;
}