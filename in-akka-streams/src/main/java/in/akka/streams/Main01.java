package in.akka.streams;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

/**
 * @Author: chengyq
 * @Date: 2020/2/23 10:47 PM
 * @Since 1.0.0
 */
public class Main01 {

    public static void main(String[] args) {
        final Source<String, NotUsed> source = Source.single("Error: test message");
        final Source<String, NotUsed> error = source.filter(m -> m.startsWith("Error"));


        final ActorSystem actorSystem = ActorSystem.create("reactive-messages");
        final Materializer mat = ActorMaterializer.create(actorSystem);
        error.runWith(Sink.foreach(System.out::println), mat);

        actorSystem.terminate();
    }
}
