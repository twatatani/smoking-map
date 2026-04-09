FROM eclipse-temurin:21-jdk-alpine

COPY . .

RUN chmod +x gradlew

RUN ./gradlew build -x test

CMD ["sh", "-c", "java -jar build/libs/*.jar"]

jar {
    enabled = false
}
