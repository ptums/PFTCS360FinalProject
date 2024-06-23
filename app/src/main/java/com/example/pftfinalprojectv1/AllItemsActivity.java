package com.example.pftfinalprojectv1;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AllItemsActivity extends AppCompatActivity {

    private InventoryDb inventoryDb;

    ListView simpleList;
    ArrayList<String> inventoryList = new ArrayList<String>();
    ArrayList<String> idList = new ArrayList<String>();

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


            String item = "Name: " + name + "\n $" + price + "\n qty: " + quantity + "\n View Details";
            inventoryList.add(item);
            idList.add(id);

        }


        simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new InventoryItemArrayAdapter(this, inventoryList, idList);
        simpleList.setAdapter(arrayAdapter);



    }
}

// Reference: https://www.geeksforgeeks.org/custom-arrayadapter-with-listview-in-android/
class InventoryItemArrayAdapter extends ArrayAdapter<String> {

    private final Context context;

    private final List<String> items;
    private final List<String> ids;

    public InventoryItemArrayAdapter(Context context, List<String> items, List<String> ids) {
        super(context, R.layout.activity_listview, items);
        this.context = context;
        this.items = items;
        this.ids = ids;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View currentInventoryItem = convertView;

        if (currentInventoryItem  == null) {
            currentInventoryItem  = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
        }

        TextView inventoryItem = currentInventoryItem.findViewById(R.id.inventoryItem);
        inventoryItem.setText(items.get(position));
        inventoryItem.setTag(ids.get(position));

        inventoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = (String) v.getTag();

                Intent goToCreateAccountScreen = new Intent(context, CreateItemActivity.class);
                goToCreateAccountScreen.putExtra("edit_item_id", id);
                context.startActivity(goToCreateAccountScreen);
            }
        });

        return currentInventoryItem;
    }
}

