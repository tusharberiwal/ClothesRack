package com.example.rushabh.clothesrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Rushabh on 26-07-2016.
 */
public class addStock extends AppCompatActivity {

    DatabaseHelper db;
    Spinner productSpinner;
    Spinner brandSpinner;
    EditText sizeText;
    EditText qtyText;
    int pos=-1;
    String itemid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addstock);
        brandSpinner= (Spinner)findViewById(R.id.brandStockSpinner) ;
        productSpinner=(Spinner)findViewById(R.id.productStockSpinner);
        sizeText=(EditText)findViewById(R.id.sizeStock);
        qtyText = (EditText)findViewById(R.id.qtyStock);
        db= new DatabaseHelper(this);
        loadSpinnerBrand();
        loadSpinnerProd();
        populateData();


    }

    private  void populateData()
    {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            pos = bundle.getInt("editPosition");
            if (pos !=-1) {

               List<String> temp= db.getBrandFromItem(bundle.getString("itemid"));
                itemid=bundle.getString("itemid");
                sizeText.setText(bundle.getString("size"));
                qtyText.setText(bundle.getString("qty"));

                ArrayAdapter<String> array_spinner_brand = (ArrayAdapter<String>)brandSpinner.getAdapter();
                brandSpinner.setSelection(array_spinner_brand.getPosition(temp.get(0).toString()));

                ArrayAdapter<String> array_spinner_prod = (ArrayAdapter<String>) productSpinner.getAdapter();
                productSpinner.setSelection(array_spinner_prod.getPosition(temp.get(1).toString()));
            }
        }
    }

    public void SaveNewStock(View view) {
        if (pos != -1) {

            String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
            String tempProductID = db.getProductID(productSpinner.getSelectedItem().toString());
            String tempBrandID = db.getBrandID(brandSpinner.getSelectedItem().toString());
            db.updateStock(tempBrandID,tempProductID,Integer.valueOf(qtyText.getText().toString()),sizeText.getText().toString(),itemid,date);
            Intent ini = new Intent(getApplicationContext(), Stock.class);
            setResult(RESULT_OK, ini);
            finish();



        } else {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());

            String tempProductID = db.getProductID(productSpinner.getSelectedItem().toString());
            String tempBrandID = db.getBrandID(brandSpinner.getSelectedItem().toString());
            ;

            db.insertStock(tempBrandID, tempProductID, qtyText.getText().toString(), sizeText.getText().toString(), date);
            finish();


        }
    }

    public void CancelNewStock(View view)
    {
        finish();
    }

    public void loadSpinnerProd( ) {
        // Spinner Drop down elements
        List<String> product = db.getProductNames();
        // Creating adapter for spinnerd
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1, product);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        productSpinner.setAdapter(dataAdapter);
    }

    public void loadSpinnerBrand( ) {
        // Spinner Drop down elements
        List<String> product = db.getBrandNames();
        // Creating adapter for spinnerd
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1, product);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        brandSpinner.setAdapter(dataAdapter);
    }



}
