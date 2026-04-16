# Stage 1: Build the JAR file with Maven
FROM maven:3.8.7-eclipse-temurin-17-alpine AS build
# Set the working directory inside the container
WORKDIR /app
# Copy the pom.xml and download dependencies
COPY pom.xml .
# Download dependencies (this step is cached unless pom.xml changes)
RUN mvn dependency:go-offline -B
# Copy the source code into the container
COPY src ./src
# Run Maven clean and install
RUN mvn clean install -DskipTests

# Stage 2: Copy the built JAR to the final image
FROM eclipse-temurin:17-jdk-alpine
# Create a volume to store temporary files
VOLUME /tmp
# Copy the JAR file from the build stage to the current stage
COPY --from=build /app/target/*.jar ipl-analytics-hub.jar
# Run the JAR file
ENTRYPOINT ["java", "-jar", "/ipl-analytics-hub.jar"]
# Expose the port the application runs on
EXPOSE 8090
