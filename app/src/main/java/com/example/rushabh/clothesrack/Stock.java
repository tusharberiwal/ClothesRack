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
public class Stock extends Fragment implements View.OnClickListener{
    View myview;
    cStockAdaptor sadapter;
//    List<StockModel> stockmodel_data = new ArrayList<StockModel>();
    SQLiteDatabase finalClothesrackDB;
    public Stock CustomStockListView= null;
    public ArrayList<StockModel> CustomStockValueArr =  new ArrayList<StockModel>();
    DatabaseHelper db;


    Button addprod;
    Button addbrand;
    Button addstock;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myview= inflater.inflate(R.layout.stock, container,false);
        addprod = (Button)myview.findViewById(R.id.addprod);
        addprod.setOnClickListener(this);
        addbrand= (Button) myview.findViewById(R.id.addbrand);
        addbrand.setOnClickListener(this);
        addstock=(Button)myview.findViewById(R.id.addstock);
        addstock.setOnClickListener(this);
        db= new DatabaseHelper(getContext());

        final ListView lv = (ListView)myview.findViewById(R.id.stocklistView1);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openEditWindow(view,position);

            }
        });
        populatestock();


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
       ft.replace(R.id.content_frame,new Stock()).commit();
  //     ft.detach(this).attach(this).commit();
      //  fragmentManager.beginTransaction().replace(R.id.content_frame,new Stock()).commit();
    }





    public void populatestock(){



      //  String selectQuery = "SELECT BrandName,ProdName,Size,Qty from Brand, Product,Stock where Stock.BrandID=Brand.BrandID AND Stock.ProdID =Product.ProdID";

        ListView List = (ListView)myview.findViewById(R.id.stocklistView1);
      //  String selectQuery="Select * from Stock";

        Cursor cursor = db.populateStock();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                final StockModel sm= new StockModel();
                String itemid= cursor.getString(cursor.getColumnIndex("ItemID"));
                String bname = cursor.getString(cursor.getColumnIndex("BrandName"));
                String pname = cursor.getString(cursor.getColumnIndex("ProdName"));
                sm.setItemID(itemid);
                sm.setProdName(bname + " " +pname);
                sm.setSize(cursor.getString(cursor.getColumnIndex("Size")));
                sm.setQty(cursor.getString(cursor.getColumnIndex("Qty")));
                CustomStockValueArr.add(sm);
                Resources res = getResources();
                sadapter = new cStockAdaptor(getContext(),CustomStockListView,CustomStockValueArr,res);
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
            case R.id.addprod:
            {
                Intent intent = new Intent(getContext(),addproduct.class);
                startActivity(intent);
                break;
            }
            case R.id.addbrand:
            {
                Intent intent = new Intent(getContext(),addbrand.class);
                startActivity(intent);

            }
            case R.id.addstock:
            {
                Intent intent= new Intent(getContext(),addStock.class);
                startActivity(intent);
            }
        }
    }
}
