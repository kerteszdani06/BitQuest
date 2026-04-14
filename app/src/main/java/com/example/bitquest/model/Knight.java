package com.example.bitquest.model;

import com.example.bitquest.R;

public class Knight extends Hero {
    public Knight(int id, String name) {
        super(id, name, "Knight", 120, 8);
    }

    @Override
    public int attack() {
        return skill + 2;
    }

    @Override
    public int specialAbility() {
        heal(10);
        return skill + 1;
    }

    @Override
    public int getImageResId() {
        return R.drawable.knight;
    }
}
