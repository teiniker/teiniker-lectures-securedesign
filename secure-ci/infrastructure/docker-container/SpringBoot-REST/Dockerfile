FROM eclipse-temurin:21-jre-alpine

# Create a non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Set working directory
WORKDIR /opt/service

# Copy the JAR file with a more generic name
COPY --chown=appuser:appgroup target/SpringBoot-REST.jar app.jar

# Switch to non-root user
USER appuser

# Expose the port your application runs on (adjust as needed)
EXPOSE 8080

# Use ENTRYPOINT with exec form for better signal handling
ENTRYPOINT ["java", "-jar", "app.jar"]