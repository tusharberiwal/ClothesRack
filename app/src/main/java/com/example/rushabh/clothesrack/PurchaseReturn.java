package com.example.rushabh.clothesrack;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public  class PurchaseReturn extends Fragment implements View.OnClickListener{
    public PurchaseReturn myfragment;
    View myview;
    cPurRetAdaptor adapter;
    Button addpr;
    Button scanpr;
    Spinner spinner;
    Button savepr;
    Button cancelpr;
    TextView purRetNo;
    EditText againstBno;
    int gtotal;
    TextView grandTotal;
    SQLiteDatabase finalClothesrackDB;
   int contactupdate=0;
    public PurchaseReturn CustomPurRetListView= null;
    public ArrayList<PurchaseReturnModel> CustomPurchaseRetValueArr =  new ArrayList<PurchaseReturnModel>();
    private DatabaseHelper db;
    TextView dateedit;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateedit.setText(sdf.format(myCalendar.getTime()));
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myview= inflater.inflate(R.layout.purchasereturn, container,false);
        myfragment = new PurchaseReturn();
        db= new DatabaseHelper(getContext());
        addpr = (Button)myview.findViewById(R.id.addpr);
        addpr.setOnClickListener(this);
        savepr = (Button)myview.findViewById(R.id.savepr);
        savepr.setOnClickListener(this);
        spinner = (Spinner) myview.findViewById(R.id.nameInputpr);
        purRetNo=(TextView)myview.findViewById(R.id.purRetNo);
        againstBno=(EditText)myview.findViewById(R.id.againstBno) ;
        scanpr=(Button)myview.findViewById(R.id.scanpr);
        scanpr.setOnClickListener(this);
        cancelpr=(Button)myview.findViewById(R.id.cancelpr) ;
        cancelpr.setOnClickListener(this);
        SQLiteDatabase clothesrackDB = null;
        clothesrackDB = getActivity().openOrCreateDatabase("ClothesRack",getContext().MODE_PRIVATE, null);
        finalClothesrackDB = clothesrackDB;
        loadSpinnerDataName();
        setPurRetNo();
        dateedit=(TextView)myview.findViewById(R.id.dateedit);
        dateedit.setOnClickListener(this);
        grandTotal=(TextView)myview.findViewById(R.id.grandTotal);
        grandTotal.setOnClickListener(this);

        final ListView lv = (ListView)myview.findViewById(R.id.purRetListView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openEditWindow(view,position);

            }
        });

        return myview;


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void openEditWindow(View editView, int position)
    {
        RelativeLayout editViewData = ((RelativeLayout) editView);
        PurchaseReturnModel purchaseItem=new PurchaseReturnModel();
        Intent intent = new Intent(getContext(),addpurchasereturn.class);
        Bundle b = new Bundle();

        b.putString("editSize", ((TextView)editViewData.findViewById(R.id.sizepret)).getText().toString());
        b.putString("editBrand", ((TextView)editViewData.findViewById(R.id.brandpret)).getText().toString());
        b.putString("editProduct", ((TextView)editViewData.findViewById(R.id.productpret)).getText().toString());
        b.putInt("editQuantity", Integer.valueOf(((TextView)editViewData.findViewById(R.id.qtypret)).getText().toString()));
        b.putInt("editCost", Integer.valueOf(((TextView)editViewData.findViewById(R.id.costpret)).getText().toString()));
        b.putInt("editPosition", position);
        intent.putExtras(b);
        startActivityForResult(intent,2);
    }




    public void savedata() {

        EditText comment = (EditText) myview.findViewById(R.id.purRetComment);
        String Cname = spinner.getSelectedItem().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
        String cid = db.getContactID(Cname);
        int grandTotal = 0;
        Boolean purretentry= Boolean.FALSE;

        db.insertPurchaseReturn(cid,againstBno.getText().toString(), dateedit.getText().toString(), "123", comment.getText().toString(), date);

        ListView listView = (ListView) myview.findViewById(R.id.purRetListView);

        View v;
        int count = listView.getChildCount();

        TextView tempCost;
        TextView tempQty;
        TextView tempItemID;
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                v = listView.getChildAt(i);

                tempCost = (TextView) v.findViewById(R.id.costpret);
                tempQty = (TextView) v.findViewById(R.id.qtypret);
                tempItemID = (TextView) v.findViewById(R.id.itemidpret);

                int stockqty = db.getStockQty(tempItemID.getText().toString());
                int qty = Integer.valueOf(tempQty.getText().toString());
                stockqty = stockqty - qty;

                db.insertPurchaseReturnDetails(tempItemID.getText().toString(), purRetNo.getText().toString(), tempQty.getText().toString(), tempCost.getText().toString(), date);
                db.updateQtyStock(tempItemID.getText().toString(), String.valueOf(stockqty), date);
                if (purretentry != Boolean.TRUE)
                    purretentry = true;
            }
        }
        if (purretentry == Boolean.FALSE) {

            db.deletepurchasereturn(purRetNo.getText().toString());


        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            Bundle response = data.getExtras();
            if (response != null) {
                String brand = response.getString("brand");
                String product = response.getString("product");
                String size = response.getString("size");
                String quantity = response.getString("quantity");
                String cost = response.getString("cost");
                int icost = Integer.valueOf(cost);
                int iqty = Integer.valueOf(quantity);
                int itotal = icost * iqty;
                String total = String.valueOf(itotal);
                String itemid = response.getString("itemid");

                int pos = response.getInt("editPosition");
                AddEditToList(brand, product, size, quantity, cost, total, itemid, pos);
            }
        }

    }


    public void AddEditToList(String brand, String product, String size, String qty, String cost,String total,String itemid, int pos)
    {
        final PurchaseReturnModel pm= new PurchaseReturnModel();
        pm.setBrand(brand);
        pm.setProduct(product);
        pm.setSize(size);
        pm.setQty(qty);
        pm.setCost(cost);
        pm.setTotal(total);
        pm.setItemid(itemid);


        if(pos==-1)
            CustomPurchaseRetValueArr.add(pm);
        else
            CustomPurchaseRetValueArr.set(pos,pm);

        Resources res = getResources();
        ListView List = (ListView)myview.findViewById(R.id.purRetListView);
        adapter = new cPurRetAdaptor(getContext(),CustomPurRetListView,CustomPurchaseRetValueArr,res);

        List.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addpr:
            {
                Intent intent = new Intent(getContext(),addpurchasereturn.class);
                startActivityForResult(intent,1);
                break;
            }
            case R.id.scanpr:
            {
                Intent intent = new Intent(getContext(),addpurchasereturn.class);
                Bundle b = new Bundle();
                String yes="y";
                b.putString("isScan", yes);
                intent.putExtras(b);
                startActivityForResult(intent,1);
                break;
            }
            case R.id.savepr:
            {
                savedata();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                break;

            }
            case R.id.dateedit:
            {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            }
            case R.id.cancelpr:
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                break;
            }
            case R.id.grandTotal:{
             setGrandTotal();
                break;
            }
        }
    }



    public void setGrandTotal(){
        ListView listView = (ListView)myview.findViewById(R.id.purRetListView);

        View v ;
        gtotal=0;
        int count=  listView.getChildCount();

        if(count>0) {
            for (int i = 0; i < count; i++) {
                v = listView.getChildAt(i);
                //int tempTotal = Integer.parseInt(((TextView) v.findViewById(R.id.totall)).getText().toString());
                int tempTotal= Integer.valueOf(((TextView) v.findViewById(R.id.totalpret)).getText().toString());
                gtotal =gtotal+ tempTotal;
            }
        }

        grandTotal.setText(String.valueOf(gtotal));
    }

    public void onSaveInstanceState(Bundle outState){
        getFragmentManager().putFragment(outState,"myfragment",this);
        outState.putInt("spinnercontact",spinner.getSelectedItemPosition());
    }
    public void onRestoreInstanceState(Bundle inState)
   {
        myfragment = (PurchaseReturn) getFragmentManager().getFragment(inState,"myfragment");

        if(inState !=null)
        {
         //   spinner.setSelection(inState.getInt("spinnercontact"));

        }

    }
    public void loadSpinnerDataName( ) {
        // Spinner Drop down elements
       List<String> names = db.getContactNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
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

