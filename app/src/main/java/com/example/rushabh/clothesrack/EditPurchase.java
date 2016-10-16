package com.example.rushabh.clothesrack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

/**
 * Created by tushar on 9/18/2016.
 */
public class EditPurchase extends Activity implements View.OnClickListener{


    cPurAdaptor adapter;
    Button addp;
    Button addcontact;
    Spinner spinner;
    Button savep;
    EditText billno;
    SQLiteDatabase finalClothesrackDB;
    public Purchase CustomPurListView= null;
    public ArrayList<PurchaseModel> CustomPurchaseValueArr =  new ArrayList<PurchaseModel>();
    private DatabaseHelper db;
    String ID;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase);
        db= new DatabaseHelper(this);
        addp = (Button)findViewById(R.id.addp);
        addp.setOnClickListener(this);
        addcontact= (Button) findViewById(R.id.addcontact);
        addcontact.setOnClickListener(this);
        savep = (Button)findViewById(R.id.savep);
        savep.setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.nameInput);
        billno=(EditText)findViewById(R.id.bnoInput);
        SQLiteDatabase clothesrackDB = null;
        clothesrackDB = this.openOrCreateDatabase("ClothesRack",this.MODE_PRIVATE, null);
        finalClothesrackDB = clothesrackDB;
        loadSpinnerDataName();
        loadEditData();

        final ListView lv = (ListView)findViewById(R.id.purListView1);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openEditWindow(view,position);

            }
        });
    }

    public void loadEditData()
    {

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null) {
            ID = bundle.getString("purchaseID");
            ListView List = (ListView) findViewById(R.id.purListView1);
            billno.setText(bundle.getString("billNo"));

            ArrayAdapter<String> array_spinner_brand = (ArrayAdapter<String>) spinner.getAdapter();
            spinner.setSelection(array_spinner_brand.getPosition(bundle.getString("custName")));

            CustomPurchaseValueArr.clear();
            Cursor cursor = db.getPurchaseDetails(ID);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    final PurchaseModel pm = new PurchaseModel();
                    String itemID=cursor.getString(cursor.getColumnIndex("ItemID"));

                    List<String> details = new ArrayList<>();
                    details= db.getBrandFromItem(itemID);
                    //tempBrand= ;
                    //tempProduct=;
                    String tempCost=cursor.getString(cursor.getColumnIndex("Cost"));
                    String tempQTY=cursor.getString(cursor.getColumnIndex("PQty"));
                    String tempSize= db.getItemSize(itemID);

                    pm.setBrand(details.get(0));
                    pm.setProduct(details.get(1));
                    pm.setSize(tempSize);
                    pm.setQty(tempQTY);
                    pm.setCost(tempCost);
                    pm.setMrp(cursor.getString(cursor.getColumnIndex("MRP")));
                    pm.setitemid(itemID);
                    int icost= Integer.valueOf(tempCost);
                    int iqty=Integer.valueOf(tempQTY);
                    int itotal= icost*iqty;

                    pm.setTotal(Integer.toString(itotal));

                    CustomPurchaseValueArr.add(pm);
                    Resources res = getResources();
                    adapter = new cPurAdaptor(this, CustomPurListView, CustomPurchaseValueArr, res);
                    List.setAdapter(adapter);

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }
    public void openEditWindow(View editView,int position)
    {
        RelativeLayout editViewData = ((RelativeLayout) editView);
        PurchaseModel purchaseItem=new PurchaseModel();
        Intent intent = new Intent(this,addpurchase.class);
        Bundle b = new Bundle();

        b.putString("editSize", ((TextView)editViewData.findViewById(R.id.size)).getText().toString());
        b.putString("editBrand", ((TextView)editViewData.findViewById(R.id.brands)).getText().toString());
        b.putString("editProduct", ((TextView)editViewData.findViewById(R.id.products)).getText().toString());
        b.putInt("editQuantity", Integer.valueOf(((TextView)editViewData.findViewById(R.id.qtys)).getText().toString()));
        b.putInt("editCost", Integer.valueOf(((TextView)editViewData.findViewById(R.id.cost)).getText().toString()));
        b.putInt("editMrp", Integer.valueOf(((TextView)editViewData.findViewById(R.id.mrps)).getText().toString()));
        //b.putString("editTotal", ((TextView)editViewData.findViewById(R.id.total)).getText().toString());
        b.putInt("editPosition", position);
        b.putString("itemid",((TextView)editViewData.findViewById(R.id.itemid)).getText().toString());
        intent.putExtras(b);
        startActivityForResult(intent,2);
    }

    @Override
    public void onResume() {
        super.onResume();
        //loadSpinnerDataName();
    }

    public void savedata(){

  //Have to edit below code to update instaed of save data
        String purid=ID;
        int countdb = db.getCountPurchaseDetails(purid);

        String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());

        EditText comment = (EditText)findViewById(R.id.purComment);
        String Cname = spinner.getSelectedItem().toString();
        String cid=db.getContactID(Cname);
        int grandTotal=0;

        db.updatePurchase(billno.getText().toString(),cid.toString(),date,"123",comment.getText().toString(),date,purid);

        String purchaseID=db.getPurID();
        ListView listView = (ListView)findViewById(R.id.purListView1);

        View v ;
        int count=  listView.getChildCount();
         String itemid;
        TextView tempProduct ;
        TextView tempBrand ;
        TextView tempSize ;
        TextView tempCost ;
        TextView tempQty ;
        TextView tempMRP ;
        TextView tempitemid;
        final ArrayList<BarCodePrintModel> barCodeData= new ArrayList<BarCodePrintModel>();
        BarCodePrintModel barCodePrintModel = new BarCodePrintModel();
        if(count>0) {
            for (int i = 0; i < count; i++) {

                v = listView.getChildAt(i);
                tempProduct = (TextView) v.findViewById(R.id.products);
                tempBrand = (TextView) v.findViewById(R.id.brands);
                tempSize = (TextView) v.findViewById(R.id.size);
                tempCost = (TextView) v.findViewById(R.id.cost);
                tempQty = (TextView) v.findViewById(R.id.qtys);
                tempMRP = (TextView) v.findViewById(R.id.mrps);
                tempitemid = (TextView) v.findViewById(R.id.itemid);
                String tempProductID = db.getProductID(tempProduct.getText().toString());
                String tempBrandID = db.getBrandID(tempBrand.getText().toString());
                int tempTotal = Integer.parseInt(((TextView) v.findViewById(R.id.totall)).getText().toString());
                grandTotal += tempTotal;

                if (i < countdb) {
                    int dbqty = db.getPurchaseDetailQty(tempitemid.getText().toString());
                    int qty = Integer.valueOf(tempQty.getText().toString());
                    int stockqty = db.getStockQty(tempitemid.getText().toString());
                    if (qty == dbqty) {

                    } else if (qty < dbqty) {

                        int updateqty = dbqty - qty;
                        stockqty = stockqty - updateqty;
                    } else if (dbqty < qty) {
                        int updateqty = qty - dbqty;
                        stockqty = stockqty + updateqty;
                    }
                    db.updateStock(tempBrandID, tempProductID, stockqty, tempSize.getText().toString(), tempitemid.getText().toString(), date);
                    db.updatePurchaseDetails(qty, tempCost.getText().toString(), tempMRP.getText().toString(), tempitemid.getText().toString(), date);


                } else {
                    db.insertStock(tempBrandID, tempProductID, tempSize.getText().toString(), tempQty.getText().toString(), date);
                    itemid = db.getStockItemID();
                    db.insertPurchaseDetails(itemid, purchaseID, tempQty.getText().toString(), tempCost.getText().toString(), tempMRP.getText().toString(), date);


                }
                String tempproduct1, tempbrand1, tempsize1;
                barCodePrintModel.Product = tempproduct1 = ((TextView) v.findViewById(R.id.products)).getText().toString();
                barCodePrintModel.Brand = tempbrand1 = ((TextView) v.findViewById(R.id.brands)).getText().toString();
                barCodePrintModel.Size = tempsize1 = ((TextView) v.findViewById(R.id.size)).getText().toString();
                barCodePrintModel.ItemID = ((TextView) v.findViewById(R.id.itemid)).getText().toString();

                for (int j = 0; j < Integer.parseInt(((TextView) v.findViewById(R.id.qtys)).getText().toString()); j++)
                    barCodeData.add(barCodePrintModel);
            }

        }

            AlertDialog.Builder builder = new AlertDialog.Builder(EditPurchase.this);


            builder.setTitle("Confirm");
            builder.setMessage("Do you want to print the Bar Codes?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    //Toast.makeText(getContext(),"You clicked yes button",Toast.LENGTH_LONG).show();
                    BarCodePrint bcp = new BarCodePrint();
                    bcp.getDataForBarCodePrint(barCodeData);
                    //dialog.dismiss();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {


                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    //dialog.dismiss();

                }
            });

            AlertDialog alert = builder.create();
            alert.show();



    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle response = data.getExtras();
        if(response!=null)
        {
            String brand = response.getString("brand");
            String product = response.getString("product");
            String size = response.getString("size");
            String quantity = response.getString("quantity");
            String cost = response.getString("cost");
            String mrp = response.getString("mrp");
            String id = response.getString("itemid");
            int icost= Integer.valueOf(cost);
            int iqty=Integer.valueOf(quantity);
            int itotal= icost*iqty;
            String total = String.valueOf(itotal);

            int pos=response.getInt("editPosition");
            AddEditToList(brand, product, size, quantity, cost, mrp, total,pos,id);
        }
    }


    public void AddEditToList(String brand, String product, String size, String qty, String cost, String mrp,String total, int pos,String id)
    {
        final PurchaseModel pm= new PurchaseModel();
        pm.setBrand(brand);
        pm.setProduct(product);
        pm.setSize(size);
        pm.setQty(qty);
        pm.setCost(cost);
        pm.setMrp(mrp);
        pm.setTotal(total);
        pm.setitemid(id);
        if(pos==-1)
            CustomPurchaseValueArr.add(pm);
        else
            CustomPurchaseValueArr.set(pos,pm);

        Resources res = getResources();
        ListView List = (ListView)findViewById(R.id.purListView1);
        adapter = new cPurAdaptor(this,CustomPurListView,CustomPurchaseValueArr,res);

        List.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addp:
            {
                Intent intent = new Intent(this,addpurchase.class);
                startActivityForResult(intent,1);
                break;
            }
            case R.id.addcontact:
            {
                Intent intent = new Intent(this,addcontact.class);
                startActivity(intent);
                break;
            }
            case R.id.savep:
            {
                savedata();
                break;
            }
        }
    }

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



}


