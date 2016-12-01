package com.example.rushabh.clothesrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rushabh on 27-08-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_Name= "ClothesRack";
    public static final String Table_Brand= "Brand";
    public static final String Table_Product="Product";
    public static final String Table_Stock="Stock";
    public static final String Table_Contacts="Contacts";
    public static final String Table_Purchase="Purchase";
    public static final String Table_PurchaseDetails="PurchaseDetails";
    public static final String Table_Sales="Sales";
    public static final String Table_SalesDetails="SalesDetails";
    public static final String Table_Payment="Payment";
    public static final String Table_Discount="Discount";
    public static final String Table_Purchase_Return="PurchaseReturn";
    public static final String Table_Purchase_ReturnDetails="PurchaseReturnDetails";
    public static final String Table_Sales_Return="SalesReturn";
    public static final String Table_Sales_ReturnDetails="SalesReturnDetails";
    public static final String Col_Brand_ID="BrandID";
    public static final String Col_Brand_Name="BrandName";
    public static final String Col_Brand_TD="BrandTD";
    public static final String Col_Prod_ID="ProdID";
    public static final String Col_Prod_Name="ProdName";
    public static final String Col_Prod_TD="ProdTD";
    public static final String Col_Stock_ItemID="ItemID";
    public static final String Col_Stock_Brand="BrandID";
    public static final String Col_Stock_Product="ProductID";
    public static final String Col_Stock_Size="Size";
    public static final String Col_Stock_Qty="Qty";
    public static final String Col_Stock_TD = "StockTD";
    public static final String Col_Contacts_ID="CID";
    public static final String Col_Contacts_Name="CName";
    public static final String Col_Contacts_No="CNo";
    public static final String Col_Contacts_Details="CDetails";
    public static final String Col_Contacts_TD="ContactsTD";
    public static final String Col_Purchase_ID="PurID";
    public static final String Col_Purchase_BillNo="BillNo";
    public static final String Col_Purchase_Contact="CID";
    public static final String Col_Purchase_Date="PDate";
    public static final String Col_Purchase_Amt="BillAmt";
    public static final String Col_Purchase_Comments="Comments";
    public static final String Col_Purchase_Td="PurTD";
    public static final String Col_PurchaseDetails_Item="ItemID";
    public static final String Col_PurchaseDetails_Pur="PurId";
    public static final String Col_PurchaseDetails_Qty="PQty";
    public static final String Col_PurchaseDetails_Cost="Cost";
    public static final String Col_PurchaseDetails_MRP="MRP";
    public static final String Col_PurchaseDetails_TD="PurDeTD";
    public static final String Col_Sales_BillNo="BillNo";
    public static final String Col_Sales_Cid="Cid";
    public static final String Col_Sales_Date="SDate";
    public static final String Col_Sales_Amt="BillAmt";
    public static final String Col_Sales_Comments="Comments";
    public static final String Col_Sales_TD="SalesTD";
    public static final String Col_SalesDetails_ID="SrNo";
    public static final String Col_SalesDetails_BillNo="BillNo";
    public static final String Col_SalesDetails_Item="ItemID";
    public static final String Col_SalesDetails_Cost="SCost";
    public static final String Col_SalesDetails_Qty="SQty";
    public static final String Col_SalesDetails_TD="SalesDeTD";
    public static final String Col_Payment_No="PayNo";
    public static final String Col_Payment_isMade="isMade";
    public static final String Col_Payment_ID="ID";
    public static final String Col_Payment_Contact="CID";
    public static final String Col_Payment_Date="PayDate";
    public static final String Col_Payment_Amt="Amt";
    public static final String Col_Payment_TD="PayTD";
    public static final String Col_Discount_No="DiscNo";
    public static final String Col_Discount_isMade="isMade";
    public static final String Col_Discount_ID="ID";
    public static final String Col_Discount_Contact="CID";
    public static final String Col_Discount_Date="DiscDate";
    public static final String Col_Discount_Amt="DiscAmt";
    public static final String Col_Discount_TD="DiscTD";
    public static final String Col_PurchaseReturn_ID="PurRetID";
    public static final String Col_PurchaseReturn_Contact="Cid";
    public static final String Col_PurchaseReturn_BillNo="PAgainstBNO";
    public static final String Col_PurchaseReturn_Date="PRDate";
    public static final String Col_PurchaseReturn_Amt="PRAmt";
    public static final String Col_PurchaseReturn_Comments="Comments";
    public static final String Col_PurchaseReturn_TD="PurRetTD";
    public static final String Col_PurchaseReturnDetails_Srno="Srno";
    public static final String Col_PurchaseReturnDetails_Item="ItemID";
    public static final String Col_PurchaseReturnDetails_ID="PurRetID";
    public static final String Col_PurchaseReturnDetails_Qty="PurRetQty";
    public static final String Col_PurchaseReturnDetails_Cost="PurRetCost";
    public static final String Col_PurchaseReturnDetails_TD="PurRetDtTD";
    public static final String Col_SalesReturn_ID="SaleRetID";
    public static final String Col_SalesReturn_Contact="Cid";
    public static final String Col_SalesReturn_BillNo="SAgainstBNO";
    public static final String Col_SalesReturn_Date="SRDate";
    public static final String Col_SalesReturn_Amt="SRAmt";
    public static final String Col_SalesReturn_Comments="Comments";
    public static final String Col_SalesReturn_TD="SalesRetTD";
    public static final String Col_SalesReturnDetails_Srno="Srno";
    public static final String Col_SalesReturnDetails_Item="ItemID";
    public static final String Col_SalesReturnDetails_ID="SalesRetID";
    public static final String Col_SalesReturnDetails_Qty="SalesRetQty";
    public static final String Col_SalesReturnDetails_Cost="SalesRetCost";
    public static final String Col_SalesReturnDetails_TD="SalesRetDtTD";



    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context,DB_Name,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String brand= "CREATE TABLE IF NOT EXISTS "+Table_Brand+" ("+Col_Brand_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+Col_Brand_Name+" VARCHAR , "+Col_Brand_TD+" DATETIME )" ;
        db.execSQL(brand);
        String product="CREATE TABLE IF NOT EXISTS " +Table_Product+" ("+Col_Prod_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+Col_Prod_Name+" VARCHAR , "+Col_Prod_TD+" DATETIME )";
        db.execSQL(product);
        String stock= "CREATE TABLE IF NOT EXISTS "+Table_Stock+" ("+Col_Stock_ItemID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+Col_Stock_Brand+" INTEGER , "+Col_Stock_Product+" INTEGER , "+Col_Stock_Size+" VARCHAR , "+Col_Stock_Qty+" INTEGER , "+Col_Stock_TD+" DATETIME )";
        db.execSQL(stock);
        String contact="CREATE TABLE IF NOT EXISTS "+Table_Contacts+" ("+Col_Contacts_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+Col_Contacts_Name+" VARCHAR , "+Col_Contacts_No+" INTEGER , "+Col_Contacts_Details+" VARCHAR , "+Col_Contacts_TD+" DATETIME )";
        db.execSQL(contact);
        String purchase="CREATE TABLE IF NOT EXISTS "+Table_Purchase+" ("+Col_Purchase_ID+" INTEGER PRIMARY KEY ,"+Col_Purchase_BillNo+" VARCHAR , "+Col_Purchase_Contact+" INTEGER , "+Col_Purchase_Date+" DATETIME , "+Col_Purchase_Amt+" INTEGER , "+Col_Purchase_Comments+" VARCHAR , "+Col_Purchase_Td+" DATETIME )";
        db.execSQL(purchase);
        String purchasedetails= "CREATE TABLE IF NOT EXISTS "+Table_PurchaseDetails+" ("+Col_PurchaseDetails_Item+" INTEGER ,"+Col_PurchaseDetails_Pur+" INTEGER , "+Col_PurchaseDetails_Qty+" INTEGER , "+Col_PurchaseDetails_Cost+" INTEGER ,  "+Col_PurchaseDetails_MRP+" INTEGER , "+Col_PurchaseDetails_TD+" DATETIME)";
        db.execSQL(purchasedetails);
        String sales="CREATE TABLE IF NOT EXISTS "+Table_Sales+" ("+Col_Sales_BillNo+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+Col_Sales_Cid+" INTEGER , "+Col_Sales_Date+" DATETIME , "+Col_Sales_Amt+" INTEGER , "+Col_Sales_Comments+" VARCHAR , "+Col_Sales_TD+" DATETIME )";
        db.execSQL(sales);
        String salesdetails="CREATE TABLE IF NOT EXISTS "+Table_SalesDetails+" ("+Col_SalesDetails_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+Col_SalesDetails_BillNo+" INTEGER , "+Col_SalesDetails_Item+" INTEGER , "+Col_SalesDetails_Cost+" INTEGER , "+Col_SalesDetails_Qty+" INTEGER , "+Col_SalesDetails_TD+" DATETIME )";
        db.execSQL(salesdetails);
        String payment = "CREATE TABLE IF NOT EXISTS "+Table_Payment+" ("+Col_Payment_No+" INTEGER PRIMARY KEY AUTOINCREMENT , "+Col_Payment_isMade+" BOOLEAN , "+Col_Payment_Contact+" INTEGER , "+Col_Payment_ID+" INTEGER , "+Col_Payment_Amt+" INTEGER , "+Col_Payment_Date+" DATETIME , "+Col_Payment_TD+" DATETIME )";
        db.execSQL(payment);
        String discount= "CREATE TABLE IF NOT EXISTS "+Table_Discount+" ("+Col_Discount_No+" INTEGER PRIMARY KEY AUTOINCREMENT , "+Col_Discount_isMade+" BOOLEAN , "+Col_Discount_Contact+" INTEGER , "+Col_Discount_ID+" INTEGER , "+Col_Discount_Amt+" INTEGER , "+Col_Discount_Date+" DATETIME , "+Col_Discount_TD+" DATETIME )";
        db.execSQL(discount);
        String purchasereturn= "CREATE TABLE IF NOT EXISTS "+Table_Purchase_Return+" ("+Col_PurchaseReturn_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+Col_PurchaseReturn_Contact+" INTEGER , "+Col_PurchaseReturn_BillNo+" VARCHAR , "+Col_PurchaseReturn_Date+" DATETIME , "+Col_PurchaseReturn_Amt+" INTEGER , "+Col_PurchaseReturn_Comments+" VARCHAR , "+Col_PurchaseReturn_TD+" DATETIME )";
        db.execSQL(purchasereturn);
        String purchasereturndetails="CREATE TABLE IF NOT EXISTS "+Table_Purchase_ReturnDetails+" ("+Col_PurchaseReturnDetails_Srno+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Col_PurchaseReturnDetails_Item+" INTEGER , "+Col_PurchaseReturnDetails_ID+" INTEGER , "+Col_PurchaseReturnDetails_Qty+" INTEGER , "+Col_PurchaseReturnDetails_Cost+" INTEGER , "+Col_PurchaseReturnDetails_TD+" DATETIME )";
        db.execSQL(purchasereturndetails);
        String salesreturn= "CREATE TABLE IF NOT EXISTS "+Table_Sales_Return+" ("+Col_SalesReturn_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Col_SalesReturn_Contact+" INTEGER , "+Col_SalesReturn_BillNo+" INTEGER , "+Col_SalesReturn_Date+" DATETIME , "+Col_SalesReturn_Amt+" INTEGER , "+Col_SalesReturn_Comments+" VARCHAR , "+Col_SalesReturn_TD+" DATETIME )";
        db.execSQL(salesreturn);
        String salesreturndetails="CREATE TABLE IF NOT EXISTS "+Table_Sales_ReturnDetails+" ("+Col_SalesReturnDetails_Srno+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Col_SalesReturnDetails_Item+" INTEGER , "+Col_SalesReturnDetails_ID+" INTEGER , "+Col_SalesReturnDetails_Qty+" INTEGER , "+Col_SalesReturnDetails_Cost+" INTEGER , "+Col_SalesReturnDetails_TD+" DATETIME )";
        db.execSQL(salesreturndetails);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<String> getContactNames(){

        SQLiteDatabase db= this.getReadableDatabase();
        List <String> names = new ArrayList<>();
        String sql= "SELECT * FROM "+Table_Contacts;
        Cursor cursor = db.rawQuery(sql,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;

    }

    public String getContactID(String cname){
        SQLiteDatabase db= this.getReadableDatabase();
        String sql= "Select "+Col_Contacts_ID+" From "+Table_Contacts+" Where "+Col_Contacts_Name+"='"+cname+"'";
        Cursor cursor = db.rawQuery(sql,null);
        String cid = new String();
                // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            cid = cursor.getString(0);
        }
        cursor.close();
        return cid;


    }

    public void insertContacts(String cname, String cno, String cdetails, String date){

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Contacts_Name,cname);
        contentValues.put(Col_Contacts_No,cno);
        contentValues.put(Col_Contacts_Details,cdetails);
        contentValues.put(Col_Contacts_TD,date);
        db.insert(Table_Contacts,null,contentValues);
        db.close();

    }

    public void insertBrand(String bname, String date)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Brand_Name,bname);
        contentValues.put(Col_Brand_TD,date);
        db.insert(Table_Brand,null,contentValues);
        db.close();

    }

    public Cursor getBrand(){
        SQLiteDatabase db= this.getReadableDatabase();
        String sql= "SELECT * FROM "+Table_Brand;
        Cursor c = db.rawQuery(sql,null);
        return c;
    }

    public void insertProduct(String pname, String date){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Prod_Name,pname);
        contentValues.put(Col_Prod_TD,date);
        db.insert(Table_Product,null,contentValues);
        db.close();

    }
    public Cursor getProduct(){
        SQLiteDatabase db= this.getReadableDatabase();
        String sql= "SELECT * FROM "+Table_Product;
        Cursor c = db.rawQuery(sql,null);
        return c;
    }

    public void insertPurchase(String billno, String cid, String pdate, String billamt, String comments, String purtd)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Purchase_BillNo,billno);
        contentValues.put(Col_Purchase_Contact,cid);
        contentValues.put(Col_Purchase_Date,pdate);
        contentValues.put(Col_Purchase_Amt,billamt);
        contentValues.put(Col_Purchase_Comments,comments);
        contentValues.put(Col_Purchase_Td,purtd);
        db.insert(Table_Purchase,null,contentValues);
        db.close();

    }

    public String getStockItemID()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        String sql ="Select Max("+Col_Stock_ItemID+") FROM "+Table_Stock ;
        String itemid=null;
        Cursor cursor = db.rawQuery(sql, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            itemid= cursor.getString(0);
        }
        cursor.close();
        return itemid;

    }

    public void insertStock(String brandid, String productid,String size,String qty,String td)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Stock_Brand,brandid);
        contentValues.put(Col_Stock_Product,productid);
        contentValues.put(Col_Stock_Size,size);
        contentValues.put(Col_Stock_Qty,qty);
        contentValues.put(Col_Stock_TD,td);
        db.insert(Table_Stock,null,contentValues);
        db.close();
    }
    public void insertPurchaseDetails(String itemid,String purid,String qty,String cost,String mrp,String td)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_PurchaseDetails_Item,itemid);
        contentValues.put(Col_PurchaseDetails_Pur,purid);
        contentValues.put(Col_PurchaseDetails_Qty,qty);
        contentValues.put(Col_PurchaseDetails_Cost,cost);
        contentValues.put(Col_PurchaseDetails_MRP,mrp);
        contentValues.put(Col_PurchaseDetails_TD,td);
        db.insert(Table_PurchaseDetails,null,contentValues);
        db.close();
    }

    public String getProductID(String tempProduct)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        String productID = new String();
        //String selectQuery = "SELECT MAX(PurID)  FROM PURCHASE";
        String sql = "SELECT "+Col_Prod_ID+" from "+Table_Product+" where "+Col_Prod_Name+" ='"+tempProduct+"'";
        Cursor cursor = db.rawQuery(sql,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            productID = cursor.getString(0);
        }
        cursor.close();

        return productID;
    }

    public String getBrandID(String tempBrand)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String brandID = new String();
        //String selectQuery = "SELECT MAX(PurID)  FROM PURCHASE";
        String sql = "SELECT "+Col_Brand_ID+" from "+Table_Brand+" where "+Col_Brand_Name+" = '"+tempBrand+"'";
        Cursor cursor = db.rawQuery(sql,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            brandID = cursor.getString(0);
        }
        cursor.close();

        return brandID;
    }

    public String getPurID()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String purchaseID = new String();
        //String selectQuery = "SELECT MAX(PurID)  FROM PURCHASE";
        String selectQuery = "SELECT Max("+Col_Purchase_ID+") from "+Table_Purchase;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            purchaseID = cursor.getString(0);
        }
        cursor.close();
        return purchaseID;
    }

    public List<String> getProductNames(){

        SQLiteDatabase db= this.getReadableDatabase();
        List <String> names = new ArrayList<>();
        String sql= "SELECT * FROM "+Table_Product;
        Cursor cursor = db.rawQuery(sql,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;

    }

    public List<String> getBrandNames(){

        SQLiteDatabase db= this.getReadableDatabase();
        List <String> names = new ArrayList<>();
        String sql= "SELECT * FROM "+Table_Brand;
        Cursor cursor = db.rawQuery(sql,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;

    }
    public String getItemSize(String itemid){
        SQLiteDatabase db = this.getReadableDatabase();
        List <String> details = new ArrayList<>();
        String temp= new String();
       // String sql = "SELECT * FROM "+Table_Stock+" Inner Join "+Table_PurchaseDetails+" ON ("+Table_Stock+"."+Col_Stock_ItemID+"="+Table_PurchaseDetails+"."+Col_PurchaseDetails_Item+")Where "+Table_Stock+"."+Col_Stock_ItemID+"='"+itemid+"'" ;
       String sql= "SELECT "+Col_Stock_Size+" FROM "+Table_Stock+" WHERE "+Col_Stock_ItemID+"='"+itemid+"'";
        Cursor cursor = db.rawQuery(sql,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                Log.d("!!!!","size");
              //  details.add(cursor.getString());
                temp=cursor.getString(0);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return temp;


    }

    public String getItemMrp(String itemid){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT * FROM "+Table_PurchaseDetails+" WHERE "+Col_PurchaseDetails_Item+"='"+itemid+"'";
        Cursor cursor = db.rawQuery(sql,null);
        String temp= new String();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                Log.d("!!!!","size");
                //  details.add(cursor.getString());
                temp=cursor.getString(4);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return temp;

    }

    public List<String> getBrandFromItem(String itemid){

        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT * FROM "+Table_Stock+" JOIN "+Table_Brand+" ON ("+Table_Stock+"."+Col_Stock_Brand+"="+Table_Brand+"."+Col_Brand_ID+") Join "+Table_Product+" ON ("+Table_Stock+"."+Col_Stock_Product+"="+Table_Product+"."+Col_Prod_ID+")"+" WHERE "+Col_Stock_ItemID+"='"+itemid+"'";
        Cursor cursor = db.rawQuery(sql,null);
       // String temp= new String();
        List<String> temp= new ArrayList<>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                Log.d("!!!!","size");
                //  details.add(cursor.getString());
                temp.add(cursor.getString(cursor.getColumnIndex(Col_Brand_Name)));
                temp.add(cursor.getString(cursor.getColumnIndex(Col_Prod_Name)));
                temp.add(cursor.getString(cursor.getColumnIndex(Col_Stock_Size)));


            } while (cursor.moveToNext());
        }
        cursor.close();
        return temp;
    }

    public int getStockQty(String itemid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql="SELECT "+Col_Stock_Qty+" From "+Table_Stock+" Where "+Col_Stock_ItemID+"='"+itemid+"'";
        String stockqtys= new String();
        Cursor cursor= db.rawQuery(sql,null);
        if (cursor.moveToFirst()) {

            do {
                stockqtys = cursor.getString(0);

            } while (cursor.moveToNext());
        }
        cursor.close();
        int stockqty= Integer.valueOf(stockqtys);
        return stockqty;


    }

    public String getNextBillNo(){
        SQLiteDatabase db = this.getReadableDatabase();
        String billno = new String();
        //String selectQuery = "SELECT MAX(PurID)  FROM PURCHASE";
        String selectQuery = "SELECT Max("+Col_Sales_BillNo+") from "+Table_Sales;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            billno = cursor.getString(0);
        }
        cursor.close();
        return billno;


    }

    public void insertSales(String cid, String date, String amt, String comment,String td)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Sales_Cid,cid);
        contentValues.put(Col_Sales_Date,date);
        contentValues.put(Col_Sales_Amt,amt);
        contentValues.put(Col_Sales_Comments,comment);
        contentValues.put(Col_Sales_TD,td);
        db.insert(Table_Sales,null,contentValues);
        db.close();

    }

    public void insertSalesDetails(String billno, String itemid,String cost,String qty,String td)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_SalesDetails_BillNo,billno);
        contentValues.put(Col_SalesDetails_Item,itemid);
        contentValues.put(Col_SalesDetails_Cost,cost);
        contentValues.put(Col_SalesDetails_Qty,qty);
        contentValues.put(Col_SalesDetails_TD,td);
        db.insert(Table_SalesDetails,null,contentValues);
        db.close();


    }

    public void updateQtyStock(String itemid,String qty,String td)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        String sql= "UPDATE "+Table_Stock+" SET "+Col_Stock_Qty+"="+qty+" WHERE "+Col_Stock_ItemID+"= '"+itemid+"'";
        db.execSQL(sql);
        db.close();

    }

    public void deletesales(String billno){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql = "DELETE FROM "+Table_Sales+" WHERE "+Col_Sales_BillNo+"= '"+billno+"'";
        db.execSQL(sql);
        db.close();
    }


    public Cursor populateStock()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        String sql="Select * From "+Table_Stock+" Join "+Table_Brand+" On ("+Table_Stock+"."+Col_Stock_Brand+"="+Table_Brand+"."+Col_Brand_ID+") Join "+Table_Product+" On ("+Table_Stock+"."+Col_Stock_Product+"="+Table_Product+"."+Col_Prod_ID+")";
        Cursor c= db.rawQuery(sql,null);
        return c;
    }

    public String getContactName(String contactID)
    {

        SQLiteDatabase db= this.getReadableDatabase();
        String sql= "Select "+Col_Contacts_Name+" From "+Table_Contacts+" Where "+Col_Contacts_ID+"='"+contactID+"'";
        Cursor cursor = db.rawQuery(sql,null);
        String cid = new String();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            cid = cursor.getString(0);
        }
        cursor.close();
        return cid;

    }

    public Cursor populatePurchaseBill()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        String sql="Select * From "+Table_Purchase;
        Cursor c= db.rawQuery(sql,null);
        return c;
    }

    public Cursor populateSaleBill()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        String sql="Select * From "+Table_Sales;
        Cursor c= db.rawQuery(sql,null);
        return c;
    }

    public Cursor populatePayment()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        String sql="Select * From "+Table_Payment;
        Cursor c= db.rawQuery(sql,null);
        return c;
    }
    public Cursor populateDiscount()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        String sql="Select * From "+Table_Discount;
        Cursor c= db.rawQuery(sql,null);
        return c;
    }

    public Cursor populatePurchaseReturn()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        String sql="Select * From "+Table_Purchase_Return;
        Cursor c= db.rawQuery(sql,null);
        return c;
    }


    public Cursor populateSaleReturn()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        String sql="Select * From "+Table_Sales_Return;
        Cursor c= db.rawQuery(sql,null);
        return c;
    }

    public Cursor populateFilteredPurchaseBill(String billNo, String contactID)
    {
        SQLiteDatabase db= this.getReadableDatabase();
        String sql="";
        if(billNo==null && contactID==null)
            return null;
        else if(billNo!=null && !billNo.isEmpty() && contactID!=null && !contactID.isEmpty())
            sql="Select * From "+Table_Purchase+" where "+Col_Purchase_BillNo+" ='"+billNo+"' AND " + Col_Purchase_Contact+" ='"+contactID+"'";
        else if ((contactID==null || contactID.isEmpty()) && billNo!=null)
            sql="Select * From "+Table_Purchase+" where "+Col_Purchase_BillNo+" ='"+billNo+"'";
        else if ((billNo==null || billNo.isEmpty()) && contactID!=null)
            sql="Select * From "+Table_Purchase+" where "+ Col_Purchase_Contact+" ='"+contactID+"'";
        Cursor c= db.rawQuery(sql,null);
        return c;
    }

    public void updatePurchasewithBillAMt(String purID, String billamt)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Purchase_Amt,billamt);
        db.update(Table_Purchase,contentValues,Col_Purchase_ID+"="+purID,null);
        db.close();
    }

    public Cursor getPurchaseDetails(String purID)
    {

        SQLiteDatabase db= this.getReadableDatabase();
        String sql="Select * From "+Table_PurchaseDetails+" where "+Col_PurchaseDetails_Pur+"= "+purID;
        Cursor c= db.rawQuery(sql,null);
        return c;
    }

    public Cursor getSaleDetails(String billNo)
    {

        SQLiteDatabase db= this.getReadableDatabase();
        String sql="Select * From "+Table_SalesDetails+" where "+Col_SalesDetails_BillNo+"= "+billNo;
        Cursor c= db.rawQuery(sql,null);
        return c;
    }




    public void paymententry(Boolean ismade ,Integer cid, Integer id, Integer amount, String date, String td){
        //payment made Boolean True
        //paymnet received Boolean False

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Payment_isMade,ismade);
        contentValues.put(Col_Payment_Contact,cid);
        contentValues.put(Col_Payment_ID,id);
        contentValues.put(Col_Payment_Amt,amount);
        contentValues.put(Col_Payment_Date,date);
        contentValues.put(Col_Payment_TD,td);
        db.insert(Table_Payment,null,contentValues);
        db.close();

    }

    public void discountentry (Boolean isMade , Integer cid ,Integer id, Integer amount, String date, String td){
        //discount received Boolean True
        //dicount given Boolean False

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Discount_isMade,isMade);
        contentValues.put(Col_Discount_Contact,cid);
        contentValues.put(Col_Discount_ID,id);
        contentValues.put(Col_Discount_Amt,amount);
        contentValues.put(Col_Discount_Date,date);
        contentValues.put(Col_Discount_TD,td);
        db.insert(Table_Discount,null,contentValues);
        db.close();

    }

    public String getItemCost(String itemid)
    {
        SQLiteDatabase db= this.getReadableDatabase();
        String sql= "Select "+Col_PurchaseDetails_Cost+" From "+Table_PurchaseDetails+" Where "+Col_PurchaseDetails_Item+"= '"+itemid+"'";
        String cost="";

        Cursor cursor = db.rawQuery(sql, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            cost = cursor.getString(0);
        }
        cursor.close();
        return cost;

    }

    public String getNextSaleRetNo(){
        SQLiteDatabase db = this.getReadableDatabase();
        String srno = new String();
        //String selectQuery = "SELECT MAX(PurID)  FROM PURCHASE";
        String selectQuery = "SELECT Max("+ Col_SalesReturn_ID+") from "+Table_Sales_Return;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            srno = cursor.getString(0);
        }
        cursor.close();
        if(srno==null)
            srno="0";
        return srno;
    }
    public String getNextPurRetNo(){
        SQLiteDatabase db = this.getReadableDatabase();
        String prno = new String();
        //String selectQuery = "SELECT MAX(PurID)  FROM PURCHASE";
        String selectQuery = "SELECT Max("+ Col_PurchaseReturn_ID+") from "+Table_Purchase_Return;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            prno = cursor.getString(0);
        }
        cursor.close();
        if(prno==null)
            prno="0";
        return prno;
    }


    public void insertSalesReturn(String cid, String bno, String date, String amt,String comments, String td){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_SalesReturn_Contact,cid);
        contentValues.put(Col_SalesReturn_BillNo,bno);
        contentValues.put(Col_SalesReturn_Date,date);
        contentValues.put(Col_SalesReturn_Amt,amt);
        contentValues.put(Col_SalesReturn_Comments,comments);
        contentValues.put(Col_SalesReturn_TD,td);
        db.insert(Table_Sales_Return,null,contentValues);

    }

    public void insertSalesReturnDetails(String itemid, String srid,String qty, String cost, String td){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_SalesReturnDetails_Item,itemid);
        contentValues.put(Col_SalesReturnDetails_ID,srid);
        contentValues.put(Col_SalesReturnDetails_Qty,qty);
        contentValues.put(Col_SalesReturnDetails_Cost,cost);
        contentValues.put(Col_SalesReturnDetails_TD,td);
        db.insert(Table_Sales_ReturnDetails,null,contentValues);
        db.close();
    }

    public void deletesalesreturn(String salesretno){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql = "DELETE FROM "+Table_Sales_Return+" WHERE "+Col_SalesReturn_ID+"= '"+salesretno+"'";
        db.execSQL(sql);
        db.close();
    }

    public void insertPurchaseReturn(String cid, String bno, String date, String amt,String comments, String td){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_PurchaseReturn_Contact,cid);
        contentValues.put(Col_PurchaseReturn_BillNo,bno);
        contentValues.put(Col_PurchaseReturn_Date,date);
        contentValues.put(Col_PurchaseReturn_Amt,amt);
        contentValues.put(Col_PurchaseReturn_Comments,comments);
        contentValues.put(Col_PurchaseReturn_TD,td);
        db.insert(Table_Purchase_Return,null,contentValues);

    }

    public void insertPurchaseReturnDetails(String itemid, String prid,String qty, String cost, String td){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_PurchaseReturnDetails_Item,itemid);
        contentValues.put(Col_PurchaseReturnDetails_ID,prid);
        contentValues.put(Col_PurchaseReturnDetails_Qty,qty);
        contentValues.put(Col_PurchaseReturnDetails_Cost,cost);
        contentValues.put(Col_PurchaseReturnDetails_TD,td);
        db.insert(Table_Purchase_ReturnDetails,null,contentValues);
        db.close();
    }

    public void deletepurchasereturn(String purretno){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql = "DELETE FROM "+Table_Purchase_Return+" WHERE "+Col_PurchaseReturn_ID+"= '"+purretno+"'";
        db.execSQL(sql);
        db.close();
    }
    public Cursor getPayment(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * From "+ Table_Payment +" Where "+Col_Payment_No+"= '"+id+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;

    }
    public Cursor getDiscount(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * From "+ Table_Discount +" Where "+Col_Discount_No+"= '"+id+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;

    }
    public Cursor getPurchaseReturnDetails(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * From "+ Table_Purchase_ReturnDetails +" Where "+Col_PurchaseReturnDetails_ID+"= '"+id+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;

    }

    public Cursor getSaleReturnDetails(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * From "+ Table_Sales_ReturnDetails +" Where "+Col_SalesReturnDetails_ID+"= '"+id+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;

    }

    public int getCountPurchaseDetails(String purid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select Count("+ Col_PurchaseDetails_Pur +") From "+Table_PurchaseDetails+" Where "+Col_PurchaseDetails_Pur+"='"+purid+"'";
        Cursor cursor = db.rawQuery(selectQuery,null);
        int count= 0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            count = Integer.valueOf(cursor.getString(0));
        }
        cursor.close();
        return count;
    }

    public void updatePurchase(String billno, String cid, String pdate, String billamt, String comments, String purtd,String purid)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Purchase_BillNo,billno);
        contentValues.put(Col_Purchase_Contact,cid);
        contentValues.put(Col_Purchase_Date,pdate);
        contentValues.put(Col_Purchase_Amt,billamt);
        contentValues.put(Col_Purchase_Comments,comments);
        contentValues.put(Col_Purchase_Td,purtd);
        db.update(Table_Purchase,contentValues,Col_Purchase_ID+"="+purid,null);
        db.close();

    }

    public int getPurchaseDetailQty(String itemid){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select "+ Col_PurchaseDetails_Qty +" From "+Table_PurchaseDetails+" Where "+Col_PurchaseDetails_Item+"='"+itemid+"'";
        Cursor cursor = db.rawQuery(selectQuery,null);
        int dbqty= 0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
           dbqty = Integer.valueOf(cursor.getString(0));
        }
        cursor.close();
        return dbqty;

    }

    public void updateStock(String brandid,String productid,int qty, String size,String itemid, String td){
        SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Stock_Brand,brandid);
        contentValues.put(Col_Stock_Product,productid);
        contentValues.put(Col_Stock_Qty,qty);
        contentValues.put(Col_Stock_Size,size);
        contentValues.put(Col_Stock_TD,td);
        db.update(Table_Stock,contentValues,Col_Stock_ItemID+"="+itemid,null);
        db.close();
    }

    public void updatePurchaseDetails(int qty,String cost,String mrp, String itemid,String td){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_PurchaseDetails_Qty,qty);
        contentValues.put(Col_PurchaseDetails_Cost,cost);
        contentValues.put(Col_PurchaseDetails_MRP,mrp);
        contentValues.put(Col_PurchaseDetails_TD,td);
        db.update(Table_PurchaseDetails,contentValues,Col_PurchaseDetails_Item+"="+itemid,null);
        db.close();
    }
    public Cursor getSalesDetailsforSrno(String srno){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select *  From "+Table_SalesDetails+" Where "+Col_SalesDetails_ID+"='"+srno+"'";
        Cursor cursor = db.rawQuery(selectQuery,null);

        cursor.close();
        return cursor;

    }
    public int getCountSalesDetails(String billno) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select Count(" + Col_SalesDetails_BillNo + ") From " + Table_SalesDetails + " Where " + Col_SalesDetails_BillNo + "='" + billno + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = 0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            count = Integer.valueOf(cursor.getString(0));
        }
        cursor.close();
        return count;
    }

    public void updateSalesDetails(String itemid, String qty, String cost, String td,String srno)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_SalesDetails_Item,itemid);
        contentValues.put(Col_SalesDetails_Qty,qty);
        contentValues.put(Col_SalesDetails_Cost,cost);
        contentValues.put(Col_SalesDetails_TD,td);
        db.update(Table_SalesDetails,contentValues,Col_SalesDetails_ID+"="+srno,null);
        db.close();
    }



    public void updatepaymententry(Boolean ismade ,Integer cid, Integer id, Integer amount, String date, String td,String payno){
        //payment made Boolean True
        //paymnet received Boolean False

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Payment_isMade,ismade);
        contentValues.put(Col_Payment_Contact,cid);
        contentValues.put(Col_Payment_ID,id);
        contentValues.put(Col_Payment_Amt,amount);
        contentValues.put(Col_Payment_Date,date);
        contentValues.put(Col_Payment_TD,td);
        db.update(Table_Payment,contentValues,Col_Payment_No+"="+payno,null);
        db.close();

    }

    public void updatediscountentry (Boolean isMade , Integer cid ,Integer id, Integer amount, String date, String td,String discno){
        //discount received Boolean True
        //dicount given Boolean False

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Discount_isMade,isMade);
        contentValues.put(Col_Discount_Contact,cid);
        contentValues.put(Col_Discount_ID,id);
        contentValues.put(Col_Discount_Amt,amount);
        contentValues.put(Col_Discount_Date,date);
        contentValues.put(Col_Discount_TD,td);
        db.update(Table_Discount,contentValues,Col_Discount_No+"="+discno,null);
        db.close();

    }

    public int getCountPurchaseReturnDetails(String purid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select Count(" + Col_PurchaseReturnDetails_ID + ") From " + Table_Purchase_ReturnDetails + " Where " + Col_PurchaseReturnDetails_ID + "='" + purid + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = 0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            count = Integer.valueOf(cursor.getString(0));
        }
        cursor.close();
        return count;
    }

    public Cursor getPurchaseReturnDetailsforSrno(String srno){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select *  From "+Table_Purchase_ReturnDetails+" Where "+Col_PurchaseReturnDetails_Srno+"='"+srno+"'";
        Cursor cursor = db.rawQuery(selectQuery,null);

       // cursor.close();
        return cursor;

    }

    public void updatePurchaseReturnDetails(String itemid, String qty, String cost, String td,String srno){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_PurchaseReturnDetails_Item,itemid);
        contentValues.put(Col_PurchaseReturnDetails_Qty,qty);
        contentValues.put(Col_PurchaseReturnDetails_Cost,cost);
        contentValues.put(Col_PurchaseReturnDetails_TD,td);
        db.update(Table_Purchase_ReturnDetails,contentValues,Col_PurchaseReturnDetails_Srno+"="+srno,null);
        db.close();
    }



    public void updateSalesReturn(String cid, String bno, String date, String amt,String comments, String td,String salesretno){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_SalesReturn_Contact,cid);
        contentValues.put(Col_SalesReturn_BillNo,bno);
        contentValues.put(Col_SalesReturn_Date,date);
        contentValues.put(Col_SalesReturn_Amt,amt);
        contentValues.put(Col_SalesReturn_Comments,comments);
        contentValues.put(Col_SalesReturn_TD,td);
        db.update(Table_Sales_Return,contentValues,Col_SalesReturn_ID+"="+salesretno,null);
        db.close();

    }

    public void updateSalesReturnDetails(String itemid, String srid,String qty, String cost, String td){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_SalesReturnDetails_Item,itemid);
        contentValues.put(Col_SalesReturnDetails_ID,srid);
        contentValues.put(Col_SalesReturnDetails_Qty,qty);
        contentValues.put(Col_SalesReturnDetails_Cost,cost);
        contentValues.put(Col_SalesReturnDetails_TD,td);
        db.update(Table_Sales_ReturnDetails,contentValues,Col_SalesReturnDetails_Srno+"="+srid,null);
        db.close();
    }

    public int getCountSalesReturnDetails(String salesretid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select Count(" + Col_SalesReturnDetails_ID + ") From " + Table_Sales_ReturnDetails + " Where " + Col_SalesReturnDetails_ID + "='" + salesretid + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = 0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            count = Integer.valueOf(cursor.getString(0));
        }
        cursor.close();
        return count;
    }

    public Cursor getSalesReturnDetailsforSrno(String srno){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select *  From "+Table_Sales_ReturnDetails+" Where "+Col_SalesReturnDetails_Srno+"='"+srno+"'";
        Cursor cursor = db.rawQuery(selectQuery,null);

        //cursor.close();
        return cursor;

    }

    public Cursor populateContact(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select * From "+Table_Contacts;
        Cursor cursor= db.rawQuery(selectQuery,null);
        return cursor;
    }


    public String getContactDetails(String cid){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "Select "+Col_Contacts_Details+ " From "+ Table_Contacts+" Where "+Col_Contacts_ID+"='"+cid+"'";
        Cursor cursor= db.rawQuery(selectQuery,null);
        String cdetails="";
        if(cursor.moveToFirst()){
            cdetails=cursor.getString(0);
        }
        return cdetails;

    }


    public void updateContact(String cid, String cname, String cno, String cdetails,String td){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_Contacts_Name,cname);
        contentValues.put(Col_Contacts_No,cno);
        contentValues.put(Col_Contacts_Details,cdetails);
        contentValues.put(Col_Contacts_TD,td);
        db.update(Table_Contacts,contentValues,Col_Contacts_ID+"="+cid,null);
        db.close();


    }




}

