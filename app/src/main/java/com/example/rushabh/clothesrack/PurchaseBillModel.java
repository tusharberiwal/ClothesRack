package com.example.rushabh.clothesrack;

/**
 * Created by tushar on 9/16/2016.
 */
public class PurchaseBillModel {

    public String Date;
    public String BillNo;
    public String Name;
    public String TotalAmount;
    public String PurchaseID;


    public void setDate(String Date){ this.Date = Date;};
    public void setBillNo(String BillNo){    this.BillNo= BillNo;    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public void setTotalAmount(String TotalAmount) {
        this.TotalAmount = TotalAmount;
    }
    public void setPurchaseID(String PurchaseID) {
        this.PurchaseID = PurchaseID;
    }

    public String getDate(){return this.Date;}
    public String getBillNo(){   return this.BillNo; }
    public String getPurchaseID(){   return this.PurchaseID; }
    public String getName() {
        return this.Name;
    }
    public String getTotalAmount() {
        return this.TotalAmount;
    }
    
}
