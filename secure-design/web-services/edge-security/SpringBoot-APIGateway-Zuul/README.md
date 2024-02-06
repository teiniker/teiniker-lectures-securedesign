# Zuul API Gateway

We use **Zuul** which is an open-source proxy server build by Netflix, acting as the entry point for
all of the company's backend streaming applications.

## Service and API Gateway Setup
First, we start the `ArticlesService` and then the API gateway.

```
$ cd SpringBoot-ArticleService
$ mvn spring-boot:run

$ cd SpringBoot-APIGateway
$ mvn spring-boot:run
```

## Accessing the Service via API Gateway

We send a request to the API gateway which forwards the request to the `ArticleService` implementation.

```
$ curl -i http://localhost:8080/api/articles/1

HTTP/1.1 200 
Date: Sat, 16 Oct 2021 09:12:15 GMT
Keep-Alive: timeout=60
Content-Type: application/json
Transfer-Encoding: chunked

{"id":1,"description":"Design Patterns","price":4295}
```

## API Gateway Routing and Filtering 

**Spring Cloud Netflix** includes an embedded **Zuul** proxy, which you can enable with the **@EnableZuulProxy** 
annotation. This will turn the Gateway application into a **reverse proxy** that forwards relevant calls to other 
services.

```Java
@EnableZuulProxy
@SpringBootApplication
public class GatewayApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
```

### Specify Routes

To forward requests from the Gateway application, we need to tell Zuul the routes that it should watch 
and the services to which to forward requests that are made to those routes. 

We specify routes by setting properties under `zuul.routes`. 
Each of our microservices can have an entry under `zuul.routes.NAME`.

We configure the API gateway to use port `8080` and to forward API requests to another service running on port `8080`.
All configurations can be done in the **application.properties** file.
```
zuul.routes.api.url=http://localhost:9090
zuul.sensitiveHeaders=
ribbon.eureka.enabled=false
server.port=8080
```

Spring Cloud Zuul automatically sets the path to the application name. 
In this sample, we set `zuul.routes.api.url` so that Zuul will proxy requests to `/api` to the 
URL `http://localhost:9090`.

Notice the second property in the `application.properties` file, Spring Cloud Netflix Zuul uses **Netflix’s Ribbon** 
to perform client-side **load balancing**. By default, Ribbon would use **Netflix Eureka** for **service discovery**. 
For this simple example, you can skip service discovery, so set `ribbon.eureka.enabled` to `false`. 
Since Ribbon now cannot use Eureka to look up services, we must specify a url for the ArticleService service.

Unfortunately, we can also bypass the API gateway and directly access the `ArticleService`.
```
$ curl -i http://localhost:8080/articles/1

HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sat, 16 Oct 2021 09:26:58 GMT

{"id":1,"description":"Design Patterns","price":4295}
```
Thus, more security configuration must take place (see: chapter Service-to-Service Communication).

### Add a Filter 

Zuul has four **standard filter types**:
* `pre` filters run before the request is routed.
* `route` filters can handle the actual routing of the request.
* `post` filters run after the request has been routed.
* `error` filters run if an error occurs in the course of handling the request.

_Example_: `pre` filter implementation
```Java
public class LoggingFilter extends ZuulFilter
{
    private static Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public String filterType()
    {
        return "pre";
    }

    @Override
    public int filterOrder()
    {
        return 1;
    }

    @Override
    public boolean shouldFilter()
    {
        return true;
    }

    @Override
    public Object run()
    {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        return null;
    }
}
```

Filter classes implement four methods:
* `filterType()`: Returns a String that stands for the type of the filter, this case, pre. 
* `filterOrder()`: Gives the order in which this filter is to be run, relative to other filters.
* `shouldFilter()`: Contains the logic that determines when to run this filter (this particular filter is always run).
* `run()`: Contains the functionality of the filter.

Zuul filters store request and state information in the `RequestContext`. 
We can use that to get at the `HttpServletRequest` and then log the HTTP method and URL of the request 
before it is sent on its way.

The `GatewayApplication` class is annotated with `@SpringBootApplication`, which includes (among others) 
the `@Configuration` annotation that tells Spring to look in a given class for `@Bean` definitions.

```Java
@EnableZuulProxy
@SpringBootApplication
public class GatewayApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public LoggingFilter loggingFilter()
    {
        return new LoggingFilter();
    }
}
```

When we access the ArticleService via the API Gateway, we get the following log output:
```
$ curl -i http://localhost:9090/api/articles

2021-11-05 11:08:13.064  INFO 300664 --- [nio-9090-exec-2] org.se.lab.LoggingFilter : GET request to http://localhost:9090/api/articles
```

As we can see, the request is logged by the API Gateway before it is handed on to the `ArticleService`.

## References
* [Routing and Filtering](https://spring.io/guides/gs/routing-and-filtering/)

* [Spring REST with a Zuul Proxy](https://www.baeldung.com/spring-rest-with-zuul-proxy)
* [Exploring the New Spring Cloud Gateway](https://www.baeldung.com/spring-cloud-gateway)

* Prabath Siriwardena, Nuwan Dias. **Microservices Security in Action**. Manning, 2020

*Egon Teiniker, 2016 - 2022, GPL v3.0*
