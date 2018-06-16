package save.the.environment.wastelessclient.services;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import save.the.environment.wastelessclient.activities.OcrResultActivity;

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
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setHint("Manual name input here");
                input.setLayoutParams(lp);
                builder.setView(input);

                builder.setNeutralButton("Manually Entered Name", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        input.setText(input.getText());
                        Intent intent = new Intent(ctx, OcrResultActivity.class);
                        intent.putExtra("choice",input.getText().toString());
                        intent.putExtra("ean", barcode);
                        ctx.startActivity(intent);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ctx, OcrResultActivity.class);
                        intent.putExtra("choice", scanResults[0]);
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
