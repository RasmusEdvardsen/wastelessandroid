package com.example.edvardsen.wastelessclient.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.edvardsen.wastelessclient.R;
import com.example.edvardsen.wastelessclient.data.FridgeObjects;
import com.example.edvardsen.wastelessclient.miscellaneous.ListAdapter;

import java.util.ArrayList;
import java.util.Date;

public class InventoryActivity extends Activity {

    ListView listView;

    ArrayList<FridgeObjects> listItems = new ArrayList<FridgeObjects>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        /*listView = findViewById(R.id.listview);
        ListAdapter listAdapter = new ListAdapter(this,listItems);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });*/
    }

    public String getDateDiffString(Date dateOne, Date dateTwo){
        long timeOne = dateOne.getTime();
        long timeTwo = dateTwo.getTime();
        long oneDay = 1000*60*60*24;
        timeOne+=oneDay;
        long delta = (timeTwo-timeOne)/oneDay;

        if(delta>=0){
            if(delta==1) return "EXPIRED!! " + delta + "day ";
            else return "EXPIRED !! " + delta + "day ";

        }else {
            delta *= -1;
            if(delta==1) return "" + delta + " day left to Expire";
            else return "" + delta + " day left to Expire";
        }
    }

    public void addItems(View v){
        for( final FridgeObjects objects : listItems ){

        }
    }
}
