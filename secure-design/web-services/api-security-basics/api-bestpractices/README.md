# API Design Best Practices

We should design the APIs first and implemented services later.

## Naming a Resource 

* Use **Nouns** and not verbs when **Naming a Resource in the Endpoint Path**. 
  HTTP methods use verbs.
  Therefore, it would be redundant to use verbs, and it would make our call look like an RPC endpoint.

_Example_: GitHub license API to retrieve the available licenses
```    
GET /licenses 
```
If we use verbs for this endpoint, then it will be `GET /getlicenses`.
It will still work, but semantically, it doesn't follow REST because it conveys the processing
instruction rather than state transfer. Therefore, we only use resource names.

GitHub's public API only offers read operations on the `licenses` resource, 
out of all the CRUD operations.

If we need to design the rest of the operations, their paths should look like following:
* `POST /licenses`: This is for creating a new license.

* `PUT /licenses/{license_key}`: This is for partial updates. Here, the path has a parameter
  (that is, an identifier), which makes the path dynamic. The license key is a unique value
  in the license collection and is being used as an identifier.
  Each license will have a unique key. This call should make the update in the given license.
  Note that `PATCH` could also be used.

* `DELETE /licenses/{license_key}`: This is for removing license information.
  We can try this with any license that we receive in the response of the `GET /licenses` call.

* Use the **Plural Form** for **Naming the Collection Resource** in the Endpoint Path.
  If we observe the GitHub `license` API, we might find that a resource name is given in the plural form.
  It is a good practice to use the plural form if the resource represents a collection.
  A `GET` call returns the collection of `licenses`.


## Status codes
Please follow the guidelines of HTTP methods and status codes.

All HTTP response status codes are separated into five classes or categories. 
The first digit of the status code defines the class of response, while the 
last two digits do not have any classifying or categorization role. 

There are five classes defined by the standard:
* **1xx informational response**: the request was received, continuing process.
* **2xx successful**: the request was successfully received, understood, and accepted.
* **3xx redirection**: further action needs to be taken in order to complete the request.
* **4xx client error**: the request contains bad syntax or cannot be fulfilled.
* **5xx server error**: the server failed to fulfil an apparently valid request.

_Examples:_ 
* **200 OK**: Document returned correctly.
* **302 Redirect**: Go someplace else to get the resource.
* **404 Not Found**: Can’t find this ressource.


## Use Hypermedia (HATEOAS)
Hypermedia (that is, links to other resources) makes the REST client's job easier.

There are two advantages if we provide explicit URL links in a response:

* The REST client is not required to construct the REST URLs on their own.

* Any upgrade in the endpoint path will be taken care of automatically and this makes
  upgrades easier for clients and developers.

_Example_: GitHub API\
If we look at the responses of GitHub APIs, we will also find other resource-related links with keys
that have a postfix of `url`.
```
$ curl -k https://api.github.com/users/teiniker
{
"login": "teiniker",
"id": 14111058,
"node_id": "MDQ6VXNlcjE0MTExMDU4",
"avatar_url": "https://avatars.githubusercontent.com/u/14111058?v=4",
"gravatar_id": "",
"url": "https://api.github.com/users/teiniker",
"html_url": "https://github.com/teiniker",
"followers_url": "https://api.github.com/users/teiniker/followers",
"following_url": "https://api.github.com/users/teiniker/following{/other_user}",
"gists_url": "https://api.github.com/users/teiniker/gists{/gist_id}",
"starred_url": "https://api.github.com/users/teiniker/starred{/owner}{/repo}",
"subscriptions_url": "https://api.github.com/users/teiniker/subscriptions",
"organizations_url": "https://api.github.com/users/teiniker/orgs",
"repos_url": "https://api.github.com/users/teiniker/repos",
"events_url": "https://api.github.com/users/teiniker/events{/privacy}",
"received_events_url": "https://api.github.com/users/teiniker/received_events",
"type": "User",
...
}
```

Additionally, we can find many URLs in the response body, such as `repos_url`.
We can follow that link to get a list of assoicated repositories.
```
$ curl -k https://api.github.com/users/teiniker/repos
[
...
{
"id": 68909117,
"node_id": "MDEwOlJlcG9zaXRvcnk2ODkwOTExNw==",
"name": "teiniker-lectures-secureservices",
"full_name": "teiniker/teiniker-lectures-secureservices",
"private": false,
"owner": {
"login": "teiniker",
"id": 14111058,
...
},
"html_url": "https://github.com/teiniker/teiniker-lectures-secureservices",
"description": null,
"fork": false,
"url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices",
"forks_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/forks",
"keys_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/keys{/key_id}",
"collaborators_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/collaborators{/collaborator}",
"teams_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/teams",
"hooks_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/hooks",
"issue_events_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/issues/events{/number}",
"events_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/events",
"assignees_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/assignees{/user}",
"branches_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/branches{/branch}",
"tags_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/tags",
...
"created_at": "2016-09-22T10:07:23Z",
"updated_at": "2021-11-03T07:57:15Z",
"pushed_at": "2021-11-03T07:57:12Z",
"git_url": "git://github.com/teiniker/teiniker-lectures-secureservices.git",
...
}
]
```

From there, we can navigate to a particular repository and from there to a list of its `tags`.
```
$ curl -k https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/tags
[
{
"name": "2020WS",
"zipball_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/zipball/refs/tags/2020WS",
"tarball_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/tarball/refs/tags/2020WS",
"commit": {
"sha": "4ff466f1819612134e9c78d51282e2c76b60012a",
"url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/commits/4ff466f1819612134e9c78d51282e2c76b60012a"
},
"node_id": "MDM6UmVmNjg5MDkxMTc6cmVmcy90YWdzLzIwMjBXUw=="
},
{
"name": "2016WS",
"zipball_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/zipball/refs/tags/2016WS",
"tarball_url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/tarball/refs/tags/2016WS",
"commit": {
"sha": "be1ebc8aeb920bacfe47205b8280d5617d1b5eef",
"url": "https://api.github.com/repos/teiniker/teiniker-lectures-secureservices/commits/be1ebc8aeb920bacfe47205b8280d5617d1b5eef"
},
"node_id": "MDM6UmVmNjg5MDkxMTc6cmVmcy90YWdzLzIwMTZXUw=="
}
]
```

REST clients can interact with RESTful web services without having any specific knowledge of how
to interact with the server. We just call any static REST API endpoint and we will receive the
dynamic links as a part of the response to interact further.

REST allows clients to dynamically navigate to the appropriate resource by traversing the links.
**REST clients can navigate to different resources in a similar way to how  humans look at a web
page and click on any link**.
The REST client makes use of these links to navigate.

_Example_: [SpringBoot HATEOAS](SpringBoot-HATEOAS)


## Always Version APIs
The versioning of APIs is key for future upgrades.
Over time, APIs keep changing, and we may have customers who are still using an older
version. Therefore, we need to support multiple versions of APIs.

There are different ways we can version your APIs:
* **Using headers**: The GitHub API uses this approach.
  We can add an `Accept` header that tells you which API version should serve the request.

_Example_:
```
  Accept: application/vnd.github.v3+json
```

This approach gives you the advantage of **setting the default version**.
If there is no `Accept` header, it should lead to the default version.
However, if a REST client that uses a versioning header is not changed after a recent upgrade
of APIs, it may lead to a functionality break.
Therefore, it is recommended that you use a versioning header.

* **Using an endpoint path**: We add a version in the endpoint path itself.
```
Example: https://demo.app/api/v1/persons
```

`v1` denotes that version 1 is being added to the path itself.
Here, **we cannot set default** versioning out of the box.
However, we can overcome this limitation by using other methods, such as request forwarding.
Clients always use the intended versions of the APIs in this approach.


## API Documentation
Documentation should be easily accessible and up to date with the latest implementation
with their respective versioning. It is always good to provide sample code and examples.
It makes the developer's integration job easier.

A **change log or a release log** should list all of the impacted libraries, and if some APIs are
deprecated, then replacement APIs or workarounds should be elaborated on inside the documentation.

_Example_: [SpringBoot Swagger](SpringBoot-Swagger)


## References

* Sourabh Sharma. **Modern API Development with Spring and Spring Boot: Design highly scalable and maintainable APIs with
  REST, gRPC, GraphQL, and the reactive paradigm**. Packt Publishing, 2021
  * Chapter: Best practices for designing REST APIs

* [List of HTTP status codes](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)

*Egon Teiniker, 2016-2023, GPL v3.0*
