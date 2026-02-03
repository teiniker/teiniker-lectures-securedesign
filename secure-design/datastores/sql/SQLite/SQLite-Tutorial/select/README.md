# SELECT Command 

A table consists of a heading and a body. The heading defines the name and 
type (in SQLite, the affinity) of each column.
The table body contains all of the rows. Each row consists of one data 
element for each column. The rows in an SQL table are unordered.

A very common mistake is to assume a given query will always return rows 
in the same order.
Don’t let your application code become dependent on the natural ordering 
of an unordered query.

The `SELECT` syntax tries to represent a generic framework that is 
capable of expressing many different types of queries.

```sql
SELECT [DISTINCT] select_heading
    FROM source_tables
    WHERE filter_expression
    GROUP BY grouping_expressions
        HAVING filter_expression
    ORDER BY ordering_expressions
    LIMIT count
        OFFSET count
```

Every `SELECT` command must have a select heading, which defines the 
returned values. Each additional line (`FROM`, `WHERE`, `GROUP BY`, 
etc.) represents an optional clause. 

Each clause represents a step in the **`SELECT` pipeline**. Conceptually, 
the result of a `SELECT` statement is calculated by generating a working 
table, and then passing that table through the pipeline. 

Each step takes the working table as input, performs a specific operation 
or manipulation, and passes the modified table on to the next step. 
Manipulations operate the whole working table, similar to vector or 
matrix operations.

The evaluation order of clauses in a `SELECT` statement looks like this: 

1. `FROM source_tables`: 
    Designates one or more source tables and combines them together 
    into one large working table. 
    
2. `WHERE filter_expression`: Filters specific rows out of the working 
    table. 
    
3. `GROUP BY grouping_expressions`: Groups sets of rows in the working 
    table based off similar values. 
    
4. `SELECT select_heading`: Defines the result set columns and (if 
    applicable) grouping aggregates. 
    
5. `HAVING filter_expression`: Filters specific rows out of the grouped 
    table. Requires a `GROUP BY`. 
    
6. `DISTINCT`: Eliminates duplicate rows.

7. `ORDER BY ordering_expressions`: Sorts the rows of the result set. 

8. `OFFSET count`: Skips over rows at the beginning of the result set. 
    Requires a `LIMIT`. 
    
9. `LIMIT count`: Limits the result set output to a specific number 
    of rows.

No matter how large or complex a SELECT statement may be, they all 
follow this basic pattern.


## FROM Clause

The `FROM` clause takes one or more source tables from the database 
and combines them into one large table. Source tables are usually 
named tables from the database, but they can also be views or 
subqueries.

Tables are combined using the `JOIN` operator. Each `JOIN` combines 
two tables into a larger table.
`JOIN` operators are evaluated left-to-right, but there are several 
different types of joins, and not all of them are commutative or 
associative.

Joins are the most important and most powerful database operator. 
Joins are the only way to bring together information stored in 
different tables.

SQL defines three major types of joins: 

* `CROSS JOIN`: 
    A `CROSS JOIN` matches every row of the first table with every 
    row of the second table. If the input tables have `x` and `y` 
    columns, the resulting table will have `x+y` columns. If the 
    input tables have `n` and `m` rows, respectively, the resulting 
    table will have `n·m` rows (**Cartesian product**).

* `INNER JOIN`:
    An `INNER JOIN` is very similar to a `CROSS JOIN`, but it has a 
    built-in **condition** that is used to limit the number of rows 
    in the resulting table. The conditional is normally used to pair 
    up or match rows from the two source tables.

    An `INNER JOIN` without any type of conditional expression will 
    result in a `CROSS JOIN`.

    An `INNER JOIN` is the most common type of join, and is the default 
    type of join. This makes the `INNER` keyword optional.

* `OUTER JOIN`:
    The `OUTER JOIN` is an extension of the `INNER JOIN`. The SQL 
    standard defines three types of `OUTER JOIN`s: `LEFT`, `RIGHT`, 
    and `FULL`. Currently, **SQLite only supports the `LEFT OUTER JOIN`**.

    `OUTER JOIN`s have a conditional that is identical to `INNER JOINs`.

    Once the primary `JOIN` is calculated, an `OUTER` join will take any 
    unjoined rows from one or both tables, pad them out with `NULL`s, 
    and append them to the resulting table.

Because the `JOIN` operator combines the columns of different tables into 
one, larger table, there may be cases when the resulting working table has 
multiple columns with the same name.

To avoid ambiguity within the `SELECT` statement, any instance of a 
source-table, view, or subquery can be assigned an alias. This is done 
with the `AS` keyword.

Technically, the `AS` keyword is optional, and each source-table name can 
simply be followed with an alias name. This can be quite confusing, however, 
so it is recommended you use the `AS` keyword.


## WHERE Clause

The `WHERE` clause is used to filter rows from the working table generated 
by the `FROM` clause. It is very similar to the `WHERE` clause found in the 
`UPDATE` and `DELETE` commands. 

An expression is provided that is evaluated for each row. Any row that causes 
the expression to evaluate to `false` or `NULL` is discarded. 

The resulting table will have the same number of columns as the original table, 
but may have fewer rows.


## GROUP BY Clause

The `GROUP BY` clause is used to collapse, or “flatten,” groups of rows. 
Groups can be counted, averaged, or otherwise aggregated together. If you 
need to perform any kind of inter-row operation that requires data from 
more than one row, chances are you’ll need a `GROUP BY`.

The `GROUP BY` clause provides a list of grouping expressions and optional 
collations. Very often the expressions are simple column references, but 
they can be arbitrary expressions.

The grouping process has two steps. First, the `GROUP BY` expression list 
is used to arrange table rows into different groups. Once the groups are 
defined, the `SELECT` header defines how those groups are flattened down 
into a single row. The resulting table will have one row for each group.


## SELECT Header 

The `SELECT` header is used to define the format and content of the final 
result table. Any column you want to appear in the final results table 
must be defined by an expression in the `SELECT` header. The `SELECT` 
heading is the only required step in the `SELECT` command pipeline.

The format of the header is consisting of a list of expressions. 
Each expression is evaluated in the context of each row, producing 
the final results table. Very often the expressions are simple column 
references, but they can be any arbitrary expression involving column 
references, literal values, or SQL functions. 

To generate the final query result, the list of expressions is evaluated 
once for each row in the working table.

Additionally, we can provide a column name using the AS keyword:

```sql
SELECT column_name AS alias_name
```

Although the `SELECT` header appears to filter columns from the working 
table, much like the `WHERE` clause filters rows, this isn’t exactly 
correct. All of the columns from the original working table are still 
available to clauses that are processed after the `SELECT` header.

It would be more accurate to say that the `SELECT` header tags specific 
columns for output.

In addition to the standard expressions, `SELECT` supports two **wildcards**. 
A simple asterisk `*` will cause every user-defined column from every source 
table in the FROM clause to be output. 
We can also target a specific table (or table alias) using the format 
`table_name.*`.

The `SELECT` header determines how row-groups (produced by the `GROUP BY` 
clause) are flattened into a single row. This is done using **aggregate 
functions**. An aggregate function takes a column expression as input 
and aggregates, or combines, all of the column values from the rows of 
a group and produces a single output value. Common aggregate functions 
include `count()`, `min()`, `max()`, and `avg()`.

Any column or expression that is not passed through an aggregate function 
will assume whatever value was contained in the last row of the group.


## HAVING Clause

The HAVING clause is identical to the WHERE clause. The HAVING clause 
consists of a filter expression that is evaluated for each row of the 
working table. Any row that evaluates to false or NULL is filtered out 
and removed. 

The resulting table will have the same number of columns, but may have 
fewer rows.

The main difference between the `WHERE` clause and the `HAVING` clause 
is where they appear in the `SELECT` pipeline. 

* The `HAVING` clause is processed after the `GROUP BY` and `SELECT` 
clauses, allowing `HAVING` to filter rows based off the results of 
any `GROUP BY` aggregate.

* The `WHERE` clause is processed before the `GROUP BY` and `SELECT`
clauses, and can only filter rows based off the original working 
table.

`HAVING` clauses should only contain filter expressions that depend 
on the `GROUP BY` output. All other filtering should be done in the 
`WHERE` clause.


## DISTINCT Keyword 

The `DISTINCT` keyword will scan the result set and eliminate any 
duplicate rows. This ensures the returned rows constitute a proper 
set. 

Only the columns and values specified in the `SELECT` header are 
considered when determining if a row is a duplicate or not. This 
is one of the few cases when `NULL`s are considered to have “equality” 
and will be eliminated.

Because `SELECT DISTINCT` must compare every row against every other 
row, it is an expensive operation. In a well-designed database, it is 
also rarely required. Therefore, its usage is somewhat unusual.


## ORDER BY Clause

The `ORDER BY` clause is used to sort, or order, the rows of the results 
table. 

```sql
ORDER BY expression [COLLATE collation_name] [ASC|DESC] [,...]
```

A list of one or more sort expressions is provided. The first expression 
is used to sort the table. The second expression is used to sort any 
equivalent rows from the first sort, and so on. 

Each expression can be sorted in ascending or descending order.
The `ASC` or `DESC` keywords can be used to force the sort in an 
ascending or descending order. By default, values are sorted in 
an ascending order.

The expression is evaluated for each row. Very often the expression 
is a simple column reference, but it can be any expression. 
The resulting value is then compared against those values generated 
by other rows.

While `ORDER BY` is extremely useful, it should only be used when 
it is actually needed — especially with very large result tables. 
Although SQLite can sometimes make use of an index to calculate the 
query results in order, in many cases SQLite must first calculate 
the entire result set and then sort it, before rows are returned.


## LIMIT and OFFSET Clauses

The `LIMIT` and `OFFSET` clauses allow you to extract a specific 
subset of rows from the final results table. 

* `LIMIT` defines the maximum number of rows that will be returned.
* `OFFSET` defines the number of rows to skip before returning the 
    first row. 
    
If no OFFSET is provided, the LIMIT is applied to the top of the table.

We usually want to define an `ORDER BY` if you’re using a LIMIT. 
Without an `ORDER BY`, there is no well-defined order to the result, 
making the limit and offset somewhat meaningless.



## References

* Jay A. Kreibich. **Using SQLite: Small. Fast. Reliable. Choose Any Three.** O'Reilly Media, 2010. 
    - Chapter 5: The SELECT Command 

*Egon Teiniker, 2020-2025, GPL v3.0*