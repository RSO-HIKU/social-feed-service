package com.hiku.socialFeedService.messaging;

import com.hiku.socialFeedService.repository.FeedRepository;

import com.rabbitmq.client.*;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FollowEventConsumer {
  private static final FeedRepository followRepository = new FeedRepository();

    public static void start() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(System.getenv().getOrDefault("RABBITMQ_HOST", "rabbitmq.platform.svc.cluster.local"));
        factory.setPort(Integer.parseInt(System.getenv().getOrDefault("RABBITMQ_PORT", "5672")));
        factory.setUsername(System.getenv().getOrDefault("RABBITMQ_USER", "hikuuser"));
        factory.setPassword(System.getenv().getOrDefault("RABBITMQ_PASSWORD", "hikupassword"));

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("follow.exchange", "topic", true);
        channel.queueDeclare("follow.queue", true, false, false, null);
        channel.queueBind("follow.queue", "follow.exchange", "follow.*");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            JSONObject event = new JSONObject(message);
            String followerId = event.getString("followerId");
            String followedId = event.getString("followedId");
            String action = event.getString("action");

            // TODO: Update your follow data in the database accordingly
            if ("CREATED".equals(action)) {
                
                followRepository.addFollow(followerId, followedId);
                System.out.println("Added follow: " + followerId + " -> " + followedId);
          
                // Add follow relationship in your DB
            } else if ("REMOVED".equals(action)) {
               followRepository.removeFollow(followerId, followedId);
                System.out.println("Removed follow: " + followerId + " -> " + followedId);
          
            }
            System.out.println("Processed follow event: " + message);
        };

        channel.basicConsume("follow.queue", true, deliverCallback, consumerTag -> {});
    }
}