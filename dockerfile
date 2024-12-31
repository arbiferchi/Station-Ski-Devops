FROM maven:3.6.3-openjdk-17
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

CMD ["mvn", "spring-boot:run"]


