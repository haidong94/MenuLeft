package com.example.dong.menuleft.model;

/**
 * Created by DONG on 12-Feb-17.
 */

public class Orders {
    private String order_ID;
    private String date_Dilivery;
    private String address;
    private long sum_Money;
    private String customer_ID;

    public String getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(String order_ID) {
        this.order_ID = order_ID;
    }

    public String getDate_Dilivery() {
        return date_Dilivery;
    }

    public void setDate_Dilivery(String date_Dilivery) {
        this.date_Dilivery = date_Dilivery;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getSum_Money() {
        return sum_Money;
    }

    public void setSum_Money(long sum_Money) {
        this.sum_Money = sum_Money;
    }

    public String getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        this.customer_ID = customer_ID;
    }
}
