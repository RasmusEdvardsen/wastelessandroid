package save.the.environment.wastelessclient.activities;

import android.app.Activity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;

import save.the.environment.wastelessclient.R;
import save.the.environment.wastelessclient.data.DateRecog;
import save.the.environment.wastelessclient.data.FridgeObjects;
import com.google.android.gms.common.api.CommonStatusCodes;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OcrResultActivity extends Activity implements View.OnClickListener {

    // Use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView textValue;
    DateRecog dateRecog = new DateRecog();
    private static final int RC_OCR_CAPTURE = 9003;
    DatePicker datePicker;
    Button setDate;
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(save.the.environment.wastelessclient.R.layout.preocr_menu);

        statusMessage = findViewById(save.the.environment.wastelessclient.R.id.status_message);
        textValue = findViewById(save.the.environment.wastelessclient.R.id.text_value);

        autoFocus = findViewById(save.the.environment.wastelessclient.R.id.auto_focus);
        useFlash = findViewById(save.the.environment.wastelessclient.R.id.use_flash);

        findViewById(save.the.environment.wastelessclient.R.id.read_text).setOnClickListener(this);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        setDate = findViewById(R.id.datePickerButton);
        datePicker = findViewById(R.id.datePicker);
        datePicker.updateDate(year, month, day);

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String datePickerDate = datePicker.getYear() + "-" + (datePicker.getMonth() < 9 ? "0"+(datePicker.getMonth()+1) : (datePicker.getMonth()+1)) + "-" + datePicker.getDayOfMonth();
                Log.i("information", datePickerDate);
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                try {
                    String barcode = getIntent().getStringExtra("ean");
                    String FoodTypeName = getIntent().getStringExtra("choice");
                    DateTime expDate = formatter.parseDateTime(datePickerDate);
                    Date dateToDB = expDate.toDate();
                    new FridgeObjects(FoodTypeName,dateToDB,barcode).SendFridgeToDb(getBaseContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == save.the.environment.wastelessclient.R.id.read_text) {
            // launch Ocr capture activity.
            Intent intent = new Intent(this, OcrCaptureActivity.class);
            intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());
            startActivityForResult(intent, RC_OCR_CAPTURE);
        }
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    statusMessage.setText(save.the.environment.wastelessclient.R.string.ocr_success);
                    textValue.setText(text);

                    Bundle extras = getIntent().getExtras();
                    String barcode = extras.getString("ean");
                    String FoodTypeName = getIntent().getStringExtra("choice");

                    String test = dateRecog.determineDateFormat(text);
                    if(test!=null){
                        SimpleDateFormat formatter = new SimpleDateFormat(test);
                        try {
                            Date date = formatter.parse(text);
                           new FridgeObjects(FoodTypeName,date,barcode).SendFridgeToDb(getBaseContext());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    statusMessage.setText(save.the.environment.wastelessclient.R.string.ocr_failure);
                }
            } else {
                statusMessage.setText(String.format(getString(save.the.environment.wastelessclient.R.string.ocr_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
