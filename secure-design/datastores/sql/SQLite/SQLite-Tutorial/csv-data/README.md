# Handle CSV Data in SQLite

SQLite provides a convenient way to import and export CSV data using the 
`.import` and `.export` commands. 
Below are examples of how to use these commands effectively.

## Importing CSV Data into SQLite

To import CSV data into an SQLite database, we can use the `.import` 
command. 

Here’s how to do it:

1. Open the SQLite command line interface:
   ```bash
   sqlite3 test.db
   ```
2. Set the mode to CSV:
   ```sql
   .mode csv
   .header on
   ```
3. Import the CSV file into a table:
   ```sql
   .import /path/to/your/file.csv table_name
   .tables
   ```

_Example:_ Importing a CSV file `data.csv` into a table named `measurements`:

```sql
sqlite> .mode csv
sqlite> .header on
sqlite> .import data.csv measurements
sqlite> .tables
measurements
sqlite> select * from measurements;
``` 


## Exporting SQLite Data to CSV

To export data from an SQLite table to a CSV file, we can use the `.export` 
command. 

Here’s how to do it:

1. Open the SQLite command line interface:
   ```bash
   sqlite3 your_database.db
   ```
2. Set the mode to CSV:
   ```sql
   .mode csv
   .header on
   ```
3. Export the data from a table to a CSV file:
   ```sql
   .output /path/to/your/output.csv
   SELECT * FROM table_name;
   .output stdout
   ```

_Example:_ Exporting data from the `measurements` table to a CSV file `output.csv`:

```sql
sqlite> .mode csv
sqlite> .header on
sqlite> .output output.csv
sqlite> SELECT * FROM measurements;
sqlite> .output stdout
```

*Egon Teiniker, 2020-2025, GPL v3.0*
