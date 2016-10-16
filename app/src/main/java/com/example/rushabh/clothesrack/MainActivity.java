package com.example.rushabh.clothesrack;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        SQLiteDatabase clothesrackDB = null;
        String ProductTable= "Product";
        String StockTable="Stock";
        String ContactsTable="Contacts";
        String PurchaseTable="Purchase";
        String PurchaseDetailTable="PurchaseDetails";
        String SalesTable= "Sales";
        String SalesDetailTable="SalesDetail";
        String PaymentTable= "Payment";
        String BrandTable="Brand";

        try{
            //create db
            clothesrackDB = this.openOrCreateDatabase("ClothesRack",MODE_PRIVATE,null);

            //create Table Brand
            clothesrackDB.execSQL("CREATE TABLE IF NOT EXISTS " + BrandTable + "(BrandID INTEGER PRIMARY KEY AUTOINCREMENT, BrandName Varchar, BrandTD DATETIME);");
            //create Table Product
            clothesrackDB.execSQL("CREATE TABLE IF NOT EXISTS " + ProductTable + "(ProdID INTEGER PRIMARY KEY AUTOINCREMENT, ProdName Varchar, ProdTD DATETIME);");
            //create Stock Table
            clothesrackDB.execSQL("CREATE TABLE IF NOT EXISTS "+StockTable+"(ItemID INT PRIMARY KEY, Qty INT, StockTD DATETIME);");
            //create contacts table
            clothesrackDB.execSQL("CREATE TABLE IF NOT EXISTS "+ContactsTable+"(CID INTEGER PRIMARY KEY AutoIncrement,CName Varchar,Details Varchar,ContactsTD DATETIME);");
            //create purchase table
            clothesrackDB.execSQL("CREATE TABLE IF NOT EXISTS "+PurchaseTable+"(PurID INTEGER PRIMARY KEY AutoIncrement ,BillNo Varchar,CName Varchar,Details Varchar,PDate Date,BillAmt Int,Comments Varchar,PurTD DateTime);");
            //create purchase detail table
            clothesrackDB.execSQL("CREATE TABLE IF NOT EXISTS "+PurchaseDetailTable+"(ItemID INTEGER Primary Key AutoIncrement,PurID Int,BrandID Int,ProdID Int, Size Char, Cost Int, Qty Int, MRP Int,PurDeID DATETIME);");
            //create sales table
            clothesrackDB.execSQL("CREATE TABLE IF NOT EXISTS "+SalesTable+"(BillNo INTEGER Primary Key AutoIncrement,CName Varchar, Details Varchar,SDate Date,BillAmt Int,Qty Int, Comments Varchar,SalesTD DATETIME);");
            //create salesdetails table
            clothesrackDB.execSQL("CREATE TABLE IF NOT EXISTS "+SalesDetailTable+"(SrNo INTEGER Primary Key AutoIncrement,BillNo Int, ItemID Int,Cost Int, Qty Int,SalesDeTD DateTime);");
            //create payment table
            clothesrackDB.execSQL("CREATE TABLE IF NOT EXISTS "+PaymentTable+"(PayNo INTEGER Primary Key AutoIncrement,PS Boolean,Id Int,PayDate Date,PayAmt Int,PayTD DateTime);");

        }

        catch(Exception e) {
            Log.e("Error", "Error", e);
        }finally {
            if (clothesrackDB != null)
                clothesrackDB.close();
        }

        */

        db= new DatabaseHelper(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();


        if (id == R.id.nav_purchase) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new Purchase()).commit();
        } else if (id == R.id.nav_sale) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new Sale()).commit();

        } else if (id == R.id.nav_stock) {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new Stock()).commit();

        } else if (id == R.id.nav_payment){
            fragmentManager.beginTransaction().replace(R.id.content_frame,new Payment()).commit();

        }
        else if(id== R.id.nav_purchase_retun){
            fragmentManager.beginTransaction().replace(R.id.content_frame,new PurchaseReturn()).commit();

        }
        else if(id== R.id.nav_sale_retun){
            fragmentManager.beginTransaction().replace(R.id.content_frame,new SaleReturn()).commit();

        }
        else if(id==R.id.nav_test){
            fragmentManager.beginTransaction().replace(R.id.content_frame,new test()).commit();
        }

        else if (id == R.id.export_database) {
            try {
                String sPackageName = "com.example.rushabh.clothesrack";
                String sDBName= "ClothesRack";
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();
                if (sd.canWrite()) {
                    String currentDBPath = "data/"+sPackageName+"/databases/"+sDBName;
                    String backupDBPath = "/.appname-external-data-cache/"+sDBName; //"{database name}";
                    File dir = new File(sd,backupDBPath.replace(sDBName,""));
                    if(dir.mkdir()) {

                    }
                    File currentDB = new File(data, currentDBPath);
                    File backupDB = new File(sd, backupDBPath);
                    if (currentDB.exists()) {
                        FileChannel src = new FileInputStream(currentDB).getChannel();
                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                    }
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),  "Error"+e, Toast.LENGTH_LONG).show();

            }


        }
        else if (id == R.id.drop_table) {
            getApplicationContext().deleteDatabase("ClothesRack");

        }
        else if (id==R.id.nav_purchase_bill)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new PurchaseBill()).commit();
        }
        else if(id==R.id.nav_sale_bill)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new SaleBill()).commit();
        }
		else if (id==R.id.nav_purchase_bill)
        {
            fragmentManager.beginTransaction().replace(R.id.content_frame,new PurchaseBill()).commit();
        }
//        else if (id==R.id.printBarCodes)
//        {
//            fragmentManager.beginTransaction().replace(R.id.content_frame,new BarCodePrint()).commit();
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.rushabh.clothesrack/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.rushabh.clothesrack/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}
