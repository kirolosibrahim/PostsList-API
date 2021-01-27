package com.motatawera.postslistretrofit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.motatawera.postslistretrofit.R;
import com.motatawera.postslistretrofit.adapter.PostsQueryAdapter;
import com.motatawera.postslistretrofit.databinding.ActivityPathBinding;

import com.motatawera.postslistretrofit.model.CommentsModel;
import com.motatawera.postslistretrofit.network.APIClient;



import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PathActivity extends AppCompatActivity {



    ActivityPathBinding binding;

    List<CommentsModel> commentsList;
    PostsQueryAdapter postsQueryAdapter;
    int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_path);

        commentsList = new ArrayList<>();
        binding.ProgressPostsListsPath.setVisibility(View.VISIBLE);



        postsQueryAdapter = new PostsQueryAdapter(PathActivity.this, commentsList);
        binding.rvPostListPath.setLayoutManager(new LinearLayoutManager(PathActivity.this));
        binding.rvPostListPath.setAdapter(postsQueryAdapter);


        GetPostsQueryList();

        binding.swpPath.setOnRefreshListener(() -> {
            commentsList = new ArrayList<>();
            GetPostsQueryList();
        });


    }


    private void GetPostsQueryList() {

        postId = getIntent().getIntExtra("postId", 0);
       APIClient.getINSTANCE().getPostsPath(postId).enqueue(new Callback<List<CommentsModel>>() {
            @Override
            public void onResponse(Call<List<CommentsModel>> call, Response<List<CommentsModel>> response) {

                postsQueryAdapter.setList(response.body());
                postsQueryAdapter.notifyDataSetChanged();
                binding.ProgressPostsListsPath.setVisibility(View.GONE);
                binding.swpPath.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<CommentsModel>> call, Throwable t) {
                Toast.makeText(PathActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.rvPostListPath.setVisibility(View.GONE);
                binding.swpPath.setRefreshing(false);
            }
        });

    }




}