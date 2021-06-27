package org.zerobs.polla.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RestConsumer {
    <T> T get(String path, Map<String, Object> queryParams);

    <T> ResponseEntity<T> getResponseEntity(String path, Map<String, Object> queryParams, HttpStatus... ignoreStatuses);
}
