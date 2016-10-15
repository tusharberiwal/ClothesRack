package com.example.rushabh.clothesrack;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

public class EditSaleReturn extends Activity implements View.OnClickListener {
    View myview;
    Button scanSaleRet;
    Button addSaleRetOpen;

    Button saveSaleRet;
    Button cancelSaleRet;
    EditText commentSaleRet;
    DatabaseHelper db ;
    Spinner saleRetContactSpinner;
    cSaleAdaptor adapter;
    TextView saleretno;
    EditText againstSBno;

    public Sale CustomSaleRetListView= null;
    public ArrayList<SaleModel> CustomSaleRetValueArr =  new ArrayList<>();



    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salereturn);
        scanSaleRet= (Button)findViewById(R.id.scanSaleRet);
        scanSaleRet.setOnClickListener(this);
        addSaleRetOpen=(Button)findViewById(R.id.addSaleRetOpen);
        addSaleRetOpen.setOnClickListener(this);
        saveSaleRet=(Button)findViewById(R.id.saveSaleRet);
        saveSaleRet.setOnClickListener(this);
        cancelSaleRet=(Button)findViewById(R.id.cancelSaleRet);
        cancelSaleRet.setOnClickListener(this);
        commentSaleRet=(EditText)findViewById(R.id.commentSaleRet);
        saleretno=(TextView)findViewById(R.id.salereturnNo);
        againstSBno=(EditText)findViewById(R.id.againstSBno);
        db= new DatabaseHelper(this);
        saleRetContactSpinner=(Spinner)findViewById(R.id.saleRetContactSpinner);
        loadSpinnerDataName();
        loadEditData();


        final ListView lv = (ListView)findViewById(R.id.saleRetListView);

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


            saleretno.setText(ID);

            ArrayAdapter<String> array_spinner_brand = (ArrayAdapter<String>) saleRetContactSpinner.getAdapter();
            saleRetContactSpinner.setSelection(array_spinner_brand.getPosition(bundle.getString("custName")));

            CustomSaleRetValueArr.clear();
            Cursor cursor = db.getSaleReturnDetails(ID);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    final SaleModel pm = new SaleModel();
                    String itemID = cursor.getString(cursor.getColumnIndex("ItemID"));

                    List<String> details = new ArrayList<>();
                    details = db.getBrandFromItem(itemID);
                    //tempBrand= ;
                    //tempProduct=;
                    String tempCost = cursor.getString(cursor.getColumnIndex("SalesRetCost"));
                    String tempQTY = cursor.getString(cursor.getColumnIndex("SalesRetQty"));


                    int icost = Integer.valueOf(tempCost);
                    int iqty = Integer.valueOf(tempQTY);
                    int itotal = icost * iqty;

                    pm.setBrand(details.get(0));
                    pm.setProduct(details.get(1));
                    pm.setSize(details.get(2));
                    pm.setQty(tempQTY);
                    pm.setMrp(tempCost);
                    pm.setItemId(itemID);
                    pm.setsrno(cursor.getString(cursor.getColumnIndex("Srno")));

                    pm.setTotal(Integer.toString(itotal));

                    CustomSaleRetValueArr.add(pm);
                    Resources res = getResources();

                    ListView List1 = (ListView)findViewById(R.id.saleRetListView);
                    adapter = new cSaleAdaptor(this,CustomSaleRetListView,CustomSaleRetValueArr,res);

                    List1.setAdapter(adapter);

                    //  adapter = new cPurRetAdaptor(this, CustomPurRetListView, CustomPurchaseRetValueArr, res);
                    // List.setAdapter(adapter);

                } while (cursor.moveToNext());
            }
            cursor.close();

        }
    }

    public void openEditWindow(View editView,int position)
    {
        RelativeLayout editViewData = ((RelativeLayout) editView);
        SaleReturnModel saleItem=new SaleReturnModel();
        Intent intent = new Intent(getApplicationContext(),addsalereturn.class);
        Bundle b = new Bundle();

        b.putString("editSizesale", ((TextView)editViewData.findViewById(R.id.sizes)).getText().toString());
        b.putString("editBrand", ((TextView)editViewData.findViewById(R.id.brands)).getText().toString());
        b.putString("editProduct", ((TextView)editViewData.findViewById(R.id.products)).getText().toString());
        b.putInt("editQuantitysale", Integer.valueOf(((TextView)editViewData.findViewById(R.id.qtys)).getText().toString()));
        //b.putInt("editCost", Integer.valueOf(((TextView)editViewData.findViewById(R.id.cost)).getText().toString()));
        b.putInt("editMrpsale", Integer.valueOf(((TextView)editViewData.findViewById(R.id.mrps)).getText().toString()));
        b.putInt("editItemIDsale",Integer.valueOf(((TextView)editViewData.findViewById(R.id.itemids)).getText().toString()));
        //b.putString("editTotal", ((TextView)editViewData.findViewById(R.id.total)).getText().toString());
        b.putString("srno",((TextView)editViewData.findViewById(R.id.srno)).getText().toString());
        b.putInt("editPositionsale", position);
        intent.putExtras(b);
        startActivityForResult(intent,2);

        /*
        RelativeLayout editViewData = ((RelativeLayout) editView);
        SaleModel saleItem=new SaleModel();
        Intent intent = new Intent(this,addsalereturn.class);
        Bundle b = new Bundle();

        b.putString("editSizesale", ((TextView)editViewData.findViewById(R.id.sizesret)).getText().toString());
        b.putString("editBrand", ((TextView)editViewData.findViewById(R.id.brandsret)).getText().toString());
        b.putString("editProduct", ((TextView)editViewData.findViewById(R.id.productsret)).getText().toString());
        b.putInt("editQuantitysale", Integer.valueOf(((TextView)editViewData.findViewById(R.id.qtysret)).getText().toString()));
        //b.putInt("editCost", Integer.valueOf(((TextView)editViewData.findViewById(R.id.cost)).getText().toString()));
        b.putInt("editMrpsale", Integer.valueOf(((TextView)editViewData.findViewById(R.id.mrpsret)).getText().toString()));
        b.putInt("editItemIDsale",Integer.valueOf(((TextView)editViewData.findViewById(R.id.itemidsret)).getText().toString()));
        //b.putString("editTotal", ((TextView)editViewData.findViewById(R.id.total)).getText().toString());
        b.putInt("editPositionsale", position);
        intent.putExtras(b);
     /  startActivityForResult(intent,2);

    */
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public void loadSpinnerDataName( ) {
        // Spinner Drop down elements
        List<String> names = db.getContactNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, names);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        saleRetContactSpinner.setAdapter(dataAdapter);
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("@@","In activity result");

        Bundle response = data.getExtras();
        if(response!=null) {
            String brand = response.getString("brand");
            String product = response.getString("product");
            String size = response.getString("size");
            String quantity = response.getString("quantity");
            String mrp = response.getString("mrp");
            String itemid= response.getString("itemid");
            String srno = response.getString("srno");
            int imrp= Integer.valueOf(mrp);
            int iqty=Integer.valueOf(quantity);
            int itotal= imrp*iqty;
            String total = String.valueOf(itotal);
            int pos=response.getInt("editPosition");
            addtolist(brand, product, size, quantity, mrp, total,itemid,pos,srno);
        }
    }


    public void addtolist(String brand, String product, String size, String qty, String mrp,String total,String itemid,int pos,String srno){
        final SaleModel sm= new SaleModel();
        Log.d("!!!","in add to list");
        sm.setBrand(brand);
        sm.setProduct(product);
        sm.setSize(size);
        sm.setQty(qty);
        sm.setMrp(mrp);
        sm.setTotal(total);
        sm.setItemId(itemid);
        sm.setsrno(srno);
        if(pos==-1)
            CustomSaleRetValueArr.add(sm);
        else
            CustomSaleRetValueArr.set(pos,sm);
        Resources res = getResources();
        ListView List = (ListView)findViewById(R.id.saleRetListView);
        adapter = new cSaleAdaptor(this,CustomSaleRetListView,CustomSaleRetValueArr,res);

        List.setAdapter(adapter);
    }



    public void savedata() {

        String Cname = saleRetContactSpinner.getSelectedItem().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
        String cid = db.getContactID(Cname);


        db.updateSalesReturn(cid,againstSBno.getText().toString(), date, "123", commentSaleRet.getText().toString(), date, saleretno.getText().toString());
        int dbcount= db.getCountSalesReturnDetails(saleretno.getText().toString());
        ListView listView = (ListView) findViewById(R.id.saleRetListView);
        View v;
        int count = listView.getChildCount();
        String itemid;
        TextView tempProduct;
        TextView tempBrand;
        TextView tempSize;
        TextView tempQty;
        TextView tempMRP;
        TextView tempItemID;
        TextView srno;
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                v = listView.getChildAt(i);

                tempQty = (TextView) v.findViewById(R.id.qtys);
                tempMRP = (TextView) v.findViewById(R.id.mrps);
                tempItemID = (TextView) v.findViewById(R.id.itemids);
                srno=(TextView)v.findViewById(R.id.srno);
                String Srno = srno.getText().toString();
                int stockqty = db.getStockQty(tempItemID.getText().toString());
                int qty = Integer.valueOf(tempQty.getText().toString());

                String dbitemid="";
                int dbqty=0;
                int dbcost=0;
                Cursor c = db.getSalesReturnDetailsforSrno(Srno);

                if(c.moveToFirst()){
                    dbitemid=c.getString(c.getColumnIndex("ItemID"));
                    dbqty=Integer.valueOf(c.getString(c.getColumnIndex("SalesRetQty")));

                    dbcost=Integer.valueOf(c.getString(c.getColumnIndex("SalesRetCost")));
                }
                c.close();

                if(i<dbcount)
                {
                    if(dbitemid.equals(tempItemID.getText().toString())){
                        if(dbqty==qty)
                        {
                            // db.updateSalesDetails(tempItemID.getText().toString(),String.valueOf(qty),tempMRP.getText().toString(),date,tempsrno.getText().toString());
                            db.updateSalesReturnDetails(tempItemID.getText().toString(),Srno,String.valueOf(qty),tempMRP.getText().toString(),date);
                        }
                        else if(qty<dbqty){
                            stockqty= stockqty -dbqty+qty;
                            db.updateSalesReturnDetails(tempItemID.getText().toString(),Srno,String.valueOf(qty),tempMRP.getText().toString(),date);
                            db.updateQtyStock(tempItemID.getText().toString(),String.valueOf(stockqty),date);

                        }
                        else if (dbqty<qty){
                            stockqty= stockqty-dbqty+qty;
                            db.updateSalesReturnDetails(tempItemID.getText().toString(),Srno,String.valueOf(qty),tempMRP.getText().toString(),date);
                            db.updateQtyStock(tempItemID.getText().toString(),String.valueOf(stockqty),date);
                        }
                    }
                    else
                    {
                        int oldstockqty=db.getStockQty(dbitemid);
                        oldstockqty-=dbqty;
                        db.updateQtyStock(String.valueOf(oldstockqty),dbitemid,date);
                        stockqty+=qty;
                        db.updateQtyStock(String.valueOf(stockqty),tempItemID.getText().toString(),date);
                        db.updateSalesReturnDetails(tempItemID.getText().toString(),Srno,String.valueOf(qty),tempMRP.getText().toString(),date);

                    }

                }
                else {

                    stockqty= stockqty+qty;
                    db.insertSalesReturnDetails(tempItemID.getText().toString(), saleretno.getText().toString(), tempQty.getText().toString(), tempMRP.getText().toString(), date);
                    db.updateQtyStock(tempItemID.getText().toString(), String.valueOf(stockqty), date);
                }
            }
        }



    }

    public void setSaleRetNo(){
        String srno= db.getNextSaleRetNo();
        int nextsrno=Integer.valueOf(srno);
        nextsrno=nextsrno+1;
       // Toast.makeText(getContext(),String.valueOf(nextbillno),Toast.LENGTH_LONG).show();
        saleretno.setText(String.valueOf(nextsrno));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.scanSaleRet:
            {

            }

            case R.id.addSaleRetOpen:
            {
                Intent intent= new Intent(this,addsalereturn.class);
                startActivityForResult(intent,1);
                break;
            }
            case R.id.saveSaleRet:
            {
               savedata();
                break;
            }
            case R.id.cancelSale:
            {

            }
        }

    }
}
