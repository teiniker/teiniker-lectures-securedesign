# Example: Script-Based Validation

The example can be built and deployed with Maven:
```
 $ mvn wildfly:deploy
```

URL: https://localhost:8443/Servlet-ClientSideControls-ScriptBasedValidation/

In this example, **Java Script is used to validate two input fields** on the client side:
```
<head>
    <script>
        function validateForm() 
        {
            var password1 = document.forms["change_password_form"]["password1"].value;
            var password2 = document.forms["change_password_form"]["password2"].value;
            if (password1 != password2) 
            {
                alert("The passwords do not match!");
                return false;
            }
            else
            {
                return true;
            }				
        }
    </script>
</head>
<body>
    <form name="change_password_form" method="POST" action="controller" onsubmit="return validateForm()">
    ...
    </form>
</body>
```

To **bypass the client-side validation**, we can:

* Use an **interception proxy** to modify the data in the HTTP request.

* Use the **browser's web development tools** (Inspector / Console) to modify the page content:
```
    > validateForm()
    > function validateForm() { return true; }
```
In the given example, we override the implementation of `validateForm()` to return always `true`. 
	
## References:
* Dafydd Stuttard, Marcus Pinto. **The Web Application Hacker’s Handbook**. Wiley, 2nd Edition, 2011
    * Chapter 5: Bypassing Client-Side Controls


*Egon Teiniker, 2019-2021, GPL v3.0*