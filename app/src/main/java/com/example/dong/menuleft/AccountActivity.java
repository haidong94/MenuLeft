package com.example.dong.menuleft;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by DONG on 17-Jan-17.
 */

public class AccountActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TableRow trTaiKhoan,trAddress,trMoney,trKey,trLogout;
    TextView txtInforAccount,txtAddress,txtMoney;
    Toolbar toolbar;


    private View.OnClickListener myVarListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v==trTaiKhoan){
                Toast.makeText(AccountActivity.this, "Tài Khoản", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AccountActivity.this,AccountInformationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
            }

            if(v==trAddress){
                Toast.makeText(AccountActivity.this, "Địa Chỉ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AccountActivity.this,AddressActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
            }

            if(v==trMoney)
                Toast.makeText(AccountActivity.this, "Tiền trong tài khoản", Toast.LENGTH_SHORT).show();
            if(v==trKey){
                Toast.makeText(AccountActivity.this, "Đổi mật khẩu", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AccountActivity.this,ChangePassWordActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_in,R.anim.left_out);
            }

            if(v==trLogout){
                Toast.makeText(AccountActivity.this, "Đăng Xuất", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AccountActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taikhoan1);


        trTaiKhoan = (TableRow) findViewById(R.id.trTaiKhoan);
        trAddress = (TableRow) findViewById(R.id.trAddress);
        trMoney = (TableRow) findViewById(R.id.trMoney);
        trKey = (TableRow) findViewById(R.id.trKey);
        trLogout = (TableRow) findViewById(R.id.trLogout);

        txtInforAccount=(TextView) findViewById(R.id.txtInforAccount);
        txtAddress=(TextView) findViewById(R.id.txtAddress);
        txtMoney=(TextView) findViewById(R.id.txtMoney);


        //sự kiện click
        trTaiKhoan.setOnClickListener(myVarListener);
        trAddress.setOnClickListener(myVarListener);
        trMoney.setOnClickListener(myVarListener);
        trKey.setOnClickListener(myVarListener);
        trLogout.setOnClickListener(myVarListener);

        //gán giá trị
        txtInforAccount.setText(LoginActivity.customers.getCustomer_Email());
        txtAddress.setText(LoginActivity.customers.getCustomer_Address());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // made drawer toggle object
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);//lấy màu của ảnh ban đầu
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.it_action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_thongbao) {
            Toast.makeText(AccountActivity.this,"Chọn thông báo",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_cuahang) {
            Toast.makeText(AccountActivity.this,"Chọn cửa hàng",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_muasam) {
            Toast.makeText(AccountActivity.this,"Chọn mua sắm",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_taikhoan) {
            Toast.makeText(AccountActivity.this,"Chọn tài khoản",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,AccountActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_donhang) {
            Toast.makeText(AccountActivity.this,"Chọn đơn hàng",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_giohang) {
            Toast.makeText(AccountActivity.this,"Chọn giỏ hàng",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_gioithieu) {
            Toast.makeText(AccountActivity.this,"Chọn giới thiệu",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_yeuthich) {
            Toast.makeText(AccountActivity.this,"Chọn yêu thích",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(AccountActivity.this,"Chọn share",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(AccountActivity.this,"Chọn send",Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

