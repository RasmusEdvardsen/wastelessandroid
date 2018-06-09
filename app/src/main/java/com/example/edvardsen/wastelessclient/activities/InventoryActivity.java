package com.example.edvardsen.wastelessclient.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.data.FridgeObjects;
import com.example.edvardsen.wastelessclient.data.UserModel;
import com.example.edvardsen.wastelessclient.miscellaneous.ProductList;

import java.util.ArrayList;
import java.util.Date;

public class InventoryActivity extends Activity {
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        UserModel.getInstance();
        ProductList listAdapter = new
                ProductList(InventoryActivity.this, UserModel.getProducts());
        list = findViewById(R.id.inventoryListView);
        list.setAdapter(listAdapter);
    }
}
