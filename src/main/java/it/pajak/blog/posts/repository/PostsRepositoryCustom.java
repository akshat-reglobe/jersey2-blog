package it.pajak.blog.posts.repository;

import it.pajak.blog.posts.model.Comment;
import it.pajak.blog.posts.model.Post;

public interface PostsRepositoryCustom {

    public void addComment(String postId, Comment comment);

    public void updatePost(String postId, Post post) throws Exception;
}
