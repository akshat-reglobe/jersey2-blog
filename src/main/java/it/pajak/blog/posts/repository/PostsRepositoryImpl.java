package it.pajak.blog.posts.repository;

import it.pajak.blog.posts.model.Comment;
import it.pajak.blog.posts.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class PostsRepositoryImpl implements PostsRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void addComment(String postId, Comment comment) {
        Query searchUserQuery = new Query(Criteria.where("id").is(postId));

        mongoTemplate.updateFirst(
            Query.query(Criteria.where("id").is(postId)),
            new Update().push("comments", comment),
            Post.class
        );
    }
}
