# Web Application Access Control 

## Build and Run

We build and run the application like any Spring-Boot application:
```
$ mvn spring-boot:run 
```

## Accessing the Web Application

The running web application can be accessed by a browser or a command line tool:
```
URL: https://localhost:8443/index.html
```

## Implementation

This web application consists only of static HTML pages. These pages are arranged according to 
roles (`user` and `admin`).
```
$ tree src/main/resources/
src/main/resources/
├── application.properties
├── server.jks
├── static
│       ├── admin
│       │    └── configuration.html
│       ├── index.html
│       └── user
│            └── info.html
└── templates
    └── error.html
```

We can only access the static HTML files if we log in as a user with 
the right role.

Note that with a login a new `JSESSIONID` cookie will be created and with 
a logout, this cookie will be invaldated.


## Resources

*Egon Teiniker, 2017-2024, GPL v3.0*