package com.motatawera.postslistretrofit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.motatawera.postslistretrofit.R;

import com.motatawera.postslistretrofit.adapter.PostsQueryAdapter;
import com.motatawera.postslistretrofit.databinding.ActivityQueryBinding;


import com.motatawera.postslistretrofit.model.CommentsModel;
import com.motatawera.postslistretrofit.network.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QueryActivity extends AppCompatActivity {

    ActivityQueryBinding binding;

    List<CommentsModel> commentsList;
    PostsQueryAdapter postsQueryAdapter;
    int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_query);

        commentsList = new ArrayList<>();
        binding.ProgressPostsListsQuery.setVisibility(View.VISIBLE);

        postsQueryAdapter = new PostsQueryAdapter(QueryActivity.this, commentsList);
        binding.rvPostListQuery.setLayoutManager(new LinearLayoutManager(QueryActivity.this));
        binding.rvPostListQuery.setAdapter(postsQueryAdapter);


        GetPostsQueryList();

        binding.swpQuery.setOnRefreshListener(() -> {
            commentsList = new ArrayList<>();
            GetPostsQueryList();
        });


    }


    private void GetPostsQueryList() {

        postId = getIntent().getIntExtra("postId", 0);
        APIClient.getINSTANCE().getPostsQuery(postId).enqueue(new Callback<List<CommentsModel>>() {
            @Override
            public void onResponse(Call<List<CommentsModel>> call, Response<List<CommentsModel>> response) {

                postsQueryAdapter.setList(response.body());
                postsQueryAdapter.notifyDataSetChanged();
                binding.ProgressPostsListsQuery.setVisibility(View.GONE);
                binding.swpQuery.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<CommentsModel>> call, Throwable t) {
                Toast.makeText(QueryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.rvPostListQuery.setVisibility(View.GONE);
                binding.swpQuery.setRefreshing(false);
            }
        });

    }


}