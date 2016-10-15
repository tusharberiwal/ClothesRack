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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public  class Purchase extends Fragment implements View.OnClickListener{
    public Purchase myfragment;
    int gtotal;
    TextView grandTotal;
    View myview;
    cPurAdaptor adapter;
    Button addp;
    Button cancelp;
    Button addcontact;
    Spinner spinner;
    Button savep;
    EditText billno;
    SQLiteDatabase finalClothesrackDB;
   int contactupdate=0;
    public Purchase CustomPurListView= null;
    public ArrayList<PurchaseModel> CustomPurchaseValueArr =  new ArrayList<PurchaseModel>();
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

        myview= inflater.inflate(R.layout.purchase, container,false);
       myfragment = new Purchase();
        db= new DatabaseHelper(getContext());
        addp = (Button)myview.findViewById(R.id.addp);
        addp.setOnClickListener(this);
        addcontact= (Button) myview.findViewById(R.id.addcontact);
        addcontact.setOnClickListener(this);
        savep = (Button)myview.findViewById(R.id.savep);
        savep.setOnClickListener(this);
        spinner = (Spinner) myview.findViewById(R.id.nameInput);
        billno=(EditText)myview.findViewById(R.id.bnoInput);
        SQLiteDatabase clothesrackDB = null;
        dateedit=(TextView)myview.findViewById(R.id.dateedit);
        dateedit.setOnClickListener(this);
        grandTotal= (TextView)myview.findViewById(R.id.grandTotal);
        grandTotal.setOnClickListener(this);
        cancelp=(Button)myview.findViewById(R.id.cancelp);
        cancelp.setOnClickListener(this);
        clothesrackDB = getActivity().openOrCreateDatabase("ClothesRack",getContext().MODE_PRIVATE, null);
        finalClothesrackDB = clothesrackDB;
        loadSpinnerDataName();

        final ListView lv = (ListView)myview.findViewById(R.id.purListView1);

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
        PurchaseModel purchaseItem=new PurchaseModel();
        Intent intent = new Intent(getContext(),addpurchase.class);
        Bundle b = new Bundle();

        b.putString("editSize", ((TextView)editViewData.findViewById(R.id.size)).getText().toString());
        b.putString("editBrand", ((TextView)editViewData.findViewById(R.id.brands)).getText().toString());
        b.putString("editProduct", ((TextView)editViewData.findViewById(R.id.products)).getText().toString());
        b.putInt("editQuantity", Integer.valueOf(((TextView)editViewData.findViewById(R.id.qtys)).getText().toString()));
        b.putInt("editCost", Integer.valueOf(((TextView)editViewData.findViewById(R.id.cost)).getText().toString()));
        b.putInt("editMrp", Integer.valueOf(((TextView)editViewData.findViewById(R.id.mrps)).getText().toString()));
        //b.putString("editTotal", ((TextView)editViewData.findViewById(R.id.total)).getText().toString());
        b.putInt("editPosition", position);
        intent.putExtras(b);
        startActivityForResult(intent,1);
    }

    @Override
    public void onResume() {
        super.onResume();

        Toast.makeText(getContext(), "here"+contactupdate, Toast.LENGTH_SHORT).show();
        if(contactupdate==1)
        loadSpinnerDataName();



    }



    public void savedata(){

        EditText comment = (EditText)myview.findViewById(R.id.purComment);
        String Cname = spinner.getSelectedItem().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
        String cid=db.getContactID(Cname);
        int grandTotal=0;


        db.insertPurchase(billno.getText().toString(),cid.toString(),dateedit.getText().toString(),"123",comment.getText().toString(),date);
        String purchaseID=db.getPurID();
        ListView listView = (ListView)myview.findViewById(R.id.purListView1);

        View v ;
        int count=  listView.getChildCount();
        String itemid;
        TextView tempProduct ;
        TextView tempBrand ;
        TextView tempSize ;
        TextView tempCost ;
        TextView tempQty ;
        TextView tempMRP ;
        if(count>0) {
            for (int i = 0; i < count; i++) {
                v = listView.getChildAt(i);
                tempProduct = (TextView) v.findViewById(R.id.products);
                tempBrand = (TextView) v.findViewById(R.id.brands);
                tempSize = (TextView) v.findViewById(R.id.size);
                tempCost = (TextView) v.findViewById(R.id.cost);
                tempQty = (TextView) v.findViewById(R.id.qtys);
                tempMRP = (TextView) v.findViewById(R.id.mrps);

                int tempTotal=Integer.parseInt(((TextView)v.findViewById(R.id.totall)).getText().toString());
                grandTotal+=tempTotal;
                String tempProductID= db.getProductID(tempProduct.getText().toString());
                String tempBrandID= db.getBrandID(tempBrand.getText().toString());;

                db.insertStock(tempBrandID,tempProductID,tempSize.getText().toString(),tempQty.getText().toString(),date);
                itemid = db.getStockItemID();
                db.insertPurchaseDetails(itemid,purchaseID,tempQty.getText().toString(),tempCost.getText().toString(),tempMRP.getText().toString(),date);

            }
            db.updatePurchasewithBillAMt(purchaseID,Integer.toString(grandTotal));

        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==1) {
                if(data!=null) {
                    Bundle response = data.getExtras();
                    if (response != null) {
                        String brand = response.getString("brand");
                        String product = response.getString("product");
                        String size = response.getString("size");
                        String quantity = response.getString("quantity");
                        String cost = response.getString("cost");
                        String mrp = response.getString("mrp");
                        int icost = Integer.valueOf(cost);
                        int iqty = Integer.valueOf(quantity);
                        int itotal = icost * iqty;
                        String total = String.valueOf(itotal);

                        int pos = response.getInt("editPosition");
                        AddEditToList(brand, product, size, quantity, cost, mrp, total, pos);
                    }
                }
            }
        else if(requestCode==2)
                loadSpinnerDataName();
    }


    public void AddEditToList(String brand, String product, String size, String qty, String cost, String mrp,String total, int pos)
    {
        final PurchaseModel pm= new PurchaseModel();
        pm.setBrand(brand);
        pm.setProduct(product);
        pm.setSize(size);
        pm.setQty(qty);
        pm.setCost(cost);
        pm.setMrp(mrp);
        pm.setTotal(total);
        if(pos==-1)
            CustomPurchaseValueArr.add(pm);
        else
            CustomPurchaseValueArr.set(pos,pm);

        Resources res = getResources();
        ListView List = (ListView)myview.findViewById(R.id.purListView1);
        adapter = new cPurAdaptor(getContext(),CustomPurListView,CustomPurchaseValueArr,res);

        List.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addp:
            {
                Intent intent = new Intent(getContext(),addpurchase.class);
                startActivityForResult(intent,1);
                break;
            }
            case R.id.addcontact:
            {
                Intent intent = new Intent(getContext(),addcontact.class);
                startActivityForResult(intent,2);
                break;
            }
            case R.id.savep:
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
            case R.id.grandTotal: {
                setGrandTotal();
                break;
            }
            case R.id.cancelp:
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                break;

            }
        }
    }


    public void onSaveInstanceState(Bundle outState){
        getFragmentManager().putFragment(outState,"myfragment",this);
        outState.putInt("spinnercontact",spinner.getSelectedItemPosition());
    }
    public void onRestoreInstanceState(Bundle inState)
   { loadSpinnerDataName();

        myfragment = (Purchase) getFragmentManager().getFragment(inState,"myfragment");

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

    public void setGrandTotal(){
        ListView listView = (ListView)myview.findViewById(R.id.purListView1);

        View v ;
        gtotal=0;
        int count=  listView.getChildCount();

        if(count>0) {
            for (int i = 0; i < count; i++) {
                v = listView.getChildAt(i);
                //int tempTotal = Integer.parseInt(((TextView) v.findViewById(R.id.totall)).getText().toString());
                int tempTotal= Integer.valueOf(((TextView) v.findViewById(R.id.totall)).getText().toString());
                gtotal =gtotal+ tempTotal;
            }
        }

        grandTotal.setText(String.valueOf(gtotal));
    }


}

