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

    // New stats
    protected int missionsPlayed;
    protected int victories;
    protected int trainingSessions;
    protected int defeats;
    protected boolean defending;

    public Hero(int id, String name, String heroClass, int maxEnergy, int skill) {
        this.id = id;
        this.name = name;
        this.heroClass = heroClass;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
        this.skill = skill;
        this.experience = 0;
        this.killCount = 0;

        this.missionsPlayed = 0;
        this.victories = 0;
        this.trainingSessions = 0;
        this.defeats = 0;
        this.defending = false;
    }

    public abstract int attack();
    public abstract int specialAbility();

    public void takeDamage(int damage) {
        if (defending) {
            damage = Math.max(0, damage / 2);
            defending = false;
        }

        energy -= damage;
        if (energy < 0) energy = 0;
    }

    public void heal(int amount) {
        energy += amount;
        if (energy > maxEnergy) energy = maxEnergy;
    }

    public void restoreFullEnergy() {
        energy = maxEnergy;
        defending = false;
    }

    public void gainExperience(int xp) {
        experience += xp;
        while (experience >= 100) {
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

    public void defend() {
        defending = true;
    }

    public boolean isDefending() {
        return defending;
    }

    public void recordMission() {
        missionsPlayed++;
    }

    public void recordVictory() {
        victories++;
    }

    public void recordTrainingSession() {
        trainingSessions++;
    }

    public void recordDefeat() {
        defeats++;
    }

    public abstract int getImageResId();

    public int getId() { return id; }
    public String getName() { return name; }
    public String getHeroClass() { return heroClass; }
    public int getEnergy() { return energy; }
    public int getMaxEnergy() { return maxEnergy; }
    public int getSkill() { return skill; }
    public int getExperience() { return experience; }
    public int getKillCount() { return killCount; }
    public int getMissionsPlayed() { return missionsPlayed; }
    public int getVictories() { return victories; }
    public int getTrainingSessions() { return trainingSessions; }
    public int getDefeats() { return defeats; }
}
