package com.example.bitquest.model;

import com.example.bitquest.R;

public class Archer extends Hero {
    public Archer(int id, String name) {
        super(id, name, "Archer", 90, 10);
    }

    @Override
    public int attack() {
        return skill + 3;
    }

    @Override
    public int specialAbility() {
        return skill + 6;
    }

    @Override
    public int getImageResId() {
        return R.drawable.archer;
    }
}
