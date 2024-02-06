# OpenAPI Specification 

OpenAPI Specification (formerly known as Swagger Specification) is an open-source format 
for **describing and documenting APIs**.

The latest version of OpenAPI is 3.0. OpenAPI definitions can be written in **JSON** or **YAML**.

A simple OpenAPI 3.0 specification looks like this:
```
openapi: 3.0.0
info:
    version: 1.0.0
    title: Sample API
    description: A sample API to illustrate OpenAPI concepts
    paths:
    /list:
        get:
            description: Returns a list of stuff              
        responses:
            '200':
            description: Successful response
```

## Meta information

In the meta information section we can find the API title, version, server URL and other descriptive information.

```
openapi: 3.0.0
info:
  version: 1.0.0
  title: Simple Artist API
  description: A simple API to illustrate OpenAPI concepts

servers:
  - url: https://example.io/v1

# Basic authentication
components:
  securitySchemes:
    BasicAuth:
      type: http
      scheme: basic
security:
  - BasicAuth: []
```

Each API definition starts with the **version of the OpenAPI Specification** that this definition uses.
The **info** object contains the **API title** and **version**, which are required, and an optional description.

The **servers** array specifies one or more server URLs for API calls. 
The API endpoint paths are appended to the server URL.

We also secure the API using **Basic authentication**, so that only authorized users can consume the API.
OpenAPI uses the term security scheme for [authentication and authorization](https://swagger.io/docs/specification/authentication/) 
schemes. 
OpenAPI 3.0 lets you describe APIs protected using the following security schemes:
* HTTP authentication schemes (they use the Authorization header):
  * Basic
  * Bearer 
  * other HTTP schemes as defined by RFC 7235 and HTTP Authentication Scheme Registry
* API keys in headers, query string or cookies
  * Cookie authentication
* OAuth 2
* OpenID Connect Discovery

## Path Items

The path items are the **endpoints of our API** under which we can specify HTTP verbs for manipulating 
the resources in the desired manner. These endpoints are relative to the server URL.
```
paths:
  /artists:
    get:
      description: Returns a list of artists
      parameters:
        - name: limit
          in: query
          description: Limits the number of items on a page
          schema:
            type: integer
        - name: offset
          in: query
          description: Specifies the page number of the artists to be displayed
          schema:
            type: integer      
      responses:
        '200':
          description: Successfully returned a list of artists
          content:
            application/json:
              schema:
                type: array
                items:
                  type: object
                  required:
                    - username
                  properties:
                    artist_name:
                      type: string
                    artist_genre:
                      type: string
                    albums_recorded:
                      type: integer
                    username:
                      type: string      
        '400':
          description: Invalid request
          content:
            application/json:
              schema:
                type: object
                properties:   
                  message:
                    type: string
    post:
      description: Lets a user post a new artist
      requestBody:
        required: true
        content:
          application/json:
            schema:
              type: object 
              required:
                - username
              properties:
                artist_name:
                  type: string
                artist_genre:
                  type: string
                albums_recorded:
                  type: integer
                username:
                  type: string

      responses:
        '200':
          description: Successfully created a new artist

        '400':
          description: Invalid request
          content:
            application/json:
              schema:
                type: object
                properties:   
                  message:
                    type: string
```

In the given example, we define the `/artists` endpoint and the `GET` method for this endpoint. 
A client will use `GET https://example.io/v1/artists` to get a list of artists.

### Query Parameters 
Query parameters are the most common type of parameters.
They appear at the end of a URL following a question mark.
Query parameters are optional and non unique, so they can be specified multiple times in the URL.
In the example, it would make sense to let the client limit the information required instead of returning
the entire list of artists from the database:
```
GET https://example.io/v1/artists?limit=20&offset=3
```

### Responses
Every response would need at least one HTTP status code to describe the kind of responses a consumer is likely 
to expect. The description gives details on what the responses of the API would be.
In the example, we have specified **200**, which is a successful client request, while **400** is an 
unsuccessful request.
A **successful response** will return the artist `name`, `genre`, `username` and `albums` recorded. 
An **unsuccessful request** is described under the 400 HTTP code, with a corresponding **error message** detailing 
why the response is invalid.

### Request Body
`POST`, `PUT` and `PATCH` requests typically contain the **request body**. 
The request body is defined by using the `requestBody` object. 
In the example, there is the ability for a user to post an artist to our database.

### Path Parameters
The path parameters can be used to isolate a specific component of the data that the client is working with, 
for example, `https://example.io/v1/artists/{username}`. 
Path parameters are part of the hierarchical component of the URL, and are thus stacked sequentially.
```
/artists/{username}:
  get:
    description: Obtain information about an artist from his or her unique username
    parameters:
      - name: username
        in: path
        required: true
        schema:
        type: string
    responses:
    #...
```
Here, we have specified the `username` as a path parameter. 
The thing to note is that path parameters have to have a `true` property set to the `required parameter`, 
for the spec to be valid.

## Reusable Components

The OpenAPI Specification allows reusable components that can be used across multiple endpoints in the same API. 
These components are defined in the **global components section** and then are referenced in individual endpoints. 

The Specification defines various types of reusable components:
* Schemas (data models)
* Parameters
* Request bodies
* Responses
* Response headers
* Examples
* Links
* Callbacks

### Schemas

The schemas subsection of the global components section can contain various data models consumed and returned by the API. 
Here is how we can use components to store the schema for an `HTTP 200 OK` response.
```
    post:
      description: Lets a user post a new artist
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Artist'
    #...
              
components:
  schemas:
    Artist:
      type: object
      required:
        - username
      properties:
        artist_name:
          type: string
        artist_genre:
            type: string
        albums_recorded:
            type: integer
        username:
            type: string
```

More details can be found in the [OpenAPI Specification](https://swagger.io/specification/).


## References
* [YouTube: What is OpenAPI?](https://youtu.be/InE6Odx--xo)
* [OpenAPI 3.0 Tutorial](https://support.smartbear.com/swaggerhub/docs/tutorials/openapi-3-tutorial.html)
* [Authentication and Authorization](https://swagger.io/docs/specification/authentication/)
* [OpenAPI Specification](https://swagger.io/specification/)

*Egon Teiniker, 2016-2022, GPL v3.0*
