package com.example.dong.menuleft;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dong.menuleft.model.Customers;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import io.saeid.fabloading.LoadingView;

import static android.widget.Toast.makeText;

/**
 * Created by DONG on 12-Jan-17.
 */

public class LoginActivity extends Activity {

    Button btnLogin,btnBoqua,btnRegister;
    Firebase roof;

    private LoadingView mLoadingView;
    public static Customers customers=new Customers();

    protected void onCreate(Bundle savedInstancState){
        super.onCreate(savedInstancState);
        setContentView(R.layout.select_login);

        Firebase.setAndroidContext(this);
        roof = new Firebase("https://shoponline-57869.firebaseio.com");

        btnLogin=(Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                displayAlertDialog();
            }
        });

        btnBoqua=(Button)findViewById(R.id.btnBoqua);
        btnBoqua.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                trangchu();
            }
        });

        btnRegister=(Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialogRegister();
            }

        });

       //hoat hình

        mLoadingView = (LoadingView) findViewById(R.id.loading_view_repeat);
        boolean isLollipop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        int marvel_1 = isLollipop ? R.drawable.marvel_1_lollipop : R.drawable.marvel_1;
        int marvel_2 = isLollipop ? R.drawable.marvel_2_lollipop : R.drawable.marvel_2;
        int marvel_3 = isLollipop ? R.drawable.marvel_3_lollipop : R.drawable.marvel_3;
        int marvel_4 = isLollipop ? R.drawable.marvel_4_lollipop : R.drawable.marvel_4;
        mLoadingView.addAnimation(Color.parseColor("#FFD200"), marvel_1, LoadingView.FROM_LEFT);
        mLoadingView.addAnimation(Color.parseColor("#2F5DA9"), marvel_2, LoadingView.FROM_TOP);
        mLoadingView.addAnimation(Color.parseColor("#FF4218"), marvel_3, LoadingView.FROM_RIGHT);
        mLoadingView.addAnimation(Color.parseColor("#C7E7FB"), marvel_4, LoadingView.FROM_BOTTOM);
        mLoadingView.setDuration(800);// thời gian cho mỗi mục tải (mặc định là 500 millis)
        mLoadingView.setRepeat(4);//Đối với giá trị lớn hơn 1, nó gọi hình ảnh động tiếp theo tự động cho lần 'lặp lại-1 ". (Mặc định là 1)
        mLoadingView.startAnimation();

        mLoadingView.addListener(new LoadingView.LoadingListener() {
            @Override
            public void onAnimationStart(int currentItemPosition) {
                //mLoadingView.startAnimation();
            }

            @Override
            public void onAnimationRepeat(int nextItemPosition) {
                //mLoadingView.startAnimation();
            }

            @Override
            public void onAnimationEnd(int nextItemPosition) {
                mLoadingView.startAnimation();

            }
        });


    }

    private void dialogRegister() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog_account, null);
        final EditText ed_email = (EditText) alertLayout.findViewById(R.id.ed_email);
        final EditText ed_password = (EditText) alertLayout.findViewById(R.id.ed_password);
        final EditText ed_name = (EditText) alertLayout.findViewById(R.id.ed_name);

         //RadioButton rad=(RadioButton) findViewById(id);
        final RadioGroup radioGroup = (RadioGroup) alertLayout.findViewById(R.id.radioGroup);

//        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked)
//                    etPassword.setTransformationMethod(null);
//                else
//                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            }
//        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Register");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Tạo tài khoản", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                roof.child("database").child("customers").addValueEventListener(new ValueEventListener() {
                    int condition=0;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = ed_name.getText().toString();
                        String pass = ed_password.getText().toString();
                        String email= ed_email.getText().toString();

                        //getSex
                        int id=radioGroup.getCheckedRadioButtonId();
                        View radioButton = radioGroup.findViewById(id);
                        int radioId = radioGroup.indexOfChild(radioButton);
                        RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                        String sex = btn.getText().toString();
                        condition++;//condition=1
                        if(isValidEmail(email)) {
                            customers.setCustomer_Email(email);
                            customers.setCustomer_Name(name);
                            customers.setCustomer_Password(pass);
                            customers.setCustomer_Sex(sex);
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                //String key=postSnapshot.getKey();
                                Customers c =  postSnapshot.getValue(Customers.class);
                                //c.setCustomer_ID(key);
                                if(c.getCustomer_Email().equals(customers.getCustomer_Email())&&condition==1){
                                    condition++;//condition=2
                                    Toast.makeText(LoginActivity.this, "Email đã dùng. Vui lòng sử dụng email khác", Toast.LENGTH_SHORT).show();
                                    break;
                                }

                            }
                        }
                        else {
                            condition++;//condition=2
                            Toast.makeText(LoginActivity.this, "Email khong hop le", Toast.LENGTH_SHORT).show();
                        }

                        if(condition==1) {
                            String s=roof.child("database").child("customers").push().getKey();
                            customers.setCustomer_ID(s);
                            roof.child("database").child("customers").child(s).setValue(customers);

                            Toast.makeText(LoginActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                            condition++;//condition=2
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);

                        }


                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public void trangchu() {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void displayAlertDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog_login, null);
        final EditText etUsername = (EditText) alertLayout.findViewById(R.id.et_Username);
        final EditText etPassword = (EditText) alertLayout.findViewById(R.id.et_Password);
        final CheckBox cbShowPassword = (CheckBox) alertLayout.findViewById(R.id.cb_ShowPassword);

        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    etPassword.setTransformationMethod(null);
                else
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Login");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Login", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // code for matching password

                final String user = etUsername.getText().toString();
                final String pass = etPassword.getText().toString();
                roof.child("database").child("customers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int condition=0;
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Customers c =  postSnapshot.getValue(Customers.class);
                            if(c.getCustomer_Email().equals(user.trim())&&c.getCustomer_Password().equals(pass)){
                                customers.setCustomer_Email(user);
                                customers.setCustomer_Password(pass);
                                condition=1;
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                break;
                            }
                        }
                        if(condition==1)
                            Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(LoginActivity.this,"Tài khoản không đúng| Vui lòng chọn tài khoản khác",Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }


    //email-address-validation
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


}
