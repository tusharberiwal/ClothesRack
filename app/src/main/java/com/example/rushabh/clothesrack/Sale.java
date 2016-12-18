package com.example.rushabh.clothesrack;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Sale extends Fragment implements View.OnClickListener {
    View myview;
    int gtotal;
    Button scanSale;
    Button addSaleOpen;
    Button addContact;
    Button saveSale;
    Button cancelSale;
    EditText commentSale;
    TextView dateedit;
    DatabaseHelper db ;
    Spinner saleContactSpinner;
    cSaleAdaptor adapter;
    TextView billno;
    TextView grandTotal;
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


    public Sale CustomSaleListView= null;
    public ArrayList<SaleModel> CustomSaleValueArr =  new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        myview= inflater.inflate(R.layout.sale, container,false);
        scanSale= (Button)myview.findViewById(R.id.scanSale);
        scanSale.setOnClickListener(this);
        addSaleOpen=(Button)myview.findViewById(R.id.addSaleOpen);
        addSaleOpen.setOnClickListener(this);
        addContact=(Button)myview.findViewById(R.id.addcontact);
        addContact.setOnClickListener(this);
        saveSale=(Button)myview.findViewById(R.id.saveSale);
        saveSale.setOnClickListener(this);
        cancelSale=(Button)myview.findViewById(R.id.cancelSale);
        cancelSale.setOnClickListener(this);
        commentSale=(EditText)myview.findViewById(R.id.commentSale);
        billno=(TextView)myview.findViewById(R.id.billno);
        db= new DatabaseHelper(getContext());
        saleContactSpinner=(Spinner)myview.findViewById(R.id.saleContactSpinner);
        dateedit = (TextView) myview.findViewById(R.id.dateedit);
        dateedit.setOnClickListener(this);
        grandTotal=(TextView)myview.findViewById(R.id.grandTotal);
        loadSpinnerDataName();
        setBillno();

        final ListView lv = (ListView)myview.findViewById(R.id.saleListView);

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
        SaleModel saleItem=new SaleModel();
        Intent intent = new Intent(getContext(),addsale.class);
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
        loadSpinnerDataName();
    }


    public void loadSpinnerDataName( ) {
        // Spinner Drop down elements
        List<String> names = db.getContactNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_expandable_list_item_1, names);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        saleContactSpinner.setAdapter(dataAdapter);
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
        Log.d("!!!","in add to list");        sm.setBrand(brand);
        sm.setProduct(product);
        sm.setSize(size);
        sm.setQty(qty);
        sm.setMrp(mrp);
        sm.setTotal(total);
        sm.setItemId(itemid);
        if(pos==-1)
            CustomSaleValueArr.add(sm);
        else
            CustomSaleValueArr.set(pos,sm);
        Resources res = getResources();
        ListView List = (ListView)myview.findViewById(R.id.saleListView);
        adapter = new cSaleAdaptor(getContext(),CustomSaleListView,CustomSaleValueArr,res);

        List.setAdapter(adapter);
    }



    public void savedata(){
        checknull();

        String Cname = saleContactSpinner.getSelectedItem().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
        String cid=db.getContactID(Cname);
        Boolean salesentry= Boolean.FALSE;

        db.insertSales(cid,dateedit.getText().toString(),"123",commentSale.getText().toString(),date);

        ListView listView = (ListView)myview.findViewById(R.id.saleListView);

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
        final ArrayList<InvoicePrintModel> invoiceData= new ArrayList<InvoicePrintModel>();
        if(count>0) {
            for (int i = 0; i < count; i++) {
                InvoicePrintModel invoicePrintModel = new InvoicePrintModel();
                v = listView.getChildAt(i);
                tempProduct = (TextView) v.findViewById(R.id.products);
                tempBrand = (TextView) v.findViewById(R.id.brands);
                tempSize = (TextView) v.findViewById(R.id.sizes);
                invoicePrintModel.Description=tempProduct.getText().toString() + tempBrand.getText().toString()+tempSize.getText().toString();
                tempQty = (TextView) v.findViewById(R.id.qtys);
                invoicePrintModel.Quantity=(Integer.parseInt(tempQty.getText().toString()));
                tempMRP = (TextView) v.findViewById(R.id.mrps);
                invoicePrintModel.Rate=(Integer.parseInt(tempMRP.getText().toString()));
                tempItemID= (TextView)v.findViewById(R.id.itemids) ;

                String tempProductID= db.getProductID(tempProduct.getText().toString());
                String tempBrandID= db.getBrandID(tempBrand.getText().toString());;

                invoiceData.add(invoicePrintModel);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());



                builder.setTitle("Confirm");
                builder.setMessage("Do you want to print the invoice?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        //Toast.makeText(getContext(),"You clicked yes button",Toast.LENGTH_LONG).show();
                        createInvoice CI = new createInvoice();
                        CI.printinvoice(invoiceData);
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


                int stockqty= db.getStockQty(tempItemID.getText().toString());
                int qty=Integer.valueOf(tempQty.getText().toString());
                if(qty<=stockqty)
                {
                    int updateqty=stockqty-qty;
                    db.insertSalesDetails(billno.getText().toString(),tempItemID.getText().toString(),tempMRP.getText().toString(),tempQty.getText().toString(),date);
                    db.updateQtyStock(tempItemID.getText().toString(),String.valueOf(updateqty),date);
                    if(salesentry!=Boolean.TRUE)
                    salesentry=true;

                }
            }

        }
        if(salesentry==Boolean.FALSE)
            db.deletesales(billno.getText().toString());
    }


    public void setBillno(){
        String billnotv= db.getNextBillNo();
        Toast.makeText(getContext(),billnotv,Toast.LENGTH_LONG).show();
        if(billnotv==null)
            billnotv="0";
        int nextbillno=Integer.valueOf(billnotv);
        nextbillno=nextbillno+1;
        Toast.makeText(getContext(),String.valueOf(nextbillno),Toast.LENGTH_LONG).show();
        billno.setText(String.valueOf(nextbillno));
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.scanSale:
            {
                Intent intent = new Intent(getContext(),addsale.class);
                Bundle b = new Bundle();
                String yes="y";
                b.putString("isScan", yes);
                intent.putExtras(b);
                startActivityForResult(intent,1);
                break;

            }
            case R.id.addcontact:
            {
                Intent intent = new Intent(getContext(),addcontact.class);
                startActivity(intent);
                break;
            }
            case R.id.addSaleOpen:
            {
                Intent intent= new Intent(getContext(),addsale.class);
                startActivityForResult(intent,1);
                break;
            }
            case R.id.saveSale:
            {
                savedata();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,new Sale()).commit();
                break;

            }
            case R.id.cancelSale:
            {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,new Sale()).commit();
                break;
                /*
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                break;*/

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
        ListView listView = (ListView)myview.findViewById(R.id.saleListView);

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


    public void checknull()
    {


    }
}
