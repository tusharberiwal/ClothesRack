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

public class EditSale extends Activity implements View.OnClickListener {
    View myview;
    Button scanSale;
    Button addSaleOpen;
    Button addContact;
    Button saveSale;
    Button cancelSale;
    EditText commentSale;
    DatabaseHelper db ;
    Spinner saleContactSpinner;
    cSaleAdaptor adapter;
    TextView billno;

    public Sale CustomSaleListView= null;
    public ArrayList<SaleModel> CustomSaleValueArr =  new ArrayList<>();



    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale);
        scanSale= (Button)findViewById(R.id.scanSale);
        scanSale.setOnClickListener(this);
        addSaleOpen=(Button)findViewById(R.id.addSaleOpen);
        addSaleOpen.setOnClickListener(this);
        addContact=(Button)findViewById(R.id.addcontact);
        addContact.setOnClickListener(this);
        saveSale=(Button)findViewById(R.id.saveSale);
        saveSale.setOnClickListener(this);
        cancelSale=(Button)findViewById(R.id.cancelSale);
        cancelSale.setOnClickListener(this);
        commentSale=(EditText)findViewById(R.id.commentSale);
        billno=(TextView)findViewById(R.id.billno);
        db = new DatabaseHelper(this);

        saleContactSpinner=(Spinner)findViewById(R.id.saleContactSpinner);
        loadSpinnerDataName();
        loadEditData();
//        setBillno();

        final ListView lv = (ListView)findViewById(R.id.saleListView);

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
            ID = bundle.getString("billNo");
            ListView List = (ListView) findViewById(R.id.saleListView);
            billno.setText(bundle.getString("billNo"));

            ArrayAdapter<String> array_spinner_brand = (ArrayAdapter<String>) saleContactSpinner.getAdapter();
            saleContactSpinner.setSelection(array_spinner_brand.getPosition(bundle.getString("custName")));

            CustomSaleValueArr.clear();
            Cursor cursor = db.getSaleDetails(ID);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    final SaleModel pm = new SaleModel();
                    String itemID = cursor.getString(cursor.getColumnIndex("ItemID"));

                    List<String> details = new ArrayList<>();
                    details = db.getBrandFromItem(itemID);
                    //tempBrand= ;
                    //tempProduct=;
                    String tempCost = cursor.getString(cursor.getColumnIndex("SCost"));
                    String tempQTY = cursor.getString(cursor.getColumnIndex("SQty"));

                    pm.setBrand(details.get(0));
                    pm.setProduct(details.get(1));
                    pm.setSize(cursor.getString(cursor.getColumnIndex("Size")));
                    pm.setQty(tempQTY);
                    pm.setMrp(tempCost);
                    int icost = Integer.valueOf(tempCost);
                    int iqty = Integer.valueOf(tempQTY);
                    int itotal = icost * iqty;
                    pm.setItemId(itemID);
                    pm.setsrno(cursor.getString(cursor.getColumnIndex("SrNo")));
                    pm.setTotal(Integer.toString(itotal));

                    CustomSaleValueArr.add(pm);
                    Resources res = getResources();
                    adapter = new cSaleAdaptor(this, CustomSaleListView, CustomSaleValueArr, res);
                    List.setAdapter(adapter);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    public void openEditWindow(View editView,int position)
    {
        RelativeLayout editViewData = ((RelativeLayout) editView);
        SaleModel saleItem=new SaleModel();
        Intent intent = new Intent(getApplicationContext(),addsale.class);
        Bundle b = new Bundle();

        b.putString("editSizesale", ((TextView)editViewData.findViewById(R.id.sizes)).getText().toString());
        b.putString("editBrand", ((TextView)editViewData.findViewById(R.id.brands)).getText().toString());
        b.putString("editProduct", ((TextView)editViewData.findViewById(R.id.products)).getText().toString());
        b.putInt("editQuantitysale", Integer.valueOf(((TextView)editViewData.findViewById(R.id.qtys)).getText().toString()));
        //b.putInt("editCost", Integer.valueOf(((TextView)editViewData.findViewById(R.id.cost)).getText().toString()));
        b.putInt("editMrpsale", Integer.valueOf(((TextView)editViewData.findViewById(R.id.mrps)).getText().toString()));
        b.putInt("editItemIDsale",Integer.valueOf(((TextView)editViewData.findViewById(R.id.itemids)).getText().toString()));
        //b.putString("editTotal", ((TextView)editViewData.findViewById(R.id.total)).getText().toString());
        b.putInt("editPositionsale", position);
        b.putString("srno",((TextView)editView.findViewById(R.id.srno)).getText().toString());
        intent.putExtras(b);
        startActivityForResult(intent,2);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSpinnerDataName();
    }


    public void loadSpinnerDataName( ) {
        // Spinner Drop down elements
        List<String> names = db.getContactNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, names);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        saleContactSpinner.setAdapter(dataAdapter);
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
            CustomSaleValueArr.add(sm);
        else
            CustomSaleValueArr.set(pos,sm);
        Resources res = getResources();
        ListView List = (ListView)findViewById(R.id.saleListView);
        adapter = new cSaleAdaptor(this,CustomSaleListView,CustomSaleValueArr,res);

        List.setAdapter(adapter);
    }



    public void savedata(){
        String Cname = saleContactSpinner.getSelectedItem().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
        String cid=db.getContactID(Cname);
        Boolean salesentry= Boolean.FALSE;

        //db.updateSales(cid,date,"123",commentSale.getText().toString(),date,billno.getText().toString());

        ListView listView = (ListView)findViewById(R.id.saleListView);

        View v ;
        int count=  listView.getChildCount();
        String itemid;
        TextView tempProduct ;
        TextView tempBrand ;
        TextView tempSize ;
        TextView tempCost ;
        TextView tempQty ;
        TextView tempMRP ;
        TextView tempItemID;
        TextView tempsrno;

        if(count>0) {
            for (int i = 0; i < count; i++) {
                v = listView.getChildAt(i);
                tempProduct = (TextView) v.findViewById(R.id.products);
                tempBrand = (TextView) v.findViewById(R.id.brands);
                tempSize = (TextView) v.findViewById(R.id.sizes);
                tempQty = (TextView) v.findViewById(R.id.qtys);
                tempMRP = (TextView) v.findViewById(R.id.mrps);
                tempItemID= (TextView)v.findViewById(R.id.itemids) ;
                tempsrno=(TextView)v.findViewById(R.id.srno);
                String srno = tempsrno.getText().toString();
                int dbcount=db.getCountSalesDetails(billno.getText().toString());

              //  String tempProductID= db.getProductID(tempProduct.getText().toString());
               // String tempBrandID= db.getBrandID(tempBrand.getText().toString());;
                int stockqty= db.getStockQty(tempItemID.getText().toString());
                int qty=Integer.valueOf(tempQty.getText().toString());
                Cursor c = db.getSalesDetailsforSrno(srno);
                String dbitemid="";
                int dbqty=0;
                int dbcost=0;
                if(c.moveToFirst()){
                    dbitemid=c.getString(c.getColumnIndex("ItemID"));
                    dbqty=Integer.valueOf(c.getString(c.getColumnIndex("SQty")));

                    dbcost=Integer.valueOf(c.getString(c.getColumnIndex("SCost")));
                }

                if(i<dbcount)
                {
                    if(dbitemid.equals(tempItemID.getText().toString())){
                        if(dbqty==qty)
                        {
                            db.updateSalesDetails(tempItemID.getText().toString(),String.valueOf(qty),tempMRP.getText().toString(),date,tempsrno.getText().toString());
                        }
                        else if(qty<dbqty){
                           stockqty= stockqty +dbqty-qty;
                            db.updateSalesDetails(tempItemID.getText().toString(),String.valueOf(qty),tempMRP.getText().toString(),date,tempsrno.getText().toString());
                            db.updateQtyStock(tempItemID.getText().toString(),String.valueOf(stockqty),date);

                        }
                        else if (dbqty<qty){
                            stockqty= stockqty+dbqty-qty;
                            db.updateSalesDetails(tempItemID.getText().toString(),String.valueOf(qty),tempMRP.getText().toString(),date,tempsrno.getText().toString());
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
                        db.updateSalesDetails(tempItemID.getText().toString(),String.valueOf(qty),tempMRP.getText().toString(),date,tempsrno.getText().toString());

                    }

                }
                else {

                    if (qty <= stockqty) {
                        int updateqty = stockqty - qty;
                        db.insertSalesDetails(billno.getText().toString(), tempItemID.getText().toString(), tempMRP.getText().toString(), tempQty.getText().toString(), date);
                        db.updateQtyStock(tempItemID.getText().toString(), String.valueOf(updateqty), date);


                    }
                }
            }

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.scanSale:
            {

            }
            case R.id.addcontact:
            {
                Intent intent = new Intent(this,addcontact.class);
                startActivity(intent);
                break;
            }
            case R.id.addSaleOpen:
            {
                Intent intent= new Intent(this,addsale.class);
                startActivityForResult(intent,1);
                break;
            }
            case R.id.saveSale:
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
