package com.example.rushabh.clothesrack;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rushabh on 22-09-2016.
 */

public class test  extends Fragment {
    View myview;
    SQLiteDatabase finalClothesrackDB;
    public test CustomTestListView = null;
    public ArrayList<TestModel> CustomStockValueArr =  new ArrayList<TestModel>();
    public ArrayList<TestModel> CustomStockValueArr1 =  new ArrayList<TestModel>();
    cTestAdaptor sadapter;
    int typeValue;

    DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myview= inflater.inflate(R.layout.test, container,false);
        db= new DatabaseHelper(getContext());
        populatedata();

        final ListView lv= (ListView)myview.findViewById(R.id.testListView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openEditWindow(view,position,id);

            }
        });


    return myview;
    }

    public void openEditWindow(View editView,int position,long id)
    {

        String test= ((TextView)editView.findViewById(R.id.typeView)).getText().toString();
        Toast.makeText(getContext(),test,Toast.LENGTH_LONG).show();

        if(test.equals("Purchase"))
        {
            Intent intent = new Intent(getContext(),EditPurchase.class);
            Bundle b = new Bundle();

            String purID=((TextView)editView.findViewById(R.id.PurchaseIDView)).getText().toString();
            String billNO=((TextView)editView.findViewById(R.id.BillNoView)).getText().toString();
            String custName=((TextView)editView.findViewById(R.id.NameView)).getText().toString();
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
        else if(test.equals("Sale"))
        {
            Intent intent = new Intent(getContext(),EditSale.class);
            Bundle b = new Bundle();

             String billNO=((TextView)editView.findViewById(R.id.sBillNoView)).getText().toString();
            String custName=((TextView)editView.findViewById(R.id.sNameView)).getText().toString();

            b.putString("billNo", billNO);
            b.putString("custName", custName);
            //b.putString("editBrand", ((TextView)editViewData.findViewById(R.id.brands)).getText().toString());
           // Toast.makeText(getContext(),purID,Toast.LENGTH_SHORT).show();
            //b.putString("editTotal", ((TextView)editViewData.findViewById(R.id.total)).getText().toString());
            b.putInt("editPosition", position);
            intent.putExtras(b);
            startActivityForResult(intent,2);
        }
        else if(test.equals("Payment")||test.equals("Discount"))
        {
            Intent intent= new Intent(getContext(),EditPayment.class);
            Bundle b = new Bundle();
            String no=((TextView)editView.findViewById(R.id.paymentidview)).getText().toString();
            b.putString("custName",((TextView)editView.findViewById(R.id.paymentnameview)).getText().toString());
            b.putString("No",no);
            b.putString("Type",test);
            b.putInt("editPosition",position);
            intent.putExtras(b);
            startActivityForResult(intent,2);
        }
        else if(test.equals("Purchase Return"))
        {
            Intent intent= new Intent(getContext(),EditPurchaseReturn.class);
            Bundle b = new Bundle();
            String purretid=((TextView)editView.findViewById(R.id.returnIDView)).getText().toString();
            String custName=((TextView)editView.findViewById(R.id.nameView)).getText().toString();
            b.putString("returnID",purretid);
            b.putInt("editPosition",position);
            b.putString("custName",custName);
            intent.putExtras(b);
            startActivityForResult(intent,2);
        }
        else if(test.equals("Sale Return"))
        {
            Intent intent= new Intent(getContext(),EditSaleReturn.class);
            Bundle b = new Bundle();
            String saleretid=((TextView)editView.findViewById(R.id.returnIDView)).getText().toString();
            String custName=((TextView)editView.findViewById(R.id.nameView)).getText().toString();
            b.putString("returnID",saleretid);
            b.putInt("editPosition",position);
            b.putString("custName",custName);
            intent.putExtras(b);
            startActivityForResult(intent,2);
        }
    }

    public void populatedata()
    {

        //  String selectQuery = "SELECT BrandName,ProdName,Size,Qty from Brand, Product,Stock where Stock.BrandID=Brand.BrandID AND Stock.ProdID =Product.ProdID";

        ListView list = (ListView)myview.findViewById(R.id.testListView) ;

        Cursor  cursor1 = db.populateStock();

        // looping through all rows and adding to list
        if (cursor1.moveToNext()) {
            do {
                final TestModel sm= new TestModel();

                String itemid= cursor1.getString(cursor1.getColumnIndex("ItemID"));
                String bname = cursor1.getString(cursor1.getColumnIndex("BrandName"));
                String pname = cursor1.getString(cursor1.getColumnIndex("ProdName"));
                sm.setType("0");
                sm.setItemID(itemid);
                sm.setProdName(bname + " " +pname);
                sm.setSize(cursor1.getString(cursor1.getColumnIndex("Size")));
                sm.setQty(cursor1.getString(cursor1.getColumnIndex("Qty")));

                CustomStockValueArr.add(sm);
                setTypeValue(0);

                Resources res = getResources();
                sadapter = new cTestAdaptor(getContext(),CustomTestListView,CustomStockValueArr,res,0);
                list.setAdapter(sadapter);




                // names.add(cursor.getString(1));
            } while (cursor1.moveToNext());
        }
        cursor1.close();

        Cursor cursor = db.populatePurchaseBill();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                final TestModel sm= new TestModel();
                //  String PDate = cursor.getString(cursor.getColumnIndex("PDate"));
                String CID = cursor.getString(cursor.getColumnIndex("CID"));
                String contactName = db.getContactName(CID);
                String PDate=new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
                sm.setType("1");
                sm.setPurchaseID(cursor.getString(cursor.getColumnIndex("PurID")));
                sm.setDate(PDate);
                sm.setName(contactName);
                sm.setBillNo(cursor.getString(cursor.getColumnIndex("BillNo")));
                sm.setTotalAmount(cursor.getString(cursor.getColumnIndex("BillAmt")));

                CustomStockValueArr.add(sm);
                setTypeValue(1);
                Resources res = getResources();
                sadapter = new cTestAdaptor(getContext(), CustomTestListView, CustomStockValueArr, res, 1);
                list.setAdapter(sadapter);

            } while (cursor.moveToNext());
        }
        cursor.close();



        // TestModel sm= new TestModel();

        Cursor cursor2 = db.populateSaleBill();
        // looping through all rows and adding to list
        if (cursor2.moveToFirst()) {
            do {

                final TestModel sm= new TestModel();
                //  String PDate = cursor.getString(cursor.getColumnIndex("PDate"));
                String CID = cursor2.getString(cursor2.getColumnIndex("Cid"));
                String contactName = db.getContactName(CID);
                String PDate=new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
                sm.setType("2");
//                sm.setPurchaseID(cursor2.getString(cursor2.getColumnIndex("PurID")));
                sm.setsDate(PDate);
                sm.setsName(contactName);
                sm.setsBillNo(cursor2.getString(cursor2.getColumnIndex("BillNo")));
                sm.setsTotalAmount(cursor2.getString(cursor2.getColumnIndex("BillAmt")));

                CustomStockValueArr.add(sm);
                setTypeValue(2);
                Resources res = getResources();
                sadapter = new cTestAdaptor(getContext(), CustomTestListView, CustomStockValueArr, res, 2);
                list.setAdapter(sadapter);

            } while (cursor2.moveToNext());
        }
        cursor2.close();

        Cursor cursor3 = db.populatePayment();
        // looping through all rows and adding to list
        if (cursor3.moveToFirst()) {
            do {

                final TestModel sm= new TestModel();
                //  String PDate = cursor.getString(cursor.getColumnIndex("PDate"));
                String CID = cursor3.getString(cursor3.getColumnIndex("CID"));
                String contactName = db.getContactName(CID);
                String PDate=new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
                sm.setType("3");
//                sm.setPurchaseID(cursor2.getString(cursor2.getColumnIndex("PurID")));
                sm.setPaymentDate(PDate);
                sm.setPaymentName(contactName);
                sm.setPaymentID(cursor3.getString(cursor3.getColumnIndex("PayNo")));
                sm.setPaymentAmount(cursor3.getString(cursor3.getColumnIndex("Amt")));
                sm.setPaymentAgainst(cursor3.getString(cursor3.getColumnIndex("ID")));
                CustomStockValueArr.add(sm);
                setTypeValue(3);
                Resources res = getResources();
                sadapter = new cTestAdaptor(getContext(), CustomTestListView, CustomStockValueArr, res, 3);
                list.setAdapter(sadapter);

            } while (cursor3.moveToNext());
        }
        cursor3.close();

        Cursor cursor4 = db.populateDiscount();
        // looping through all rows and adding to list
        if (cursor4.moveToFirst()) {
            do {

                final TestModel sm= new TestModel();
                //  String PDate = cursor.getString(cursor.getColumnIndex("PDate"));
                String CID = cursor4.getString(cursor4.getColumnIndex("CID"));
                String contactName = db.getContactName(CID);
                String PDate=new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
                sm.setType("4");
//                sm.setPurchaseID(cursor2.getString(cursor2.getColumnIndex("PurID")));
                sm.setPaymentDate(PDate);
                sm.setPaymentName(contactName);
                sm.setPaymentID(cursor4.getString(cursor4.getColumnIndex("DiscNo")));
                sm.setPaymentAmount(cursor4.getString(cursor4.getColumnIndex("DiscAmt")));
                sm.setPaymentAgainst(cursor4.getString(cursor4.getColumnIndex("DiscID")));
                CustomStockValueArr.add(sm);
                setTypeValue(4);
                Resources res = getResources();
                sadapter = new cTestAdaptor(getContext(), CustomTestListView, CustomStockValueArr, res, 4);
                list.setAdapter(sadapter);

            } while (cursor4.moveToNext());
        }
        cursor4.close();


        Cursor cursor5 = db.populatePurchaseReturn();
        // looping through all rows and adding to list
        if (cursor5.moveToFirst()) {
            do {

                final TestModel sm= new TestModel();
                //  String PDate = cursor.getString(cursor.getColumnIndex("PDate"));
                String CID = cursor5.getString(cursor5.getColumnIndex("Cid"));
                String contactName = db.getContactName(CID);
                String PDate=new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
                sm.setType("5");
//                sm.setPurchaseID(cursor2.getString(cursor2.getColumnIndex("PurID")));
                sm.setReturnDate(PDate);
                sm.setReturnName(contactName);
                sm.setReturnID(cursor5.getString(cursor5.getColumnIndex("PurRetID")));
                sm.setReturnAmount(cursor5.getString(cursor5.getColumnIndex("PRAmt")));
                sm.setReturnAgainst(cursor5.getString(cursor5.getColumnIndex("PAgainstBNO")));
                CustomStockValueArr.add(sm);
                setTypeValue(5);
                Resources res = getResources();
                sadapter = new cTestAdaptor(getContext(), CustomTestListView, CustomStockValueArr, res, 5);
                list.setAdapter(sadapter);

            } while (cursor5.moveToNext());
        }
        cursor5.close();


        Cursor cursor6 = db.populateSaleReturn();
        // looping through all rows and adding to list
        if (cursor6.moveToFirst()) {
            do {

                final TestModel sm= new TestModel();
                //  String PDate = cursor.getString(cursor.getColumnIndex("PDate"));
                String CID = cursor6.getString(cursor6.getColumnIndex("Cid"));
                String contactName = db.getContactName(CID);
                String PDate=new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
                sm.setType("6");
//                sm.setPurchaseID(cursor2.getString(cursor2.getColumnIndex("PurID")));
                sm.setReturnDate(PDate);
                sm.setReturnName(contactName);
                sm.setReturnID(cursor6.getString(cursor6.getColumnIndex("SaleRetID")));
                sm.setReturnAmount(cursor6.getString(cursor6.getColumnIndex("SRAmt")));
                sm.setReturnAgainst(cursor6.getString(cursor6.getColumnIndex("SAgainstBNO")));
                CustomStockValueArr.add(sm);
                setTypeValue(6);
                Resources res = getResources();
                sadapter = new cTestAdaptor(getContext(), CustomTestListView, CustomStockValueArr, res, 6);
                list.setAdapter(sadapter);


            } while (cursor6.moveToNext());
        }
        cursor6.close();






    }

    public void setTypeValue(int value){

        typeValue= value;


    }
    public int getTypeValue(){
        return typeValue;
    }



}