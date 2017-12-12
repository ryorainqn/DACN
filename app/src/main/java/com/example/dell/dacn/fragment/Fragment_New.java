package com.example.dell.dacn.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DELL on 10/10/17.
 */

public class Fragment_New extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private AdapterNews adapterNews;
    private List<News> news = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout refreshLayout;
    private boolean change = false;
    private boolean reset = false;
    private ProgressBar progressBar;
    private IconTextView iconTextView;
    private TextView textView;
    View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_new, container, false);
            init(rootView);
            setContent();
            getData();
        }
        return rootView;
    }

    public void init(View view) {
        recyclerView = view.findViewById(R.id.new_recycler_view);
        refreshLayout = view.findViewById(R.id.srlMain);
        progressBar = view.findViewById(R.id.pb);
        iconTextView = view.findViewById(R.id.ic_wifi);
        textView = view.findViewById(R.id.tv_wifi);
    }

    //Function set dữ liệu
    public void setContent() {
        //Set bla bla
        refreshLayout.setOnRefreshListener(this);
        adapterNews = new AdapterNews(news, getActivity());
        recyclerView.setAdapter(adapterNews);
        //Set Recycleview
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    public void getData() {
        progressBar.setVisibility(View.VISIBLE);
        Interface apiInterface = APIRetro.getClient().create(Interface.class);
        Call<Entry> newsCall = apiInterface.getNews();
        newsCall.enqueue(new Callback<Entry>() {
            @Override
            public void onResponse(Call<Entry> call, Response<Entry> response) {
                if (response.isSuccessful()) {
                    news.addAll(response.body().getEntry());
                    adapterNews.notifyDataSetChanged();
                    //Set Adapter
                    progressBar.setVisibility(View.GONE);
                    iconTextView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<Entry> call, Throwable t) {
                if(news.size()>0){

                }else {
                    iconTextView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Lỗi kết nối Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        if (InternetConnection.checkConnection(getContext())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                    if (change) {
                        adapterNews = new AdapterNews(news, getActivity());
                        recyclerView.setAdapter(adapterNews);
                        adapterNews.notifyDataSetChanged();
                    } else {
                        news.clear();
                        getData();
                        reset = true;
                        iconTextView.setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);
                        adapterNews.notifyDataSetChanged();
                    }
                }
            }, 2000);
        }else {
            refreshLayout.setRefreshing(false);
        }
    }

}
