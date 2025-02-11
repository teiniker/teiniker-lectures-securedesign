# Example: Spring Boot MVC

## Setup and Run
```
$ mvn spring-boot:run 
```

```
$ mvn clean package
$ java -jar target/springboot-mvc-0.0.1-SNAPSHOT.jar
```

## Access the Web Application
```
URL: https://localhost:8443/index.html

$ curl -i -k https://localhost:8443/index.html
$ curl -i -k -X POST -d 'word=cat&language=Deutsch&action=Translate' https://localhost:8443/translator
```

## Spring MVC

The term Spring MVC is used for many things:

* Implementing the **Model-View-Controller pattern** in a Spring application.

* Creating an application specifically using Spring MVC component concepts like the **Model interface**, 
  **@Controller classes**, and **view technologies**.

Spring MVC can be considered both an approach and an implementation.

### View Technologies
**Template engines** provide a way for a so-called server-side application to generate the final pages that 
will be displayed and executed in the end user’s browser. 
These view technologies differ in approaches but generally provide the following:
* A **template language** and/or collection of tags that define inputs used by the template engine to produce the expected outcome
* A **view resolver** that determines the view/template to use to fulfill a requested resource.

**Thymeleaf** is perhaps the most widely used of these for several reasons and provides excellent support for 
both Spring MVC and Spring WebFlux applications. 

Thymeleaf uses **natural templates**: files that incorporate code elements but that can be opened and viewed directly 
(and correctly) in any standard web browser.

**Plain HTML pages** requires no template support.
By default, a Spring Boot application will look for static pages in the classpath under **static and public directories**. 
To properly place them there during build, place them within one of those two directories under **src/main/resources** 
within the project.


### Model and Controller
In Spring’s approach to building web sites, HTTP requests are handled by a controller. 
You can easily identify the controller by the **@Controller** annotation.

_Example_: Controller class
```Java
@Controller
public class TranslatorController
{
    @Autowired
    private TranslatorService service;

    @PostMapping("/translator")
    public String translate(@RequestParam(name="language") String language, 
                            @RequestParam(name="word") String word, 
                            Model model)
    {
        String translation = service.translate(word);
        model.addAttribute("word", word);
        model.addAttribute("translation", translation);
        return "translation";
    }
}
```

Within an controler class, each method annotated with **@RequestMapping** or one of its specialized aliases like 
**@PostMapping** will return a String value that corresponds to the name of a template file minus its extension.

**@RequestParam** binds the value of the HTTP query string parameter name into the name parameter of a controller 
method.

Application data can be added to a **Model** object, to making it accessible to the view template.


## Resources
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)

* [Spring RequestMapping](https://www.baeldung.com/spring-requestmapping)

* Mark Heckler. **Spring Boot: Up and Running**. O'Reilly Media, 2021

*Egon Teiniker, 2017-2025, GPL v3.0*
