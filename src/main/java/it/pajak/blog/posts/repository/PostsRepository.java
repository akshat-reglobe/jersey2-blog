package it.pajak.blog.posts.repository;

import it.pajak.blog.posts.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostsRepository extends MongoRepository<Post, String>, PostsRepositoryCustom {

    public Post findOneBySlug(String slug);
}