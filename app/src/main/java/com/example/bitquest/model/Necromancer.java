package com.example.bitquest.model;

public class Necromancer extends Hero {
    public Necromancer(int id, String name) {
        super(id, name, "Necromancer", 80, 11);
    }

    @Override
    public int attack() {
        return skill + 1;
    }
}
