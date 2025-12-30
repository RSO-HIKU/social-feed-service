package com.hiku.socialFeedService.messaging;

import com.rabbitmq.client.*;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FollowEventConsumer {

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
            Long followerId = event.getLong("followerId");
            Long followedId = event.getLong("followedId");
            String action = event.getString("action");

            // TODO: Update your follow data in the database accordingly
            if ("CREATED".equals(action)) {
                // Add follow relationship in your DB
            } else if ("REMOVED".equals(action)) {
                // Remove follow relationship in your DB
            }
            System.out.println("Processed follow event: " + message);
        };

        channel.basicConsume("follow.queue", true, deliverCallback, consumerTag -> {});
    }
}