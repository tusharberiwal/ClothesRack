package com.example.rushabh.clothesrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rushabh on 31-08-2016.
 */
public class addsalereturn extends Activity {

    EditText itemIDSale;
    EditText sizeSale;
    EditText qtySale;
    EditText costSale;
    String srno;
    int pos= -1;
    int camflag= 0;
    DatabaseHelper db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsalereturn);
        itemIDSale =(EditText)findViewById(R.id.itemIDsale);
        sizeSale=(EditText)findViewById(R.id.sizeSale);
        qtySale=(EditText)findViewById(R.id.qtySale);
        costSale=(EditText)findViewById(R.id.costSale);
        db = new DatabaseHelper(this);
        populateData();


    }
    private  void populateData()
    {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            if(bundle.getString("isScan").equals("y"))
            {
                getIntent().removeExtra("isScan");
                openscanner();
            }

            else {
            pos = bundle.getInt("editPosition");
            if (pos !=-1) {

                itemIDSale.setText(Integer.toString(bundle.getInt("editItemIDsale")));
                sizeSale.setText(bundle.getString("editSizesale"));
                qtySale.setText(Integer.toString(bundle.getInt("editQuantitysale")));
                costSale.setText(Integer.toString(bundle.getInt("editMrpsale")));
                srno = bundle.getString("srno");

            }
            }
        }
    }


    public void openscanner(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null && resultCode==RESULT_OK) {
            Toast.makeText(this, "In Scanning result", Toast.LENGTH_SHORT).show();

            //View view=;
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            // display it on screen
           //  Toast toast = Toast.makeText(getApplicationContext(),scanContent,Toast.LENGTH_LONG);
           // toast.show();

           itemIDSale.setText(scanContent);
            search(null);

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }



    public void AddSaleToList(View view){

        if(!isAnyFieldEmpty(itemIDSale,sizeSale,qtySale,costSale)) {

              String tempBrand, tempProduct;


            List<String> details = new ArrayList<>();
            details = db.getBrandFromItem(itemIDSale.getText().toString());
            tempBrand = details.get(0);
            tempProduct = details.get(1);
            Bundle b = new Bundle();
            b.putString("brand", tempBrand);
            b.putString("product", tempProduct);
            b.putString("size", sizeSale.getText().toString());
            b.putString("quantity", qtySale.getText().toString());
            b.putString("mrp", costSale.getText().toString());
            b.putString("itemid", itemIDSale.getText().toString());
            b.putString("srno", srno);
            if (pos != -1)
                b.putInt("editPosition", pos);
            else
                b.putInt("editPosition", -1);

            Intent ini = new Intent(getApplicationContext(), Sale.class);
            ini.putExtras(b);
            setResult(RESULT_OK, ini);
            finish();
        }
    }

    public boolean isAnyFieldEmpty(EditText itemIDSale,EditText sizeSale,EditText qtySale, EditText costSale)
    {
        if(checkEditText(itemIDSale,"ItemID") && checkEditText(sizeSale,"Size")
                && checkEditText(qtySale,"Qty") &&checkEditText(costSale,"Cost")) {
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

    public void search(View view){
        String itemid= itemIDSale.getText().toString();

       String temp = " ";
               temp=db.getItemSize(itemid);;
        Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
       sizeSale.setText(temp);
      String temp1=db.getItemMrp(itemid);
        if(temp1.isEmpty())
        {
            Toast.makeText(this,"No MRP",Toast.LENGTH_LONG).show();
        }
       else {
            costSale.setText(temp1);
        }
    }
    public void canceladdsaleret(View view){
        finish();
    }

}
