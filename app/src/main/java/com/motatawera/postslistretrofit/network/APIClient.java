package com.motatawera.postslistretrofit.network;

import com.motatawera.postslistretrofit.interfaces.ApiInterface;
import com.motatawera.postslistretrofit.interfaces.PostsInterface;
import com.motatawera.postslistretrofit.model.CommentsModel;
import com.motatawera.postslistretrofit.model.PostsModel;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class APIClient {
    private static final String SERVER_BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static APIClient INSTANCE;
    private ApiInterface apiInterface;

    public APIClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static APIClient getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new APIClient();
        }
        return INSTANCE;
    }

    public Single<List<PostsModel>> getPosts() {
        return apiInterface.getPosts();
    }

    public Call<PostsModel> putPost(int id,PostsModel postsModel) {
        return apiInterface.putPost(id,postsModel);
    }
    public  Call<Void> deletePost(int id) {
        return apiInterface.deletePost(id);
    }

   public Call<PostsModel> setPost( PostsModel postsModel) {
        return apiInterface.setPost(postsModel);
    }
   public Call<List<CommentsModel>> getPostsPath( int postId) {
        return apiInterface.getPostsPath(postId);
    }
    public Call<List<CommentsModel>> getPostsQuery(int postId) {
        return apiInterface.getPostsQuery(postId);
    }

}
