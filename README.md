# EventCatalogue

maven -> clean, package

docker build -t ime .

docker run -d --name climb-db -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=climb-db -p 5432:5432 postgres:13
java -jar ./api/target/api-1.0.0-SNAPSHOT.jar

docker run -d -p 8080:8080 (--network= ) ime
(docker run -d -p 8080:8080 --network=event-network -e CONFIG=krniki jstrem/event-image)
docker network create ime (bbffe6e86ca18704a31caf2edac5089ad04f06397e2738c0a69c16189e23f5e3 event-network)
--network ime (pri bazi in image)

consul -> consul agent -dev
-> na localhost:8500



## Azure
// install Azure CLI
open cmd
// az aks install-cli
az login
az account set --subscription 93e7bea7-07f6-4624-a530-259ee9dadfda
az aks get-credentials --resource-group climbapp_group_1638641310048 --name climbapp
kubectl get nodes
// cd to your kubernetes deployment.yaml (C:\Users\Jana\Documents\fax\mag\1.letnik\RSO\EventCatalogue\EventCatalogue\k8s)
// kubectl create -f event-catalogue-deployment.yaml
kubectl apply -f event-catalogue-deployment.yaml
kubectl get services
EXTERNAL-IP:[PORT]/[REST path] in browser
kubectl get services
kubectl get deployments
kubectl get pods
kubectl logs event-catalogue-deployment-68744cc4fc-????
kubectl delete pod [image name]