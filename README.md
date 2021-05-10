# Coding Exercise
For privacy, I am omitting any references to myself or the organization.

## Development

### Running locally

Recommended: using Docker

```
docker build -t st/coding-exercise .
docker run -p 8080:8080 -e GITHUB_TOKEN='yourOAuthToken' st/coding-exercise
```

The `GITHUB_TOKEN` environment variable is optional. If you don't include it, the client will still
have unauthenticated access to the Github API.

You can also run with Gradle and Java 14 (probably Java 11 and up):

```
./gradlew build
./gradlew bootRun
./gradlew test
```

### Usage

The app runs on port `8080` by default, and you can access its OpenAPI docs at `localhost:8080/docs`. There
you will see documentation for the single endpoint, `GET /github-users/{username}`. For convenience,
there is also a [Postman collection with the endpoint](/docs/postman/Exercise%20API.postman_collection.json).

## Design choices

### Language & Framework

I used Kotlin with Spring Boot. Kotlin and the JVM provide access to a vast
array of libraries and Spring Boot is highly configurable/extensible while
also providing everything I need for a web server out of the box. I used the [Spring Initializr](https://start.spring.io/) 
to create the project.

### Project structure

The project uses a pretty standard Spring Boot setup with a `Controller` for
 handling API requests and a `Service` for performing application logic,
 along with some config and data classes. 
 
I intentionally didn't create different packages here. We could organize the project
by domain or by type of Spring component.
The right choice would only be clear as the project grows. It's my philosophy to 
defer these choices until they actually need to be made, because my understanding/vision
of the project may change often in the early stages. If things can be
simple and straightforward for now, I will keep it that way to avoid 
making uneducated guesses.

### Github client

I used a Github SDK for a few reasons. First, that's what I would do if this code were going
to production. It's a widely-used, robust client and it doesn't make a lot of
sense to reinvent the wheel here. I also wanted to be faithful to the recommended
assessment timeframe, and this let me focus on enhancing other parts 
of the code. If I were to implement this myself, I would use the Spring REST Client, `RestTemplate`
to query the API. Unless there is a future use-case for it, I would probably not deserialize the responses
into intermediate data classes. Instead, I would directly access the json trees of the responses
to create the final `GithubUser` object we want.

### Data model

I called the object representing the response a `GithubUser`. In this model
I consider the repositories to simply be a property of the `GithubUser`.
If I knew more about how this data was being used I would consider a different
name because it obviously conflicts with the model in the Github API, which 
distinguishes between a user and their repos.

### Next steps

Given more time I would want to do the following:
 - enhancing the error responses beyond the default spring formatting
 - logging
 - integration tests using `WireMock`
 - spring will correctly respond with 500 but I would want to handle some error cases differently such as GH being down, bad token, etc
 - authenticate as a Github app instead of just with a personal OAuth token

Thank you for the time spent reviewing this!