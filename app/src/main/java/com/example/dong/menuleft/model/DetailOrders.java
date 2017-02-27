package com.example.dong.menuleft.model;

/**
 * Created by DONG on 12-Feb-17.
 */

public class DetailOrders {
    private String order_ID;
    private int number;
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }



    public String getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(String order_ID) {
        this.order_ID = order_ID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}
