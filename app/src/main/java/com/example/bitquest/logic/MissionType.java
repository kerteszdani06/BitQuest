package com.example.bitquest.logic;

import com.example.bitquest.model.Hero;

public enum MissionType {
    COMBAT,
    REPAIR_STATION,
    HEALING_RUINS,
    ARCANE_TOWER;

    public int getSkillBonus(Hero hero) {
        String type = hero.getHeroClass();

        switch (this) {
            case REPAIR_STATION:
                if (type.equals("Necromancer")) return 2;
                break;

            case HEALING_RUINS:
                if (type.equals("Cleric")) return 2;
                break;

            case ARCANE_TOWER:
                if (type.equals("Wizard")) return 2;
                break;

            case COMBAT:
                if (type.equals("Knight")) return 2;
                break;
        }

        return 0;
    }
}
