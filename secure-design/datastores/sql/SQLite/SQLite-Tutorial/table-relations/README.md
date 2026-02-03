# Table Relationships in SQLite

In a relational database, tables are related to each other through the use
of **foreign keys**. A foreign key is a column in one table that is used to
reference a primary key in another table. The foreign key column in the
first table is called a **foreign key constraint**. The primary key column
in the second table is called a **referenced key constraint**.

* **Primary Key (PK)**:	A column (or set of columns) that uniquely 
    identifies each row in a table.

* **Foreign Key (FK)**:	A column in one table that refers to the 
    primary key of another table.

* **Constraint**: Enforces referential integrity between tables 
    (e.g., ON DELETE CASCADE).

This is foundational to relational databases, which are designed around 
the concept of interrelated data.


## JOIN Types 

A `JOIN` is used to **combine rows from two or more tables based on a related column**, 
usually a foreign key.

Querying Relationships: JOINs let you query related tables:

* **INNER JOIN**: Returns only the rows where there is a match in both tables and 
    excludes rows that do not match.

* **LEFT JOIN (aka LEFT OUTER JOIN)**: Returns all rows from the left table (A) 
    and the matched rows from the right table (B). 
    If no match, B columns will be NULL.

* **RIGHT JOIN (aka RIGHT OUTER JOIN)** (not in SQLite): Returns all rows from 
    the right table (B) and matched rows from the left table (A). 
    If no match, A columns are NULL.

* **FULL JOIN (aka FULL OUTER JOIN)** (Not supported directly in SQLite): Returns all 
    rows from both tables — with NULL where there's no match.

* **CROSS JOIN**: Returns the Cartesian product of both tables, meaning every
    row from the first table is combined with every row from the second table.
    This is rarely used in practice due to the large result set it can produce.

| JOIN Type     | Returns                                                           | SQLite Support |
|---------------|-------------------------------------------------------------------|----------------|
| **INNER JOIN**| Only matching rows in both tables                                 | Supported   |
| **LEFT JOIN** | All rows from left + matching rows from right, else NULLs         | Supported   |
| **RIGHT JOIN**| All rows from right + matching rows from left, else NULLs         | Simulate    |
| **FULL JOIN** | All rows from both tables, matching when possible, else NULLs     | Simulate    |
| **CROSS JOIN**| Cartesian product of both tables (all combinations)               | Supported   |


## One to One (1:1) Relationship

* Each row in Table A is linked to exactly one row in Table B.

* Implemented by placing a unique foreign key in one of the tables.

_Example:_ User ---1- Mail
```sql
CREATE TABLE User (
    id INTEGER PRIMARY KEY,
    username TEXT NOT NULL UNIQUE
    password TEXT NOT NULL
);

CREATE TABLE Mail (
    id INTEGER PRIMARY KEY,
    user_id INTEGER UNIQUE,  -- Enforces one-to-one relationship
    address TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);
```

* `User.id` is the **primary key**.

* `Mail.user_id` is a **foreign key** that refers to `User.id`, and is marked 
    as `UNIQUE` — this ensures only one mail per user.

* We can optionally use `ON DELETE CASCADE` to automatically delete the mail 
    when the user is deleted.


_Example:_ Query all users with their mail 
```sql
SELECT User.username, Mail.address
FROM User
LEFT JOIN Mail ON User.id = Mail.user_id;
```

* `FROM User`
    - The query starts with the `User` table.
    - This is the left table in the `LEFT JOIN`.
    - Every row from the User table will appear in the result regardless of 
        whether there's a matching Mail row.

* `LEFT JOIN Mail ON User.id = Mail.user_id`
    - A `LEFT JOIN` returns all rows from the left table `User`, and matching 
        rows from the right table `Mail`.
    - If a `User` doesn't have a corresponding `Mail` record, the result will 
        still include that `User`, but with `NULL` in the `Mail.address` column.
    - The `ON` condition specifies how the tables are related: `Mail.user_id` 
        must match `User.id`.

* `SELECT User.username, Mail.address`
    - This tells SQLite to retrieve:
        - `User.username` (from the `User` table)
        - `Mail.address` (from the `Mail` table)


## One to Many (1:N) Relationship

* Each row in Table A can be linked to many rows in Table B.

* Common and used in most normalized designs.

_Example:_ User -1---*- Mail
```sql
CREATE TABLE User (
    id INTEGER PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

CREATE TABLE Mail (
    id INTEGER PRIMARY KEY,
    user_id INTEGER NOT NULL,
    address TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);
```

* `User.id`: **Primary key** that uniquely identifies each user.

* `Mail.user_id`: A **foreign key** that refers to `User.id`.

* This means each `Mail` is linked to one `User`, and any `User` can have many `Mails`.

* `ON DELETE CASCADE`: If a user is deleted, their mails are automatically deleted too.


_Example:_ Lists all users who have at least one email address, and shows each associated 
    address, ordered by username.
```sql
SELECT User.username, Mail.address
FROM User
JOIN Mail ON User.id = Mail.user_id
ORDER BY User.username;
```

* `FROM User`
    - The query starts with the `User` table.
    - This is the left side of the `JOIN`, and serves as the base for the results.

* `JOIN Mail ON User.id = Mail.user_id`
    - This is an `INNER JOIN`, meaning: Only rows where there is a match 
        between `User` and `Mail` will be returned.

    - It joins the `User` and `Mail` tables where: `User.id = Mail.user_id`
    - Each row in the result will consist of: A user and one of their associated 
        mail addresses.
        If a user does not have any mail, they will not appear in the result.

* `SELECT User.username, Mail.address`
    - Specifies which columns to include in the output:
        - `User.username`: the name of the user.
        - `Mail.address`: the associated email address from the `Mail` table.

* `ORDER BY User.username`
    - Sorts the result alphabetically by username.
    - Ensures grouped output if multiple mails belong to the same user.


_Example:_ Lists all users and their email addresses, including users without emails.
```sql
SELECT User.username, Mail.address
FROM User
LEFT JOIN Mail ON User.id = Mail.user_id
ORDER BY User.username;
```

* `LEFT JOIN` is used to include all users, even those without emails.
    - If a user has no associated mail, the `Mail.address` will be `NULL`.
    - This is useful for getting a complete list of users and their emails.


## Many-to-Many (N:N) Relationship

* Rows in Table A can be linked to many rows in Table B, and vice versa.

* Implemented with a **junction table**.

_Example:_ User -*---*- Role
```sql
CREATE TABLE User (
    id INTEGER PRIMARY KEY,
    username TEXT
);

CREATE TABLE Role (
    id INTEGER PRIMARY KEY,
    name TEXT
);

-- Junction table to represent many-to-many relationship
CREATE TABLE UserRole (
    user_id INTEGER,
    role_id INTEGER,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (role_id) REFERENCES Role(id)
);
```

* `User.id`: **Primary key** for the `User` table.
* `Role.id`: **Primary key** for the `Role` table.
* `UserRole` table:
    - `user_id`: A **foreign key** that refers to `User.id`.
    - `role_id`: A **foreign key** that refers to `Role.id`.
    - The combination of `user_id` and `role_id` must be unique 
        (**composite primary key**).
    - We cannot assign the same role to the same user more than once.


_Example:_ Query all users with their roles
```sql 
SELECT User.username, Role.name AS role
FROM User
JOIN UserRole ON User.id = UserRole.user_id
JOIN Role ON Role.id = UserRole.role_id;
```

* `FROM User`
    - Starts with the `User` table as the base (left side of the joins).
    - Every row in `User` is a potential match.

* `JOIN UserRole ON User.id = UserRole.user_id`
    - Performs an **inner join** between `User` and `UserRole`.
    - It links each user to their corresponding entries in the `UserRole` table.
    - This filters down to users who have at least one role.

* `JOIN Role ON Role.id = UserRole.role_id`
    - This connects the `UserRole` table to the `Role` table.
    - Each row in `UserRole` is linked to a row in `Role` using the role_id.
    - Now you’ve joined all three tables together: `User ↔ UserRole ↔ Role`.

* `SELECT User.username, Role.name AS role`
    - Selects and displays:
        - The `username` from the `User` table.
        - The `name` of the role from the `Role` table (renamed to role in the output).


_Example:_ Query all roles assigned to a specific user
```sql 
SELECT Role.name
FROM Role
JOIN UserRole ON Role.id = UserRole.role_id
JOIN User ON User.id = UserRole.user_id
WHERE User.username = 'alice';
```

* `FROM Role`
    - The query starts from the `Role` table — meaning it begins with all roles available.

* `JOIN UserRole ON Role.id = UserRole.role_id`
    - Joins the `Role` table with `UserRole`, linking roles to their assignments.
    - Matches each role to the rows in `UserRole` where the role was assigned to a user.

* `JOIN User ON User.id = UserRole.user_id`
    - Joins the `UserRole` table to the `User` table.
    - This brings in the user who was assigned each role.
    - We now have: `Role ↔ UserRole ↔ User`.

* `WHERE User.username = 'alice'`
    - Filters the result to only include roles assigned to the user named 'alice'.

* `SELECT Role.name`
    - Finally, the query outputs only the role names assigned to `'alice'`.


_Example:_ Query all users who have a specific role
```sql 
SELECT User.username
FROM User
JOIN UserRole ON User.id = UserRole.user_id
JOIN Role ON Role.id = UserRole.role_id
WHERE Role.name = 'Editor';
```

* `FROM User`
    - The query starts from the User table — meaning we’re working with a list of 
    all users as the base.

*  `JOIN UserRole ON User.id = UserRole.user_id`
    - This is an `INNER JOIN`, linking each user to the `UserRole` table.
    - It connects users to the roles they’ve been assigned via the `user_id`.

* `JOIN Role ON Role.id = UserRole.role_id`
    - Another `INNER JOIN`, linking each `UserRole` entry to the actual `Role` table.
    - Now we have access to both the user and the role name for each user-role assignment.

* `WHERE Role.name = 'Editor'`
    - This filters the result to include only users who have been assigned the 
        role named `'Editor'`.

* `SELECT User.username`
    - The output includes only the username column from the User table.

 
 ## References

 * Jay A. Kreibich. **Using SQLite: Small. Fast. Reliable. Choose Any Three.** O'Reilly Media, 2010. 

*Egon Teiniker, 2020-2025, GPL v3.0*