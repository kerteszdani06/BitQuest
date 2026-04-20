package com.example.bitquest.logic;

import com.example.bitquest.model.Hero;
import com.example.bitquest.model.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatManager {

    private Hero lead;
    private Hero reserve1;
    private Hero reserve2;
    private Monster monster;
    private final Random random = new Random();
    private final List<Hero> defeatedHeroes = new ArrayList<>();

    private MissionType missionType;

    public CombatManager(Hero lead, Hero reserve1, Hero reserve2, int floor, MissionType missionType) {
        this.lead = lead;
        this.reserve1 = reserve1;
        this.reserve2 = reserve2;
        this.monster = Monster.generateMonster(floor);
        this.missionType = missionType;
    }

    public CombatManager(Hero lead, Hero partner, int floor) {
    }

    public String attack() {
        if (lead == null) return "No lead hero.";
        int bonus = missionType.getSkillBonus(lead);
        int dmg = lead.attack() + bonus + random.nextInt(4);
        monster.takeDamage(dmg);
        return lead.getName() + " dealt " + dmg + " damage.";
    }

    public String defend() {
        if (lead == null) return "No lead hero.";
        lead.defend();
        return lead.getName() + " is defending.";
    }

    public String useSpecial() {
        if (lead == null) return "No lead hero.";
        int bonus = missionType.getSkillBonus(lead);
        int dmg = lead.specialAbility() + bonus;
        if (dmg > 0) {
            monster.takeDamage(dmg);
            return lead.getName() + " used special ability for " + dmg + " damage.";
        }
        return lead.getName() + " used a support special ability.";
    }

    public String swapToReserve1() {
        if (reserve1 == null) return "No first reserve available.";
        Hero temp = lead;
        lead = reserve1;
        reserve1 = temp;
        return "Swapped with reserve 1.";
    }

    public String swapToReserve2() {
        if (reserve2 == null) return "No second reserve available.";
        Hero temp = lead;
        lead = reserve2;
        reserve2 = temp;
        return "Swapped with reserve 2.";
    }

    public String enemyTurn() {
        if (lead == null || !monster.isAlive()) return "No enemy action.";
        int damage = monster.attack();
        if (lead.getEnergy() < lead.getMaxEnergy() / 3) {
            damage += 3;
        }
        lead.takeDamage(damage);
        return monster.getName() + " attacks " + lead.getName() + " for " + damage + " damage.";
    }

    public String checkDeaths() {
        StringBuilder log = new StringBuilder();

        if (lead != null && !lead.isAlive()) {
            defeatedHeroes.add(lead);
            log.append(lead.getName()).append(" has fallen.\n");
            lead = getNextAvailableReserve();
            if (lead != null) {
                log.append(lead.getName()).append(" becomes the new Lead.\n");
            }
        }

        if (reserve1 != null && !reserve1.isAlive()) {
            defeatedHeroes.add(reserve1);
            log.append(reserve1.getName()).append(" has fallen.\n");
            reserve1 = null;
        }

        if (reserve2 != null && !reserve2.isAlive()) {
            defeatedHeroes.add(reserve2);
            log.append(reserve2.getName()).append(" has fallen.\n");
            reserve2 = null;
        }

        return log.toString();
    }

    private Hero getNextAvailableReserve() {
        if (reserve1 != null && reserve1.isAlive()) {
            Hero next = reserve1;
            reserve1 = reserve2;
            reserve2 = null;
            return next;
        }
        if (reserve2 != null && reserve2.isAlive()) {
            Hero next = reserve2;
            reserve2 = null;
            return next;
        }
        return null;
    }

    public boolean isVictory() {
        return !monster.isAlive();
    }

    public boolean isGameOver() {
        return lead == null && reserve1 == null && reserve2 == null;
    }

    public Hero getLead() { return lead; }
    public Hero getReserve1() { return reserve1; }
    public Hero getReserve2() { return reserve2; }
    public Monster getMonster() { return monster; }
    public List<Hero> getDefeatedHeroes() { return defeatedHeroes; }

    public void clearDefeatedHeroes() {
        defeatedHeroes.clear();
    }

    public List<Hero> getAllHeroes() {
        List<Hero> list = new ArrayList<>();
        if (lead != null) list.add(lead);
        if (reserve1 != null) list.add(reserve1);
        if (reserve2 != null) list.add(reserve2);
        return list;
    }
}
