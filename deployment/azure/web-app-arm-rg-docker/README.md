## Deploy on a Azure Web App with Azure CLI and ARM template

### Prerequisites

You need an Azure subscription and Azure CLI configured. You also need to create a resource group for the deployment. Move to the ./deployment/azure/web-app-arm-rg-docker directory to issue commands.

### Deploy on Azure

You can tune the web app deployment parameters in the azuredeploy-webapp-docker.parameters.json file. By default a Free Tier webapp is created. As such, a public docker image is used.

You can then issue the following command to create the azure resources in your resource group and deploy the application. Here, the resource group is named default and has been pre-created, replace accordingly :

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
