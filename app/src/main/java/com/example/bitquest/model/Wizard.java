package com.example.bitquest.model;

import com.example.bitquest.R;

public class Wizard extends Hero {
    public Wizard(int id, String name) {
        super(id, name, "Wizard", 70, 14);
    }

    @Override
    public int attack() {
        return skill + 5;
    }

    @Override
    public int specialAbility() {
        return skill + 10;
    }

    @Override
    public int getImageResId() {
        return R.drawable.wizard;
    }
}
