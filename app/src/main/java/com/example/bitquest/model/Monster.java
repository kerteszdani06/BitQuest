package com.example.bitquest.model;

import java.io.Serializable;
import java.util.Random;

public class Monster implements Serializable {
    private String name;
    private int energy;
    private int maxEnergy;
    private int damage;

    public Monster(String name, int maxEnergy, int damage) {
        this.name = name;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
        this.damage = damage;
    }

    public static Monster generateMonster(int floor) {
        int hp = 40 + (floor * 10);
        int dmg = 5 + (floor * 2);
        return new Monster("Monster " + floor, hp, dmg);
    }

    public int attack() {
        return damage + new Random().nextInt(4);
    }

    public void takeDamage(int dmg) {
        energy -= dmg;
        if (energy < 0) energy = 0;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public String getName() { return name; }
    public int getEnergy() { return energy; }
    public int getMaxEnergy() { return maxEnergy; }
}
