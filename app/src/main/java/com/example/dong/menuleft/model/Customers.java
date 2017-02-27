package com.example.dong.menuleft.model;

import java.io.Serializable;

/**
 * Created by DONG on 04-Oct-16.
 */

public class Customers implements Serializable {

    private String customer_ID;



    private String customer_Name;
    private String customer_Address;
    private String customer_Email;
    private String customer_Password;
    private String customer_Phone;
    private String customer_Sex;


    public String getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        this.customer_ID = customer_ID;
    }
    public String getCustomer_Sex() {
        return customer_Sex;
    }

    public void setCustomer_Sex(String customer_Sex) {
        this.customer_Sex = customer_Sex;
    }

    public String getCustomer_Phone() {
        return customer_Phone;
    }

    public void setCustomer_Phone(String customer_Phone) {
        this.customer_Phone = customer_Phone;
    }

    public String getCustomer_Password() {
        return customer_Password;
    }

    public void setCustomer_Password(String customer_Password) {
        this.customer_Password = customer_Password;
    }


    public String getCustomer_Email() {
        return customer_Email;
    }

    public void setCustomer_Email(String customer_Email) {
        this.customer_Email = customer_Email;
    }

    public String getCustomer_Address() {
        return customer_Address;
    }

    public void setCustomer_Address(String customer_Address) {
        this.customer_Address = customer_Address;
    }

    public String getCustomer_Name() {
        return customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        this.customer_Name = customer_Name;
    }
}
