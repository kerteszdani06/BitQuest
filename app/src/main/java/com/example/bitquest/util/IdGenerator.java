package com.example.bitquest.util;

import android.content.Context;
import android.content.SharedPreferences;

public class IdGenerator {

    private static final String PREF_NAME = "bitquest_id_prefs";
    private static final String KEY_NEXT_ID = "next_id";

    public static int getNextId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int nextId = prefs.getInt(KEY_NEXT_ID, 1);

        prefs.edit().putInt(KEY_NEXT_ID, nextId + 1).apply();

        return nextId;
    }

    public static void reset(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_NEXT_ID, 1).apply();
    }
}
