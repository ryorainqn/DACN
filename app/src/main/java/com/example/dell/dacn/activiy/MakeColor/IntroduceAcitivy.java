package com.example.dell.dacn.activiy.MakeColor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.dell.dacn.R;

/**
 * Created by DELL on 24/10/17.
 */

public class IntroduceAcitivy extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        toolbar = (Toolbar) findViewById(R.id.toolbar_header_intro);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }
}
