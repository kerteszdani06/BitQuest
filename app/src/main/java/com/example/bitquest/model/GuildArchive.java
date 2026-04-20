package com.example.bitquest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuildArchive implements Serializable {
    private HashMap<Integer, Hero> heroMap = new HashMap<>();

    public void addHero(Hero hero) {
        heroMap.put(hero.getId(), hero);
    }

    public Hero getHero(int id) {
        return heroMap.get(id);
    }

    public List<Hero> getAllHeroes() {
        return new ArrayList<>(heroMap.values());
    }

    public List<Hero> getAvailableHeroes() {
        List<Hero> available = new ArrayList<>();
        for (Hero hero : heroMap.values()) {
            if (!hero.isInMedbay()) {
                available.add(hero);
            }
        }
        return available;
    }

    public List<Hero> getMedbayHeroes() {
        List<Hero> medbay = new ArrayList<>();
        for (Hero hero : heroMap.values()) {
            if (hero.isInMedbay()) {
                medbay.add(hero);
            }
        }
        return medbay;
    }

    public void restoreAllAvailableHeroesEnergy() {
        for (Hero hero : heroMap.values()) {
            if (!hero.isInMedbay()) {
                hero.restoreFullEnergy();
            }
        }
    }

    public void releaseAllFromMedbay() {
        for (Hero hero : heroMap.values()) {
            if (hero.isInMedbay()) {
                hero.releaseFromMedbay();
            }
        }
    }

    public int size() {
        return heroMap.size();
    }

    public boolean isEmpty() {
        return heroMap.isEmpty();
    }
}
