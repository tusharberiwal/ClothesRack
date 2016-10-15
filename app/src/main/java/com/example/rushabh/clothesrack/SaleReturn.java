package com.example.rushabh.clothesrack;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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

public class SaleReturn extends Fragment implements View.OnClickListener {
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
    TextView grandTotal;
    int gtotal;

    public Sale CustomSaleRetListView= null;
    public ArrayList<SaleModel> CustomSaleRetValueArr =  new ArrayList<>();

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
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        myview= inflater.inflate(R.layout.salereturn, container,false);
        scanSaleRet= (Button)myview.findViewById(R.id.scanSaleRet);
        scanSaleRet.setOnClickListener(this);
        addSaleRetOpen=(Button)myview.findViewById(R.id.addSaleRetOpen);
        addSaleRetOpen.setOnClickListener(this);
        saveSaleRet=(Button)myview.findViewById(R.id.saveSaleRet);
        saveSaleRet.setOnClickListener(this);
        cancelSaleRet=(Button)myview.findViewById(R.id.cancelSaleRet);
        cancelSaleRet.setOnClickListener(this);
        commentSaleRet=(EditText)myview.findViewById(R.id.commentSaleRet);
        saleretno=(TextView)myview.findViewById(R.id.salereturnNo);
        againstSBno=(EditText) myview.findViewById(R.id.againstSBno);
        grandTotal=(TextView)myview.findViewById(R.id.grandTotal);
        grandTotal.setOnClickListener(this);
        db= new DatabaseHelper(getContext());
        saleRetContactSpinner=(Spinner)myview.findViewById(R.id.saleRetContactSpinner);
        loadSpinnerDataName();
        setSaleRetNo();
        dateedit=(TextView)myview.findViewById(R.id.dateedit);
        dateedit.setOnClickListener(this);


        final ListView lv = (ListView)myview.findViewById(R.id.saleRetListView);

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
        SaleReturnModel saleItem=new SaleReturnModel();
        Intent intent = new Intent(getContext(),addsalereturn.class);
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
        intent.putExtras(b);
        startActivityForResult(intent,2);

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public void loadSpinnerDataName( ) {
        // Spinner Drop down elements
        List<String> names = db.getContactNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_expandable_list_item_1, names);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        saleRetContactSpinner.setAdapter(dataAdapter);
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("@@","In activity result");
        if(data!=null) {
            Bundle response = data.getExtras();
            if (response != null) {
                String brand = response.getString("brand");
                String product = response.getString("product");
                String size = response.getString("size");
                String quantity = response.getString("quantity");
                String mrp = response.getString("mrp");
                String itemid = response.getString("itemid");
                int imrp = Integer.valueOf(mrp);
                int iqty = Integer.valueOf(quantity);
                int itotal = imrp * iqty;
                String total = String.valueOf(itotal);
                int pos = response.getInt("editPosition");
                addtolist(brand, product, size, quantity, mrp, total, itemid, pos);
            }
        }
    }


    public void addtolist(String brand, String product, String size, String qty, String mrp,String total,String itemid,int pos){
        final SaleModel sm= new SaleModel();
        Log.d("!!!","in add to list");
        sm.setBrand(brand);
        sm.setProduct(product);
        sm.setSize(size);
        sm.setQty(qty);
        sm.setMrp(mrp);
        sm.setTotal(total);
        sm.setItemId(itemid);
        if(pos==-1)
            CustomSaleRetValueArr.add(sm);
        else
            CustomSaleRetValueArr.set(pos,sm);
        Resources res = getResources();
        ListView List = (ListView)myview.findViewById(R.id.saleRetListView);
        adapter = new cSaleAdaptor(getContext(),CustomSaleRetListView,CustomSaleRetValueArr,res);

        List.setAdapter(adapter);
    }



    public void savedata() {

        String Cname = saleRetContactSpinner.getSelectedItem().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
        String cid = db.getContactID(Cname);
        Boolean salesretentry = Boolean.FALSE;

        db.insertSalesReturn(cid,againstSBno.getText().toString(),dateedit.getText().toString(), "123", commentSaleRet.getText().toString(), date);

        ListView listView = (ListView) myview.findViewById(R.id.saleRetListView);
        View v;
        int count = listView.getChildCount();
        String itemid;
        TextView tempProduct;
        TextView tempBrand;
        TextView tempSize;
        TextView tempQty;
        TextView tempMRP;
        TextView tempItemID;
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                v = listView.getChildAt(i);


                tempQty = (TextView) v.findViewById(R.id.qtys);
                tempMRP = (TextView) v.findViewById(R.id.mrps);
                tempItemID = (TextView) v.findViewById(R.id.itemids);
                 int stockqty = db.getStockQty(tempItemID.getText().toString());
                 int qty = Integer.valueOf(tempQty.getText().toString());
                stockqty= stockqty+qty;

                    db.insertSalesReturnDetails(tempItemID.getText().toString(),saleretno.getText().toString(),tempQty.getText().toString(),tempMRP.getText().toString(),date);
                    db.updateQtyStock(tempItemID.getText().toString(),String.valueOf(stockqty),date);
                   if (salesretentry != Boolean.TRUE)
                       salesretentry = true;
            }
        }
        if (salesretentry == Boolean.FALSE) {

            db.deletesalesreturn(saleretno.getText().toString());

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
                Intent intent = new Intent(getContext(),addsalereturn.class);
                Bundle b = new Bundle();
                String yes="y";
                b.putString("isScan", yes);
                intent.putExtras(b);
                startActivityForResult(intent,1);
                break;
            }

            case R.id.addSaleRetOpen:
            {
                Intent intent= new Intent(getContext(),addsalereturn.class);
                startActivityForResult(intent,1);
                break;
            }
            case R.id.saveSaleRet:
            {
               savedata();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                break;
            }
            case R.id.cancelSale:
            {
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
            case R.id.grandTotal:{
                setGrandTotal();
                break;
            }
        }

    }
    public void setGrandTotal(){
        ListView listView = (ListView)myview.findViewById(R.id.saleRetListView);

        View v ;
        gtotal=0;
        int count=  listView.getChildCount();

        if(count>0) {
            for (int i = 0; i < count; i++) {
                v = listView.getChildAt(i);
                //int tempTotal = Integer.parseInt(((TextView) v.findViewById(R.id.totall)).getText().toString());
                int tempTotal= Integer.valueOf(((TextView) v.findViewById(R.id.totals)).getText().toString());
                gtotal =gtotal+ tempTotal;
            }
        }

        grandTotal.setText(String.valueOf(gtotal));
    }

}
