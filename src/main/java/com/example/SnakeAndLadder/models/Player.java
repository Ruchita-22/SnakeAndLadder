package com.example.SnakeAndLadder.models;

import lombok.Data;

import java.util.UUID;

@Data
public class Player {
    private String id;
    private String name;

    public Player(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
