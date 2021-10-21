package com.example.demo;

import lombok.Data;

import java.util.UUID;

@Data
public class ApiKey {

    UUID apiKey;

    public ApiKey() {
        this.apiKey = UUID.randomUUID();
    }
}
