## "Hello" SOAP webservice cloud native app. with Spring Boot and Apache CXF

This example shows a SOAP webservice application built with Spring Boot and Apache CXF (JAVA 8). The application is designed to be compliant with the [12-factor app. methodology](https://12factor.net/) and be easy to build, configure and deploy on various environments.

The application comes with scripts to deploy the application with Docker Compose, on Kubernetes or on a Azure Web App. A monitoring stack composed of Prometheus (metrics), Loki (logs) and Grafana (visualization) is preconfigured for these deployment methods.

### CLI usage

The commands used in this guide must be issued from the root directory of the project unless another path is specified.

### Development

Guidelines about development can be found [here](src/main/README.md).

### Configuration

You may wish to change the base URL used by the application. This setting can be configured by changing the app.service.name property in the src/main/resources/application.yml file. Default is "hellow".

You can also change configuration at runtime using [externalized configuration](https://docs.spring.io/spring-boot/docs/1.0.1.RELEASE/reference/html/boot-features-external-config.html).

### Build from sources with Maven

```
mvn clean package
```

### Run the java application

```
java -jar target/spring-soap-ws-hellow-1.0.0.jar
```

### Build the containers with Docker Compose

You can build all the containers using Docker Compose : 

```
docker-compose build
```

The .env file defines some env. variables that can be used to customize image tags, container repository used, etc... All images will be pushed in the same container repository and will be tagged this way : 

<DOCKERHUB_REPO>:<BASE_TAG>-<IMAGES_VERSION>

You can push all container images using Docker Compose :

```
docker-compose push
```

### Run the full app. with Docker Compose

You can start all the containers on your Docker machine (webservice app. + monitoring) using Docker Compose :

```
docker-compose up -d
```

This command stops and removes all the containers and volumes :

```
docker-compose down -v
```

Endpoints :

http://docker-ip:8080/hellow/services : SOAP Webservice UDDI

http://docker-ip:8080/hellow/admin/health : Health endpoint

http://docker-ip:3000 : Grafana

### Deploy the app.

- [Using Kubernetes](deployment/k8s/README.md)
- [Using an Azure Web App](deployment/azure/web-app-arm-rg-docker/README.md)

### Monitoring with Grafana

Grafana has a default account (user : admin - pwd : admin). On the first connection, Grafana will ask you to change the password.

Grafana comes with a [predefined dashboard](https://micrometer.io/docs/registry/prometheus) provided by Micrometer to monitor your SOAP applications.

Grafana is preconfigured to [use Loki to get the logs](https://grafana.com/docs/grafana/latest/features/datasources/loki/).

### Review of the 12-factor app. methodology

1. **Code base**

The application has only one code base for its logic. You can then configure many ways to deploy it without modifying any source code.

2. **Dependencies**

Dependencies are declared in the pom.xml file and are managed by Maven. All dependencies are available and maintened in the Maven Central Repository.

3. **Configuration**

Spring already manage [externalized configuration](https://docs.spring.io/spring-boot/docs/1.0.1.RELEASE/reference/html/boot-features-external-config.html) in a way that is compliant with the 12-factor app. methodology.

4. **Backing Services**

This one is not applicable in the current example. However, Spring has a great support to manage backing service in a way that is compliant with the 12-factor app. methodology.

5. **Build, release, run**

Maven is used for the build and test stages. Docker is used to build the container images that will be the building blocks of our deployment. Various technologies and providers can be used to run the container.

6. **Processes**

This one is not applicable in our example but here are a few guidelines to comply to it : 

- Always think that there will be many instances of your application that will run and share the workload. Design your process accordingly.

- Always think that a process can be interrupted or fail. Handle failures/interruption gracefully. Every process should be able to restart without any harm for your system or your data.

- Do not share state among processes or threads in an instance of the application. Instead, use a backing service that can manage the state of your application instances (a database, a distributed data store, a blockchain, ...).

- Do not try to make your processes or thread communicate directly. Instead, use a backing service that can manage that communication (A queue, a publish/subscribe messaging system, ...).

7. **Port Binding**

The use of Spring Boot allows you to start the application like a classic Java application. The application binds to a port and is directly usable through the network.

8. **Concurrency**

If you apply the guidelines in the 6. Processes section, you should be fine with this one. Your application should be able to handle more workload by scaling the number of application instances and by sharing the workload among them.

9. **Disposability**

Spring already provide support to handle a SIGTERM and shutdown gracefully the application. For the fast startup, it all depends on you.

10. **Dev/Prod parity**

By using container deployment and Docker Compose, you allow your devs to easly spin-up the whole application on their laptop and run their integration tests against live services that are the same as those in prod (not against in-memory databases, etc...).

You can then deploy the application in your production environment with no or minimal divergence.

11. **Logs**

In the code, logs are managed regardless of their destination, routing, etc... using a framework like SLF4J. Logs should always be written to stdout. Then, they are redirected to a log appender that can react to the logs, manage them, display them, etc...

Our example is not absolutly compliant since our agent (promtail) fetch logs from a log file to route them to Loki.

12. **Admin processes**

This one is not applicable to our example. However, Spring can  run scripts when the application starts and offers various endpoints that can be used to shutdown the app.

### Conclusion

This example has shown you how to make a simple SOAP Webservice application with a modern framework like Spring. You can reuse all your Spring skills to enhance your application.

This example is also suitable to build and deploy micro-service SOAP webservice application. Remember : one wsdl, one service, one interface, one request message and one response message containing a single element for each operation. Mind your SOLID principles.

This application can be deployed on various cloud environment without the need to modify or rebuild the sources. The application can be easily reconfigured using environment variables. You can then script your deployment and your CI/CD pipeline. 

Enjoy !
