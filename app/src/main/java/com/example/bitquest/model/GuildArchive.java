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

    public void removeHero(int id) {
        heroMap.remove(id);
    }

    public Hero getHero(int id) {
        return heroMap.get(id);
    }

    public List<Hero> getAllHeroes() {
        return new ArrayList<>(heroMap.values());
    }

    public boolean isEmpty() {
        return heroMap.isEmpty();
    }

    public int size() {
        return heroMap.size();
    }
}
