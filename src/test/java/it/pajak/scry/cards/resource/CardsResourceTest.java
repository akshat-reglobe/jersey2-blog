package it.pajak.scry.cards.resource;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.ws.rs.core.Application;
import java.io.IOException;

public class CardsResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        ResourceConfig config = new ResourceConfig();
        config.packages("it.pajak.scry.cards");
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

        db.getCollection("cards").drop();
    }
}
