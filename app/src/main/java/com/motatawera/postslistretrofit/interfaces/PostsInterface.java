package com.motatawera.postslistretrofit.interfaces;

import com.motatawera.postslistretrofit.model.PostsModel;

import java.util.List;

public interface PostsInterface {
     void onSuccess(List<PostsModel>postsList);
     void onFailer(String msg);
     void isProgress(boolean p);
}
