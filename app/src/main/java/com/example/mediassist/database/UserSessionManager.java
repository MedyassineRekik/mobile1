package com.example.mediassist.database;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_EMAIL = "email";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public UserSessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String username, String firstname, String email) {
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_FIRSTNAME, firstname);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getFirstname() {
        return sharedPreferences.getString(KEY_FIRSTNAME, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public boolean isLoggedIn() {
        return getUsername() != null;
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }

    // Add these new methods
    public User getUserDetails() {
        if (!isLoggedIn()) return null;

        return new User(
                getUsername(),
                getFirstname(),
                getEmail()
        );
    }

    // Simple User model class
    public static class User {
        public final String username;
        public final String firstname;
        public final String email;

        public User(String username, String firstname, String email) {
            this.username = username;
            this.firstname = firstname;
            this.email = email;
        }
    }
}