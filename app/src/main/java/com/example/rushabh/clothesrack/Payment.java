package com.example.rushabh.clothesrack;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Payment extends Fragment implements View.OnClickListener {
    View myview;
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
    TextView dateedit;

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateedit.setText(sdf.format(myCalendar.getTime()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        myview= inflater.inflate(R.layout.payment, container,false);
         spinner= (Spinner)myview.findViewById(R.id.PContactSpinner);
         save=(Button)myview.findViewById(R.id.savePayment);
        save.setOnClickListener(this);
         cancel=(Button)myview.findViewById(R.id.cancelPayment);
        cancel.setOnClickListener(this);
        against=(EditText)myview.findViewById(R.id.againstPayment);
        amount=(EditText)myview.findViewById(R.id.amountPayment);
        paydisc=(RadioGroup)myview.findViewById(R.id.paydisc);
        recmade=(RadioGroup)myview.findViewById(R.id.recmade);
        payment=(RadioButton)myview.findViewById(R.id.paymentRB);
        payment.setId(0);
        disc=(RadioButton)myview.findViewById(R.id.discountRB);
       // disc.setId(1);
        received=(RadioButton)myview.findViewById(R.id.receivedRB);
        //received.setId(2);
        made=(RadioButton) myview.findViewById(R.id.madeRB);
        //made.setId(3);
        db= new DatabaseHelper(getContext());
        dateedit=(TextView)myview.findViewById(R.id.dateedit);
        dateedit.setOnClickListener(this);
        loadSpinnerDataName();
        return myview;
    }


    public void loadSpinnerDataName( ) {
        // Spinner Drop down elements
        List<String> names = db.getContactNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_expandable_list_item_1, names);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.savePayment:
            {
                savepayment();
                break;
            }
            case R.id.cancelPayment:
            {
                getid();
                break;
            }
            case R.id.dateedit:
            {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
         }

    }

    public void savepayment(){

        Boolean recmad;
        String Cname = spinner.getSelectedItem().toString();
        String cid= db.getContactID(Cname);
        Toast.makeText(getContext(),String.valueOf(recmade.getCheckedRadioButtonId()).toString(),Toast.LENGTH_LONG).show();
        String recmadetext= ((RadioButton)myview.findViewById(recmade.getCheckedRadioButtonId())).getText().toString();


        if(recmadetext.equals("Made"))
        {
            recmad=Boolean.TRUE;
        }
        else
        {
            recmad=Boolean.FALSE;
        }
        String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
        String paydisctext=((RadioButton)myview.findViewById(paydisc.getCheckedRadioButtonId())).getText().toString();
        Toast.makeText(getContext(),paydisctext,Toast.LENGTH_SHORT).show();
        if(paydisctext.equals("Payment"))
        {
            db.paymententry(recmad,Integer.valueOf(cid),Integer.valueOf(against.getText().toString()),Integer.valueOf(amount.getText().toString()),dateedit.getText().toString(),date);
        }
        else
        {
            db.discountentry(recmad,Integer.valueOf(cid),Integer.valueOf(against.getText().toString()),Integer.valueOf(amount.getText().toString()),dateedit.getText().toString(),date);
        }


    }

    public void getid(){
        String value= ((RadioButton)myview.findViewById(recmade.getCheckedRadioButtonId())).getText().toString();
        Toast.makeText(getContext(),value,Toast.LENGTH_LONG).show();
        String paydisctext=((RadioButton)myview.findViewById(paydisc.getCheckedRadioButtonId())).getText().toString();
        Toast.makeText(getContext(),paydisctext,Toast.LENGTH_LONG).show();
    }


}
