package com.example.bitquest.logic;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bitquest.model.GuildArchive;
import com.example.bitquest.model.Hero;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class SaveManager {

    private static final String PREF = "bitquest";
    private static final String KEY = "archive";
    private static final String FILE_NAME = "crew_data.json";

    private static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Hero.class, new HeroTypeAdapter())
                .create();
    }

    // Existing auto-save using SharedPreferences
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

    // New: save to file
    public static void saveToFile(Context context, GuildArchive archive) {
        try {
            String json = getGson().toJson(archive);
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // New: load from file
    public static GuildArchive loadFromFile(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            reader.close();
            fis.close();

            String json = builder.toString();

            if (json.isEmpty()) {
                return new GuildArchive();
            }

            return getGson().fromJson(json, GuildArchive.class);

        } catch (Exception e) {
            e.printStackTrace();
            return new GuildArchive();
        }
    }
}
