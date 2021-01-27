package com.motatawera.postslistretrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.motatawera.postslistretrofit.R;
import com.motatawera.postslistretrofit.databinding.LayoutPostsBinding;
import com.motatawera.postslistretrofit.model.PostsModel;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    Context context;
    List<PostsModel> postsList;
    onPostListener onPostListener;

    public PostsAdapter(Context context, List<PostsModel> postsList, PostsAdapter.onPostListener onPostListener) {
        this.context = context;
        this.postsList = postsList;
        this.onPostListener = onPostListener;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutPostsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.layout_posts, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {

        PostsModel postsModel = postsList.get(position);

        holder.binding.txtPostId.setText(String.valueOf(postsModel.getId()));
        holder.binding.txtPostTitle.setText(postsModel.getTitle());

        holder.binding.btnPath.setOnClickListener(v -> {
            onPostListener.onPathClick(position, context, postsList);
        });

        holder.binding.btnQuery.setOnClickListener(v -> {
            onPostListener.onQueryClick(position, context, postsList);
        });

        holder.binding.btnPut.setOnClickListener(v -> onPostListener.onPutClick(position, context, postsList));
        holder.binding.btnDelete.setOnClickListener(v -> {
            onPostListener.onDeleteClick(position, context, postsList);
        });

    }

    public void setList(List<PostsModel> postsList) {
        this.postsList = postsList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPostsBinding binding;

        public ViewHolder(@NonNull LayoutPostsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onPostListener {
        void onPathClick(int position, Context context, List<PostsModel> postsList);

        void onQueryClick(int position, Context context, List<PostsModel> postsList);

        void onPutClick(int position, Context context, List<PostsModel> postsList);

        void onDeleteClick(int position, Context context, List<PostsModel> postsList);
    }


}
