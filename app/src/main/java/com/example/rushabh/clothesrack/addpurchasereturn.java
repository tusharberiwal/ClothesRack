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
public class addpurchasereturn extends Activity {

    EditText itemIDpreturn;
    EditText sizepreturn;
    EditText qtypreturn;
    EditText costpreturn;
    String srno;
    int pos= -1;
    DatabaseHelper db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpurchasereturn);
        itemIDpreturn =(EditText)findViewById(R.id.itemIDpreturn);
        sizepreturn=(EditText)findViewById(R.id.sizepreturn);
        qtypreturn=(EditText)findViewById(R.id.qtypreturn);
        costpreturn=(EditText)findViewById(R.id.costpreturn);
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
                if (pos != -1) {

                    itemIDpreturn.setText(Integer.toString(bundle.getInt("editItemID")));
                    sizepreturn.setText(bundle.getString("editSize"));
                    qtypreturn.setText(Integer.toString(bundle.getInt("editQuantity")));
                    costpreturn.setText(Integer.toString(bundle.getInt("editCost")));
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

        if (scanningResult != null) {
            //View view=;
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            // display it on screen
            Toast toast = Toast.makeText(getApplicationContext(),scanContent,Toast.LENGTH_LONG);
            toast.show();
            itemIDpreturn.setText(scanContent);
            search(null);

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void AddPRetToList(View view) {
        if (!isAnyFieldEmpty(itemIDpreturn,sizepreturn,qtypreturn,costpreturn)) {
            String tempBrand, tempProduct;

            int stqty = db.getStockQty(itemIDpreturn.getText().toString());
            int qty = Integer.valueOf(qtypreturn.getText().toString());

            if (stqty >= qty) {
                List<String> details = new ArrayList<>();
                details = db.getBrandFromItem(itemIDpreturn.getText().toString());
                tempBrand = details.get(0);
                tempProduct = details.get(1);
                Bundle b = new Bundle();
                b.putString("brand", tempBrand);
                b.putString("product", tempProduct);
                b.putString("size", sizepreturn.getText().toString());
                b.putString("quantity", qtypreturn.getText().toString());
                b.putString("cost", costpreturn.getText().toString());
                b.putString("itemid", itemIDpreturn.getText().toString());
                b.putString("srno", srno);
                if (pos != -1)
                    b.putInt("editPosition", pos);
                else
                    b.putInt("editPosition", -1);

                Intent ini = new Intent(getApplicationContext(), PurchaseReturn.class);
                ini.putExtras(b);
                setResult(RESULT_OK, ini);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "Insufficient Quantity", Toast.LENGTH_LONG).show();

        }
    }
    public boolean isAnyFieldEmpty(EditText itemIDpreturn,EditText sizepreturn,EditText qtypreturn, EditText costpreturn)
    {
        if(checkEditText(itemIDpreturn,"ItemID") && checkEditText(sizepreturn,"Size")
                && checkEditText(qtypreturn,"Qty") &&checkEditText(costpreturn,"Cost")) {
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
        String itemid= itemIDpreturn.getText().toString();

       String temp = " ";
        temp=db.getItemSize(itemid);;
        Toast.makeText(this,temp,Toast.LENGTH_LONG).show();
       sizepreturn.setText(temp);
       String temp1=db.getItemCost(itemid);
       costpreturn.setText(temp1);

    }
    public void canceladdpurchaseret(View view){
        finish();
    }

}
