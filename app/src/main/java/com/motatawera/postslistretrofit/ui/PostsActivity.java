package com.motatawera.postslistretrofit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.motatawera.postslistretrofit.R;
import com.motatawera.postslistretrofit.adapter.PostsAdapter;

import com.motatawera.postslistretrofit.builders.PostBuilder;
import com.motatawera.postslistretrofit.databinding.ActivityPostsBinding;

import com.motatawera.postslistretrofit.databinding.AddpostalertLayoutBinding;

import com.motatawera.postslistretrofit.model.PostsModel;
import com.motatawera.postslistretrofit.model.PostsViewModel;
import com.motatawera.postslistretrofit.network.APIClient;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsActivity extends AppCompatActivity implements PostsAdapter.onPostListener {
    AddpostalertLayoutBinding layoutBinding;


    ActivityPostsBinding binding;
    private PostsViewModel postsViewModel;
    private int userId;
    private String title, body;
    private PostsAdapter postsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_posts);
        binding.setLifecycleOwner(this);

        binding.rvPostList.setLayoutManager(new LinearLayoutManager(this));

        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);

        postsViewModel.getisProggress().observe(this, aBoolean -> {
            if (aBoolean) {
                binding.ProgressPostsLists.setVisibility(View.VISIBLE);
            } else {
                binding.ProgressPostsLists.setVisibility(View.GONE);
            }
        });

        postsViewModel.getError().observe(this, s -> {
            binding.ProgressPostsLists.setVisibility(View.GONE);
            Toast.makeText(PostsActivity.this, s, Toast.LENGTH_SHORT).show();
        });


        binding.fab.setOnClickListener(v -> AddPost());

        GetPostsList();


    }


    private void GetPostsList() {

        postsViewModel.getList().observe(this, postsModelList -> {
            postsAdapter = new PostsAdapter(this, postsModelList, this);
            postsAdapter.setList(postsModelList);
            Toast.makeText(this, postsModelList.get(1).getTitle(), Toast.LENGTH_SHORT).show();
            binding.rvPostList.setAdapter(postsAdapter);

        });


    }

    @Override
    public void onPathClick(int position, Context context, List<PostsModel> postsList) {

        Intent intent = new Intent(context, PathActivity.class)
                .putExtra("postId", postsList.get(position).getId());
        startActivity(intent);

    }

    @Override
    public void onQueryClick(int position, Context context, List<PostsModel> postsList) {

        Intent intent = new Intent(context, QueryActivity.class)
                .putExtra("postId", postsList.get(position).getId());
        startActivity(intent);

    }

    @Override
    public void onPutClick(int position, Context context, List<PostsModel> postsList) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        layoutBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.addpostalert_layout, null, false);
        alertDialog.setView(layoutBinding.getRoot());
        alertDialog.show();
        int id = postsList.get(position).getId();

        layoutBinding.btnDone.setOnClickListener(v -> {


            EditText a = layoutBinding.AddPostDialogUserid;

            userId = Integer.parseInt(a.getText().toString());

            title = layoutBinding.AddPostDialogTitle.getText().toString();
            body = layoutBinding.AddPostDialogBody.getText().toString();

            PostsModel postsModel = new PostBuilder().setUserId(userId).setTitle(title).setBody(body).setPost();
            APIClient.getINSTANCE().putPost(id, postsModel).enqueue(new Callback<PostsModel>() {
                @Override
                public void onResponse(@NotNull Call<PostsModel> call, @NotNull Response<PostsModel> response) {
                    Toast.makeText(PostsActivity.this, response.body().getTitle(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(@NotNull Call<PostsModel> call, @NotNull Throwable t) {
                    Toast.makeText(PostsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            alertDialog.dismiss();
        });

    }

    @Override
    public void onDeleteClick(int position, Context context, List<PostsModel> postsList) {
        int id = postsList.get(position).getId();


        APIClient.getINSTANCE().deletePost(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                Toast.makeText(PostsActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {

            }

        });


    }


    private void AddPost() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        layoutBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.addpostalert_layout, null, false);
        alertDialog.setView(layoutBinding.getRoot());
        alertDialog.show();


        layoutBinding.btnDone.setOnClickListener(v -> {


            EditText a = layoutBinding.AddPostDialogUserid;

            userId = Integer.parseInt(a.getText().toString());

            title = layoutBinding.AddPostDialogTitle.getText().toString();
            body = layoutBinding.AddPostDialogBody.getText().toString();

            PostsModel postsModel = new PostBuilder().setUserId(userId).setTitle(title).setBody(body).setPost();
            APIClient.getINSTANCE().setPost(postsModel).enqueue(new Callback<PostsModel>() {
                @Override
                public void onResponse(Call<PostsModel> call, Response<PostsModel> response) {
                    Toast.makeText(PostsActivity.this, response.body().getTitle(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<PostsModel> call, Throwable t) {
                    Toast.makeText(PostsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            alertDialog.dismiss();
        });


    }
}