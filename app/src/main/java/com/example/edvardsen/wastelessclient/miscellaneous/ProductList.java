package com.example.edvardsen.wastelessclient.miscellaneous;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.data.Product;

import java.util.ArrayList;

public class ProductList extends ArrayAdapter<Product> {
    private final Activity context;
    private final ArrayList<Product> products;
    public ProductList(Activity context,
                       ArrayList<Product> products) {
        super(context, R.layout.inventory_item, products);
        this.context = context;
        this.products = products;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.inventory_item, null, true);
        TextView productName = rowView.findViewById(R.id.productName);
        TextView expirationDate = rowView.findViewById(R.id.expirationDate);
        productName.setText(products.get(position).name);
        expirationDate.setText(products.get(position).expirationDate);
        return rowView;
    }
}
