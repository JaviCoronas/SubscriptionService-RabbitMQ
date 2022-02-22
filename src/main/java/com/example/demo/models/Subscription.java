package com.example.demo.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
public class Subscription {
    private UUID id;

    private String name;

    private String email;


    public Subscription(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
