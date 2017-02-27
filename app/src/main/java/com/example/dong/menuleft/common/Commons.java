package com.example.dong.menuleft.common;

import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.dong.menuleft.model.DetailOrders;
import com.example.dong.menuleft.model.Orders;

import java.util.ArrayList;

/**
 * Created by DONG on 13-Feb-17.
 */

public class Commons {
    public static ArrayList<DetailOrders> listDetailOrder=new ArrayList<DetailOrders>();
    public static Orders orders=new Orders();


    //Convert long to money
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String formatNumber(long number) {
        if (number < 1000) {
            return String.valueOf(number);
        }
        try {

            NumberFormat formatter = new DecimalFormat("###,###");
            String resp = formatter.format(number);
            resp = resp.replaceAll(",", ".");
            return resp;

        } catch (Exception e) {
            return "";
        }
    }
}
