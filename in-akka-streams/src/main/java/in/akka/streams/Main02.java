package in.akka.streams;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.IOResult;
import akka.stream.Materializer;
import akka.stream.javadsl.*;
import akka.util.ByteString;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * @Author: chengyq
 * @Date: 2020/2/24 11:10 AM
 * @Since 1.0.0
 *
 * save source to file
 */
public class Main02 {

    public static void main(String[] args) {

        final Sink<String, CompletionStage<IOResult>> fileSink = Flow.of(String.class)
                .map(s -> ByteString.fromString(s + System.getProperty("line.separator")))
                .toMat(FileIO.toPath(Paths.get("/tmp/in.akka.streams.main02.txt")), Keep.right());

        final List<String> sourceStrings = Arrays.asList("Hello",
                "Akka Streams");

        final ActorSystem actorSystem = ActorSystem.create("akka-streams");
        final Materializer materializer = ActorMaterializer.create(actorSystem);

        Source.from(sourceStrings).runWith(fileSink, materializer);

        actorSystem.terminate();
    }
}
