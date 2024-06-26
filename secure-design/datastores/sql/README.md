# SQL Processing

The means of accessing information within the database is 
**Structured Query Language (SQL)**. SQL can be used to read, update, 
add, and delete information held within the database.

SQL is an **interpreted language**, and applications commonly construct 
SQL statements that **incorporate user-supplied data**.

SQL injection can enable an anonymous attacker to **read and modify all 
data** stored within the database, and even **take full control of the 
server** on which the database is running.

## Common Design Flaws in SQL Processing

A serious problem is the usage of **dynamically generated SQL statements**:
```SQL
final String SQL = "SELECT id FROM User WHERE username ='" 
                    + username + "' AND password = '" 
                    + password + "'";
```

The code that the SQL interpreter processes is a **mix of the instructions** 
written by the programmer and the **data supplied by the user**.

An attacker can supply crafted input that breaks out of the data context.

_Example_: SQL Injection Attack: `username = "admin'--"` 
```SQL
SELECT id FROM User 
WHERE username ='admin'--' AND password = ''
```

The result is that part of this input gets interpreted as program 
instructions, which are executed in the same way as if they had been 
written by the original programmer.

## Securing SQL Processing

SQL injection is in general one of the easier vulnerabilities to prevent.

**Partially Effective Measures**:
* A common approach to preventing attacks is to **escape any single quotation 
    marks** within user input by doubling them.
* Another countermeasure that is often cited is the use of **stored procedures** 
    for all database access.

**Parameterized Queries** (**Prepared Statements**):
The construction of a SQL statement containing user input is performed in two steps:
* The application specifies the **query’s structure**, leaving **placeholders** 
    for each item of user input:
    ```SQL
    String SQL = "SELECT id FROM User WHERE username=? AND password=?";
    ```
* The application specifies the **contents of each placeholder**:    
    ```SQL
    pstmt.setString(1, username);
    pstmt.setString(2, password);
    ```

Using parameterized queries, we need to keep in mind several important provisos:
* They should be used for **every database query**.
* **Every item of data** inserted into the query should be properly parameterized 
    (sometimes, one or two items are concatenated directly into the string used 
    to specify the query structure).
* If parameterized queries can't be applied, the best approach is to use a 
    **white list of known good values** and to reject any input that does not 
    match an item on this list.

**Defense in Depth**:
In the context of attacks against back-end databases, three layers of further 
defense can be employed:
* The application should use the **lowest possible level of privileges** when 
    accessing the database. The application does not need DBA level permissions.
    In security-critical situations, the application may employ a different 
    database account for performing different actions.
* Wherever possible, **unnecessary default functions should be removed** or 
    disabled from enterprise databases.
* All **vendor-issued security patches** should be evaluated, tested, and 
    applied in a timely way to fix known vulnerabilities within the database 
    software itself.



## References
* Jim Manico, August Detlefson. **Iron-Clad Java: Building Secure Web Applications**. Oracle Press, 2015

* Dafydd Stuttard, Marcus Pinto. **The Web Application Hacker’s Handbook**. Wiley, 2nd Edition, 2011.
    * Chapter 9: Attacking Data Stores

* [OWASP Cheat Sheet: SQL Injection Prevention](https://cheatsheetseries.owasp.org/cheatsheets/SQL_Injection_Prevention_Cheat_Sheet.html)

*Egon Teiniker, 2017-2024, GPL v3.0*