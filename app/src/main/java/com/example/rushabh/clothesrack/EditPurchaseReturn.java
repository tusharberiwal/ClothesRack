package com.example.rushabh.clothesrack;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public  class EditPurchaseReturn extends Activity implements View.OnClickListener{

    View myview;
    cPurRetAdaptor adapter;
    Button addpr;
    Spinner spinner;
    Button savepr;
    TextView purRetNo;
    EditText againstBno;
    SQLiteDatabase finalClothesrackDB;
   int contactupdate=0;
    public PurchaseReturn CustomPurRetListView= null;
    public ArrayList<PurchaseReturnModel> CustomPurchaseRetValueArr =  new ArrayList<PurchaseReturnModel>();
    private DatabaseHelper db;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchasereturn);
        db= new DatabaseHelper(this);
        addpr = (Button)findViewById(R.id.addpr);
        addpr.setOnClickListener(this);
        savepr = (Button)findViewById(R.id.savepr);
        savepr.setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.nameInputpr);
        purRetNo=(TextView)findViewById(R.id.purRetNo);
        againstBno=(EditText)findViewById(R.id.againstBno) ;


        loadSpinnerDataName();
        loadEditData();
        //setPurRetNo();

        final ListView lv = (ListView)findViewById(R.id.purRetListView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openEditWindow(view,position);

            }
        });




    }

    public void loadEditData() {
        Bundle bundle = getIntent().getExtras();
        String ID;
        if (bundle != null) {
            ID = bundle.getString("returnID");
            ListView List = (ListView) findViewById(R.id.purRetListView);
            Cursor cursor = db.getPurchaseReturnDetails(ID);
            purRetNo.setText(ID);

            ArrayAdapter<String> array_spinner_brand = (ArrayAdapter<String>) spinner.getAdapter();
            spinner.setSelection(array_spinner_brand.getPosition(bundle.getString("custName")));

            CustomPurchaseRetValueArr.clear();

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    final PurchaseReturnModel pm = new PurchaseReturnModel();
                    String itemID = cursor.getString(cursor.getColumnIndex("ItemID"));

                    List<String> details = new ArrayList<>();
                    details = db.getBrandFromItem(itemID);
                    //tempBrand= ;
                    //tempProduct=;
                    String tempCost = cursor.getString(cursor.getColumnIndex("PurRetCost"));
                    String tempQTY = cursor.getString(cursor.getColumnIndex("PurRetQty"));



                    int icost = Integer.valueOf(tempCost);
                    int iqty = Integer.valueOf(tempQTY);
                    int itotal = icost * iqty;

                    pm.setBrand(details.get(0));
                    pm.setProduct(details.get(1));
                    pm.setSize(details.get(2));
                    pm.setQty(tempQTY);
                    pm.setCost(tempCost);
                    pm.setItemid(itemID);
                    pm.setsrno(cursor.getString(cursor.getColumnIndex("Srno")));

                    pm.setTotal(Integer.toString(itotal));

                    CustomPurchaseRetValueArr.add(pm);
                    Resources res = getResources();

                    ListView List1 = (ListView)findViewById(R.id.purRetListView);
                    adapter = new cPurRetAdaptor(this,CustomPurRetListView,CustomPurchaseRetValueArr,res);

                    List1.setAdapter(adapter);

                  //  adapter = new cPurRetAdaptor(this, CustomPurRetListView, CustomPurchaseRetValueArr, res);
                   // List.setAdapter(adapter);

                } while (cursor.moveToNext());
            }
            cursor.close();

        }
    }


       public void openEditWindow(View editView, int position)
    {
        RelativeLayout editViewData = ((RelativeLayout) editView);
        PurchaseReturnModel purchaseItem=new PurchaseReturnModel();
        Intent intent = new Intent(this,addpurchasereturn.class);
        Bundle b = new Bundle();


        b.putString("editSize", ((TextView)editViewData.findViewById(R.id.sizepret)).getText().toString());
        b.putString("editBrand", ((TextView)editViewData.findViewById(R.id.brandpret)).getText().toString());
        b.putString("editProduct", ((TextView)editViewData.findViewById(R.id.productpret)).getText().toString());
        b.putInt("editQuantity", Integer.valueOf(((TextView)editViewData.findViewById(R.id.qtypret)).getText().toString()));
        b.putInt("editCost", Integer.valueOf(((TextView)editViewData.findViewById(R.id.costpret)).getText().toString()));
        b.putInt("editItemID",Integer.valueOf(((TextView)editViewData.findViewById(R.id.itemidpret)).getText().toString()));
        b.putInt("editPosition", position);
        b.putString("srno",((TextView)editView.findViewById(R.id.srno)).getText().toString());
        intent.putExtras(b);
        startActivityForResult(intent,2);
    }




    public void savedata() {

        EditText comment = (EditText) findViewById(R.id.purRetComment);
        String Cname = spinner.getSelectedItem().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
        String cid = db.getContactID(Cname);
        int grandTotal = 0;
     //   Boolean purretentry= Boolean.FALSE;

        db.insertPurchaseReturn(cid,againstBno.getText().toString(), date, "123", comment.getText().toString(), date);

        ListView listView = (ListView) findViewById(R.id.purRetListView);

        View v;
        int count = listView.getChildCount();
        int dbcount= db.getCountPurchaseReturnDetails(purRetNo.getText().toString());

        TextView tempCost;
        TextView tempQty;
        TextView tempItemID;
        TextView srno;
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                v = listView.getChildAt(i);

                tempCost = (TextView) v.findViewById(R.id.costpret);
                tempQty = (TextView) v.findViewById(R.id.qtypret);
                tempItemID = (TextView) v.findViewById(R.id.itemidpret);
                srno=(TextView)v.findViewById(R.id.srno);
                String Srno = srno.getText().toString();
                int stockqty = db.getStockQty(tempItemID.getText().toString());
                int qty = Integer.valueOf(tempQty.getText().toString());
                Cursor c = db.getPurchaseReturnDetailsforSrno(Srno);
                String dbitemid="";
                int dbqty=0;
                int dbcost=0;
                if(c.moveToFirst()){
                    dbitemid=c.getString(c.getColumnIndex("ItemID"));
                    dbqty=Integer.valueOf(c.getString(c.getColumnIndex("PurRetQty")));

                    dbcost=Integer.valueOf(c.getString(c.getColumnIndex("PurRetCost")));
                }
                c.close();
                if(i<dbcount)
                {
                    if(dbitemid.equals(tempItemID.getText().toString())){
                        if(dbqty==qty)
                        {
                           // db.updateSalesDetails(tempItemID.getText().toString(),String.valueOf(qty),tempMRP.getText().toString(),date,tempsrno.getText().toString());
                            db.updatePurchaseReturnDetails(tempItemID.getText().toString(),String.valueOf(qty),tempCost.getText().toString(),date,Srno);
                        }
                        else if(qty<dbqty){
                            stockqty= stockqty +dbqty-qty;
                            db.updatePurchaseReturnDetails(tempItemID.getText().toString(),String.valueOf(qty),tempCost.getText().toString(),date,Srno);
                            db.updateQtyStock(tempItemID.getText().toString(),String.valueOf(stockqty),date);

                        }
                        else if (dbqty<qty){
                            stockqty= stockqty+dbqty-qty;
                            db.updatePurchaseReturnDetails(tempItemID.getText().toString(),String.valueOf(qty),tempCost.getText().toString(),date,Srno);
                            db.updateQtyStock(tempItemID.getText().toString(),String.valueOf(stockqty),date);
                        }
                    }
                    else
                    {
                        int oldstockqty=db.getStockQty(dbitemid);
                        oldstockqty+=dbqty;
                        db.updateQtyStock(String.valueOf(oldstockqty),dbitemid,date);
                        stockqty-=qty;
                        db.updateQtyStock(String.valueOf(stockqty),tempItemID.getText().toString(),date);
                        db.updatePurchaseReturnDetails(tempItemID.getText().toString(),String.valueOf(qty),tempCost.getText().toString(),date,Srno);

                    }

                }
                else {


                    stockqty = stockqty - qty;
                    db.insertPurchaseReturnDetails(tempItemID.getText().toString(), purRetNo.getText().toString(), tempQty.getText().toString(), tempCost.getText().toString(), date);
                    db.updateQtyStock(tempItemID.getText().toString(), String.valueOf(stockqty), date);
                }
            }
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
               Bundle response = data.getExtras();
                if (response != null) {
                    String brand = response.getString("brand");
                    String product = response.getString("product");
                    String size = response.getString("size");
                    String quantity = response.getString("quantity");
                    String cost = response.getString("cost");
                    String srno= response.getString("srno");
                    int icost = Integer.valueOf(cost);
                    int iqty = Integer.valueOf(quantity);
                    int itotal = icost * iqty;
                    String total = String.valueOf(itotal);
                    String itemid= response.getString("itemid");

                    int pos = response.getInt("editPosition");
                  AddEditToList(brand, product, size, quantity, cost,total,itemid, pos,srno);
                }

    }


    public void AddEditToList(String brand, String product, String size, String qty, String cost,String total,String itemid, int pos,String srno)
    {
        final PurchaseReturnModel pm= new PurchaseReturnModel();
        pm.setBrand(brand);
        pm.setProduct(product);
        pm.setSize(size);
        pm.setQty(qty);
        pm.setCost(cost);
        pm.setTotal(total);
        pm.setItemid(itemid);
        pm.setsrno(srno);


        if(pos==-1)
            CustomPurchaseRetValueArr.add(pm);
        else
            CustomPurchaseRetValueArr.set(pos,pm);

        Resources res = getResources();
        ListView List = (ListView)findViewById(R.id.purRetListView);
        adapter = new cPurRetAdaptor(this,CustomPurRetListView,CustomPurchaseRetValueArr,res);

        List.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addpr:
            {
                Intent intent = new Intent(this,addpurchasereturn.class);
                startActivityForResult(intent,1);
                break;
            }

            case R.id.savepr:
            {
                savedata();
                break;
            }
        }
    }

  /*  public void onSaveInstanceState(Bundle outState){
        getFragmentManager().putFragment(outState,"myfragment",getApplicationContext());
        outState.putInt("spinnercontact",spinner.getSelectedItemPosition());
    }*/

    public void loadSpinnerDataName( ) {
        // Spinner Drop down elements
       List<String> names = db.getContactNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, names);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    public void setPurRetNo(){
        String prno= db.getNextPurRetNo();
        int nextprno=Integer.valueOf(prno);
        nextprno=nextprno+1;
        // Toast.makeText(getContext(),String.valueOf(nextbillno),Toast.LENGTH_LONG).show();
        purRetNo.setText(String.valueOf(nextprno));
    }

}

