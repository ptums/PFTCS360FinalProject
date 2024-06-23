package com.example.pftfinalprojectv1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AllItemsActivity extends AppCompatActivity {

    private InventoryDb inventoryDb;

    ListView simpleList;
    ArrayList<String> inventoryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_items);

        inventoryDb = new InventoryDb(this);
        inventoryDb.getWritableDatabase();

        // When the user clicks add item
        // direct user to activity_create_item.xml
        TextView addItemTextView = findViewById(R.id.add_item);
        addItemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToCreateItemActivity = new Intent(AllItemsActivity.this, CreateItemActivity.class);
                startActivity(goToCreateItemActivity);

            }
        });


        // query all current items
        Cursor all_items = inventoryDb.getAllItems();
        int items_length = all_items.getCount();

        int idIndex = all_items.getColumnIndex("_id");
        int nameIndex = all_items.getColumnIndex("name");
        int priceIndex = all_items.getColumnIndex("price");
        int quantityIndex = all_items.getColumnIndex("quantity");

        for(int i = 0; i < items_length; i++) {
            all_items.moveToNext();
            String id = all_items.getString(idIndex);
            String name = all_items.getString(nameIndex);
            String price = all_items.getString(priceIndex);
            String quantity = all_items.getString(quantityIndex);


            String item = "Name: " + name + " - $" + price + " - qty:" + quantity + " - View Details";
            inventoryList.add(item);
        }


        simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, inventoryList);
        simpleList.setAdapter(arrayAdapter);

    }
    public void editItemBtn(View view) {
        Log.v(null, "We have a clickablebutton");
        // TODO: Pass ID into this function
        // Then redirect to create item screen with id as argument
    }

}
