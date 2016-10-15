package com.example.rushabh.clothesrack;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Rushabh on 03-10-2016.
 */

public class EditPayment extends Activity implements View.OnClickListener {
    DatabaseHelper db;
    Spinner spinner;
    Button save;
    Button cancel;
    EditText against;
    EditText amount;
    RadioGroup paydisc;
    RadioGroup recmade;
    RadioButton payment;
    RadioButton disc;
    RadioButton received;
    RadioButton made;
    String ID;
    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        spinner = (Spinner) findViewById(R.id.PContactSpinner);
        save = (Button) findViewById(R.id.savePayment);
        save.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.cancelPayment);
        cancel.setOnClickListener(this);
        against = (EditText) findViewById(R.id.againstPayment);
        amount = (EditText) findViewById(R.id.amountPayment);
        paydisc = (RadioGroup) findViewById(R.id.paydisc);
        recmade = (RadioGroup) findViewById(R.id.recmade);
        payment = (RadioButton) findViewById(R.id.paymentRB);
        disc = (RadioButton) findViewById(R.id.discountRB);
        received = (RadioButton) findViewById(R.id.receivedRB);
        made = (RadioButton) findViewById(R.id.madeRB);
        disc.setEnabled(false);
        //received.setEnabled(false);
        payment.setEnabled(false);
        //made.setEnabled(false);
        //paydisc.setEnabled(false);
        //recmade.setEnabled(false);
        db = new DatabaseHelper(this);
        loadSpinnerDataName();
        loadEditData();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.savePayment: {
                 savepayment();
                break;
            }
            case R.id.cancelPayment: {
                //  getid();
                break;
            }
        }

    }

    public void loadSpinnerDataName() {
        // Spinner Drop down elements
        List<String> names = db.getContactNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, names);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }


    public void loadEditData() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.getString("Type").equals("Payment")) {

                String custName;

                ID = bundle.getString("No");
                Cursor cursor = db.getPayment(ID);

                if (cursor.moveToFirst()) {
                    do{
                    paydisc.check(R.id.paymentRB);

                    String pmade = cursor.getString(cursor.getColumnIndex("isMade"));
                    if (pmade.equals("0"))
                        recmade.check(R.id.receivedRB);
                    else
                        recmade.check(R.id.madeRB);

                    amount.setText(cursor.getString(cursor.getColumnIndex("Amt")));
                    against.setText(cursor.getString(cursor.getColumnIndex("ID")));
                    ArrayAdapter<String> array_spinner_brand = (ArrayAdapter<String>) spinner.getAdapter();
                    spinner.setSelection(array_spinner_brand.getPosition(bundle.getString("custName")));
                   // spinner.setSelection(array_spinner_brand.getPosition(custName));
                    } while (cursor.moveToNext());

                }

            } else if (bundle.getString("Type").equals("Discount")) {


                String custName;

                ID = bundle.getString("No");
                Cursor cursor = db.getDiscount(ID);

                if (cursor.moveToFirst()) {

                    paydisc.check(R.id.discountRB);

                    String pmade = cursor.getString(cursor.getColumnIndex("isMade"));
                    if (pmade.equals("False"))
                        recmade.check(R.id.receivedRB);
                    else
                        recmade.check(R.id.madeRB);

                    amount.setText(cursor.getString(cursor.getColumnIndex("DiscAmt")));
                    against.setText(cursor.getString(cursor.getColumnIndex("ID")));
                    ArrayAdapter<String> array_spinner_brand = (ArrayAdapter<String>) spinner.getAdapter();
                    spinner.setSelection(array_spinner_brand.getPosition(bundle.getString("custName")));

                }
            }
        }
    }


    public void savepayment(){

        Boolean recmad;
        String Cname = spinner.getSelectedItem().toString();
        String cid= db.getContactID(Cname);
        String paymentdic= ((RadioButton)findViewById(paydisc.getCheckedRadioButtonId())).getText().toString();
        Toast.makeText(this,paymentdic,Toast.LENGTH_LONG).show();
String recmadetext =((RadioButton)findViewById(recmade.getCheckedRadioButtonId())).getText().toString();

       if(recmadetext.equals("Made"))
        {
            recmad=Boolean.TRUE;
        }
        else
        {
            recmad=Boolean.FALSE;
        }
        String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
        String paydisctext=((RadioButton)findViewById(paydisc.getCheckedRadioButtonId())).getText().toString();
        Toast.makeText(this,paydisctext,Toast.LENGTH_SHORT).show();
        if(paydisctext.equals("Payment"))
        {
            db.updatepaymententry(recmad,Integer.valueOf(cid),Integer.valueOf(against.getText().toString()),Integer.valueOf(amount.getText().toString()),date,date,ID);
        }
        else
        {
            db.updatediscountentry(recmad,Integer.valueOf(cid),Integer.valueOf(against.getText().toString()),Integer.valueOf(amount.getText().toString()),date,date,ID);
        }


    }





}
