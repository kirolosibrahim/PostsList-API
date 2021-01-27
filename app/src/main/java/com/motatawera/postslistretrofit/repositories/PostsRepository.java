package com.motatawera.postslistretrofit.repositories;

import com.motatawera.postslistretrofit.interfaces.PostsInterface;
import com.motatawera.postslistretrofit.model.PostsModel;
import com.motatawera.postslistretrofit.network.APIClient;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostsRepository {

    PostsInterface postsInterface;

    public PostsRepository(PostsInterface postsInterface) {
        this.postsInterface = postsInterface;
    }

    public void GetPostsList() {

        postsInterface.isProgress(true);

        Single<List<PostsModel>> observable = APIClient.getINSTANCE().getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(l->{
            postsInterface.isProgress(false);
            postsInterface.onSuccess(l);

        },e->{
            postsInterface.isProgress(false);
            postsInterface.onFailer(e.getMessage());

        });

    }

}
