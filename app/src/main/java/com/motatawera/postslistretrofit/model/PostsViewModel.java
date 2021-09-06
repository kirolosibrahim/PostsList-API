package com.motatawera.postslistretrofit.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.motatawera.postslistretrofit.interfaces.PostsInterface;
import com.motatawera.postslistretrofit.repositories.PostsRepository;

import java.util.List;

public class PostsViewModel extends ViewModel implements PostsInterface {

    public PostsViewModel() {
        PostsRepository postsRepository = new PostsRepository(this);
        postsRepository.GetPostsList();
    }

    MutableLiveData<List<PostsModel>> PostsListMutableLiveData = new MutableLiveData<>();
    MutableLiveData<String> ErrorMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isProgressMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<PostsModel>> getList() {
        return PostsListMutableLiveData;
    }

    public MutableLiveData<String> getError() {
        return ErrorMutableLiveData;
    }

    public MutableLiveData<Boolean> getisProggress() {
        return isProgressMutableLiveData;
    }

    @Override
    public void onSuccess(List<PostsModel> postsList) {

        PostsListMutableLiveData.setValue(postsList);
    }

    @Override
    public void onFailer(String msg) {
        ErrorMutableLiveData.setValue(msg);
    }

    @Override
    public void isProgress(boolean p) {
        isProgressMutableLiveData.setValue(p);
    }
}
