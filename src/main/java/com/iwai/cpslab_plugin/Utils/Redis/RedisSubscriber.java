package com.iwai.cpslab_plugin.Utils.Redis;
//
//import io.lettuce.core.RedisClient;
//import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
//import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
//import reactor.core.Disposable;
//import reactor.core.publisher.Flux;
//import reactor.core.scheduler.Schedulers;
//
//import java.time.Duration;
//import java.util.logging.Logger;

public class RedisSubscriber {
//    Logger logger = Logger.getLogger(RedisSubscriber.class.getName());
//    public static void main(String[] args) {
//        RedisSubscriber redisSubscriber = new RedisSubscriber();
//        redisSubscriber.subscribe();
//    }
//
//    public void subscribe() {
//        RedisClient redisClient = RedisClient.create("redis://host.docker.internal:6379");
//        try (StatefulRedisPubSubConnection<String, String> connection = redisClient.connectPubSub()) {
//            RedisPubSubReactiveCommands<String, String> reactiveCommands = connection.reactive();
//            Disposable disposable = Flux.interval(Duration.ofSeconds(1L)).subscribe(count -> {
//                reactiveCommands.publish("channel", "message-" + count).subscribe();
//            });
//            System.console().readLine("> Enter stop.");
//
//            disposable.dispose();
//            Schedulers.shutdownNow();
//        } finally {
//            redisClient.shutdown();
//        }
//    }
}
