## "Hello" SOAP webservice cloud native app. with Spring Boot and Apache CXF

This example shows a SOAP webservice application built with Spring Boot and Apache CXF. The application is designed to be compliant with the 12-factor app. methodology and be easy to build, configure and deploy on various environments.

The application comes with scripts to deploy the application with Docker Compose, on Kubernetes or on a Azure Web App. A monitoring stack composed of Prometheus (metrics), Loki (logs) and Grafana (visualization) is preconfigured for these deployment methods.

### CLI usage

The commands used in this guide must be issued from the root directory of the project unless another path is specified.

### Configuration

You may wish to change the base URL used by the application. This setting can be configured by changing the app.service.name property in the src/main/resources/application.yml file. Default is "hellow".

### Build from sources

```
mvn clean package
```

### Build the containers

You can build all the containers using Docker Compose : 

```
docker-compose build
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

### Run the full app. with Kubernetes

1. Be sure that ingress is enabled on your cluster

2. Deploy the monitoring :

```
kubectl apply -f ./monitoring/prometheus-rbac.yaml
kubectl apply -f ./monitoring/monitoring.
```

3. Deploy the webservice app. :

```
kubectl apply -f ./app/webservices.yaml
```

4. Deploy the ingress resource :

```
kubectl apply -f ./network/ingress.yaml
```

Endpoints :

http://kube-ip/hellow/services : SOAP Webservice UDDI

http://kube-ip/hellow/admin/health : Health endpoint

http://kube-ip/grafana : Grafana

### Monitoring with Grafana

Grafana has a default account (user : admin - pwd : admin) on the first connection, Grafana will ask you to change the password.

Grafana comes with a predefined dashboard provided by Micrometer : https://micrometer.io/docs/registry/prometheus

Grafana is preconfigured to use Loki to get the logs : https://grafana.com/docs/grafana/latest/features/datasources/loki/


### Deploy on a Azure Web App with Azure CLI and ARM template

You need an Azure subscription and Azure CLI configured. You also need to create a resource group for the deployment. Move to the ./deployment/azure/web-app-arm-rg-docker directory.

You can tune the web app deployment parameters in the azuredeploy-webapp-docker.parameters.json file. By default a Free Tier webapp is created using a public docker image.

You can then issue the following command to create the azure resources in your resource group and deploy the application (here, the resource group is named default and has been pre-created) :

```
az group deployment create --resource-group default --template-file .\azuredeploy-webapp-docker.json --parameters @azuredeploy-webapp-docker.parameters.json
```

Once the deployment is complete, the CLI will output a big JSON document that describes the runtime state of your deployment template. The interresting part is the outputs field which provides you the URL to use to access your web app :

```
"outputs": {
      "uddi": {
        "type": "String",
        "value": "https://gbdevw-cloudnative-soap-ws.azurewebsites.net/hellow/services"
      },
      "url": {
        "type": "String",
        "value": "https://gbdevw-cloudnative-soap-ws.azurewebsites.net"
    }
}
```

NOTE : There is no monitoring with Grafana since Azure provides a default monitoring for your resources.

### About the 12-factor app. methodology

https://12factor.net/