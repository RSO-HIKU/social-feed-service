FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy JAR
COPY target/social-feed-service-0.1.0.jar ./social-feed-service.jar

# Copy configuration
COPY src/main/resources/config.yaml ./config.yaml

# Expose port as defined in config.yaml
EXPOSE 8091

# Run JAR with explicit config
CMD ["java", "-jar", "social-feed-service.jar"]
