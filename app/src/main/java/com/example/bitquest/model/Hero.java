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

    protected int missionsPlayed;
    protected int victories;
    protected int trainingSessions;
    protected int defeats;
    protected int lostMissions;

    protected boolean defending;
    protected boolean inMedbay;

    // Initial stats
    protected int initialMaxEnergy;
    protected int initialSkill;

    public Hero(int id, String name, String heroClass, int maxEnergy, int skill) {
        this.id = id;
        this.name = name;
        this.heroClass = heroClass;

        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
        this.skill = skill;

        this.initialMaxEnergy = maxEnergy;
        this.initialSkill = skill;

        this.experience = 0;
        this.killCount = 0;

        this.missionsPlayed = 0;
        this.victories = 0;
        this.trainingSessions = 0;
        this.defeats = 0;
        this.lostMissions = 0;

        this.defending = false;
        this.inMedbay = false;
    }

    public abstract int attack();
    public abstract int specialAbility();
    public abstract int getImageResId();

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

    public void recordLostMission() {
        lostMissions++;
    }

    public void sendToMedbay() {
        inMedbay = true;
        energy = initialMaxEnergy;
        maxEnergy = initialMaxEnergy;
        skill = Math.max(1, initialSkill - 1); // simple penalty
        experience = 0; // optional penalty
        defending = false;
    }

    public void releaseFromMedbay() {
        inMedbay = false;
        restoreFullEnergy();
    }

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
    public int getLostMissions() { return lostMissions; }
    public boolean isInMedbay() { return inMedbay; }
    public boolean isDefending() { return defending; }
}
