package com.example.rushabh.clothesrack;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tushar on 9/11/2016.
 */
public class PurchaseBill extends Fragment implements View.OnClickListener {

    View myview;
    Button addEditFilters;
    Button removeFilters;
    cPurchaseBillAdaptor pBAdapter;
    cStockAdaptor cStockAdaptor;
    List<PurchaseBillModel> purchaseBillModel_Data = new ArrayList<>();
    SQLiteDatabase finalClothesrackDB;
    public PurchaseBill CustomPurchaseBillListView= null;
    public Stock CustomStockListView= null;
    public ArrayList<StockModel> CustomStockValueArr=new ArrayList<>();
    public ArrayList<PurchaseBillModel> CustomPurchaseBillValueArr =  new ArrayList<>();
    DatabaseHelper db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myview= inflater.inflate(R.layout.purchasebill, container,false);
        addEditFilters = (Button)myview.findViewById(R.id.addEditFilters);
        removeFilters = (Button)myview.findViewById(R.id.removeFilters);
        addEditFilters.setOnClickListener(this);
        removeFilters.setOnClickListener(this);
        db= new DatabaseHelper(getContext());
        populatePurchaseBillList();


        final ListView lv = (ListView)myview.findViewById(R.id.lstDemo);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openEditWindow(view,position);

            }
        });
        return myview;
    }

    public void openEditWindow(View editView,int position)
    {
        RelativeLayout editViewData = ((RelativeLayout) editView);
        PurchaseBillModel purchaseItem=new PurchaseBillModel();
        Intent intent = new Intent(getContext(),EditPurchase.class);
        Bundle b = new Bundle();

        String purID=((TextView)editViewData.findViewById(R.id.PurchaseIDView)).getText().toString();
        String billNO=((TextView)editViewData.findViewById(R.id.BillNoView)).getText().toString();
        String custName=((TextView)editViewData.findViewById(R.id.NameView)).getText().toString();
        b.putString("purchaseID", purID);
        b.putString("billNo", billNO);
        b.putString("custName", custName);
        //b.putString("editBrand", ((TextView)editViewData.findViewById(R.id.brands)).getText().toString());
        Toast.makeText(getContext(),purID,Toast.LENGTH_SHORT).show();
        //b.putString("editTotal", ((TextView)editViewData.findViewById(R.id.total)).getText().toString());
        b.putInt("editPosition", position);
        intent.putExtras(b);
        startActivityForResult(intent,2);
    }

    public void populatePurchaseBillList(){
        ListView List = (ListView)myview.findViewById(R.id.lstDemo);

        CustomPurchaseBillValueArr.clear();
        Cursor cursor = db.populatePurchaseBill();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                final PurchaseBillModel sm= new PurchaseBillModel();
                String PDate= cursor.getString(cursor.getColumnIndex("PDate"));
                String CID = cursor.getString(cursor.getColumnIndex("CID"));
                String contactName=db.getContactName(CID);

                sm.setPurchaseID(cursor.getString(cursor.getColumnIndex("PurID")));
                sm.setDate(PDate);
                sm.setName(contactName);
                sm.setBillNo(cursor.getString(cursor.getColumnIndex("BillNo")));
                sm.setTotalAmount(cursor.getString(cursor.getColumnIndex("BillAmt")));

                CustomPurchaseBillValueArr.add(sm);
                Resources res = getResources();
                pBAdapter = new cPurchaseBillAdaptor(getContext(),CustomPurchaseBillListView,CustomPurchaseBillValueArr,res);
                List.setAdapter(pBAdapter);

            } while (cursor.moveToNext());
        }
        cursor.close();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle response = data.getExtras();
        if(response!=null)
        {
            ListView List = (ListView)myview.findViewById(R.id.lstDemo);
            String filterBillNo = response.getString("filterBillNo");
            String filterContactName = response.getString("filterContactName");
            String filterContactID=db.getContactID(filterContactName);

            Cursor cursor = db.populateFilteredPurchaseBill(filterBillNo,filterContactID);
            CustomPurchaseBillValueArr.clear();
            // looping through all rows and adding to list
            if (cursor!=null && cursor.moveToFirst()) {
                do {
                    final PurchaseBillModel sm= new PurchaseBillModel();
                    String PDate= cursor.getString(cursor.getColumnIndex("PDate"));
                    String CID = cursor.getString(cursor.getColumnIndex("CID"));
                    String contactName=db.getContactName(CID);

                    sm.setDate(PDate);
                    sm.setName(contactName);
                    sm.setBillNo(cursor.getString(cursor.getColumnIndex("BillNo")));
                    sm.setTotalAmount(cursor.getString(cursor.getColumnIndex("BillAmt")));

                    CustomPurchaseBillValueArr.add(sm);
                    Resources res = getResources();
                    pBAdapter = new cPurchaseBillAdaptor(getContext(),CustomPurchaseBillListView,CustomPurchaseBillValueArr,res);

                    List.setAdapter(pBAdapter);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addEditFilters:
            {
                //Intent intent = new Intent(getContext(),BillDetails.class);
                Intent intent = new Intent(getContext(),PurchaseBillFilters.class);
                startActivityForResult(intent,1);
                break;
            }
            case R.id.removeFilters:
            {
                //Intent intent = new Intent(getContext(),BillDetails.class);
                populatePurchaseBillList();
                break;
            }

        }
    }
}
