package com.example.bitquest.model;

import java.io.Serializable;

public abstract class Hero implements Serializable {
    protected int id;
    protected String name;
    protected String heroClass;
    protected int energy;
    protected int maxEnergy;
    protected int skill;
    protected int experience;
    protected int killCount;

    public Hero(int id, String name, String heroClass, int maxEnergy, int skill) {
        this.id = id;
        this.name = name;
        this.heroClass = heroClass;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
        this.skill = skill;
        this.experience = 0;
        this.killCount = 0;
    }

    public abstract int attack();

    public void takeDamage(int damage) {
        energy -= damage;
        if (energy < 0) energy = 0;
    }

    public void heal(int amount) {
        energy += amount;
        if (energy > maxEnergy) energy = maxEnergy;
    }

    public void restoreFullEnergy() {
        energy = maxEnergy;
    }

    public void gainExperience(int xp) {
        experience += xp;
        if (experience >= 100) {
            experience -= 100;
            skill++;
        }
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public void incrementKillCount() {
        killCount++;
    }

    public void resetSkillOnDeath() {
        skill = 1;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getHeroClass() { return heroClass; }
    public int getEnergy() { return energy; }
    public int getMaxEnergy() { return maxEnergy; }
    public int getSkill() { return skill; }
    public int getExperience() { return experience; }
    public int getKillCount() { return killCount; }
}
