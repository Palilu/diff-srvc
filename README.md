# Diff API

### Assumptions

The assumptions made where in how the following phrases were interpreted:

> Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints

I assumed that they payload needs to be a JSON that act as an envelope of a base64 encoded property called `data`.
I also assumed that the endpoints should not accept non Base64 characters within `data`.

> The provided data needs to be diff-ed

I assumed that the data should not be decoded for the diff purposes, other than a basic validation I assumed that I should not care about what the decoded data is.
If this were not the case, the way to get the byte arrays for each side in `DiffServiceImpl` would have had to be

`byte[] decodedBytes = Base64.getDecoder().decode(encodedString);`

### Notes

I'm using [Project Lombok](https://projectlombok.org/). In order to see the code properly, you might need to install a plugin for your IDE of choice that can be found [here](https://projectlombok.org/setup/overview). 

 ### Running Diff API

##### Pre-requisites
* Java 11.
* Maven.

1.1. Open terminal, go to the project's folder.

1.2. Run `mvn clean install`.

1.3. Run `mvn spring-boot:run`.

### Testing Diff API

2.a There's a postman collection to test the API [here](https://www.getpostman.com/collections/cd8c0a450f1333c1fd6d)

2.b.1 Alternatively, you can use [Diff API Swagger](http://localhost:8080/swagger-ui.html)

2.b.2 There are example requests under `/src/test/java/respurces`

2.c If you want to access the database you can go [here](http://localhost:8080/h2-console/) and log in using:
    
    JDBC_URL = "jdbc:h2:mem:testdb"
    User Name = "sa"
    Password = ""

### Running Sonar

##### Pre-requisites
* Docker

3.1. Run `docker pull sonarqube`

3.2. Run `docker run -d --name sonarqube -p 9000:9000 sonarqube`

3.3. In the project's folder, run `mvn clean test sonar:sonar`

3.4. Go to [Diff API Sonar](http://localhost:9000/dashboard?id=com.palilu%3Adiff)

### Nice to haves

In future versions I would like to add:

1. Replace in memory database.
2. Authentication.
3. Liquibase.
4. Layering. Different mvn modules for server, service, domain and model. 

