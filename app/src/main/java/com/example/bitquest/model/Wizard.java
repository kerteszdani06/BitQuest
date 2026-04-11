package com.example.bitquest.model;

public class Wizard extends Hero {
    public Wizard(int id, String name) {
        super(id, name, "Wizard", 70, 14);
    }

    @Override
    public int attack() {
        return skill + 5;
    }
}
