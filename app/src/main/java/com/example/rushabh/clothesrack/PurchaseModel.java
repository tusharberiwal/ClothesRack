package com.example.rushabh.clothesrack;

/**
 * Created by Rushabh on 21-07-2016.
 */
public class PurchaseModel {

    public  String brand;
    public String product;
    public String size;
    public String qty;
    public String cost;
    public String mrp;
    public String total;
    public String itemid;



    public void setBrand(String brand)
    {
        this.brand=brand;
    }
    public void setProduct(String product) {    this.product= product;  }
    public void setSize(String size) {
      this.size = size;
  }
    public void setQty(String qty) {
      this.qty = qty;
  }
    public void setCost(String cost) {
      this.cost = cost;
  }
    public void setMrp(String mrp)
  {
    this.mrp=mrp;
    }
    public void setTotal(String total){this.total=total;}
    public void setitemid(String itemid){this.itemid = itemid;}


    public String getBrand(){return this.brand;}
    public String getProduct() {   return this.product; }
    public String getSize() {
       return this.size;
    }
    public String getQty() {
       return this.qty;
    }
    public String getCost() {
       return this.cost ;
    }
    public String getMrp() { return this.mrp;    }
    public String getTotal(){return this.total;}
    public String getitemid(){return this.itemid;}
}
