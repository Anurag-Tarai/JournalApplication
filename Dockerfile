# Use a base image that includes Java and Maven
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the entire project directory into the container
COPY . .

# Build the project using Maven
RUN mvn clean package

# Expose the port that the application will run on
EXPOSE 8080

# Second stage: Use a smaller base image to run the Java application
FROM openjdk:17-jdk-alpine

# Copy the jar file from the build stage to the final image
COPY --from=build /app/target/JournalAppPractice-0.0.1-SNAPSHOT.jar /app/app.jar

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
