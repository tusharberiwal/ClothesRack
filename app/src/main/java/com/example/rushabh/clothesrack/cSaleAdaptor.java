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


public class cSaleAdaptor extends BaseAdapter {
    private Sale activity;
    private ArrayList data;
    private static LayoutInflater inflater= null;
    public Resources res;
    Context context;
    SaleModel tempValues= null;
    int i=0;



    @SuppressLint("ServiceCast")
    public cSaleAdaptor(Context context,Sale a , ArrayList d, Resources resLocal){
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
        return position;
    }



    static class SaleHolder
    {
        TextView brand;
        TextView product;
        TextView size;
        TextView qty;
        TextView mrp;
        TextView total;
        TextView itemid;
        TextView srno;
    }

    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row= convertView;
        SaleHolder holder= null;

        if(row==null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row= inflater.inflate(R.layout.salelistview,parent,false);
            holder =  new SaleHolder();
            holder.brand=(TextView)row.findViewById(R.id.brands);
            holder.product= (TextView)row.findViewById(R.id.products);
            holder.size= (TextView)row.findViewById(R.id.sizes);
            holder.qty= (TextView)row.findViewById(R.id.qtys);
            holder.mrp= (TextView)row.findViewById(R.id.mrps);
            holder.total=(TextView)row.findViewById(R.id.totals);
            holder.itemid=(TextView)row.findViewById(R.id.itemids);
            holder.srno=(TextView)row.findViewById(R.id.srno);
            row.setTag(holder);
        }

        else
        {
            holder = (SaleHolder)row.getTag();
        }

        if(data.size()<=0)
        {
            holder.product.setText("No Data");
        }
        else
        {
            tempValues=null;
            tempValues = (SaleModel)data.get(position);
            holder.brand.setText(tempValues.brand);
            holder.product.setText(tempValues.product);
            holder.size.setText(tempValues.size);
            holder.qty.setText(tempValues.qty);
            holder.mrp.setText(tempValues.mrp);
            holder.total.setText(tempValues.total);
            holder.itemid.setText(tempValues.itemid);
            holder.srno.setText(tempValues.srno);
        }
        return row;
    }

}
