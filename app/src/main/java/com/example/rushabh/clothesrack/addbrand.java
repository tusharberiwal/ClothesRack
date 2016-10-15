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
public class addbrand extends AppCompatActivity {

    EditText addbrand;
    DatabaseHelper db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbrand);
        addbrand= (EditText)findViewById(R.id.addBrandEditText);
        db = new DatabaseHelper(this);

    }



        public void SaveAddBrand(View view)
    {
       // if (!isAnyFieldEmpty() && !isBrandDuplicate()) {
         if(!isAnyFieldEmpty()){
            String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
          db.insertBrand(addbrand.getText().toString(),date
          );
            Toast.makeText(addbrand.this, addbrand.getText().toString()+" entered as new brand", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void CancelBrand(View view)
    {
        finish();
    }

    public boolean isAnyFieldEmpty()
    {
        if(checkEditText(addbrand,"Brand")) {
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


    public boolean isBrandDuplicate()
    {
        if(getAllBrands().contains(addbrand.getText().toString())) {
            addbrand.setError("Duplicate Entry");
            Toast.makeText(this, "Please Enter New Brand ", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public List<String> getAllBrands(){
        List<String> brands = new ArrayList<String>();
        Cursor cursor = db.getBrand();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                brands.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return brands;

    }
}
