package com.example.dell.dacn.activiy.News;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.dacn.API.APIRetro;
import com.example.dell.dacn.API.Interface;
import com.example.dell.dacn.Adapter.AdapterNews;
import com.example.dell.dacn.Front.IconTextView;
import com.example.dell.dacn.Internet.InternetConnection;
import com.example.dell.dacn.Model.Entry;
import com.example.dell.dacn.Model.News;
import com.example.dell.dacn.R;
import com.example.dell.dacn.SaveLogin.SessionManagerUser;
import com.example.dell.dacn.fragment.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveNews extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private EndlessRecyclerViewScrollListener loadmore;
    private RecyclerView recyclerView;
    private AdapterNews adapterNews;
    private List<News> news = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private boolean change = false;
    private boolean reset = false;
    private ProgressBar progressBar;
    private SessionManagerUser sessionManagerUser;
    private HashMap<String, String> hashMap;
    private Toolbar toolbar;
    private int SaveNews = 1;
    private LinearLayoutManager LinearLayoutManager;
    private TextView textView, tvWifi;
    private IconTextView iconTextView, icWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_news);
        toolbar = (Toolbar) findViewById(R.id.toolbar_header_savenews);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        sessionManagerUser = new SessionManagerUser(SaveNews.this);
        hashMap = new HashMap<>();
        hashMap = sessionManagerUser.getUserDetails();
        init();
        setData();
        getData();
    }

    public void init() {
        recyclerView = (RecyclerView) findViewById(R.id.savenew_recycler_view);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.srlMainSave);
        progressBar = (ProgressBar) findViewById(R.id.pb_savenews);
        textView = (TextView) findViewById(R.id.tvNodata);
        iconTextView = (IconTextView) findViewById(R.id.ic_new);
        icWifi = (IconTextView) findViewById(R.id.ic_wifi_save);
        tvWifi = (TextView) findViewById(R.id.tv_wifi_save);
    }

    public void setData() {
        refreshLayout.setOnRefreshListener(this);
        adapterNews = new AdapterNews(news, SaveNews.this, SaveNews);
        recyclerView.setAdapter(adapterNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(SaveNews.this));
        recyclerView.setHasFixedSize(true);
    }

    public void getData() {
        String id = hashMap.get(SessionManagerUser.KEY_ID);
        progressBar.setVisibility(View.VISIBLE);
        Interface apiInterface = APIRetro.getClient().create(Interface.class);
        Call<Entry> newsCall = apiInterface.getNewsSave(id);
        newsCall.enqueue(new Callback<Entry>() {
            @Override
            public void onResponse(Call<Entry> call, Response<Entry> response) {
                if (response.isSuccessful()) {
                    news.addAll(response.body().getEntry());
                    adapterNews.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    if (news.size() < 1) {
                        iconTextView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        textView.setVisibility(View.GONE);
                        iconTextView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Entry> call, Throwable t) {
                if (news.size() > 0) {

                } else {
                    tvWifi.setVisibility(View.VISIBLE);
                    icWifi.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SaveNews.this, "Lỗi kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                    if (change) {
                        adapterNews = new AdapterNews(news, SaveNews.this, SaveNews);
                        recyclerView.setAdapter(adapterNews);
                        adapterNews.notifyDataSetChanged();
                    } else {
                        reset = true;
                        news.clear();
                        getData();
                        adapterNews.notifyDataSetChanged();
                        tvWifi.setVisibility(View.GONE);
                        icWifi.setVisibility(View.GONE);
                    }
                }
            }, 2000);
        }else {
            refreshLayout.setRefreshing(false);
        }
    }
}

