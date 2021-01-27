package com.motatawera.postslistretrofit.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.motatawera.postslistretrofit.R;
import com.motatawera.postslistretrofit.databinding.LayoutQueryBinding;
import com.motatawera.postslistretrofit.model.CommentsModel;

import java.util.List;

public class PostsQueryAdapter extends RecyclerView.Adapter<PostsQueryAdapter.ViewHolder> {

    Context context;
    List<CommentsModel> commentsModelList;

    public PostsQueryAdapter(Context context, List<CommentsModel> commentsModelList) {
        this.context = context;
        this.commentsModelList = commentsModelList;
    }

    @NonNull
    @Override
    public PostsQueryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutQueryBinding binding = DataBindingUtil.inflate((LayoutInflater.from(context)), R.layout.layout_query, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsQueryAdapter.ViewHolder holder, int position) {
        CommentsModel commentsModel = commentsModelList.get(position);

        holder.binding.txtPostIdQuery.setText(String.valueOf(commentsModel.getId()));
        holder.binding.txtPostTitleQuery.setText(commentsModel.getBody());


    }

    @Override
    public int getItemCount() {
        return commentsModelList.size();
    }

    public void setList(List<CommentsModel> commentsModelList) {
        this.commentsModelList = commentsModelList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutQueryBinding binding;

        public ViewHolder(@NonNull LayoutQueryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
