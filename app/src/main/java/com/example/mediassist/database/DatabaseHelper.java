package com.example.mediassist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MediAssist.db";
    private static final int DATABASE_VERSION = 4;

    // Table Users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_SALT = "salt";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FIRSTNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_USERNAME + " TEXT UNIQUE,"
                + COLUMN_SALT + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + "age TEXT,"
                + "gender TEXT,"
                + "bloodType TEXT,"
                + "weight TEXT,"
                + "height TEXT,"
                + "allergies TEXT,"
                + "phone TEXT,"
                + "address TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.encodeToString(salt, Base64.NO_WRAP);
    }

    private String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(Base64.decode(salt, Base64.NO_WRAP));
            byte[] hashedBytes = digest.digest(password.getBytes());
            return Base64.encodeToString(hashedBytes, Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hash error: " + e);
        }
    }

    public boolean addUser(String firstname, String email, String username, String password) {
        if (usernameExists(username)) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, firstname);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_SALT, salt);
        values.put(COLUMN_PASSWORD, hashedPassword);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();

        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_PASSWORD, COLUMN_SALT};
        String selection = COLUMN_USERNAME + " = ?";

        Cursor cursor = db.query(TABLE_USERS, columns, selection,
                new String[]{username}, null, null, null);

        if (cursor.moveToFirst()) {
            String storedHash = cursor.getString(0);
            String salt = cursor.getString(1);
            String inputHash = hashPassword(password, salt);

            cursor.close();
            db.close();
            return storedHash.equals(inputHash);
        }

        cursor.close();
        db.close();
        return false;
    }

    public boolean usernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean emailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Cursor getUserData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USERS,
                new String[]{COLUMN_ID, COLUMN_FIRSTNAME, COLUMN_EMAIL, COLUMN_USERNAME,
                        "age", "gender", "bloodType", "weight", "height",
                        "allergies", "phone", "address"},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);
    }



    public String getUserFirstName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_FIRSTNAME},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        if (cursor.moveToFirst()) {
            String firstName = cursor.getString(0);
            cursor.close();
            return firstName;
        }
        cursor.close();
        return null;
    }
    public boolean updateUserProfile(String username, String age, String gender, String bloodType,
                                     String weight, String height, String allergies,
                                     String phone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("age", age);
        values.put("gender", gender);
        values.put("bloodType", bloodType);
        values.put("weight", weight);
        values.put("height", height);
        values.put("allergies", allergies);
        values.put("phone", phone);
        values.put("address", address);

        int rowsAffected = db.update(TABLE_USERS, values, COLUMN_USERNAME + "=?",
                new String[]{username});
        db.close();
        Log.d("DB_UPDATE", "Rows affected: " + rowsAffected);

        return rowsAffected > 0;

    }


    public void logAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        while (cursor.moveToNext()) {
            Log.d("DB_DEBUG", "User: " + cursor.getString(3)); // username
        }
        cursor.close();
    }
}