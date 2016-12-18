package com.example.rushabh.clothesrack;

/**
 * Created by tushar on 12/4/2016.
 */

public class InvoicePrintModel {

    public String Description;
    public int Quantity;
    public long Rate;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public long getRate() {
        return Rate;
    }

    public void setRate(long rate) {
        Rate = rate;
    }
}
