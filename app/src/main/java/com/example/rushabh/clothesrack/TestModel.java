package com.example.rushabh.clothesrack;

/**
 * Created by Rushabh on 22-09-2016.
 */

public class TestModel {

    public String ItemID;
    public String ProdName;
    public String Size;
    public String Qty;
    public String Date;
    public String BillNo;
    public String Name;
    public String TotalAmount;
    public String PurchaseID;
    public String Type;
    public String sDate;
    public String sBillNo;
    public String sName;
    public String sTotalAmount;
    public String PaymentID;
    public String PaymentDate;
    public String PaymentName;
    public String PaymentAmount;
    public String PaymentAgainst;
    public String returnID;
    public String returnDate;
    public String returnName;
    public String returnAmount;
    public String returnAgainst;



    public void setItemID(String ItemID){ this.ItemID = ItemID;};
    public void setProdName(String ProdName){    this.ProdName= ProdName;    }
    public void setSize(String Size) {
        this.Size = Size;
    }
    public void setQty(String Qty) {
        this.Qty = Qty;
    }

    public String getItemID(){return this.ItemID;}
    public String getProdName(){   return this.ProdName; }
    public String getSize() {
        return this.Size;
    }
    public String getQty() {
        return this.Qty;
    }

    public void setType(String type){this.Type=type;}
    public String getType(){return this.Type;}



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


    public void setsDate(String sDate){ this.sDate = sDate;};
    public void setsBillNo(String sBillNo){    this.sBillNo= sBillNo;    }
    public void setsName(String sName) {
        this.sName = sName;
    }
    public void setsTotalAmount(String sTotalAmount) {
        this.sTotalAmount = sTotalAmount;
    }

    public String getsDate(){return this.sDate;}
    public String getsBillNo(){   return this.sBillNo; }
    public String getsName() {
        return this.sName;
    }
    public String getsTotalAmount() {
        return this.sTotalAmount;
    }


    public void setPaymentID(String PaymentID){this.PaymentID=PaymentID;}
    public void setPaymentDate(String PaymentDate){this.PaymentDate=PaymentDate;}
    public void setPaymentName(String PaymentName){this.PaymentName=PaymentName;}
    public void setPaymentAmount(String PaymentAmount){this.PaymentAmount=PaymentAmount;}
    public void setPaymentAgainst(String PaymentAgainst){this.PaymentAgainst=PaymentAgainst;}

    public String getPaymentID(){return this.PaymentID;}
    public String getPaymentDate(){return this.PaymentDate;}
    public String getPaymentName(){return this.PaymentName;}
    public String getPaymentAmount(){return this.PaymentAmount;}
    public String getPaymentAgainst(){return this.PaymentAgainst;}


    public void setReturnAgainst(String returnAgainst) {
        this.returnAgainst = returnAgainst;
    }
    public void setReturnID(String returnID) {
        this.returnID= returnID;
    }
    public void setReturnDate(String returnDate){
        this.returnDate= returnDate;
    }
    public void setReturnName(String returnName){
        this.returnName=returnName;
    }
    public void setReturnAmount(String returnAmount){
        this.returnAmount=returnAmount;
    }

    public String getReturnID(){return this.returnID;}
    public String getReturnDate(){return this.returnDate;}
    public String getReturnName(){return this.returnName;}
    public String getReturnAmount(){return this.returnAmount;}
    public String getReturnAgainst(){return this.returnAgainst;}
}
