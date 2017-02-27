package com.example.dong.menuleft;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by DONG on 19-Jan-17.
 */

public class ChangePassWordActivity extends AppCompatActivity {
    Toolbar toolbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matkhau);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//mũi tên quay về

        Animation animLeftIn = AnimationUtils.loadAnimation(this, R.anim.left_in);
        toolbar.startAnimation(animLeftIn);
    }
}
