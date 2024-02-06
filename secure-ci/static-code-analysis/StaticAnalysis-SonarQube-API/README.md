# SonarQube: Web API


## Client: curl

$ curl -v -u student:student http://localhost:9000/api/metrics/search | json_pp -json_opt pretty,canonical

{
    "decimalScale" : 1,
    "description" : "Complexity average by function",
    "direction" : -1,
    "domain" : "Complexity",
    "hidden" : true,
    "id" : "AX3IulyJlGJkrmjQt4MZ",
    "key" : "function_complexity",
    "name" : "Complexity / Function",
    "qualitative" : true,
    "type" : "FLOAT"
},


$ curl -v -u student:student http://localhost:9000/api/components/search?qualifiers=TRK | json_pp -json_opt pretty,canonical
{
    "components" : [
        {
            "key" : "StaticAnalysis-Target",
            "name" : "StaticAnalysis-Target",
            "project" : "StaticAnalysis-Target",
            "qualifier" : "TRK"
        }
    ],
    "paging" : {
        "pageIndex" : 1,
        "pageSize" : 100,
        "total" : 1
    }
}


## Client: Python 

SonarQube Client with Python
https://python-sonarqube-api.readthedocs.io/en/latest/install.html

$ pip3 install --upgrade python-sonarqube-api


## References
