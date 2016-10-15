package com.example.rushabh.clothesrack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rushabh on 26-07-2016.
 */
public class cTestAdaptor extends BaseAdapter {

    private test activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    Context context;
    TestModel tempValues = null;
    TestModel tempValues1 = null;
    int typeView;
    int i = 0;
    int typeValue;

    @SuppressLint("ServiceCast")
    public cTestAdaptor(Context context, test a, ArrayList d, Resources resLocal, int type) {
        this.context = context;
        activity = a;
        data = d;
        res = resLocal;
        typeView = type;
        setTypeValue(typeView);

    }

    public void setTypeValue(int value){

        typeValue= value;


    }
    public int getTypeValue(){
        return typeValue;
    }


    @Override
    public int getCount() {
        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

   @Override
    public int getItemViewType(int position) {


      TestModel tempdata= (TestModel) data.get(position);
       String s = tempdata.getType();
       switch (s) {
           case "0":
               return 0;
           case "1":
               return 1;
           case "2":
               return 2;
           case "3":
               return 3;
           case "4":
               return 4;
           case "5":
               return 5;
           case "6":
               return 6;

           default:
               return 99999;

       }

       /*if(position>=0 && position<=3){
            return 0;
        }
        else if(position>3&& position <=5)
            return 1;

       else return 2;*/
    }

    @Override
    public int getViewTypeCount() {
        return 7;
    }

    protected class stockHolder {
        TextView ProdName;
        TextView Size;
        TextView Qty;
        TextView ItemID;
        TextView type;


    }

    protected class billholder{
        TextView Name;
        TextView BillNo;
        TextView TotalAmount;
        TextView Date;
        TextView PurchaseID;
        TextView type;

    }

    protected class salebillholder{

        TextView sName;
        TextView sBillNo;
        TextView sTotalAmount;
        TextView sDate;
        TextView type;
    }

    protected class paymentholder{

        TextView PaymentID;
        TextView PaymentDate;
        TextView PaymentName;
        TextView PaymentAmount;
        TextView PaymentAgainst;
        TextView type;
    }

    protected class returnholder{

        TextView ReturnID;
        TextView ReturnName;
        TextView ReturnDate;
        TextView ReturnAmount;
        TextView ReturnAgainst;
        TextView type;
    }







    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        tempValues1 = (TestModel) data.get(position);
        String s = tempValues1.getType();
        Log.d("------", s.toString());
        int type = getItemViewType(position);
         stockHolder holder= null;
         billholder holder1=null;
        salebillholder holder2=null;
        paymentholder holder3=null;
        paymentholder holder4=null;
        returnholder holder5 = null;
        returnholder holder6 = null;



        switch (type) {
            case 0: {


                 holder = new stockHolder();
                if (row == null) {

                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    row = inflater.inflate(R.layout.stocklistview,null);

                    holder.ItemID = (TextView) row.findViewById(R.id.ItemIdView);
                    holder.ProdName = (TextView) row.findViewById(R.id.ProdNameView);
                    holder.Size = (TextView) row.findViewById(R.id.SizeView);
                    holder.Qty = (TextView) row.findViewById(R.id.QtyView);

                    row.setTag(holder);
                    holder1=null;
                    holder2= null;

                } else {
                    holder1=null;
                    holder2= null;
                    holder = (stockHolder) row.getTag();
                }

                if (data.size() <= 0) {
                    holder.ProdName.setText("No Data");

                } else {
                    tempValues = null;
                    tempValues = (TestModel) data.get(position);
                    Log.d("*********",tempValues.getItemID());
                    holder.ItemID.setText(tempValues.getItemID());
                    holder.ProdName.setText(tempValues.ProdName);
                    holder.Size.setText(tempValues.Size);
                    holder.Qty.setText(tempValues.Qty);

                }
                return row;
                //   break;


            }

            case 1: {

                 holder1 = new billholder();

                if (row == null) {

                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    row = inflater.inflate(R.layout.purchasebillview,null);

                    holder1.Date = (TextView) row.findViewById(R.id.DateView);
                    holder1.Name = (TextView) row.findViewById(R.id.NameView);
                    holder1.BillNo = (TextView) row.findViewById(R.id.BillNoView);
                    holder1.TotalAmount = (TextView) row.findViewById(R.id.TotalAmountView);
                    holder1.PurchaseID = (TextView) row.findViewById(R.id.PurchaseIDView);
                    holder1.type=(TextView)row.findViewById(R.id.typeView);
                    row.setTag(holder1);
                    holder=null;
                    holder2= null;

                }else {

                    holder=null;
                    holder2= null;
                    holder1 = (billholder) row.getTag();
                }

                if (data.size() <= 0) {
                    holder1.Name.setText(" ");
                } else {
                    tempValues = null;
                    tempValues = (TestModel) data.get(position);
                    holder1.Date.setText(tempValues.Date);
                    holder1.Name.setText(tempValues.Name);
                    holder1.BillNo.setText(tempValues.BillNo);
                    holder1.TotalAmount.setText(tempValues.TotalAmount);
                    holder1.PurchaseID.setText(tempValues.PurchaseID);
                    holder1.type.setText("Purchase");
                }
                return row;
                //   break;
            }


            case 2: {

                holder2 = new salebillholder();

                if (row == null) {

                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    row = inflater.inflate(R.layout.salebillview, null);

                    holder2.sDate = (TextView) row.findViewById(R.id.sDateView);
                    holder2.sName = (TextView) row.findViewById(R.id.sNameView);
                    holder2.sBillNo = (TextView) row.findViewById(R.id.sBillNoView);
                    holder2.sTotalAmount = (TextView) row.findViewById(R.id.sTotalAmountView);
                    holder2.type=(TextView)row.findViewById(R.id.typeView);
                    row.setTag(holder2);
                    holder=null;
                    holder1= null;

                }else {

                    holder=null;
                    holder1= null;
                    holder2 = (salebillholder) row.getTag();
                }

                if (data.size() <= 0) {
                    holder2.sName.setText(" ");
                } else {
                    tempValues = null;
                    tempValues = (TestModel) data.get(position);
                    holder2.sDate.setText(tempValues.sDate);
                    holder2.sName.setText(tempValues.sName);
                    holder2.sBillNo.setText(tempValues.sBillNo);
                    holder2.sTotalAmount.setText(tempValues.sTotalAmount);
                    holder2.type.setText("Sale");
                }
                return row;
                //   break;
            }

            case 3: {

                holder3 = new paymentholder();

                if (row == null) {

                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    row = inflater.inflate(R.layout.paymentlistview, null);

                    holder3.PaymentDate = (TextView) row.findViewById(R.id.paymentdateview);
                    holder3.PaymentName = (TextView) row.findViewById(R.id.paymentnameview);
                    holder3.PaymentAmount = (TextView) row.findViewById(R.id.paymentamountview);
                    holder3.PaymentID = (TextView) row.findViewById(R.id.paymentidview);
                    holder3.PaymentAgainst=(TextView)row.findViewById(R.id.paymentagainstview);
                    holder3.type=(TextView)row.findViewById(R.id.typeView);
                    row.setTag(holder3);
                    holder=null;
                    holder1= null;
                    holder2=null;
                }else {

                    holder=null;
                    holder1= null;
                    holder2=null;
                    holder3 = (paymentholder) row.getTag();
                }

                if (data.size() <= 0) {
                    holder3.PaymentName.setText(" ");
                } else {
                    tempValues = null;
                    tempValues = (TestModel) data.get(position);
                    holder3.PaymentName.setText(tempValues.PaymentName);
                    holder3.PaymentDate.setText(tempValues.PaymentDate);
                    holder3.PaymentID.setText(tempValues.PaymentID);
                    holder3.PaymentAmount.setText(tempValues.PaymentAmount);
                    holder3.PaymentAgainst.setText(tempValues.PaymentAgainst);
                    holder3.type.setText("Payment");
                }
                return row;
                //   break;
            }

            case 4: {

                holder4 = new paymentholder();

                if (row == null) {

                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    row = inflater.inflate(R.layout.paymentlistview, null);

                    holder4.PaymentDate = (TextView) row.findViewById(R.id.paymentdateview);
                    holder4.PaymentName = (TextView) row.findViewById(R.id.paymentnameview);
                    holder4.PaymentAmount = (TextView) row.findViewById(R.id.paymentamountview);
                    holder4.PaymentID = (TextView) row.findViewById(R.id.paymentidview);
                    holder4.PaymentAgainst=(TextView)row.findViewById(R.id.paymentagainstview);
                    holder4.type=(TextView)row.findViewById(R.id.typeView);
                    row.setTag(holder4);
                    holder=null;
                    holder1= null;
                    holder2=null;
                }else {

                    holder=null;
                    holder1= null;
                    holder2=null;
                    holder4 = (paymentholder) row.getTag();
                }

                if (data.size() <= 0) {
                    holder4.PaymentName.setText(" ");
                } else {
                    tempValues = null;
                    tempValues = (TestModel) data.get(position);
                    holder4.PaymentName.setText(tempValues.PaymentName);
                    holder4.PaymentDate.setText(tempValues.PaymentDate);
                    holder4.PaymentID.setText(tempValues.PaymentID);
                    holder4.PaymentAmount.setText(tempValues.PaymentAmount);
                    holder4.PaymentAgainst.setText(tempValues.PaymentAgainst);
                    holder4.type.setText("Discount");
                }
                return row;
                //   break;
            }

            case 5: {

                holder5 = new returnholder();

                if (row == null) {

                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    row = inflater.inflate(R.layout.returnlistview, null);

                    holder5.ReturnDate = (TextView) row.findViewById(R.id.returnDateView);
                    holder5.ReturnName = (TextView) row.findViewById(R.id.nameView);
                    holder5.ReturnAmount = (TextView) row.findViewById(R.id.amountView);
                    holder5.ReturnID = (TextView) row.findViewById(R.id.returnIDView);
                    holder5.ReturnAgainst=(TextView)row.findViewById(R.id.againstView);
                    holder5.type=(TextView)row.findViewById(R.id.typeView);

                    row.setTag(holder5);
                    holder=null;
                    holder1= null;
                    holder2=null;
                }else {

                    holder=null;
                    holder1= null;
                    holder2=null;
                    holder5 = (returnholder) row.getTag();
                }

                if (data.size() <= 0) {
                    holder5.ReturnName.setText(" ");
                } else {
                    tempValues = null;
                    tempValues = (TestModel) data.get(position);
                    holder5.ReturnName.setText(tempValues.returnName);
                    holder5.ReturnDate.setText(tempValues.returnDate);
                    holder5.ReturnID.setText(tempValues.returnID);
                    holder5.ReturnAmount.setText(tempValues.returnAmount);
                    holder5.ReturnAgainst.setText(tempValues.returnAgainst);
                    holder5.type.setText("Purchase Return");
                }
                return row;
                //   break;
            }

            case 6: {

                holder6 = new returnholder();

                if (row == null) {

                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    row = inflater.inflate(R.layout.returnlistview, null);

                    holder6.ReturnDate = (TextView) row.findViewById(R.id.returnDateView);
                    holder6.ReturnName = (TextView) row.findViewById(R.id.nameView);
                    holder6.ReturnAmount = (TextView) row.findViewById(R.id.amountView);
                    holder6.ReturnID = (TextView) row.findViewById(R.id.returnIDView);
                    holder6.ReturnAgainst=(TextView)row.findViewById(R.id.againstView);
                    holder6.type=(TextView)row.findViewById(R.id.typeView);

                    row.setTag(holder6);
                    holder=null;
                    holder1= null;
                    holder2=null;
                }else {

                    holder=null;
                    holder1= null;
                    holder2=null;
                    holder6 = (returnholder) row.getTag();
                }

                if (data.size() <= 0) {
                    holder6.ReturnName.setText(" ");
                } else {
                    tempValues = null;
                    tempValues = (TestModel) data.get(position);
                    holder6.ReturnName.setText(tempValues.returnName);
                    holder6.ReturnDate.setText(tempValues.returnDate);
                    holder6.ReturnID.setText(tempValues.returnID);
                    holder6.ReturnAmount.setText(tempValues.returnAmount);
                    holder6.ReturnAgainst.setText(tempValues.returnAgainst);
                    holder6.type.setText("Sale Return");
                }
                return row;
                //   break;
            }
            default: {
                return convertView;
            }


        }
    }
}
