package com.example.bitquest.model;

import com.example.bitquest.R;

public class Cleric extends Hero {
    public Cleric(int id, String name) {
        super(id, name, "Cleric", 100, 7);
    }

    @Override
    public int attack() {
        heal(5);
        return skill;
    }

    @Override
    public int specialAbility() {
        heal(20);
        return 0;
    }

    @Override
    public int getImageResId() {
        return R.drawable.cleric;
    }
}
