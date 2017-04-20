package com.example.dong.menuleft;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dong.menuleft.common.Commons;
import com.example.dong.menuleft.model.DetailOrders;
import com.firebase.client.Firebase;

import static com.example.dong.menuleft.common.Commons.listDetailOrder;
import static com.example.dong.menuleft.common.Commons.orders;

/**
 * Created by DONG on 19-Jan-17.
 */

public class CartActivity extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView recyclerView;
    private RecyclerViewAdapterCart adapter;
    private LinearLayoutManager lLayout;


    private TextView txtSumMoney;
    private Button btnPay;

    Firebase roof;
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//mũi tên quay về

        Animation animLeftIn = AnimationUtils.loadAnimation(this, R.anim.left_in);
        toolbar.startAnimation(animLeftIn);

        Firebase.setAndroidContext(this);
        roof = new Firebase("https://shoponline-57869.firebaseio.com");

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        txtSumMoney=(TextView) findViewById(R.id.txtSumMoney);
        btnPay=(Button) findViewById(R.id.btnPay);

        //tinh tong tien
        int sum=0;
        for (DetailOrders item:listDetailOrder)
        {
            sum+=item.getNumber()*item.getProduct().getPrice();
        }
        txtSumMoney.setText("Tổng tiền:"+ Commons.formatNumber(sum)+" đ");
        orders.setSum_Money(sum);

        //sự kiện thanh toán



        // If the size of views will not change as the data changes.
        recyclerView.setHasFixedSize(true);
        // Setting the LayoutManager.
        lLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lLayout);
        adapter = new RecyclerViewAdapterCart(CartActivity.this,listDetailOrder);
        recyclerView.setAdapter(adapter);


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LoginActivity.customers.getCustomer_Email()==null)
                {
                    Toast.makeText(CartActivity.this, "Bạn cần đăng nhập", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CartActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    roof.child("database").child("order").child(orders.getOrder_ID()).setValue(orders);
                    for (DetailOrders item : listDetailOrder) {
                        item.setOrder_ID(orders.getOrder_ID());
                        roof.child("database").child("detail_order").push().setValue(item);
                    }
                    Toast.makeText(CartActivity.this, "Mua hàng thành công", Toast.LENGTH_SHORT).show();
                    listDetailOrder.removeAll(listDetailOrder);
                    Intent intent = new Intent(CartActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

    }
}
