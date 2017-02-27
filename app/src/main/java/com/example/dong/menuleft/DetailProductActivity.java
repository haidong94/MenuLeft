package com.example.dong.menuleft;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dong.menuleft.common.Commons;
import com.example.dong.menuleft.model.DetailOrders;
import com.example.dong.menuleft.model.Product;
import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import io.realm.Realm;

import static com.example.dong.menuleft.common.Commons.listDetailOrder;
import static com.example.dong.menuleft.common.Commons.orders;

/**
 * Created by DONG on 11-Feb-17.
 */

public class DetailProductActivity extends AppCompatActivity {
    ImageView hinhanh;
    TextView ten,gia,txtSoluong;
    ImageButton btnGiam,btnTang;
    Button btnMua;
    Firebase roof;




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_product);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//mũi tên quay về
        Animation animLeftIn = AnimationUtils.loadAnimation(this, R.anim.left_in);
        toolbar.startAnimation(animLeftIn);

        Realm realm = Realm.getDefaultInstance();

        Firebase.setAndroidContext(this);
        roof = new Firebase("https://shoponline-57869.firebaseio.com");

        btnGiam=(ImageButton) findViewById(R.id.btnGiam);
        btnTang=(ImageButton) findViewById(R.id.btnTang);
        btnMua=(Button) findViewById(R.id.btnMua) ;

        txtSoluong=(TextView) findViewById(R.id.txtSoLuong);
        ten=(TextView) findViewById(R.id.txtTen);
        gia=(TextView) findViewById(R.id.txtGia);
        hinhanh=(ImageView) findViewById(R.id.imageSP);

        final Intent intent=getIntent();
        int id=intent.getIntExtra("id",10);
        final Product product=realm.where(Product.class).equalTo("id",id).findFirst();



        ten.setText(product.getName());
        gia.setText(Commons.formatNumber(product.getPrice())+" đ" );

        Glide.with(this)
                .load(product.getImage())
                .override(200, 200)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(hinhanh);

        //sự kiện tăng giảm số lượng
        btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(txtSoluong.getText().toString())>1)
                    txtSoluong.setText(Integer.parseInt(txtSoluong.getText().toString())-1+"");
                else
                    Toast.makeText(DetailProductActivity.this,"Không thể giảm nữa",Toast.LENGTH_SHORT).show();
            }
        });

        btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    txtSoluong.setText(Integer.parseInt(txtSoluong.getText().toString())+1+"");
            }
        });

        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now=Calendar.getInstance();
                DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//Định dạng ngày / tháng /năm
                String s=roof.child("database").child("orders").push().getKey();

                orders.setCustomer_ID(LoginActivity.customers.getCustomer_ID());
                orders.setOrder_ID(s);
                orders.setCustomer_ID(LoginActivity.customers.getCustomer_ID());
                orders.setDate_Dilivery(df1.format(now.getTime()));


                int condition=0;

                for (DetailOrders item:listDetailOrder)
                {
                    if (product.getId()==item.getProduct().getId()) {
                        condition=1;
                        item.setNumber(Integer.parseInt(txtSoluong.getText().toString()) + item.getNumber());
                        break;
                    }

                }

                if (condition==0)
                {
                    DetailOrders detail_orders = new DetailOrders();
                    detail_orders.setProduct(product);
                    detail_orders.setNumber(Integer.parseInt(txtSoluong.getText().toString()));
                    listDetailOrder.add(detail_orders);
                }


                finish();//finishing activity

            }
        });

    }




}
