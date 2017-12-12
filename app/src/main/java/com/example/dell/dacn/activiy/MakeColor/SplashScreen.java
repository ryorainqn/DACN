package com.example.dell.dacn.activiy.MakeColor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dell.dacn.R;
import com.example.dell.dacn.SaveLogin.SessionManagerUser;
import com.example.dell.dacn.activiy.LogAndReg.LoginActivity;
import com.example.dell.dacn.activiy.News.MainActivity;

/**
 * Created by DELL on 20/10/17.
 */

public class SplashScreen extends AppCompatActivity {
    private ImageView iv;
    private SessionManagerUser sessionManagerUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        iv = (ImageView) findViewById(R.id.ivSplash);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition);
        sessionManagerUser = new SessionManagerUser(this);
        iv.startAnimation(animation);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    if (sessionManagerUser.isLoggedIn()){
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent1 = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                }
            }
        };
        timer.start();
    }
}
