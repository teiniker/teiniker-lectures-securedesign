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





## References

* [OWASP Juice Shop](https://owasp.org/www-project-juice-shop/)
* [GitHub: OWASP Juice Shop](https://github.com/juice-shop/juice-shop)
* [Pwning OWASP Juice Shop](https://pwning.owasp-juice.shop/companion-guide/latest/)

*Egon Teiniker, 2016-2026, GPL v3.0*