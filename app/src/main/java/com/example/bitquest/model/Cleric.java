package com.example.bitquest.model;

public class Cleric extends Hero {
    public Cleric(int id, String name) {
        super(id, name, "Cleric", 100, 7);
    }

    @Override
    public int attack() {
        heal(5); // support mechanic
        return skill;
    }
}
