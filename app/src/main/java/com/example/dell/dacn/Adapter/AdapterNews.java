package com.example.dell.dacn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.dacn.Model.News;
import com.example.dell.dacn.R;
import com.example.dell.dacn.activiy.DetailNews.DetailActivity;
import com.example.dell.dacn.activiy.DetailNews.DetailSaveNewsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DELL on 18/10/17.
 */

public class AdapterNews extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NEWS = 0;
    private static final int SAVENEWS = 1;

    public List<News> news;
    public Context context;
    public int mStatus;

    public AdapterNews(List<News> news, Context context) {
        this.context = context;
        this.news = news;
        this.mStatus = NEWS;
    }

    public AdapterNews(List<News> news, Context context, int status) {
        this.context = context;
        this.news = news;
        this.mStatus = status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_listitems, parent, false);
        switch (mStatus) {
            case NEWS: {
                viewHolder = new NewsViewHolder(view);
                break;
            }
            case SAVENEWS: {
                viewHolder = new SaveNewsViewHolder(view);
                break;
            }
            default:
                viewHolder = new NewsViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (mStatus) {
            case NEWS: {
                NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
                ViewHolderNews(newsViewHolder, position);
                break;
            }
            case SAVENEWS: {
                SaveNewsViewHolder saveNewsViewHolder = (SaveNewsViewHolder) holder;
                ViewHolderSaveNews(saveNewsViewHolder, position);
                break;
            }
        }
    }

    private void ViewHolderNews(NewsViewHolder holder, int position) {
        final News newa = news.get(position);
        holder.tvTitle.setText(newa.getTitle());
        holder.tvDescription.setText(newa.getCreate_At());
        Picasso.with(context).load(newa.getImage()).error(R.drawable.no_image).resize(150, 150).into(holder.ivNew);
        holder.newLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("image", newa.getImage());
                intent.putExtra("title", newa.getTitle());
                intent.putExtra("description", newa.getDescription());
                intent.putExtra("new_id", newa.getNew_Id());
                intent.putExtra("url_news", newa.getUrl_news());
                context.startActivity(intent);
            }
        });
    }

    private void ViewHolderSaveNews(SaveNewsViewHolder holder, int position) {
        final News newa = news.get(position);
        holder.tvTitle.setText(newa.getTitle());
        holder.tvDescription.setText(newa.getCreate_At());
        Picasso.with(context).load(newa.getImage()).error(R.drawable.no_image).resize(150, 150).into(holder.ivNew);
        holder.newLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailSaveNewsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("image", newa.getImage());
                intent.putExtra("title", newa.getTitle());
                intent.putExtra("description", newa.getDescription());
                intent.putExtra("new_id", newa.getNew_Id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView ivNew;
        TextView tvTitle;
        TextView tvDescription;
        CardView newLayout;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ivNew = (ImageView) itemView.findViewById(R.id.ivNew);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            newLayout = (CardView) itemView.findViewById(R.id.new_layout);
        }
    }

    public class SaveNewsViewHolder extends RecyclerView.ViewHolder {

        ImageView ivNew;
        TextView tvTitle;
        TextView tvDescription;
        CardView newLayout;

        public SaveNewsViewHolder(View itemView) {
            super(itemView);
            ivNew = (ImageView) itemView.findViewById(R.id.ivNew);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            newLayout = (CardView) itemView.findViewById(R.id.new_layout);
        }
    }
}

