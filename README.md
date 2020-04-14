
# learn-words
Spring Boot, API REST
Meaning and definition of words

### Login

> POST
> http://localhost:8080/api/login

    {
    	"username":"admin",
    	"password":"admin"
    }

> or

    {
    	"username":"user",
    	"password":"user"
    }

### Api Users examples

***Inser User***
> POST (Authorization whit Bearer Token ADMIN)
> http://localhost:8080/api/v1/users

    {
        "name": "Jhon",
        "lastName": "Doe",
        "username": "jhondoe",
        "password":"jhondoe",
        "email": "jhondoe@gmail.com",
        "status":"ENABLED"
    }

***Update User***
> PUT (Authorization whit Bearer Token ADMIN)
> http://localhost:8080/api/v1/users

    {
        "idUser": "nº_Id",
        "name": "new_name",
        "lastName": "new_last_name",
        "username": "new_rsername",
        "password":"new_password",
        "email": "new_email@gmail.com",
        "status":"ENABLED"
    }

***Delete User***
> DELETE (Authorization whit Bearer Token ADMIN)
> http://localhost:8080/api/v1/users/{id}

***All Users***
> GET (whit Bearer Token USER authorization)
> http://localhost:8080/api/v1/users

***User by id***
> GET (whit Bearer Token USER authorization)
> http://localhost:8080/api/v1/users/{id}

### Swagger
http://localhost:8080/swagger-ui.html

# Access:
http://localhost:8080/

