package com.example.dong.menuleft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by DONG on 12-Jan-17.
 */

public class StartActivity extends Activity {
    Handler handler;
    Animation animation;

    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.khoidong);

        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        View layout=findViewById(R.id.layoutid);
        layout.startAnimation(animation);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent login=new Intent(StartActivity.this,LoginActivity.class);
                startActivity(login);
                finish();
            }
        },5000);
    }
}
