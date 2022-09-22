FROM openjdk:11

EXPOSE 8080

ADD target/*.jar travel-budget-app-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/travel-budget-app-0.0.1-SNAPSHOT.jar"]