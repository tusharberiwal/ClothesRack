package com.example.rushabh.clothesrack;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rushabh on 14-07-2016.
 */
public class addproduct extends AppCompatActivity {

    EditText addprod;
     DatabaseHelper db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproduct);
        addprod = (EditText)findViewById(R.id.addProdEditText);
        db= new DatabaseHelper(this);

    }

    public void SaveAddProduct(View view)
    {
        if(!isAnyFieldEmpty()&& !isProductDuplicate()) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
            db.insertProduct(addprod.getText().toString(),date);
            Toast.makeText(addproduct.this, addprod.getText().toString()+" as new product", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void CancelProduct(View view)
    {
        finish();
    }

    public boolean isAnyFieldEmpty()
    {
        if(checkEditText(addprod,"Product")) {
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

    public boolean isProductDuplicate()
    {
        if(getAllProducts().contains(addprod.getText().toString())) {
            addprod.setError("Duplicate Entry");
            Toast.makeText(this, "Please Enter New Product ", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public List<String> getAllProducts(){
        List<String> prods = new ArrayList<String>();
        Cursor cursor = db.getProduct();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                prods.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return prods;

    }

}
