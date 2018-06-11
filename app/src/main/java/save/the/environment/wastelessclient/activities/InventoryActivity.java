package save.the.environment.wastelessclient.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import save.the.environment.wastelessclient.data.Product;
import save.the.environment.wastelessclient.data.UserModel;
import save.the.environment.wastelessclient.miscellaneous.ProductList;

public class InventoryActivity extends Activity {
    ListView list;
    ProductList productListAdapter;
    ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(save.the.environment.wastelessclient.R.layout.activity_inventory);

        list = findViewById(save.the.environment.wastelessclient.R.id.inventoryListView);

        UserModel.getInstance();
        if(UserModel.getProducts().size() > 0){
            products = UserModel.getProducts();
            productListAdapter = new
                    ProductList(InventoryActivity.this, products);
            list.setAdapter(productListAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView(){
        //TODO: Check if um.gprods differs from products
        if(UserModel.getProducts().size() > 0){
            products = UserModel.getProducts();
            if(productListAdapter != null) productListAdapter.notifyDataSetChanged();
        }
    }
}
