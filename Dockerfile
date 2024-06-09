FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY pom.xml .

COPY src ./src

COPY mvnw .

COPY .mvn .mvn

RUN chmod a+x mvnw

RUN ./mvnw package

CMD ["java", "-jar", "target/aulabd2-0.0.1-SNAPSHOT.war"]
