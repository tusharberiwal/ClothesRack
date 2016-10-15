package com.example.rushabh.clothesrack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by tushar on 9/11/2016.
 */
public class BillDetails extends Activity {

    ListView listView;

    ArrayList< String> arrayList; // list of the strings that should appear in ListView
    ArrayAdapter arrayAdapter; // a middle man to bind ListView and array list

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_details_view);






    }
}
