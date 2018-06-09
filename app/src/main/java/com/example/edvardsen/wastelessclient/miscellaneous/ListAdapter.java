package com.example.edvardsen.wastelessclient.miscellaneous;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.data.FridgeObjects;
import com.example.edvardsen.wastelessclient.data.Product;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<FridgeObjects> {
    private Context mContext;
    private ArrayList<Product> products;

    public ListAdapter(@NonNull Context context,  ArrayList<Product> products){
        super(context,0,products);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mContext).inflate(R.layout.single_item,parent,false);
        }

        FridgeObjects fridgeObjects = foodList.get(position);

        TextView name = listItem.findViewById(R.id.FoodName);
        name.setText(fridgeObjects.name);

        TextView expDate = listItem.findViewById(R.id.TimeRemaining);
        expDate.setText((CharSequence) fridgeObjects.expDate);

        return listItem;
    }
}
