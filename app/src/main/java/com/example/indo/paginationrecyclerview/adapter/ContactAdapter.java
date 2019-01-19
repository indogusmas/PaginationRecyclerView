package com.example.indo.paginationrecyclerview.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.indo.paginationrecyclerview.R;
import com.example.indo.paginationrecyclerview.model.Contact;
import com.example.indo.paginationrecyclerview.model.OnLoadMoreListerner;

import java.util.List;

public class ContactAdapter  extends  RecyclerView.Adapter{

    private  final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListerner onLoadMoreListerner;

    private  boolean isLoading;
    private Activity activity;
    private List<Contact>contacts;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;


    public  ContactAdapter(RecyclerView recyclerView, List<Contact> contacts, Activity activity){
        this.contacts = contacts;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)){
                    if (onLoadMoreListerner != null){
                        onLoadMoreListerner.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return contacts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public  void setOnLoadMoreListerner(OnLoadMoreListerner mOnLoadMoreListener){
        this.onLoadMoreListerner = mOnLoadMoreListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_recycler_view_row, viewGroup, false);
            return  new UserViewHolder(view);
        }else  if (i == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(activity).inflate(R.layout.item_loading, viewGroup, false);
            return  new LoaingViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    private  class LoaingViewHolder extends  RecyclerView.ViewHolder{

        private ProgressBar progressBar;
        public LoaingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar1);
        }
    }

    public class UserViewHolder extends  RecyclerView.ViewHolder{
        public TextView phone;
        public TextView email;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            phone = (TextView)itemView.findViewById(R.id.txt_phone);
            email = (TextView)itemView.findViewById(R.id.txt_email);
        }
    }
}
