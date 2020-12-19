package com.example.century22.preferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public abstract class AppPreferences {

    private static final String LOGIN_PREFERENCES_KEY = "loginPreferencesKey";

    /* Method to store the Agent login in preferences*/
    public static void saveAgentLogin(@NonNull Context context, @NonNull String name)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final Editor editor = defaultSharedPreferences.edit();
        editor.putString(AppPreferences.LOGIN_PREFERENCES_KEY, name);
        editor.apply();
    }

    /* Method to get Agent name from preferences*/
    @Nullable
    public static String getAgentName(@NonNull Context context)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getString(AppPreferences.LOGIN_PREFERENCES_KEY, null);
    }

    /* Method to remove the Agent login in preferences*/
    public static void removeLogin(@NonNull Context context)
    {
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final Editor editor = defaultSharedPreferences.edit();
        editor.remove(AppPreferences.LOGIN_PREFERENCES_KEY);
        editor.apply();

    }

    private AppPreferences() {

    }
}
