package com.example.rushabh.clothesrack;

/**
 * Created by Rushabh on 26-07-2016.
 */
public class ContactModel {
    public String cName;
    public String cNumber;
    public String cID;



    public void setcName(String cName){ this.cName = cName;};
    public void setcNumber(String cNumber){    this.cNumber= cNumber;    }
    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getcName(){return this.cName;}
    public String getcNumber(){   return this.cNumber; }
    public String getcID() {return this.cID;}

}
