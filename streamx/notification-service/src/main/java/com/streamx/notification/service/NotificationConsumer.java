package com.streamx.notification.service;


import com.streamx.notification.model.WatchedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "user.watched", groupId = "notification-group")
    public void consume(WatchedEvent event) {
        log.info("Sending notification for user: {}", event.getUserId());

        messagingTemplate.convertAndSend(
                "/topic/notifications/" + event.getUserId(),
                Map.of(
                        "type", "VIDEO_WATCHED",
                        "userId", event.getUserId(),
                        "videoKey", event.getVideoKey(),
                        "message", "You just watched a video!"
                )
        );
    }
}
