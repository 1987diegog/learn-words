# learn-words
Spring Boot, API REST

Meaning and definition of words

POST
http://localhost:8080/api/login
{
	"username":"admin",
	"password":"admin"
}
or
{
	"username":"user",
	"password":"user"
}

POST (whit Bearer Token ADMIN authorization)
http://localhost:8080/api/v1/users
{
    "name": "Jhon",
    "lastName": "Doe",
    "username": "jhondoe",
    "password":"jhondoe",
    "email": "jhondoe@gmail.com",
    "status":"ENABLED"
}


PUT (whit Bearer Token ADMIN authorization)
http://localhost:8080/api/v1/users
{
    "idUser": "XYZ"
    "name": "newName",
    "lastName": "newLastName",
    "username": "newUsername",
    "password":"newPassword",
    "email": "newEmail@gmail.com",
    "status":"ENABLED"
}

DELETE (whit Bearer Token ADMIN authorization)
http://localhost:8080/api/v1/users/{id}

GET (whit Bearer Token USER authorization)
http://localhost:8080/api/v1/users
http://localhost:8080/api/v1/users/3



