package com.example.rushabh.clothesrack;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rushabh on 26-07-2016.
 */
public class Contact extends Fragment implements View.OnClickListener{
    View myview;
    cContactAdaptor sadapter;
    //    List<StockModel> stockmodel_data = new ArrayList<StockModel>();
    SQLiteDatabase finalClothesrackDB;
    public Contact CustomContactListView= null;
    public ArrayList<ContactModel> CustomContactValueArr = new ArrayList<>();
    DatabaseHelper db;
    Button addnewContact;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myview= inflater.inflate(R.layout.contact, container,false);

        addnewContact=(Button)myview.findViewById(R.id.addnewContact);
        addnewContact.setOnClickListener(this);
        db= new DatabaseHelper(getContext());

        final ListView lv = (ListView)myview.findViewById(R.id.contactListView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openEditWindow(view,position);

            }
        });
        populatecontact();


        return myview;
    }


    public void openEditWindow(View editView,int position) {
       LinearLayout editViewData = ((LinearLayout) editView);

        Intent intent = new Intent(getContext(), addStock.class);
        Bundle b = new Bundle();
        b.putString("itemid", ((TextView) editView.findViewById(R.id.ItemIdView)).getText().toString());
        b.putString("size", ((TextView) editView.findViewById(R.id.SizeView)).getText().toString());
        b.putString("qty", ((TextView) editView.findViewById(R.id.QtyView)).getText().toString());
        intent.putExtras(b);
        startActivityForResult(intent, 1);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
       ft.replace(R.id.content_frame,new Contact()).commit();
  //     ft.detach(this).attach(this).commit();
      //  fragmentManager.beginTransaction().replace(R.id.content_frame,new Stock()).commit();
    }





    public void populatecontact(){



      //  String selectQuery = "SELECT BrandName,ProdName,Size,Qty from Brand, Product,Stock where Stock.BrandID=Brand.BrandID AND Stock.ProdID =Product.ProdID";

        ListView List = (ListView)myview.findViewById(R.id.contactListView);
      //  String selectQuery="Select * from Stock";

        Cursor cursor = db.populateContact();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                final ContactModel sm= new ContactModel();
                String cid= cursor.getString(cursor.getColumnIndex("CID"));
                String cname = cursor.getString(cursor.getColumnIndex("CName"));
                String cnumber = cursor.getString(cursor.getColumnIndex("CNo"));
                sm.setcID(cid);
                sm.setcName(cname);
                sm.setcNumber(cnumber);

                CustomContactValueArr.add(sm);
                Resources res = getResources();
                sadapter = new cContactAdaptor(getContext(),CustomContactListView,CustomContactValueArr,res);
                List.setAdapter(sadapter);
               // names.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();




    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addnewContact:
            {}
        }
    }
}
