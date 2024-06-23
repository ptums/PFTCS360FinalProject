package com.example.pftfinalprojectv1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class CreateItemActivity extends AppCompatActivity {
    private TextView itemNameText, itemQuantityText, itemDescriptionText, itemCategoryText, itemStatusText, itemPriceText;
    private InventoryDb inventoryDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_item);

        itemNameText = findViewById(R.id.item_name_text);
        itemQuantityText = findViewById(R.id.item_quantity_text);
        itemDescriptionText = findViewById(R.id.item_description_text);
        itemCategoryText = findViewById(R.id.item_category_text);
        itemStatusText = findViewById(R.id.item_status_text);
        itemPriceText = findViewById(R.id.item_price_text);
        // Initialize inventory database
        inventoryDb = new InventoryDb(this);
        inventoryDb.getWritableDatabase();


        // When the user clicks back to all inventory
        // direct user to activity all items.xml
        TextView goToAllInventoryTextView = findViewById(R.id.go_to_all_inventory);
        goToAllInventoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAllInventoryScreen();
            }
        });

        // Set values if there is an edit item id
        Intent intent = getIntent();
        String edit_item_id = intent.getStringExtra("edit_item_id");

        if(edit_item_id != null) {

            Cursor getItem = inventoryDb.getItemById(edit_item_id);

            int items_length = getItem.getCount();

            int nameIndex = getItem.getColumnIndex("name");
            int priceIndex = getItem.getColumnIndex("price");
            int quantityIndex = getItem.getColumnIndex("quantity");
            int categoryIndex = getItem.getColumnIndex("category");
            int descriptionIndex = getItem.getColumnIndex("description");
            int statusIndex = getItem.getColumnIndex("status");

            for(int i = 0; i < items_length; i++) {
                getItem.moveToNext();
                String name = getItem.getString(nameIndex);
                String price = getItem.getString(priceIndex);
                String quantity = getItem.getString(quantityIndex);
                String description = getItem.getString(descriptionIndex);
                String category = getItem.getString(categoryIndex);
                String status = getItem.getString(statusIndex);


                itemNameText.setText(name);
                itemQuantityText.setText(quantity);
                itemDescriptionText.setText(description);
                itemCategoryText.setText(category);
                itemStatusText.setText(status);
                itemPriceText.setText(price);

            }



        }
    }

    private void goToAllInventoryScreen() {
        Intent goToAllInventoryScreen = new Intent(CreateItemActivity.this, AllItemsActivity.class);
        startActivity(goToAllInventoryScreen);
    }

    public void createItemBtn(View view) {
        String name = itemNameText.getText().toString();
        String quantity = itemQuantityText.getText().toString();
        String description = itemDescriptionText.getText().toString();
        String category = itemCategoryText.getText().toString();
        String status = itemStatusText.getText().toString();
        String price = itemPriceText.getText().toString();

        inventoryDb.addItem(name, quantity,description,category,status, price);

        itemNameText.setText("");
        itemQuantityText.setText("");
        itemDescriptionText.setText("");
        itemCategoryText.setText("");
        itemStatusText.setText("");
        itemPriceText.setText("");
        inventoryDb.close();
        Toast.makeText(this, "A new item has been created! You will now be directed back to inventory list", Toast.LENGTH_SHORT).show();
        goToAllInventoryScreen();
    }
}
