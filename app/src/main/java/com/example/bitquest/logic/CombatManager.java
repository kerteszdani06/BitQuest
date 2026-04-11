package com.example.bitquest.logic;

import com.example.bitquest.model.Hero;
import com.example.bitquest.model.Monster;

import java.util.Random;

public class CombatManager {

    private Hero lead;
    private Hero partner;
    private Monster monster;
    private int floor;
    private Random random = new Random();

    public CombatManager(Hero lead, Hero partner, int floor) {
        this.lead = lead;
        this.partner = partner;
        this.floor = floor;
        this.monster = Monster.generateMonster(floor);
    }

    public String attack() {
        int dmg = lead.attack() + random.nextInt(4);
        monster.takeDamage(dmg);
        return lead.getName() + " dealt " + dmg;
    }

    public String swap() {
        Hero temp = lead;
        lead = partner;
        partner = temp;
        return "Swapped!";
    }

    public String enemyTurn() {
        int dmg = monster.attack();
        lead.takeDamage(dmg);
        return monster.getName() + " hit " + lead.getName() + " for " + dmg;
    }

    public void checkDeath() {
        if (!lead.isAlive() && partner != null && partner.isAlive()) {
            lead = partner;
            partner = null;
        }
    }

    public boolean isVictory() {
        return !monster.isAlive();
    }

    public boolean isGameOver() {
        return (lead == null || !lead.isAlive()) &&
                (partner == null || !partner.isAlive());
    }

    public Monster getMonster() { return monster; }
    public Hero getLead() { return lead; }
    public Hero getPartner() { return partner; }
}
