package save.the.environment.wastelessclient.miscellaneous;

import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import save.the.environment.wastelessclient.R;
import save.the.environment.wastelessclient.data.Product;
import save.the.environment.wastelessclient.data.UserModel;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductList extends ArrayAdapter<Product> {
    private final Activity context;
    private final ArrayList<Product> products;
    public ProductList(Activity context,
                       ArrayList<Product> products) {
        super(context, save.the.environment.wastelessclient.R.layout.inventory_item, products);
        this.context = context;
        this.products = products;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(save.the.environment.wastelessclient.R.layout.inventory_item, null, true);
        TextView productName = rowView.findViewById(save.the.environment.wastelessclient.R.id.productName);
        TextView expirationDate = rowView.findViewById(save.the.environment.wastelessclient.R.id.expirationDate);
        ImageView deleteProduct = rowView.findViewById(R.id.deleteProduct);
        deleteProduct.setTag(products.get(position).id);
        productName.setText(products.get(position).name);

        String formatted = products.get(position).expirationDate;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            formatted = sdf.format(sdf.parse(products.get(position).expirationDate));
        }catch (Exception e){}
        expirationDate.setText(String.format("Expires on: %1$s", formatted));

        return rowView;
    }

    @Override
    public int getCount() {
        return this.products.size();
    }

    @Override
    public Product getItem(final int position) {
        return this.products.get(position);
    }
}
