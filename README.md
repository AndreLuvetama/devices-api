
# DEVECES-API

 REST API that persist and managing device resources.

# **Requirements** <br>

For building and running the application you need:      
- JDK 21 <br>
- Maven 3.9 <br>
- Database Postgress
- clone the project https://github.com/AndreLuvetama/devices-api.git

Docker  
- Docker compose up
- Configure database in path http://localhost:15432/browser/

#Endpoints for testing  
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

Update Partial/Full device    
http://localhost:8089/api/device/{deviceId}





