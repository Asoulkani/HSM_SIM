# Use an official Maven image to build the application
FROM maven:3.8.4-openjdk-17 AS build

# Install the local dependency to the local Maven repository
COPY ../hsm-sim-dataConverter /app/hsm-sim-dataConverter
WORKDIR /app/hsm-sim-dataConverter
RUN mvn dependency:go-offline
RUN mvn install

WORKDIR /app
COPY ./hsm-sim-TcpServer/pom.xml .
RUN mvn dependency:go-offline
COPY ./hsm-sim-TcpServer/src ./src
RUN mvn clean package -DskipTests
RUN mvn install

# Use an official Java runtime as a parent image
FROM gradle:jdk17-alpine
WORKDIR /app
COPY --from=build /app/target/hsm-sim-TcpServer-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables
ENV JAVA_OPTS=""

# Expose port 1500 for the application
EXPOSE 1500

# Run the application when the container starts
CMD ["java", "-jar", "app.jar"]

