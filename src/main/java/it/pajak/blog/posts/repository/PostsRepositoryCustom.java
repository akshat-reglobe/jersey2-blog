package it.pajak.blog.posts.repository;

import it.pajak.blog.posts.model.Comment;

public interface PostsRepositoryCustom {

    public void addComment(String postId, Comment comment);
}
