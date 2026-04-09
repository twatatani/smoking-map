FROM eclipse-temurin:17-jdk-alpine
COPY . .
RUN chmod +x gradlew
RUN ./gradlew build -x test
CMD ["java", "-jar", "build/libs/smoking-map-0.0.1-SNAPSHOT.jar"]
