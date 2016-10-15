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
class cPurRetAdaptor extends BaseAdapter  {

    private PurchaseReturn activity;
    private ArrayList data;
    private static LayoutInflater inflater= null;
    public Resources res;
    Context context;
    PurchaseReturnModel tempValues= null;
    int i=0;



    @SuppressLint("ServiceCast")
    public cPurRetAdaptor(Context context, PurchaseReturn a , ArrayList d, Resources resLocal){
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
        TextView total;
        TextView itemid;
        TextView srno;

    }

    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row= convertView;
        PurchaseHolder holder= null;

        if(row==null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row= inflater.inflate(R.layout.purreturnlistview,parent,false);
            holder =  new PurchaseHolder();
            holder.brand=(TextView)row.findViewById(R.id.brandpret);
            holder.product= (TextView)row.findViewById(R.id.productpret);
            holder.size= (TextView)row.findViewById(R.id.sizepret);
            holder.qty= (TextView)row.findViewById(R.id.qtypret);
            holder.cost= (TextView)row.findViewById(R.id.costpret);
            holder.total=(TextView)row.findViewById(R.id.totalpret);
            holder.itemid=(TextView)row.findViewById(R.id.itemidpret) ;
            holder.srno=(TextView)row.findViewById(R.id.srno);
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

            tempValues = (PurchaseReturnModel) data.get(position);
            holder.brand.setText(tempValues.brand);
            holder.product.setText(tempValues.product);
            holder.size.setText(tempValues.size);
            holder.qty.setText(tempValues.qty);
            holder.cost.setText(tempValues.cost);
            holder.total.setText(tempValues.total);
            holder.itemid.setText(tempValues.itemid);
            holder.srno.setText(tempValues.srno);

        }
        return row;
    }



}
