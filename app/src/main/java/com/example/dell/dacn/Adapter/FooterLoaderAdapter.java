package com.example.dell.dacn.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.dell.dacn.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 18/10/17.
 */

public abstract class FooterLoaderAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected boolean showLoader;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_LOADER = 2;

    protected List<T> news = new ArrayList<>();
    protected LayoutInflater inflater;
    protected Context context;


    public FooterLoaderAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_LOADER) {
            View view = inflater.inflate(R.layout.item_loading, viewGroup, false);
            return new LoaderViewHolder(view);
        }
        if (viewType == VIEW_TYPE_ITEM) {
            return createViewHolder(viewGroup);
        }
        throw new IllegalArgumentException("Invalid ViewType: " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof LoaderViewHolder) {
            LoaderViewHolder loaderViewHolder = (LoaderViewHolder) viewHolder;
            if (showLoader) {
                loaderViewHolder.progressLoadMore.setVisibility(View.VISIBLE);
            } else {
                loaderViewHolder.progressLoadMore.setVisibility(View.GONE);
            }
            return;
        }
        bindRecyclerViewHolder(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        if (news == null || news.size() == 0) {
            return 0;
        }
        return news.size() +1 ;
    }

    @Override
    public int getItemViewType(int position) {
        if (position != 0 && position == getItemCount() - 1) {
            return VIEW_TYPE_LOADER;
        }
        return VIEW_TYPE_ITEM;
    }

    public void showLoading(boolean status) {
        showLoader = status;
        notifyDataSetChanged();
    }

    public abstract RecyclerView.ViewHolder createViewHolder(ViewGroup parent);

    public abstract void bindRecyclerViewHolder(RecyclerView.ViewHolder holder, int position);

    static class LoaderViewHolder extends RecyclerView.ViewHolder {




        @BindView(R.id.progressBar1)
        ProgressBar progressLoadMore;

        public LoaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /*
     * Viáº¿t cÃ¡c phÆ°Æ¡ng thá»©c thÃªm, xÃ³a, sá»­a danh sÃ¡ch á»Ÿ Ä‘Ã¢y náº¿u cáº§n thiáº¿t
     */
    public int getListSize() {
        return news.size();
    }

    public void addItem(T item) {
        news.add(item);
        notifyItemInserted(news.size());
        notifyItemRangeInserted(news.size() - 1, news.size());
    }

    public void updateItem(T item, int position) {
        news.set(position, item);
        notifyItemRangeChanged(position, news.size());
    }

    public void removeItem(int position) {
        news.remove(position);
        notifyItemRemoved(position);
    }

    public T getItem(int position) {
        return news.get(position);
    }

    public List<T> getList() {
        return news;
    }

    public void setList(List<T> newList) {
        news = new ArrayList<>();
        for (T item : newList)
            addItem(item);
    }

    public void addAll(List<T> newList) {
        for (T item : newList)
            addItem(item);
    }

    public void clear() {
        news = new ArrayList<>();
        notifyDataSetChanged();
    }
}