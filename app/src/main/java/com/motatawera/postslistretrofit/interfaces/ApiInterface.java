package com.motatawera.postslistretrofit.interfaces;

import com.motatawera.postslistretrofit.model.CommentsModel;
import com.motatawera.postslistretrofit.model.PostsModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("posts/")
    Single<List<PostsModel>> getPosts();

    @GET("posts/")
    Call<List<CommentsModel>> getPostsQuery(@Query("postId") int postId);

    @GET("posts/{id}/comments")
    Call<List<CommentsModel>> getPostsPath(@Path("id") int postId);

    @POST("/posts")
    Call<PostsModel> setPost(@Body PostsModel postsModel);

    @PUT("/posts/{id}")
    Call<PostsModel> putPost(@Path("id") int id, @Body PostsModel postsModel);

    @DELETE("/posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

}
