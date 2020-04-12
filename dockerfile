# Start with a base image containing Java runtime
# It is a very light OpenJDK 8 runtime image that Alpine Linux uses.
# (It is perfect for small Java microservices).
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer=1987diegog@gmail.com

# The VOLUME instruction creates a mount point with the specified name.
# It is recommended to choose the / tmp directory because spring boot application
# creates working directories for Tomcat in this path
VOLUME /tmp

# Make port 8099 available to the world outside this container
EXPOSE 8099

# The application's jar file
# The ARG instruction defines a variable with a default value.
# You can override the default value of the variable by passing it at build time.
# Once defined, the variable can be used by the instructions following it.
ARG JAR_FILE=target/learn-words.jar

# The ADD instruction is used to copy new files and directories to the docker image.
# Add the application's jar to the container
ADD ${JAR_FILE} learn-words.jar

# Configure how the application is executed inside the container. Run the jar file
# -Djava.security.egd=file:/dev/./urandom: Activates a much faster implementation for the SecureRandom
# provider. In some applications this can cut down the applications startup time in half.
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/learn-words.jar"]
