package com.example.rushabh.clothesrack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by tushar on 9/16/2016.
 */
public class cSaleBillAdaptor extends BaseAdapter{

    private SaleBill activity;
    private ArrayList data;
    private static LayoutInflater inflater= null;
    public Resources res;
    Context context;
    SaleBillModel tempValues= null;
    int i=0;

    @SuppressLint("ServiceCast")
    public cSaleBillAdaptor(Context context, SaleBill a , ArrayList d, Resources resLocal) {
        this.context = context;
        activity  = a;
        data= d;
        res = resLocal;

    }



    @Override
    public int getCount() {
        if(data.size()<=0)
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


    static class SaleBillHolder
    {
        TextView Name;
        TextView BillNo;
        TextView TotalAmount;
        TextView Date;
      //  TextView PurchaseID;


    }
    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row= convertView;
        SaleBillHolder holder= null;

        if(row==null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row= inflater.inflate(R.layout.salebillview,parent,false);
            holder =  new SaleBillHolder();
            holder.Date=(TextView)row.findViewById(R.id.sDateView) ;
            holder.Name= (TextView)row.findViewById(R.id.sNameView);
            holder.BillNo = (TextView)row.findViewById(R.id.sBillNoView);
            holder.TotalAmount= (TextView)row.findViewById(R.id.sTotalAmountView);
           // holder.PurchaseID=(TextView)row.findViewById(R.id.PurchaseIDView);


            row.setTag(holder);
        }

        else
        {
            holder = (SaleBillHolder) row.getTag();
        }

        if(data.size()<=0)
        {
            holder.Name.setText("No Data");
        }
        else
        {
            tempValues=null;
            tempValues = (SaleBillModel) data.get(position);

            holder.Date.setText(tempValues.Date);
            holder.Name.setText(tempValues.Name);
            holder.BillNo.setText(tempValues.BillNo);
            holder.TotalAmount.setText(tempValues.TotalAmount);
         //   holder.PurchaseID.setText(tempValues.PurchaseID);

        }
        return row;
    }
}
