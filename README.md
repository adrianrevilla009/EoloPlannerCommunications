# EoloPlannerCommunications
EoloPlanner app, using Graphql, Grpc and Rest apis, MySQL and MongoDB databases, and Websockets, Kafka and RabbitMQ for messaging

Steps to be followed to execute app:
- First generate proto files for grpc communication in weather service
  cd C:\Proyects\master\Modulo2\eoloPlannerCommunications\weatherservice
  mvn compile
- Then, launch mysql, mongodb and rabbitmq instances with docker
  docker run --rm -e MYSQL_ROOT_PASSWORD=password \ -e MYSQL_DATABASE=eoloplantsDB -p 3306:3306 -d mysql:8.0.22
  docker run --rm -p 27017:27017 -d mongo:4.4-bionic
  docker run --rm -p 5672:5672 -p 15672:15672 rabbitmq:3.11-management
- Finally, execute all the services (server is developed with both spring and quarkus, so it has to be launched only once)
1. Server service (Spring)
   cd C:\Proyects\master\Modulo2\eoloPlannerCommunications\server
   mvn spring-boot:run
2. Server service (Quarkus)
   cd C:\Proyects\master\Modulo2\eoloPlannerCommunications\server-quarkus
   ./mvnw quarkus:dev
3. Planner service
   cd C:\Proyects\master\Modulo2\eoloPlannerCommunications\planner
   mvn spring-boot:run
4. Topo service
   cd C:\Proyects\master\Modulo2\eoloPlannerCommunications\toposervice
   mvn spring-boot:run
5. Weather service
   cd C:\Proyects\master\Modulo2\eoloPlannerCommunications\weatherservice
   ./mvnw quarkus:dev
