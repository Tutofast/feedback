FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV ENVIRONMENT="dev"
ENV POSTGRES_DATABASE_URI="jdbc:postgresql://34.134.120.49/tutofast-feedback"
ENV POSTGRES_DATABASE_USERNAME="postgres"
ENV POSTGRES_DATABASE_PASSWORD="Password"
ENV AXON_SERVER="18.222.81.236"
ENTRYPOINT ["java","-jar","/app.jar"]