package com.motatawera.postslistretrofit.builders;

import com.motatawera.postslistretrofit.model.PostsModel;

public class PostBuilder {

    private int userId;
    private int id;
    private String title;
    private String body;

    public PostBuilder setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public PostBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public PostBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public PostBuilder setBody(String body) {
        this.body = body;
        return this;
    }
    public PostsModel setPost()
    {
        return new PostsModel(id,userId,title,body);
    }

}
