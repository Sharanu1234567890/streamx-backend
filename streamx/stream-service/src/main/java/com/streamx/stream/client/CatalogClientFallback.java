package com.streamx.stream.client;


import com.streamx.stream.controller.CatalogClient;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class CatalogClientFallback implements CatalogClient {

    @Override
    public Map<String, Object> getMovie(String id) {
        return Map.of(
                "id", id,
                "title", "Unknown (Catalog Service unavailable)",
                "fallback", true
        );
    }
}