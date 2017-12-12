package com.example.dell.dacn.activiy.DetailNews;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.example.dell.dacn.activiy.News.SaveNews;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSaveNewsActivity extends AppCompatActivity {

    private ImageView ivNew_Detail;
    private TextView tvTitle_Detail;
    private WebView tvDescription_Detail;
    private IconTextView itvDelete;
    private Entry entry;
    private Login login;
    private Toolbar toolbar;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashMap;
    private final CharSequence[] items = {"Có", "Không"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_save_news);
        toolbar = (Toolbar) findViewById(R.id.toolbar_header_detail_savenews);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        sessionManagerUser = new SessionManagerUser(DetailSaveNewsActivity.this);
        hashMap = new HashMap<>();
        hashMap = sessionManagerUser.getUserDetails();
        init();
        getdata();
        onClick();
    }

    public void init() {
        ivNew_Detail = (ImageView) findViewById(R.id.ivNew_Detail_Save);
        tvTitle_Detail = (TextView) findViewById(R.id.tvTitle_Detail_Save);
        tvDescription_Detail = (WebView) findViewById(R.id.tvDescription_Detail_Save);
        itvDelete = (IconTextView) findViewById(R.id.itvDelete);
    }

    public void getdata() {
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String image = getIntent().getStringExtra("image");
        tvDescription_Detail.loadData(ScaleImageHtml.scaleImageHtml(description), "text/html", "UTF-8");
        Picasso.with(DetailSaveNewsActivity.this).load(image).error(R.drawable.no_image).resize(365, 200).into(ivNew_Detail);
    }

    public void onClick() {
        final String id_user = hashMap.get(SessionManagerUser.KEY_ID);
        final String new_id = getIntent().getStringExtra("new_id");

        itvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(DetailSaveNewsActivity.this);
                builder.setMessage("Bạn có muốn xóa lưu tin tức này không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, final int i) {
                        Interface apiInterface = APIRetro.getClient().create(Interface.class);
                        Call<Login> entryCall = apiInterface.getDelete(id_user, new_id);
                        entryCall.enqueue(new Callback<Login>() {
                            @Override
                            public void onResponse(Call<Login> call, Response<Login> response) {
                                if (response.isSuccessful()) {
                                    login = response.body();
                                    if (login.getStatus().equals("success")) {
                                        Toast.makeText(DetailSaveNewsActivity.this, "Xóa tin thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DetailSaveNewsActivity.this, SaveNews.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Login> call, Throwable t) {
                                Toast.makeText(DetailSaveNewsActivity.this, "Kiểm tra kết nối internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
