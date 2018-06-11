package save.the.environment.wastelessclient.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import save.the.environment.wastelessclient.data.UserModel;
import save.the.environment.wastelessclient.miscellaneous.ProductList;

public class InventoryActivity extends Activity {
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(save.the.environment.wastelessclient.R.layout.activity_inventory);
        RenderView(false);
    }

    public void RenderView(boolean isChanged){
        UserModel.getInstance();
        if(UserModel.getProducts().size() > 0){
            ProductList listAdapter = new
                    ProductList(InventoryActivity.this, UserModel.getProducts());
            list = findViewById(save.the.environment.wastelessclient.R.id.inventoryListView);
            list.setAdapter(listAdapter);
            //TODO: TRY THIS
            if (isChanged) listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        RenderView(true);
    }
}
