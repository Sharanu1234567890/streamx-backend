package com.streamx.stream.controller;


import com.streamx.stream.client.CatalogClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;

@FeignClient(name = "catalog-service", fallback = CatalogClientFallback.class)
public interface CatalogClient {
    @GetMapping("/api/v1/catalog/movies/{id}")
    Map<String, Object> getMovie(@PathVariable String id);
}