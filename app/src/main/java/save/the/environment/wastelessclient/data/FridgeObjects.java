package save.the.environment.wastelessclient.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import save.the.environment.wastelessclient.activities.BarcodeActivity;
import save.the.environment.wastelessclient.miscellaneous.Constants;
import save.the.environment.wastelessclient.services.HandlerService;

import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FridgeObjects extends Activity {
    public Date expDate;
    public String name;
    public String ean;
    public String id;

    public FridgeObjects(){

    }

    public FridgeObjects(String name, Date expDate, String ean){
        this.expDate = expDate;
        this.name = name;
        this.ean= ean;
    }

    public void SendFridgeToDb(final Context ctx){
        final String url = Constants.baseURL + Constants.productsPath;
        final String json = "{\"UserID\": " + "\"" + String.valueOf(UserModel.getUserID()) + "\","
                + "\"EAN\": " + "\"" + ean + "\","
                + "\"FoodTypeName\": " + "\"" + name + "\","
                + "\"ExpirationDate\": " + "\"" + DateFormat.format("yyyy-MM-dd", expDate) + "\"," + "}";
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    response = client.newCall(request).execute();

                    if(response == null){
                        HandlerService.makeToast(getBaseContext(), "Something went wrong.", Toast.LENGTH_SHORT, 500);
                    }else{
                        if(response.code() == 200){
                            HandlerService.makeToast(ctx, "Product added!", Toast.LENGTH_SHORT, 500);
                            ctx.startActivity(new Intent(ctx, BarcodeActivity.class));
                        }else{
                        }
                    }
                }catch (Exception e){
                }
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate){
        this.expDate=expDate;
    }
}
