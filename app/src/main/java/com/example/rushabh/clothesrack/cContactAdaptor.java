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
public class cContactAdaptor extends BaseAdapter{

    private Contact activity;
    private ArrayList data;
    private static LayoutInflater inflater= null;
    public Resources res;
    Context context;
    ContactModel tempValues= null;
    int i=0;

    @SuppressLint("ServiceCast")
    public cContactAdaptor(Context context, Contact a , ArrayList d, Resources resLocal) {
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


    static class ContactHolder
    {
        TextView cName;
        TextView cNumber;
        TextView cID;



    }
    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row= convertView;
       ContactHolder holder= null;

        if(row==null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row= inflater.inflate(R.layout.contactlistview,parent,false);
            holder =  new ContactHolder();
            holder.cName=(TextView)row.findViewById(R.id.cName) ;
            holder.cNumber= (TextView)row.findViewById(R.id.cNumber);
            holder.cID = (TextView)row.findViewById(R.id.cID);

            row.setTag(holder);
        }

        else
        {
            holder = (ContactHolder) row.getTag();
        }

        if(data.size()<=0)
        {
            holder.cName.setText("No Data");
        }
        else
        {
            tempValues=null;
            tempValues = (ContactModel) data.get(position);

            holder.cID.setText(tempValues.cID);
            holder.cName.setText(tempValues.cName);
            holder.cNumber.setText(tempValues.cNumber);


        }
        return row;
    }


}
