package in.akka.streams;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.stream.testkit.TestSubscriber;
import akka.stream.testkit.javadsl.TestSink;
import akka.testkit.javadsl.TestKit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: chengyq
 * @Date: 2020/2/24 6:03 PM
 * @Since 1.0.0
 */

public class FirstTest {

    ActorSystem actorSystem;
    Materializer materializer;

    @Before
    public void setup() {
        actorSystem = ActorSystem.create();
        materializer = ActorMaterializer.create(actorSystem);
    }

    @After
    public void tearDown() {
        TestKit.shutdownActorSystem(actorSystem);
    }

    @Test
    public void t() {
        Assert.assertNotNull(actorSystem);
    }

    @Test
    public void test_a_source() {
        Sink<Object, TestSubscriber.Probe<Object>> sink = TestSink.probe(actorSystem);
        Source<Object, NotUsed> source = Source.single("test");

        source.runWith(sink, materializer)
                .request(1)
                .expectNext("test")
                .expectComplete();
    }
}
