package it.pajak.blog.posts.resource;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import it.pajak.blog.posts.model.Post;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PostsResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        ResourceConfig config = new ResourceConfig();
        config.packages("it.pajak.blog");
        config.property("contextConfigLocation", "classpath:test-application-context.xml");
        return config;
    }

    private static final String MONGO_HOST = "127.0.0.1";
    private static final int MONGO_PORT = 27028;
    private static final String MONGO_DB = "testdb";
    private static MongodExecutable mongodExecutable;
    private static DB db;

    @BeforeClass
    public static void initializeDB() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(MONGO_PORT, Network.localhostIsIPv6()))
                .build();

        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();

        // mongo client
        MongoClient mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT);
        db = mongoClient.getDB(MONGO_DB);
    }

    @AfterClass
    public static void shutdownDB() throws InterruptedException {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        db.getCollection("posts").drop();
    }

    @Test
    public void should_create_new_post_ang_get_it() throws Exception {
        Post post = new Post();
        post.title = "post title";
        /*
        Entity<Post> payload = Entity.entity(post, MediaType.APPLICATION_JSON);

        Response postResponse = target()
                .path("v1/posts")
                .request()
                .post(payload);
        assertEquals(HttpServletResponse.SC_CREATED, postResponse.getStatus());


        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx: " + postResponse.getLocation().toString());

        Response response = target()
                .path("v1/posts/" + postResponse.getLocation().toString())
                .request()
                .get(Response.class);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        */
    }
}
