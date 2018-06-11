package save.the.environment.wastelessclient.services;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import save.the.environment.wastelessclient.activities.OcrResultActivity;

/**
 * Created by Epico-u-01 on 4/7/2018.
 */

public class HandlerService {
    public static void setVisibility(final View view, final Integer visibility){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(visibility);
            }
        });
    }

    public static void setBuilder(final Context ctx, final String[] scanResults, final String barcode){
        new Handler(Looper.getMainLooper()).post(new Runnable(){
            @Override
            public void run() {

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
                builder.setItems(scanResults, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ctx, OcrResultActivity.class);
                        intent.putExtra("choice", scanResults[i]);
                        intent.putExtra("ean", barcode);
                        ctx.startActivity(intent);

                    }
                });

                final EditText input = new EditText(ctx);

                builder.setNeutralButton("Manually Enter Name ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        input.setText(input.getText());
                        //secondDialog(result);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ctx, OcrResultActivity.class);
                        intent.putExtra("choise", scanResults[0]);
                        intent.putExtra("ean", barcode);
                        ctx.startActivity(intent);
                    }
                });

                android.support.v7.app.AlertDialog alert1 = builder.create();
                alert1.show();
            }
        });
    }

    public static void setVisibility(final View view, final Integer visibility, int delay){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(visibility);
            }
        }, delay);
    }

    public static void makeToast(final Context context,final String text,final int duration) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, duration).show();
            }
        });
    }

    public static void makeToast(final Context context,final String text,final int duration, int delay) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, duration).show();
            }
        }, delay);
    }
}
