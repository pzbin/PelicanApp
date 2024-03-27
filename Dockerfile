# Use an OpenJDK runtime as a base image
FROM openjdk:17.0.1-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/pelican-api*.jar pelican-api.jar

# Expose the port the Spring Boot application runs on
EXPOSE 9090

# Command to run the Spring Boot application
CMD ["java", "-jar", "pelican-api.jar"]