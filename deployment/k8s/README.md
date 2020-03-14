## Run the full app. with Kubernetes

### CLI usage

The commands used in this guide must be issued from the root directory of the project unless another path is specified.

### Prerequisites

You will need a Kubernetes cluster and kubectl configured to use it with the appropriate rights. Ingress must be enabled on your cluster

### Deploy on Kubernetes

1. Be sure that ingress is enabled on your cluster

2. Deploy the monitoring stack :

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
