# OWASP Juice Shop

OWASP Juice Shop is an insecure web application. 

It can be used in security trainings, awareness demos, CTFs and as 
a guinea pig for security tools. Juice Shop encompasses vulnerabilities 
from the entire **OWASP Top Ten** along with many other security flaws 
found in real-world applications.


## Setup 

For a fast setup, we use Docker:

```bash
# Make sure the Docker Engine is running 
$ sudo systemctl start docker
$ sudo systemctl status docker
$ docker version

# Download Docker image 
$ docker pull bkimminich/juice-shop

# Run Docker container
$ docker run --rm -p 127.0.0.1:3000:3000 bkimminich/juice-shop
```

## Penetration Testing

### Base URL + Login to Get a Token  

```bash
export BASE="http://localhost:3000"

$ curl -sS -X POST "$BASE/rest/user/login" -H "Content-Type: application/json" -d '{"email":"admin@juice-sh.op","password":"admin123"}'

{"authentication":{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdGF0dXMiOiJzdWNjZXNzIiwiZGF0YSI6eyJpZCI6MSwidXNlcm5hbWUiOiIiLCJlbWFpbCI6ImFkbWluQGp1aWNlLXNoLm9wIiwicGFzc3dvcmQiOiIwMTkyMDIzYTdiYmQ3MzI1MDUxNmYwNjlkZjE4YjUwMCIsInJvbGUiOiJhZG1pbiIsImRlbHV4ZVRva2VuIjoiIiwibGFzdExvZ2luSXAiOiIiLCJwcm9maWxlSW1hZ2UiOiJhc3NldHMvcHVibGljL2ltYWdlcy91cGxvYWRzL2RlZmF1bHRBZG1pbi5wbmciLCJ0b3RwU2VjcmV0IjoiIiwiaXNBY3RpdmUiOnRydWUsImNyZWF0ZWRBdCI6IjIwMjYtMDEtMjMgMDc6MzQ6MDguNDE0ICswMDowMCIsInVwZGF0ZWRBdCI6IjIwMjYtMDEtMjMgMDc6MzQ6MDguNDE0ICswMDowMCIsImRlbGV0ZWRBdCI6bnVsbH0sImlhdCI6MTc2OTE2MDI3OH0.qe8J9C4c69srbhC1CjX1O6M0VboArB3oSB-VAqZJMFnCJE3vnH88wnIwyWOBvvWhb7T2uVpJ-EDe1H28YnGZ8Je2nkd-35GurPczfhF_c0dP5ZZGaPzAy-P04-akwzCkyidqgHStM5bkN-AVizJGc83fuMhEw-p7rvekR_hBI4c","bid":1,"umail":"admin@juice-sh.op"}}

$ export TOKEN="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdGF0dXMiOiJzdWNjZXNzIiwiZGF0YSI6eyJpZCI6MSwidXNlcm5hbWUiOiIiLCJlbWFpbCI6ImFkbWluQGp1aWNlLXNoLm9wIiwicGFzc3dvcmQiOiIwMTkyMDIzYTdiYmQ3MzI1MDUxNmYwNjlkZjE4YjUwMCIsInJvbGUiOiJhZG1pbiIsImRlbHV4ZVRva2VuIjoiIiwibGFzdExvZ2luSXAiOiIiLCJwcm9maWxlSW1hZ2UiOiJhc3NldHMvcHVibGljL2ltYWdlcy91cGxvYWRzL2RlZmF1bHRBZG1pbi5wbmciLCJ0b3RwU2VjcmV0IjoiIiwiaXNBY3RpdmUiOnRydWUsImNyZWF0ZWRBdCI6IjIwMjYtMDEtMjMgMDc6MzQ6MDguNDE0ICswMDowMCIsInVwZGF0ZWRBdCI6IjIwMjYtMDEtMjMgMDc6MzQ6MDguNDE0ICswMDowMCIsImRlbGV0ZWRBdCI6bnVsbH0sImlhdCI6MTc2OTE2MDI3OH0.qe8J9C4c69srbhC1CjX1O6M0VboArB3oSB-VAqZJMFnCJE3vnH88wnIwyWOBvvWhb7T2uVpJ-EDe1H28YnGZ8Je2nkd-35GurPczfhF_c0dP5ZZGaPzAy-P04-akwzCkyidqgHStM5bkN-AVizJGc83fuMhEw-p7rvekR_hBI4c"
```

Here a **JSON Web Token (JWT)** based authentication is used.

1. Login request: We authenticate by sending credentials to: `POST /rest/user/login`

2. Login response (JWT issued): A successful response includes a JWT, usually in a JSON structure.
    - The token is signed (HMAC SHA-256 in most builds)

3. Using the token (Bearer authentication):
    The JWT is sent with each API request using the HTTP Authorization header: `Authorization: Bearer <JWT>`    

    This is Bearer Token Authentication (as defined in RFC 6750).

Common Default Credentials (Demo Accounts) are:

| Role  | Username (Email)    | Password   |
| ----- | ------------------- | ---------- |
| Admin | `admin@juice-sh.op` | `admin123` |
| User  | `user@juice-sh.op`  | `password123` |


### OWASP API1:2023 - Broken Object Level Authorization (BOLA)






## References

* [OWASP Juice Shop](https://owasp.org/www-project-juice-shop/)
* [GitHub: OWASP Juice Shop](https://github.com/juice-shop/juice-shop)
* [Pwning OWASP Juice Shop](https://pwning.owasp-juice.shop/companion-guide/latest/)

*Egon Teiniker, 2016-2026, GPL v3.0*