package com.example.pftfinalprojectv1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UsersDb extends SQLiteOpenHelper {
    public UsersDb(Context context) {
        super(context, "users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String accountsTable = "CREATE TABLE accounts (_id INTEGER PRIMARY KEY AUTOINCREMENT, employee_id TEXT, full_name TEXT, email TEXT, password TEXT)";
        db.execSQL(accountsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(String employee_id, String full_name, String email, String password) {

        Log.v("employeeId in DB call:", employee_id);
        Log.v("fullName in DB call:", full_name);
        Log.v("password in DB call:", password);
        Log.v("email in DB call:", email);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("employee_id", employee_id);
        values.put("full_name", full_name);
        values.put("email", email);
        values.put("password", password);
        Log.v("values: ", String.valueOf(values));
        db.insert("accounts", null, values);

    }
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM accounts ", null);
    }

    public Cursor findUser(String employeeId, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM accounts WHERE employee_id = ? AND password = ? ", new String[]{employeeId, password});
    }
}