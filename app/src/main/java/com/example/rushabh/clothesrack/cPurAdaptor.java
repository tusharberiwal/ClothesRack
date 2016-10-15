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
 * Created by Rushabh on 16-07-2016.
 */
class cPurAdaptor extends BaseAdapter  {

    private Purchase activity;
    private ArrayList data;
    private static LayoutInflater inflater= null;
    public Resources res;
    Context context;
    PurchaseModel tempValues= null;
    int i=0;



    @SuppressLint("ServiceCast")
    public cPurAdaptor(Context context,Purchase a , ArrayList d, Resources resLocal){
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



    static class PurchaseHolder
    {
        TextView brand;
        TextView product;
        TextView size;
        TextView qty;
        TextView cost;
        TextView mrp;
        TextView total;
        TextView itemid;

    }

    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row= convertView;
        PurchaseHolder holder= null;

        if(row==null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row= inflater.inflate(R.layout.purlistview,parent,false);
            holder =  new PurchaseHolder();
            holder.brand=(TextView)row.findViewById(R.id.brands);
            holder.product= (TextView)row.findViewById(R.id.products);
            holder.size= (TextView)row.findViewById(R.id.size);
            holder.qty= (TextView)row.findViewById(R.id.qtys);
            holder.cost= (TextView)row.findViewById(R.id.cost);
            holder.mrp= (TextView)row.findViewById(R.id.mrps);
            holder.total=(TextView)row.findViewById(R.id.totall);
            holder.itemid=(TextView)row.findViewById(R.id.itemid);
            row.setTag(holder);
        }

        else
        {
            holder = (PurchaseHolder) row.getTag();
        }

        if(data.size()<=0)
        {
            holder.product.setText("No Data");
        }
        else
        {
            tempValues=null;
            tempValues = (PurchaseModel) data.get(position);
            holder.brand.setText(tempValues.brand);
            holder.product.setText(tempValues.product);
            holder.size.setText(tempValues.size);
            holder.qty.setText(tempValues.qty);
            holder.cost.setText(tempValues.cost);
            holder.mrp.setText(tempValues.mrp);
            holder.total.setText(tempValues.total);
            holder.itemid.setText(tempValues.itemid);
        }
        return row;
    }



}
