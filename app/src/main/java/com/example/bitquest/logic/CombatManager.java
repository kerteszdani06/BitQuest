package com.example.bitquest.logic;

import com.example.bitquest.model.Hero;
import com.example.bitquest.model.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatManager {

    private Hero lead;
    private Hero partner;
    private Monster monster;
    private int floor;
    private final Random random = new Random();
    private final List<Hero> defeatedHeroes = new ArrayList<>();

    public CombatManager(Hero lead, Hero partner, int floor) {
        this.lead = lead;
        this.partner = partner;
        this.floor = floor;
        this.monster = Monster.generateMonster(floor);
    }

    public String attack() {
        int dmg = lead.attack() + random.nextInt(4);
        monster.takeDamage(dmg);
        return lead.getName() + " dealt " + dmg + " damage.";
    }

    public String defend() {
        lead.defend();
        return lead.getName() + " is defending and will take reduced damage.";
    }

    public String useSpecial() {
        int dmg = lead.specialAbility();
        if (dmg > 0) {
            monster.takeDamage(dmg);
            return lead.getName() + " used a special ability for " + dmg + " damage.";
        } else {
            return lead.getName() + " used a special support ability.";
        }
    }

    public String swap() {
        if (partner == null) {
            return "No partner available to swap.";
        }

        Hero temp = lead;
        lead = partner;
        partner = temp;
        return "Swapped! " + lead.getName() + " is now the Lead.";
    }

    public String enemyTurn() {
        if (!monster.isAlive() || lead == null) {
            return "No enemy action.";
        }

        int damage = monsterAiDamage();
        lead.takeDamage(damage);
        return monster.getName() + " attacks " + lead.getName() + " for " + damage + " damage.";
    }

    private int monsterAiDamage() {
        int base = monster.attack();

        if (lead != null && lead.getEnergy() < (lead.getMaxEnergy() / 3)) {
            return base + 3;
        }

        return base;
    }

    public String checkDeaths() {
        StringBuilder log = new StringBuilder();

        if (lead != null && !lead.isAlive()) {
            defeatedHeroes.add(lead);
            log.append(lead.getName()).append(" has fallen.\n");

            if (partner != null && partner.isAlive()) {
                lead = partner;
                partner = null;
                log.append(lead.getName()).append(" becomes the new Lead.\n");
            } else {
                lead = null;
            }
        }

        if (partner != null && !partner.isAlive()) {
            defeatedHeroes.add(partner);
            log.append(partner.getName()).append(" has fallen.\n");
            partner = null;
        }

        return log.toString();
    }

    public boolean isVictory() {
        return !monster.isAlive();
    }

    public boolean isGameOver() {
        return lead == null && partner == null;
    }

    public Monster getMonster() { return monster; }
    public Hero getLead() { return lead; }
    public Hero getPartner() { return partner; }
    public List<Hero> getDefeatedHeroes() { return defeatedHeroes; }

    public void clearDefeatedHeroes() {
        defeatedHeroes.clear();
    }
}
