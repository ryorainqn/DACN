package com.example.dell.dacn.activiy.DetailNews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.dacn.API.APIRetro;
import com.example.dell.dacn.API.Interface;
import com.example.dell.dacn.Front.IconTextView;
import com.example.dell.dacn.Front.ScaleImageHtml;
import com.example.dell.dacn.Model.Entry;
import com.example.dell.dacn.Model.Login;
import com.example.dell.dacn.R;
import com.example.dell.dacn.SaveLogin.SessionManagerUser;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DELL on 20/10/17.
 */

public class DetailActivity extends AppCompatActivity {
    private ImageView ivNew_Detail;
    private TextView tvTitle_Detail;
    private WebView tvDescription_Detail;
    private IconTextView itvSave, itvSaved;
    private Entry entry;
    private Login login;
    private Toolbar toolbar;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar_header_detail);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        sessionManagerUser = new SessionManagerUser(DetailActivity.this);
        hashMap = new HashMap<>();
        hashMap = sessionManagerUser.getUserDetails();
        init();
        getdata();
        onClick();
    }

    public void init() {
        ivNew_Detail = (ImageView) findViewById(R.id.ivNew_Detail);
        tvTitle_Detail = (TextView) findViewById(R.id.tvTitle_Detail);
        tvDescription_Detail = (WebView) findViewById(R.id.tvDescription_Detail);
        itvSave = (IconTextView) findViewById(R.id.itvSave);
        itvSaved = (IconTextView) findViewById(R.id.itvSaved);
    }

    public void getdata() {
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String image = getIntent().getStringExtra("image");
        String url = getIntent().getStringExtra("url_news");
        tvDescription_Detail.loadData(ScaleImageHtml.scaleImageHtml(replce(description)), "text/html", "UTF-8");
        Picasso.with(DetailActivity.this).load(image).error(R.drawable.no_image).resize(365, 200).into(ivNew_Detail);
//        tvTitle_Detail.setText(title);
    }

    public void onClick() {
        final String id_user = hashMap.get(SessionManagerUser.KEY_ID);
        final String new_id = getIntent().getStringExtra("new_id");
        itvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                itvSaved.setVisibility(View.VISIBLE);
                Interface apiInterface = APIRetro.getClient().create(Interface.class);
                Call<Login> entryCall = apiInterface.postSave(id_user, new_id);
                entryCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if (response.isSuccessful()) {
                            login = response.body();
                            if (login.getStatus().equals("success")) {
                                Toast.makeText(DetailActivity.this, "Lưu tin tức thành công", Toast.LENGTH_SHORT).show();
                                itvSave.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(DetailActivity.this, "Tin tức đã được lưu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(DetailActivity.this, "Kiểm tra kết nối internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
//        itvSaved.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itvSave.setVisibility(View.VISIBLE);
//                itvSaved.setVisibility(View.GONE);
//            }
//        });
    }

    public String replce(String replace) {
        String s = replace;
        s = s.replace("/imgnews/","https://www.hutech.edu.vn/imgnews/");
        return s;
    }
}