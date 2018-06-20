package save.the.environment.wastelessclient.miscellaneous;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

        String expdate = products.get(position).expirationDate;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        if(expdate != null){
            if(expdate.contains("T"))
                expdate = expdate.replace("T", " ");
            DateTime expDate = formatter.parseDateTime(expdate);
            DateTime nowDate = new DateTime();
            long diff = expDate.getMillis() - nowDate.getMillis();
            Log.i("information", diff + "");
            Log.i("information", String.valueOf(diff < 86400000 * 2));
            Log.i("information", 86400000 * 2 + "");
            if(diff < 86400000 * 2 && diff > 0) {
                productName.setTextColor(context.getResources().getColor(R.color.colorPink));
                expirationDate.setTextColor(context.getResources().getColor(R.color.colorPink));
            }
        }
        String formatted = formatter.parseLocalDate(expdate).toString("yyyy-MM-dd");
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
