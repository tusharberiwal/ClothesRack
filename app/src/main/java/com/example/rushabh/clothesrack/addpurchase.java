package com.example.rushabh.clothesrack;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rushabh on 14-07-2016.
 */
public class addpurchase extends Activity {
    Purchase pur = new Purchase();
    Bundle b = new Bundle();
    int i =1;
    Spinner prod;
    Spinner brand;
    EditText Size;
    EditText MRP;
    EditText CP;
    EditText QTY;
    TextView itemid;
    int pos=-1;
    String id;
    SQLiteDatabase finalClothesrackDB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpurchase);
        brand = (Spinner)findViewById(R.id.brandspin) ;
        prod = (Spinner)findViewById(R.id.productspin);

        Size = (EditText)findViewById(R.id.sizeap);
        MRP = (EditText)findViewById(R.id.mrpInput);
        CP = (EditText)findViewById(R.id.costpriceInput);
        QTY = (EditText)findViewById(R.id.qtys);
        itemid=(TextView)findViewById(R.id.itemidTV) ;



        SQLiteDatabase clothesrackDB = null;
        clothesrackDB = openOrCreateDatabase("ClothesRack",getApplicationContext().MODE_PRIVATE, null);
        finalClothesrackDB = clothesrackDB;
        loadSpinnerProd();
        loadSpinnerBrand();
        populateData();


    }

    private  void populateData()
    {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            pos = bundle.getInt("editPosition");
            if (pos !=-1) {
                Size.setText(bundle.getString("editSize"));

                MRP.setText(Integer.toString(bundle.getInt("editMrp")));
                CP.setText(Integer.toString(bundle.getInt("editCost")));
                QTY.setText(Integer.toString(bundle.getInt("editQuantity")));
                itemid.setText(bundle.getString("itemid"));

                ArrayAdapter<String> array_spinner_brand = (ArrayAdapter<String>) brand.getAdapter();
                brand.setSelection(array_spinner_brand.getPosition(bundle.getString("editBrand")));

                ArrayAdapter<String> array_spinner_prod = (ArrayAdapter<String>) prod.getAdapter();
                prod.setSelection(array_spinner_prod.getPosition(bundle.getString("editProduct")));
            }
        }
    }

    public void AddEditProductToLIst(View view)
    {
        if(!isAnyFieldEmpty(Size,QTY,CP,MRP)) {
            Bundle b = new Bundle();
            b.putString("brand", brand.getSelectedItem().toString());
            b.putString("product", prod.getSelectedItem().toString());
            b.putString("size", Size.getText().toString());
            b.putString("quantity", QTY.getText().toString());
            b.putString("cost", CP.getText().toString());
            b.putString("mrp", MRP.getText().toString());
            b.putString("itemid",itemid.getText().toString());
            if(pos!=-1)
                b.putInt("editPosition",pos);
            else
                b.putInt("editPosition",-1);

            Intent in = new Intent(getApplicationContext(), Purchase.class);
            in.putExtras(b);
            setResult(RESULT_OK, in);
            finish();
        }
    }

    public boolean isAnyFieldEmpty(EditText size,EditText qty,EditText cost, EditText mrp)
    {
        if(checkEditText(size,"Size") && checkEditText(qty,"Quantity")
                && checkEditText(cost,"Cost") &&checkEditText(mrp,"M.R.P.")) {
            return true;
        }
        else
            return false;
    }

    public boolean checkEditText(EditText edtTxt,String toastMessage)
    {
        if(TextUtils.isEmpty(edtTxt.getText().toString())) {
            edtTxt.setError("Field cannot be blank");
            Toast.makeText(this, "Please Enter "+toastMessage, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void CancelPurchase(View view)
    {
        Intent in = new Intent(getApplicationContext(), Purchase.class);
        setResult(RESULT_CANCELED, in);
        finish();
    }

    public void loadSpinnerProd( ) {
        // Spinner Drop down elements
        List<String> product = getAllProd();
        // Creating adapter for spinnerd
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1, product);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        prod.setAdapter(dataAdapter);
    }


    public List<String> getAllProd(){
        List<String> prod = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM Product";
        Cursor cursor = finalClothesrackDB.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                prod.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return prod;

    }


    public void loadSpinnerBrand( ) {
        // Spinner Drop down elements
        List<String> product = getAllBrand();
        // Creating adapter for spinnerd
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1, product);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        brand.setAdapter(dataAdapter);
    }


    public List<String> getAllBrand(){
        List<String> prod = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM Brand";
        Cursor cursor = finalClothesrackDB.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                prod.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return prod;

    }

    public void addbrand(View view){
        Intent intent = new Intent(this,addbrand.class);
        startActivityForResult(intent,1);
    }
    public void addproduct(View view){
        Intent intent = new Intent(this,addproduct.class);
        startActivityForResult(intent,2);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            loadSpinnerBrand();
        }
        if(requestCode == 2){
            loadSpinnerProd();

        }
        }

}
