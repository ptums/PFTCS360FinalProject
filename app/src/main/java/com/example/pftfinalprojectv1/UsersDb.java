package com.example.pftfinalprojectv1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("employee_id", employee_id);
        values.put("full_name", full_name);
        values.put("email", email);
        values.put("password", password);
        db.insert("accounts", null, values);

    }

    public Cursor findUser(String employeeId, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM accounts WHERE employee_id = ? AND password = ? ", new String[]{employeeId, password});
    }
}