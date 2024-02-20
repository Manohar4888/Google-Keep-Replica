FROM maven AS builder 
WORKDIR /app 
COPY . . 
RUN mvn clean package 

FROM amazoncorretto:latest
COPY --from=builder /target/*.jar .
ENTRYPOINT ["java", "-jar", "*.jar"]
