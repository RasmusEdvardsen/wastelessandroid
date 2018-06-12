package save.the.environment.wastelessclient.miscellaneous;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



import save.the.environment.wastelessclient.data.Product;

import java.util.ArrayList;

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
        productName.setText(products.get(position).name);
        expirationDate.setText(products.get(position).expirationDate);
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
