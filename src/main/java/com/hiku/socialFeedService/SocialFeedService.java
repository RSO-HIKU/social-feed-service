package com.hiku.socialFeedService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api/feed")
public class SocialFeedService extends Application {
        public SocialFeedService() {
        try {
com.hiku.socialFeedService.messaging.FollowEventConsumer.start();
            System.out.println("RabbitMQ consumer started successfully.");
        } catch (Exception e) {
            System.err.println("Failed to start RabbitMQ consumer: " + e.getMessage());
        }
    }
}
