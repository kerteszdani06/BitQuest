package com.example.bitquest.model;

public class Archer extends Hero {
    public Archer(int id, String name) {
        super(id, name, "Archer", 90, 10);
    }

    @Override
    public int attack() {
        return skill + 3;
    }
}
