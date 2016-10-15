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
 * Created by Rushabh on 26-07-2016.
 */
public class cStockAdaptor extends BaseAdapter{

    private Stock activity;
    private ArrayList data;
    private static LayoutInflater inflater= null;
    public Resources res;
    Context context;
    StockModel tempValues= null;
    int i=0;

    @SuppressLint("ServiceCast")
    public cStockAdaptor(Context context,Stock a , ArrayList d, Resources resLocal) {
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


    static class StockHolder
    {
        TextView ProdName;
        TextView Size;
        TextView Qty;
        TextView ItemID;


    }
    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row= convertView;
        StockHolder holder= null;

        if(row==null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row= inflater.inflate(R.layout.stocklistview,parent,false);
            holder =  new StockHolder();
            holder.ItemID=(TextView)row.findViewById(R.id.ItemIdView) ;
            holder.ProdName= (TextView)row.findViewById(R.id.ProdNameView);
            holder.Size = (TextView)row.findViewById(R.id.SizeView);
            holder.Qty= (TextView)row.findViewById(R.id.QtyView);


            row.setTag(holder);
        }

        else
        {
            holder = (StockHolder) row.getTag();
        }

        if(data.size()<=0)
        {
            holder.ProdName.setText("No Data");
        }
        else
        {
            tempValues=null;
            tempValues = (StockModel) data.get(position);

            holder.ItemID.setText(tempValues.ItemID);
            holder.ProdName.setText(tempValues.ProdName);
            holder.Size.setText(tempValues.Size);
            holder.Qty.setText(tempValues.Qty);

        }
        return row;
    }


}
