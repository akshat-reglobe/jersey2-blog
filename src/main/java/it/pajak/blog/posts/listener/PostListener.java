package it.pajak.blog.posts.listener;

import com.github.slugify.Slugify;
import com.mongodb.DBObject;
import it.pajak.blog.posts.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;

import java.util.Date;

public class PostListener extends AbstractMongoEventListener<Post> {

    @Autowired
    private Slugify slugify;

    @Override
    public void onBeforeSave(Post post, DBObject dbo) {
        dbo.put("slug", slugify.slugify(post.title));
        dbo.put("createDate", new Date());
    }
}