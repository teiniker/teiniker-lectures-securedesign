# Export SQLite Data  

The `.mode` command in SQLite's CLI sets the output and input format.

Here are the available values you can use:

| Mode           | Description / Use                                             |
|----------------|---------------------------------------------------------------|
| **ascii**      | ASCII-art table (default, neatly formatted tables)            |
| **box**        | Box-drawing characters table (Unicode borders around results) |
| **column**     | Aligned columns (fixed-width columns)                         |
| **csv**        | Comma-separated values (CSV format)                           |
| **html**       | HTML table format                                             |
| **insert**     | SQL INSERT statements (for exporting to SQL format)           |
| **json**       | JSON format                                                   |
| **line**       | One value per line (each row is separated clearly)            |
| **list**       | Pipe-separated list (default separator is `|`)                |
| **markdown**   | Markdown-formatted table                                      |
| **quote**      | Quoted and comma-separated values                             |
| **table**      | Text table with borders (similar to ASCII, but cleaner)       |
| **tabs**       | Tab-separated values (TSV format)                             |
| **tcl**        | TCL list format                                               |


_Example:_ Format output in CSV

```sql
sqlite> .mode csv
sqlite> select * from measurements;
id,voltage,current
1,4.52,1.23
2,5.01,0.95
3,3.87,1.75
4,4.10,1.10
5,4.90,0.85
...
```

_Example:_ Format output in a ASCII table

```sql
sqlite> .mode table
sqlite> select * from measurements;
+----+---------+---------+
| id | voltage | current |
+----+---------+---------+
| 1  | 4.52    | 1.23    |
| 2  | 5.01    | 0.95    |
| 3  | 3.87    | 1.75    |
| 4  | 4.10    | 1.10    |
| 5  | 4.90    | 0.85    |
...
+----+---------+---------+
```

_Example:_ Format output in JSON

```sql
sqlite> .mode json
sqlite> select * from measurements;
[{"id":"1","voltage":"4.52","current":"1.23"},
{"id":"2","voltage":"5.01","current":"0.95"},
{"id":"3","voltage":"3.87","current":"1.75"},
{"id":"4","voltage":"4.10","current":"1.10"},
{"id":"5","voltage":"4.90","current":"0.85"},
...
]
```

*Egon Teiniker, 2020-2025, GPL v3.0*
