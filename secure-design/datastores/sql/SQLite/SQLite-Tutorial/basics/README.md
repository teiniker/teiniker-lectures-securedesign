# SQLite Tutorial

SQL provides commands to configure the tables, indexes, and other data 
structures within the database. SQL commands are also used to insert, 
update, and delete data records, as well as query those records to look 
up specific data values.

## Basic SQL Syntax 

Although it is customary to use all capital letters for SQL commands and 
keywords, **SQL is a case-insensitive language**. All commands and keywords 
are case insensitive, as are identifiers (such as table names and column names).

**Single-line comments** start with a double dash `--` and go to the end 
of the line. SQL also supports **multi-line comments** using the C comment 
syntax `/* */`.

**Numeric literals** are represented bare. Both integer `453` and real numbers 
`43.23` are recognized, as is exponent-style scientific notation `9.745e-6`. 
In order to avoid ambiguities in the parser, SQLite requires that the decimal 
point is always represented as a period `.`, regardless of the current 
internationalization setting.

**Text literals** are enclosed in single quotes `' '`. To represent a string 
literal that includes a single quote character, use two single quotes in a row 
(publisher = `'O''Reilly'`).
Double quotes are reserved for identifiers (table names, columns, etc.).

SQL allows any value to be assigned a **NULL**. NULL is not a value in itself 
but is used as a marker or flag to represent unknown or missing data.
We cannot use the equality operator `=` to test for NULLs. We must use the 
**IS NULL** operator.

SQLite supports the following **unary prefix operators**:

* **- +**: These adjust the **sign of a value**. The `-` operator flips the sign 
    of the value, effectively multiplying it by `-1.0`. The `+` operator is 
    essentially a no-op, leaving a value with the same sign it previously had. 
    It does not make negative values positive.

* **~**: As in the C language, the `~` operator performs a **bit-wise inversion**. 
    This operator is not part of the SQL standard.

* **NOT**: The NOT operator **reverses a Boolean expression**.


There are also a number of **binary operators**:

* **||**: **String concatenation**. This is the only string concatenation operator 
    recognized by the SQL standard. 

* **+ - * / %**: Standard **arithmetic operators** for addition, subtraction, 
    multiplication, division, and modulus (remainder).

* **| & << >>**: The **bitwise operators** or, and, and shift-high/shift-low, as 
    found in the C language. These operators are not part of the SQL standard.

* **< <= => >**: **Comparison test operators**. Again, just as in the C language 
    we have less-than, less-than or equal, greater-than or equal, and greater 
    than.

* **= == != <>**: **Equality test operators**. Both `=` and `==` will test for 
    equality, while both `!=` and `<>` test for inequality. 

* **AND OR**: **Logical operators**.

* **IN LIKE GLOB MATCH REGEXP**: These five keywords are **logic operators**,
    returning, true, false, or NULL state.



## SQL Data Languages

SQL commands are divided into four major categories, or languages:

* **Data Definition Language (DDL)**: Which refers to commands that define 
    the structure of tables, views, indexes, and other data containers and 
    objects within the database. 
    
    _Example:_ `CREATE TABLE`

* **Data Manipulation Language (DML)**: These are all of the commands that 
    insert, update, delete, and query actual data values from the data 
    structures defined by the DDL. 
    
    _Example:_ `INSERT`


* **Transaction Control Language (TCL)**: TCL commands can be used to control 
    transactions of DML and DDL commands. `BEGIN` (used to begin a multistatement 
    transaction) and `COMMIT` (used to end and accept a transaction) are examples 
    of TCL commands.


* **Data Control Language (DCL)**: The main purpose of the DCL is to grant or 
    revoke access control. Much like file permissions, DCL commands are used to 
    allow (or deny) specific database users (or groups of users) permission to 
    utilize or access specific resources within a database. 
    These permissions can apply to both the DDL and the DML.
    `GRANT` (used to assign a permission) and `REVOKE` (used to delete an existing 
    permission) are the primary DCL commands.


SQLite supports the majority of standardized DDL, DML, and TCL commands but lacks 
any DCL commands. Because SQLite does not have user names or logins, it does not 
have any concept of assigned permissions. Rather, SQLite depends on datatype 
permissions to define who can open and access a database.


## Data Definition Language

The DDL is used to define the structure of data containers and objects within the 
database. The most common of these containers and objects are tables, indexes, 
and views.

* **CREADE**: The `CREATE` command is used to create a new table, index, or view. 
    The `CREATE` command can also be used to create a new database.
    
    _Example:_ `CREATE TABLE users (id INTEGER PRIMARY KEY, username TEXT, password TEXT);`

* **DROP**: The `DROP` command is used to delete a table, index, or view. 
    The `DROP` command can also be used to delete a database.
    
    _Example:_ `DROP TABLE users;`

DDL commands are often held in a **script file**, so that the structure of the database 
can be easily recreated.

Most `CREATE` commands in SQLite have an optional `IF NOT EXISTS` clause.

Normally, a `CREATE` statement will return an error if an object with the requested name 
already exists. If the optional `IF NOT EXISTS` clause is present, then this error is 
suppressed and nothing is done, even if the structure of the existing object and the new 
object are not compatible.

Similarly, most `DROP` statements allow an optional `IF EXISTS` clause that 
silently ignores any request to delete an object that isn’t there.

### Tables 

The most common DDL command is `CREATE TABLE`. No data values can be stored 
in a database until a table is defined to hold that data.

**Creating Tables**

Basic syntax for creating a table is:

```sql
CREATE TABLE [IF NOT EXISTS] table_name
(
   column_name  column_type [column_constraint],
   [...]
   [table_constraint]
);
```

* `table_name`: A table name must be provided to identify the new table.
    Table names come from a global namespace of all identifiers, including 
    the names of tables, views, and indexes.

* `column_name`: Each column in the table must be given a name. 
    Column names are also taken from a global namespace of identifiers.

* `column_type`: Each column must be assigned a datatype.
    
    SQLite supports a number of different datatypes, including:
    * `INTEGER`: A signed integer value.
    * `REAL`: A floating-point value.
    * `TEXT`: A string value.
    * `BLOB`: A binary large object (BLOB) value.

* `column_constraint`: Each column can be assigned a number of constraints.
    These constraints are used to enforce data integrity and consistency.

    Some common column constraints include:
    * `PRIMARY KEY`: The column is the primary key for the table.
    * `UNIQUE`: The column must contain unique values.
    * `NOT NULL`: The column cannot contain NULL values.
    * `DEFAULT`: The column will be assigned a default value if no value is 
        provided.
    * `CHECK`: The column must satisfy a specific condition.

* `table_constraint`: A table constraint is a constraint that applies to the
    entire table, rather than a specific column. 

    Some common table constraints include:
    * `UNIQUE`: The table must contain unique values.
    
    * `PRIMARY KEY`: The primary key is a column or set of columns that uniquely 
        identifies each row in the table. The primary key must contain **unique 
        values and cannot contain NULL values**. 
        A table can only have one primary key.
        The primary key can be defined using the `PRIMARY KEY` constraint in the
        `CREATE TABLE` statement. The primary key can be a single column or a
        combination of multiple columns. If the primary key is a combination of
        multiple columns, the columns must be defined in the same order in the
        `CREATE TABLE` statement as they are in the primary key constraint.
        
        The primary key can also be defined using the `AUTOINCREMENT` keyword.
        The `AUTOINCREMENT` keyword is used to automatically generate a unique
        value for the primary key column. 
        The `AUTOINCREMENT` keyword can only be used with the `INTEGER` datatype. 
    
    * `FOREIGN KEY`: The table has a foreign key constraint. These columns 
        reference rows in another (foreign) table. Foreign keys can be used to 
        create links between rows in different tables.


**Altering Tables** 

SQLite supports a limited version of the `ALTER TABLE` command. 
Currently, there are only two operations supported by `ALTER TABLE`: 

* `ALTER TABLE table_name RENAME TO new_table_name;`
    * This command renames the table `table_name` to `new_table_name`.

* `ALTER TABLE table_name ADD COLUMN column_name column_type;`
    * This command adds a new column `column_name` to the table `table_name`.
    * The new column can be assigned a datatype and constraints, but it cannot 
        be assigned a default value. 
    * The new column will be added to the end of the table. 


**Dropping Tables**

`DROP TABLE` is used to delete them. The `DROP TABLE` command deletes a table 
and all of the data it contains. The table definition is also removed from the 
database system catalogs.

The only argument is the name of the table you wish to drop: 

```sql
DROP TABLE table_name;
```

## Views 

A **view** is a virtual table that is based on the result of a query.
A view does not store any data itself, but rather provides a way to access
data from one or more tables in a specific way.
A view is created using the `CREATE VIEW` command. The basic syntax for
creating a view is:

```sql
CREATE VIEW view_name AS
SELECT column1, column2, ...
FROM table_name
WHERE condition;
```


## Indexes

An **index** is a data structure that is used to improve the performance of
queries. An index is created on one or more columns of a table. 
When a query is executed, the database engine can use the index to quickly
find the rows that match the query criteria, rather than scanning the entire
table.

An index is created using the `CREATE INDEX` command. The basic syntax for
creating an index is:

```sql
CREATE INDEX index_name ON table_name (column1, column2, ...);
```

The index is automatically updated when rows are inserted, updated, 
or deleted from the table.

The `DROP INDEX` command is used to delete an index. The basic 
syntax for deleting an index is:

```sql
DROP INDEX index_name;
```


## Data Manipulation Language

The Data Manipulation Language is all about getting user data in and out of 
the database.

The DML supported by SQLite falls into two basic categories: 

* The first category consists of the **update commands** (row modifications), 
    These commands are used to update (or modify), insert, and delete 
    the rows of a table. All of these commands alter the stored data in 
    some way. The update commands are the primary means of managing all 
    the data within a database.

* The second category consists of the **query commands**, which are used 
    to extract data from the database. Actually, there is only one query 
    command: `SELECT`. The `SELECT` command not only prints returned values, 
    but provides a great number of options to combine different tables 
    and rows and otherwise manipulate data before returning the final 
    result.


### Row Modification Commands 

There are three commands used for adding, modifying, and removing data 
from the database. `INSERT` adds new rows, `UPDATE` modifies existing 
rows, and `DELETE` removes rows.

**INSERT** 

The `INSERT` command is used to create new rows in the specified table. 
There are two meaningful versions of the command. 

* The first version uses a `VALUES` clause to specify a list of values 
    to insert: 
    
    ```sql
    INSERT INTO table_name (column_name [, ...]) VALUES (new_value [, ...]); 
    ```
    
    A table name is provided, along with a list of columns and a list 
    of values. Both lists must have the same number of items. 
    A single new row is created and each value is recorded into its 
    respective column. 
    The columns can be listed in any order, just as long as the list 
    of columns and the list of values line up correctly. Any columns 
    that are not listed will receive their default values.

* The second version of `INSERT` allows you to define values by using 
    a query statement. This is the only version of `INSERT` that can 
    insert more than one row with a single command: 
    
    ```sql
    INSERT INTO table_name (column_name, [...]) SELECT query_statement; 
    ```

    This type of INSERT is most commonly used to bulk copy data from 
    one table to another.


**UPDATE** 

The `UPDATE` command is used to assign new values to one or more columns 
of existing rows in a table. The command can update more than one row, 
but all of the rows must be part of the same table. 

The basic syntax is: 

```sql
UPDATE table_name SET column_name=new_value [, ...] WHERE expression; 
```

The command requires a table name followed by a list of column name/value 
pairs that should be assigned. Which rows are updated is determined by 
a conditional expression that is tested against each row of the table. 

The most common usage pattern uses the expression to check for equality 
on some unique column, such as a `PRIMARY KEY` column.

If no `WHERE` condition is given, the `UPDATE` command will attempt to 
update the designated columns in every row of a table.

It is not considered an error if the `WHERE` expression evaluates to false 
for every row in the table, resulting in no actual updates.


**DELETE** 

The `DELETE` command is used to delete or remove one or more rows from 
a single table. The rows are completely deleted from the table:

```sql
DELETE FROM table_name WHERE expression; 
```

The command requires only a table name and a conditional expression to 
pick out rows. The `WHERE` expression is used to select specific rows 
to delete.

If no `WHERE` condition is given, the `DELETE` command will attempt to 
delete every row of a table.

It is not considered an error if the `WHERE` expression evaluates to 
false for every row in the table, resulting in no actual deletions.



### Query Commands 

`SELECT` is used to extract or return values from the database. Almost 
any time we want to extract or return some kind of value, we will need 
to use the `SELECT` command.

Generally, the returned values are derived from the contents of the 
database, but `SELECT` can also be used to return the value of simple 
expressions.

```sql
SELECT 1+1, 5*32, 'abc'||'def';
```

`SELECT` is a read-only command, and will not modify the database.

The `SELECT` syntax tries to represent a generic framework that is 
capable of expressing a great many different types of queries.
`SELECT` has a large number of optional clauses, each with its own 
set of options and formats.


The most basic form of `SELECT` is: 

```sql
SELECT output_list FROM input_table WHERE row_filter;
```
* The `output_list` is a list of expressions that should be evaluated 
    and returned for each resulting row.

* The `FROM` clause defines the source of the table data.

* The `WHERE` clause is a conditional filtering expression that 
    is applied to each row. Those rows that evaluate to true will 
    be part of the result, while the other rows will be filtered out.


## Transaction Control Language

The TCL is used in conjunction with the Data Manipulation Language to 
control the processing and exposure of changes. Transactions are a 
fundamental part of how relational databases **protect the integrity 
and reliability of the data** they hold. 

Transactions are automatically used on all DDL and DML commands.

### ACID Transactions 
A transaction is used to group together a series of low-level changes 
into a single, logical update. 

A transaction can be anything from updating a single value to a complex, 
multistep procedure that might end up inserting several rows into a 
number of different tables.

The classic transaction example is a database that holds **account 
numbers and balances**. If you want to transfer a balance from one 
account to another, that is a simple two-step process: 
subtract an amount from one account balance and then add the same 
amount to the other account balance. 
That process needs to be done as a single logical unit of change, 
and should not be broken apart. 
**Both steps should either succeed completely, resulting in the balance 
being correctly transferred, or both steps should fail completely, 
resulting in both accounts being left unchanged**. Any other outcome, 
where one step succeeds and the other fails, is not acceptable.

* Typically a transaction is **opened**, or started. 

* As individual **data manipulation commands** are issued, they 
    become part of the transaction. 
    
* When the logical procedure has finished, the transaction can be 
    **committed**, which applies all of the changes to the permanent 
    database record. 

* If, for any reason, the commit fails, the transaction is **rolled back**, 
    removing all traces of the changes. 
    A transaction can also be **manually rolled back**.

The standard for reliable, robust transactions is the ACID test. 
ACID stands for: 

* **Atomic**: A transaction should be atomic, in the sense that the 
    change cannot be broken down into smaller pieces. When a transaction 
    is committed to the database, the entire transaction must be applied 
    or the entire transaction must not be applied. 
    It should be impossible for only part of a transaction to be applied.


* **Consistent**: A transaction should also keep the database consistent. 
    A typical database has a number of rules and limits that help ensure 
    the stored data is correct and consistent with the design of the 
    database. 
    Assuming a database starts in a consistent state, applying a transaction 
    must keep the database consistent. This is important, because the 
    database is allowed to (and is often required to) become inconsistent 
    while the transaction is open.
    This is acceptable, as long as the transaction, as a whole, is 
    consistent when it is committed.

* **Isolated**: An open transaction must also be isolated from other clients. 
    When a client opens a transaction and starts to issue individual change 
    commands, the results of those commands are visible to the client. 
    Those changes should not be visible to any other system accessing the 
    database until the entire transaction is committed.
    transaction. Isolation is required for transactions to be atomic and 
    consistent.

* **Durable**: If the transaction is successfully committed, it must have 
    become a permanent and irreversible part of the database record. 
    Once a success status is returned, it should not matter if the process 
    is killed, the system loses power, or the database filesystem disappears
    — upon restart, the committed changes should be present in the database. 
    
Transactions are not just for writing data. Opening a transaction for an 
**extended read-only operation** is sometimes useful if you need to gather 
data with multiple queries. Having the transaction open **keeps your view of 
the database consistent**, ensuring that the data doesn’t change between 
queries.


### SQL Transactions 

Normally, SQLite is in **autocommit mode**. This means that SQLite will 
automatically start a transaction for each command, process the command, 
and (assuming no errors were generated) automatically commit the transaction. 

This process is **transparent to the user**, but it is important to realize 
that even individually entered commands are processed within a transaction, 
even if no TCL commands are used.


The autocommit mode can be disabled by **explicitly opening a transaction**. 
The `BEGIN` command is used to start or open a transaction. Once an explicit 
transaction has been opened, it will remain open until it is committed or 
rolled back. The keyword `TRANSACTION` is optional: 

```sql
`BEGIN [ DEFERRED | IMMEDIATE | EXCLUSIVE ] [TRANSACTION]` 
```

The optional keywords `DEFERRED`, `IMMEDIATE`, or `EXCLUSIVE` are specific to 
SQLite and control how the required read/write locks are acquired.

* `DEFERRED`: The transaction is opened in deferred mode. 
    This is the **default mode**. 
    No locks are acquired until the first read or write operation is performed.

* The `IMMEDIATE` mode will acquire a write lock on the database file as 
    soon as the transaction is opened. This prevents any other process from 
    writing to the database, but allows other processes to read from it.

* The `EXCLUSIVE` mode will acquire a write lock on the database file as
    soon as the transaction is opened. This prevents any other process from 
    writing to or reading from the database.

Once a transaction is open, we can continue to issue other SQL commands, 
including both DML and DDL commands.
The **changes are only visible to the local client** and have not been 
fully and permanently applied to the database. 
If the client process is killed or the server loses power in the middle of an
open transaction, the transaction and any proposed changes it has will be lost, 
but the rest of the database will remain intact and consistent.

The `COMMIT` command is used to close out a transaction and commit the changes 
to the database. As with BEGIN, the TRANSACTION keyword is optional:

```sql
COMMIT [TRANSACTION]
```

Once a `COMMIT` has successfully returned, all the proposed changes are 
fully committed to the database and become visible to other clients. 
At that point, if the system loses power or the client process is killed, 
the changes will remain safely in the database.

Rather than committing the proposed changes, the transaction can be 
**manually rolled back**, effectively canceling the transaction and all 
of the changes it contains. 
Rolling back a set of proposed changes is useful if an error is encountered.

To cancel the transaction and roll back all the proposed changes, we can 
use the `ROLLBACK` command. Again, the keyword `TRANSACTION` is optional: 

```sql
ROLLBACK [TRANSACTION] ROLLBACK 
```

This will undo and revert all the proposed changes made by the current 
transaction and then close the transaction. 

A `ROLLBACK` only cancels the proposed changes made by this client within 
the current transaction. 

Both `COMMIT` and `ROLLBACK` will end the current transaction, putting 
SQLite back into autocommit mode.




## References

* Jay A. Kreibich. **Using SQLite: Small. Fast. Reliable. Choose Any Three.** O'Reilly Media, 2010. 

*Egon Teiniker, 2020-2025, GPL v3.0*