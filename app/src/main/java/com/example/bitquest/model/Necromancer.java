package com.example.bitquest.model;

import com.example.bitquest.R;

public class Necromancer extends Hero {
    public Necromancer(int id, String name) {
        super(id, name, "Necromancer", 80, 11);
    }

    @Override
    public int attack() {
        return skill + 1;
    }

    @Override
    public int specialAbility() {
        heal(8);
        return skill + 4;
    }

    @Override
    public int getImageResId() {
        return R.drawable.necromancer;
    }
}
