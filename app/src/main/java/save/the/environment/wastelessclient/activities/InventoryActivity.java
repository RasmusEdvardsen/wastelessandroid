package save.the.environment.wastelessclient.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.games.GamesMetadata;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import save.the.environment.wastelessclient.R;
import save.the.environment.wastelessclient.data.Product;
import save.the.environment.wastelessclient.data.UserModel;
import save.the.environment.wastelessclient.miscellaneous.Constants;
import save.the.environment.wastelessclient.miscellaneous.ProductList;
import save.the.environment.wastelessclient.services.AsyncTaskService;
import save.the.environment.wastelessclient.services.HandlerService;
import save.the.environment.wastelessclient.services.ReaderService;

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

    public void deleteProduct(View v){
        final String id = (String)v.getTag();
        Log.i("information", id);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL loginURL = new URL(Constants.baseURL + Constants.productsPath + "?productId=" + id + "&userId=" + UserModel.getUserID());
                    Log.i("information", loginURL.toString());
                    HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();
                    httpURLConnection.setRequestMethod("DELETE");
                    Log.i("information", "" + httpURLConnection.getResponseCode());
                    if (httpURLConnection.getResponseCode() == 200) {
                        HandlerService.makeToast(getBaseContext(), "Success!", Toast.LENGTH_SHORT);
                        updateView();
                    }
                } catch (Exception e) {}
            }
        });
    }

    private void updateView(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create URL
                    URL loginURL = new URL(Constants.baseURL + Constants.productsPath + "?userID=" + UserModel.getUserID() + "&format=concrete");

                    // Create connection
                    HttpURLConnection httpURLConnection = (HttpURLConnection) loginURL.openConnection();

                    if (httpURLConnection.getResponseCode() == 200) {
                        JSONArray jsonArray = new JSONArray(ReaderService.resultToString(httpURLConnection.getInputStream()));
                        UserModel.resetProducts();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObject = jsonArray.getJSONObject(i);
                            Product product = new Product(jObject.getString("Name")
                                    , jObject.getString("ExpiryDate")
                                    , jObject.getString("Id"));
                            UserModel.addProduct(product);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                products = UserModel.getProducts();
                                if (productListAdapter != null){
                                    productListAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                } catch (Exception e) {}
            }
        });
    }
}
