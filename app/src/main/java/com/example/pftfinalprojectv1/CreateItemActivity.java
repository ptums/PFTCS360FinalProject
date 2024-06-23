package com.example.pftfinalprojectv1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class CreateItemActivity extends AppCompatActivity {
    private TextView itemNameText, itemQuantityText, itemDescriptionText, itemCategoryText, itemStatusText, itemPriceText;
    private InventoryDb inventoryDb;

    private String currentId, currentField, currentValue;
    @SuppressLint("SetTextI18n")
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

        // Get button element from UI
        Button createItemButton = findViewById(R.id.create_item_button);
        Button deleteItemButton = findViewById(R.id.delete_item_button);

        if(edit_item_id != null) {
            Cursor getItem = inventoryDb.getItemById(edit_item_id);
            int items_length = getItem.getCount();
            int idIndex = getItem.getColumnIndex("_id");
            int nameIndex = getItem.getColumnIndex("name");
            int priceIndex = getItem.getColumnIndex("price");
            int quantityIndex = getItem.getColumnIndex("quantity");
            int categoryIndex = getItem.getColumnIndex("category");
            int descriptionIndex = getItem.getColumnIndex("description");
            int statusIndex = getItem.getColumnIndex("status");

            for(int i = 0; i < items_length; i++) {
                getItem.moveToNext();
                String id = getItem.getString(idIndex);
                String name = getItem.getString(nameIndex);
                String price = getItem.getString(priceIndex);
                String quantity = getItem.getString(quantityIndex);
                String description = getItem.getString(descriptionIndex);
                String category = getItem.getString(categoryIndex);
                String status = getItem.getString(statusIndex);

                // Set the item values to the field inputs
                itemNameText.setText(name);
                itemQuantityText.setText(quantity);
                itemDescriptionText.setText(description);
                itemCategoryText.setText(category);
                itemStatusText.setText(status);
                itemPriceText.setText(price);

                // Store the current items id for later use
                currentId = id;

                // Update the create item button text
                createItemButton.setText("Update Item");

                // Add click event listener if we are the update item flow
                createItemButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateItemBtn(currentField, currentValue, currentId);
                    }
                });

                // Add a click even listener to delete the item
                deleteItemButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteItemBtn(currentId);
                    }
                });
            }
        } else {
            // Add click event listener if we are the create item flow
            createItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createItemBtn();
                }
            });

            // Hide delete button in create item flow
            deleteItemButton.setVisibility(View.GONE);
        }

        // Watchers for field values
        itemNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentValue = s.toString();
                currentField = "name";

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        itemQuantityText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentValue = s.toString();
                currentField = "quantity";

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        itemDescriptionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentValue = s.toString();
                currentField = "description";

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        itemCategoryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentValue = s.toString();
                currentField = "category";

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        itemStatusText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentValue = s.toString();
                currentField = "status";

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        itemPriceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentValue = s.toString();
                currentField = "price";

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void goToAllInventoryScreen() {
        Intent goToAllInventoryScreen = new Intent(CreateItemActivity.this, AllItemsActivity.class);
        startActivity(goToAllInventoryScreen);
    }

    public void createItemBtn() {
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
        Toast.makeText(this, "The item has been created! You will now be directed back to inventory list", Toast.LENGTH_SHORT).show();
        goToAllInventoryScreen();
    }

    public void updateItemBtn(String field, String value, String id) {
        inventoryDb.updateItem(field, value, id);
        inventoryDb.close();
        Toast.makeText(this, "This item has been updated! You will now be directed back to inventory list", Toast.LENGTH_SHORT).show();
        goToAllInventoryScreen();
    }

    public void deleteItemBtn(String id) {
        inventoryDb.deleteItem(id);
        inventoryDb.close();
        Toast.makeText(this, "This item has been deleted! You will now be directed back to inventory list", Toast.LENGTH_SHORT).show();
        goToAllInventoryScreen();
    }
}
