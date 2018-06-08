package com.example.edvardsen.wastelessclient.miscellaneous;

import java.util.Date;

/**
 * Created by Epico-u-01 on 6/8/2018.
 */

public class AdapterItem {
    public AdapterItem(String itenName, Date ExpDate, int ID){
        itemName2=itenName;
        expDate2= ExpDate;
        idNum= ID;
    }

    String itemName2;
    Date expDate2;
    int idNum;
}
