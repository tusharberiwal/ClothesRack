package com.example.rushabh.clothesrack;

/**
 * Created by Rushabh on 26-07-2016.
 */
public class StockModel {
    public String ItemID;
    public String ProdName;
    public String Size;
    public String Qty;



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
    public String setSize() {
        return this.Size;
    }
    public String setQty() {
        return this.Qty;
    }
}
