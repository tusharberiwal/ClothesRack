package com.example.rushabh.clothesrack;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rushabh on 09-08-2016.
 */
public class addcontact extends AppCompatActivity {

    SQLiteDatabase finalClothesrackDB = null;
    EditText addContactName;
    EditText addContactDetail;
    EditText addContactNo;
    DatabaseHelper db;
    int pos=-1;
    String cID;

    Purchase purchase = new Purchase();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontact);
        SQLiteDatabase clothesrackDB = null;
        addContactName = (EditText) findViewById(R.id.addContactName);
        addContactDetail = (EditText) findViewById(R.id.Cdetails);
        addContactNo = (EditText) findViewById(R.id.ContactNo);
        Purchase purchase = new Purchase();
        clothesrackDB = openOrCreateDatabase("ClothesRack", MODE_PRIVATE, null);
        //Button addprod = (Button) findViewById(R.id.saveaddprod);
        //  finalClothesrackDB = clothesrackDB;
        db = new DatabaseHelper(this);

        populateContact();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void populateContact(){


            Bundle bundle = getIntent().getExtras();
            if(bundle!=null) {
                pos = bundle.getInt("pos");
                if (pos !=-1) {
                    addContactName.setText(bundle.getString("cName"));
                    addContactNo.setText(bundle.getString("cNumber"));
                    cID= bundle.getString("cID");
                    addContactDetail.setText(db.getContactDetails(cID));


                }
            }


    }

    public void SaveAddContact(View view) {

        if (!isAnyFieldEmpty()) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());

            // finalClothesrackDB.execSQL("INSERT INTO Contacts (CName, Details, ContactsTD)Values('" + addContactName.getText() + "' , '" + addContactDetail.getText() + "' , '" + date + "');");
            // Toast.makeText(addcontact.this, addContactName.getText() + ":" + addContactDetail.getText(), Toast.LENGTH_SHORT).show();
           if(pos!=-1){
               db.updateContact(cID,addContactName.getText().toString(),addContactNo.getText().toString(),addContactDetail.getText().toString(),date);
               Intent in = new Intent(getApplicationContext(), Contact.class);
               setResult(RESULT_OK, in);
               finish();

           }

            else {
               String temp = addContactNo.getText().toString();

               db.insertContacts(addContactName.getText().toString(), addContactNo.getText().toString(), addContactDetail.getText().toString(), date);

               Intent in = new Intent(getApplicationContext(), Purchase.class);
               setResult(RESULT_OK, in);
               finish();
           }
        }
    }

    public void CancelContact(View view) {
        finish();
    }


    public boolean isAnyFieldEmpty() {
        if (checkEditText(addContactName, "Contact Name") && checkEditText(addContactNo, "Contact No.")
                && checkEditText(addContactDetail, "Contact Details")) {
            return true;
        } else
            return false;
    }

    public boolean checkEditText(EditText edtTxt, String toastMessage) {
        if (TextUtils.isEmpty(edtTxt.getText().toString())) {
            edtTxt.setError("Field cannot be blank");
            Toast.makeText(this, "Please Enter " + toastMessage, Toast.LENGTH_SHORT).show();

            return true;
        }
        return false;
    }
}
