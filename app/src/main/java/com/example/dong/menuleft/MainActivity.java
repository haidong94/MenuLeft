package com.example.dong.menuleft;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dong.menuleft.common.AppStatus;
import com.example.dong.menuleft.common.BlurBuilder;
import com.example.dong.menuleft.model.Product;
import com.example.dong.menuleft.model.Types;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import io.realm.Realm;

import static android.R.layout.simple_spinner_item;
import static com.example.dong.menuleft.common.Commons.listDetailOrder;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SearchView.OnQueryTextListener {
        FloatingActionMenu materialDesignFAM;
        FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
        TextView txtAccount;
        Spinner spinnerDanhMuc;
        LinearLayout lnBackground;
        Realm realm;
        public ArrayList<Product> arrProduct=new ArrayList<Product>();

        Firebase roof;

        private RecyclerView recyclerView;
        private RecyclerViewAdapter adapter;
        private GridLayoutManager lLayout;
       //cặp đối tượng dùng cho spinner
       // private ArrayList<Types> arrType=new ArrayList<Types>(realm.where(Types.class).findAll());
        private SpinAdapter adapterSpinner=null;
        protected Handler handler;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (AppStatus.getInstance(this).isOnline()) {
            start();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Connect network")
                    .setMessage("Bạn cần kết nối mạng để có dữ liệu mới nhất!!!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            start();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void start(){
        inforAccount();

        handler = new Handler();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        spinnerDanhMuc=(Spinner) findViewById(R.id.spinnerDanhMuc);
        // If the size of views will not change as the data changes.
        recyclerView.setHasFixedSize(true);

        // Setting the LayoutManager.
        lLayout = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(lLayout);
        realm=Realm.getDefaultInstance();

        //fire base
        Firebase.setAndroidContext(this);
        roof = new Firebase("https://shoponline-57869.firebaseio.com");

        roof.child("database").child("type").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final Types types = postSnapshot.getValue(Types.class);
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(types);
                    realm.commitTransaction();
                    roof.child("database").child("product").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Product product =  postSnapshot.getValue(Product.class);
                                if(product.getType_id()==types.getType_id()) {
                                    realm.beginTransaction();
                                    realm.copyToRealmOrUpdate(product);
                                    realm.commitTransaction();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        final ArrayList<Types> arrType=new ArrayList(realm.where(Types.class).findAll());

        adapterSpinner=new SpinAdapter(MainActivity.this, simple_spinner_item,arrType);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerDanhMuc.setAdapter(adapterSpinner);
        spinnerDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                Object  item = parent.getItemAtPosition(position);
                if (item != null) {
                    Toast.makeText(MainActivity.this, arrType.get(position).getName(),
                            Toast.LENGTH_SHORT).show();
                    arrProduct.removeAll(arrProduct);
                    final ArrayList<Product> arrProduct=new ArrayList(realm.where(Product.class).equalTo("type_id",arrType.get(position).getType_id()).findAll());

//                    final ArrayList<Product> arrProduct1=new ArrayList<Product>();
//                    arrProduct1.addAll(arrProduct.subList(0,2));
                    adapter = new RecyclerViewAdapter(MainActivity.this,arrProduct,recyclerView);
                    recyclerView.setAdapter(adapter);
//                    adapter.setOnLoadMoreListener(new RecyclerViewAdapter.OnLoadMoreListener() {
//                        @Override
//                        public void onLoadMore() {
//                            //add null , so the adapter will check view_type and show progress bar at bottom
//                            arrProduct1.add(null);
//                            adapter.notifyItemInserted(arrProduct1.size() - 1);
//
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    //   remove progress item
//                                    arrProduct1.remove(arrProduct1.size() - 1);
//                                    adapter.notifyItemRemoved(arrProduct1.size());
//                                    //add items one by one
//                                    int start = arrProduct1.size();
//                                    int end = start + 10;
//                                    if(end>arrProduct.size())
//                                        end=arrProduct.size();
//
//                                    for (int i = start + 1; i <= end; i++) {
//                                        arrProduct1.add(arrProduct.get(i-1));
//                                        adapter.notifyItemInserted(arrProduct1.size());
//                                    }
//                                    adapter.setLoaded();
//                                    //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
//                                }
//
//                            }, 2000);
//
//
//                        }
//                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
            }
        });

        DrawerLayout drawer=(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // made drawer toggle object
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
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
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchView=menu.findItem(R.id.it_search_view);
        SearchView searchView1= (SearchView) searchView.getActionView();
        searchView1.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView1.setSubmitButtonEnabled(true);
        searchView1.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    //phương thức lọc khi search
    @Override
    public boolean onQueryTextChange(String newText) {
        //Toast.makeText(MainActivity.this,newText,Toast.LENGTH_LONG).show();
//        arrProduct.removeAll(arrProduct);
//        ArrayList<Product> arrProduct=new ArrayList(realm.where(Product.class).equalTo("name",newText).findAll());
//        adapter = new RecyclerViewAdapter(MainActivity.this,arrProduct,);
//        recyclerView.setAdapter(adapter);
        return false;
    }

    public void inforAccount(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        txtAccount=(TextView) headerView.findViewById(R.id.txtAccount);
        txtAccount.setText(LoginActivity.customers.getCustomer_Email());
        lnBackground= (LinearLayout) headerView.findViewById(R.id.lnBackground);
        Bitmap bm = ((BitmapDrawable) getResources().getDrawable(R.drawable.hinh_nen)).getBitmap();
        Bitmap blur = BlurBuilder.blur(this,bm);
        Drawable drawable = new BitmapDrawable(getResources(), blur);
        lnBackground.setBackgroundDrawable(drawable);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);//lấy màu của ảnh ban đầu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.it_action_settings) {
            return true;
        }
        if (id == R.id.it_search_view) {
            return true;
        }
        if (id == R.id.it_cart) {
            if(listDetailOrder.size()==0)
            {
                Toast.makeText(this,"Chưa có sản phẩm nào trong giỏ hàng",Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent=new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_thongbao) {
            Toast.makeText(MainActivity.this,"Chọn thông báo",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_cuahang) {
            Toast.makeText(MainActivity.this,"Chọn cửa hàng",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_muasam) {
            Toast.makeText(MainActivity.this,"Chọn mua sắm",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_taikhoan) {
            Toast.makeText(MainActivity.this,"Chọn tài khoản",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,AccountActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_donhang) {
            Toast.makeText(MainActivity.this,"Chọn đơn hàng",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_giohang) {
            Toast.makeText(MainActivity.this,"Chọn giỏ hàng",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_gioithieu) {
            Toast.makeText(MainActivity.this,"Chọn giới thiệu",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_yeuthich) {
            Toast.makeText(MainActivity.this,"Chọn yêu thích",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(MainActivity.this,"Chọn share",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(MainActivity.this,"Chọn send",Toast.LENGTH_LONG).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
