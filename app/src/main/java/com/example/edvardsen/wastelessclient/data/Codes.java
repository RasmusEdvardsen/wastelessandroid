package com.example.edvardsen.wastelessclient.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Codes {

    private static String barcode = "";
    private static Codes codes = null;
    private ArrayList<String> ListOfCodes = new ArrayList<String>();
    private static String ocr = "";
    private static Date expDate = null;
    private static int userID = -1;
    private static int ean = 0;

    public static String getBarcode(String barcode) {
        return Codes.barcode;
    }
    public static String getOcr() {return ocr;}

    public ArrayList<String> getListofCodes(String barcode, String ocr) {
        ListOfCodes.add(ocr);
        ListOfCodes.add(barcode);
        return ListOfCodes;
    }

    public static void setOCr(String ocr) {
        Codes.ocr= ocr;
    }
    public static void setBarcode(String barcode) {
        Codes.barcode= barcode;
    }

    public static void setExpDate(Date expDate){
        Codes.expDate = expDate;
    }
    public static int getUserID(){return userID;}

    public static void setUserID(int userID) {
        Codes.userID = userID;
    }
    public static void setEan(int ean){
        Codes.ean = ean;
    }
    public static int getEan(){return ean;}

}
