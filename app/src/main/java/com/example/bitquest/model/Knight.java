package com.example.bitquest.model;

public class Knight extends Hero {
    public Knight(int id, String name) {
        super(id, name, "Knight", 120, 8);
    }

    @Override
    public int attack() {
        return skill + 2;
    }
}
