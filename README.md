# Kotlin Multiplatform Blog 

This project is a full stack Kotlin multiplatform project for my own personal developer blog. The server/client is written using the Ktor framework, and everything is written entirely in Kotlin. 

## How to Run the Project

### Locally with IntelliJ

Before building the project, you must have the following environment variables setup in your project in order for the application to not crash on startup. 

* `PORT` - `8080` - needed for Heroku since they can change the port
* `JDBC_DATABSE_URL` - `jdbc:postgresql:articles?user=postgres` - url to local postgresql server that you are running 
* `JWT_SECRET` - secret key for the hashing function which issues JSON web tokens
* `SECRET_KEY` - secret key for the hashing function of passwords

You will also need to make sure that you have a PostgreSQL server running locally on your machine, otherwise you will find the project will not compile. 

### Locally with Heroku

To configure those environment variables with Heroku, at the project root folder add a `.env` file. Then add the same system variables into the file. 

```
PORT=8080
JDBC_DATABASE_URL=jdbc:postgresql:articles?user=postgres
JWT_SECRET=your_jwt_secret
SECRET_KEY=your_secret
```

Then run `heroku local` to actually start the staging process of the heroku server locally. 