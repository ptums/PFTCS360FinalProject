package com.example.pftfinalprojectv1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InventoryDb extends SQLiteOpenHelper {
    public InventoryDb(Context context) {
        super(context, "inventory.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String inventoryTable = "CREATE TABLE inventory (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, quantity TEXT, description TEXT, category TEXT, status TEXT, price TEXT)";
        db.execSQL(inventoryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addItem(String name, String quantity, String description, String category, String status, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("description", description);
        values.put("category", category);
        values.put("status", status);
        values.put("price", price);
        db.insert("inventory", null, values);
        db.close();
    }

    public Cursor getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM inventory",null);
    }

    public Cursor getItemById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM inventory WHERE _id = ?", new String[]{id});
    }

    public void updateItem(String field, String value, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(field, value);
        db.update("inventory", values, "_id = ?", new String[]{id} );
        db.close();
    }

    public void deleteItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete("inventory", "_id =? ", new String[]{id});
    }
}


