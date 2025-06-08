
# DEVECES-API

 REST API that persist and managing device resources.

# **Requirements** <br>

For building and running the application you need:      
- JDK 21 <br>
- Maven 3.9 <br>
- clone the project https://github.com/AndreLuvetama/devices-api.git

Docker  

#Create Network    
docker create network devicesdb-network    

#Run container Postgres in version 16.3  
docker run --name devicesdb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=devicesdb -d --network devicesdb-network postgres:16.3

#Run Pgadmin 4  
docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin -d --network devicesdb-network dpage/pgadmin4

#Built the app    
docker build -t devicesapi .

#Run app   
docker run --name devicesapi -p 8089:8089 devicesapi

#End points for testing  
Creating new device  
http://localhost:8089/api/device

Geting a single device  
http://localhost:8089/api/device/{deviceId}

Fetch devices by brand.    
http://localhost:8089/api/device/brand/{brandName}     

Fetch devices by state.           
http://localhost:8089/api/device/state/{stateName}     

Delete a single device.          
http://localhost:8089/api/device/{deviceId}    





