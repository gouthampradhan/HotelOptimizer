# Hotel Room Optimizer

A simple spring-boot application for hotel room occupancy optimization

## Getting Started

Follow the instructions below to setup and build the project in our local machine.

### Prerequisites

This project is build using the below tools. Please ensure you have the below prerequisites satisfied

```
1. JDK 1.8 or above
2. Gradle 4.1 or above
```

### Open source libraries or plug-ins used

This project uses the below open source libraries, frameworks and plugins.

```
1. Spring Boot gradle plug-in 2.0.3.RELEASE
2. Guava:26.0-jre
3. Gson: 2.7
```


### Installing

Clone and run the project using below commands.
```
git clone https://github.com/gouthampradhan/HotelOptimizer.git

```
If you are using a IDE for example Intellij then, import project and choose build.gradle file. For more information
you can check out the link https://spring.io/guides/gs/intellij-idea/

### Testing

Use the below command to run automated test suite

```
./gradlew test

```

### Running

Use the below command to build the project and run from the project root directory.
The project will build and run in a embedded tomcat instance.

```
./gradlew bootRun

```

OR

Simply run Application.java file

If the build is successful then you should see something like this in the console
```

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.3.RELEASE)

2018-08-17 02:36:14.164  INFO 40219 --- [           main] hotel.Application                        : Starting Application on Gouthams-MBP.fritz.box with PID 40219 (/Users/gouthamvidyapradhan/Documents/workspace/hotelRoomOptimizer/build/classes/java/main started by gouthamvidyapradhan in /Users/gouthamvidyapradhan/Documents/workspace/hotelRoomOptimizer)
2018-08-17 02:36:14.169  INFO 40219 --- [           main] hotel.Application                        : No active profile set, falling back to default profiles: default
2018-08-17 02:36:14.275  INFO 40219 --- [           main] ConfigServletWebServerApplicationContext : Refreshing org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@5b0abc94: startup date [Fri Aug 17 02:36:14 CEST 2018]; root of context hierarchy
2018-08-17 02:36:16.783  INFO 40219 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
```


### How to use the application?

Use the sample URLs as shown below.

1. GET request with simple url parameters
By default the application uses the customer.json file in the resource folder as the source of input for customer price offer.
If you would like to test with different data set then please replace the json content of this file with a different data set.

```
http://localhost:8080/hotel/usageReport?premium=3&economy=3

Method Type: GET
Request Params:
premium=3
economy=3
priceMargin=100 (optional). If nothing is provided then by default priceMargin is considered as 100 Euros
```

Example use-case with optional priceMargin
```
http://localhost:8080/hotel/usageReport?premium=3&economy=3&priceMargin=100
```

You should see a json response as below
```
{
    "premium": {
        "count": 3,
        "amount": 738
    },
    "economy": {
        "count": 3,
        "amount": 167
    }
}

The reponse indicates the optimal split of premium and economy rooms, the total count and the total amount
```

2. POST request with request body
The application also provides POST method type to pass the full request in request body as shown below.

```
http://localhost:8080/hotel/usageReport

Method Type: POST
Headder: Content-Type : application/json
Body:
{
 "customerOffers" : [
  23,
  45,
  155,
  374,
  22,
  99,
  100,
  101,
  115,
  209
],
"premium" : 4,
"economy" : 2,
"priceMargin" : 100 (optional)
}
```

The response will remain same as the response from the above GET request