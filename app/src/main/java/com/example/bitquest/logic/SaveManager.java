package com.example.bitquest.logic;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bitquest.model.GuildArchive;
import com.example.bitquest.model.Hero;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SaveManager {

    private static final String PREF = "bitquest";
    private static final String KEY = "archive";

    private static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Hero.class, new HeroTypeAdapter())
                .create();
    }

    public static void save(Context context, GuildArchive archive) {
        SharedPreferences prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String json = getGson().toJson(archive);
        editor.putString(KEY, json);
        editor.apply();
    }

    public static GuildArchive load(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY, null);

        if (json == null || json.isEmpty()) {
            return new GuildArchive();
        }

        try {
            return getGson().fromJson(json, GuildArchive.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new GuildArchive();
        }
    }

    public static void clear(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}
