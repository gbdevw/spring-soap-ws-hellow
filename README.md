##  Simple "Hello" SOAP webservice cloud native app. with Spring Boot and Apache CXF

This example shows a SOAP webservice application built with Spring Boot and Apache CXF. The application is designed to be compliant with the 12-factor app. methodology and be easy to build, configure and deploy on various environments.

The application comes with scripts to deploy the application with Docker Compose or on Kubernetes. A monitoring stack composed of Prometheus (metrics), Loki (logs) and Grafana (visualization) is preconfigured for these deployment methods.

### CLI usage

The commands used in this guide must be issued from the root directory of the project (that contains the pom.xml and docker-compose.yml).

### Build from sources

mvn clean package

### Build the containers

You can build all the containers using Docker Compose : docker-compose build

### Run the full app. with Docker Compose

You can start all the containers on your Docker machine (webservice app. + monitoring) using Docker Compose :

docker-compose up -d

This command stops and removes all the containers and volumes :

docker-compose down -v

Endpoints :

http://<docker-ip>:8080/hellow/services : SOAP Webservice UDDI
http://<docker-ip>:8080/hellow/admin/health : Health endpoint
http://<docker-ip>:3000 : Grafana

### Run the full app. with Kubernetes

1. Be sure that ingress is enabled on your cluster

2. Deploy the monitoring :

kubectl apply -f ./monitoring/prometheus-rbac.yaml
kubectl apply -f ./monitoring/monitoring.

3. Deploy the webservice app. :

kubectl apply -f ./app/webservices.yaml

4. Deploy the ingress resource :

kubectl apply -f ./network/ingress.yaml

Endpoints :

http://<k8s-ip>/hellow/services : SOAP Webservice UDDI
http://<k8s-ip>/hellow/admin/health : Health endpoint
http://<k8s-ip>/grafana : Grafana

### Monitoring with Grafana

Grafana has a default account (user : admin - pwd : admin) on the first connection, Grafana will ask you to change the password.

Grafana comes with a predefined dashboard provided by Micrometer : https://micrometer.io/docs/registry/prometheus

Grafana is preconfigured to use Loki to get the logs : https://grafana.com/docs/grafana/latest/features/datasources/loki/
