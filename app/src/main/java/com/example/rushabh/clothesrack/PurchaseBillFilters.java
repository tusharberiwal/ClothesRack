package com.example.rushabh.clothesrack;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by tushar on 9/17/2016.
 */
public class PurchaseBillFilters extends Activity {


    EditText filterBillNo;
    EditText filterContactName;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpurchasefilter);

        filterBillNo = (EditText)findViewById(R.id.filterBillNo);
        filterContactName = (EditText)findViewById(R.id.filterContactName);
    }

    public void AddFilters(View view)
    {
        Bundle b = new Bundle();
        b.putString("filterBillNo", filterBillNo.getText().toString());
        b.putString("filterContactName", filterContactName.getText().toString());

            Intent in = new Intent(getApplicationContext(), PurchaseBill.class);
            in.putExtras(b);
            setResult(RESULT_OK, in);
            finish();
    }

    public void CancelFilter(View view)
    {
        Intent in = new Intent(getApplicationContext(), PurchaseBill.class);
        setResult(RESULT_CANCELED, in);
        finish();
    }
}
