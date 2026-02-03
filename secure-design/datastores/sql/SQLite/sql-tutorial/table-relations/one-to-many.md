# Examples: One-to-Many Relationship

## Setup Database Tables 

```sql
PRAGMA foreign_keys = ON;

CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

CREATE TABLE mails (
    id INTEGER PRIMARY KEY,
    user_id INTEGER NOT NULL,
    address TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO users (id, username, password) VALUES (1, 'homer', 'd8hGjs6sg.7F');
INSERT INTO users (id, username, password) VALUES (2, 'marge', 'Nf7JgR02pl!A');
INSERT INTO users (id, username, password) VALUES (3, 'bart', 'rE2vLmP93*Wq');
INSERT INTO users (id, username, password) VALUES (4, 'lisa', 'Mz4FgQt1@zU8');
INSERT INTO users (id, username, password) VALUES (5, 'maggie', 'Tx93LqvV#nF1');
INSERT INTO users (id, username, password) VALUES (6, 'moe', 'Bc8KpYi7!5Lo');
INSERT INTO users (id, username, password) VALUES (7, 'apu', 'Gh1ZxRt5^cE2');
INSERT INTO users (id, username, password) VALUES (8, 'ned', 'Ue3NpHw9%tPq');
INSERT INTO users (id, username, password) VALUES (9, 'milhouse', 'Yi7DnWb2@fR3');
INSERT INTO users (id, username, password) VALUES (10, 'krusty', 'Wc5XsLa8@qJz');
INSERT INTO users (id, username, password) VALUES (24, 'snowball', 'Qw2ErTy6&uIo');
INSERT INTO users (id, username, password) VALUES (25, 'blinky', 'Zy5KwXc4$uYq');

INSERT INTO mails (id, user_id, address) VALUES (1, 1, 'homer.simpson@springfield.com');
INSERT INTO mails (id, user_id, address) VALUES (2, 2, 'marge.simpson@springfield.com');
INSERT INTO mails (id, user_id, address) VALUES (3, 3, 'bart.simpson@springfield.com');
INSERT INTO mails (id, user_id, address) VALUES (4, 4, 'lisa.simpson@springfield.com');
INSERT INTO mails (id, user_id, address) VALUES (5, 5, 'maggie.simpson@springfield.com');
INSERT INTO mails (id, user_id, address) VALUES (6, 6, 'moe.szyslak@springfield.com');
INSERT INTO mails (id, user_id, address) VALUES (7, 7, 'apu.nahasapeemapetilon@kwikemart.com');
INSERT INTO mails (id, user_id, address) VALUES (8, 8, 'ned.flanders@springfield.com');
INSERT INTO mails (id, user_id, address) VALUES (9, 9, 'milhouse.van.houten@springfield.com');
INSERT INTO mails (id, user_id, address) VALUES (10, 10, 'krusty.clown@krustycorp.com');
INSERT INTO mails (id, user_id, address) VALUES (11, 1, 'homer.powerplant@springfield.com');
INSERT INTO mails (id, user_id, address) VALUES (12, 3, 'bart.school.com');
```

## Query Data 

```sql
SELECT * FROM users;
+----+----------+--------------+
| id | username |   password   |
+----+----------+--------------+
| 1  | homer    | d8hGjs6sg.7F |
| 2  | marge    | Nf7JgR02pl!A |
| 3  | bart     | rE2vLmP93*Wq |
| 4  | lisa     | Mz4FgQt1@zU8 |
| 5  | maggie   | Tx93LqvV#nF1 |
| 6  | moe      | Bc8KpYi7!5Lo |
| 7  | apu      | Gh1ZxRt5^cE2 |
| 8  | ned      | Ue3NpHw9%tPq |
| 9  | milhouse | Yi7DnWb2@fR3 |
| 10 | krusty   | Wc5XsLa8@qJz |
| 24 | snowball | Qw2ErTy6&uIo |
| 25 | blinky   | Zy5KwXc4$uYq |
+----+----------+--------------+
```

```sql
SELECT * FROM mails;
+----+---------+--------------------------------------+
| id | user_id |               address                |
+----+---------+--------------------------------------+
| 1  | 1       | homer.simpson@springfield.com        |
| 2  | 2       | marge.simpson@springfield.com        |
| 3  | 3       | bart.simpson@springfield.com         |
| 4  | 4       | lisa.simpson@springfield.com         |
| 5  | 5       | maggie.simpson@springfield.com       |
| 6  | 6       | moe.szyslak@springfield.com          |
| 7  | 7       | apu.nahasapeemapetilon@kwikemart.com |
| 8  | 8       | ned.flanders@springfield.com         |
| 9  | 9       | milhouse.van.houten@springfield.com  |
| 10 | 10      | krusty.clown@krustycorp.com          |
| 11 | 1       | homer.powerplant@springfield.com     |
| 12 | 3       | bart.school.com                      |
+----+---------+--------------------------------------+
```

### INNER JOIN 

```sql
-- Get all usernames with mail addresses 
SELECT users.username, mails.address FROM users JOIN mails ON users.id = mails.user_id;
+----------+--------------------------------------+
| username |               address                |
+----------+--------------------------------------+
| homer    | homer.simpson@springfield.com        |
| marge    | marge.simpson@springfield.com        |
| bart     | bart.simpson@springfield.com         |
| lisa     | lisa.simpson@springfield.com         |
| maggie   | maggie.simpson@springfield.com       |
| moe      | moe.szyslak@springfield.com          |
| apu      | apu.nahasapeemapetilon@kwikemart.com |
| ned      | ned.flanders@springfield.com         |
| milhouse | milhouse.van.houten@springfield.com  |
| krusty   | krusty.clown@krustycorp.com          |
| homer    | homer.powerplant@springfield.com     |
| bart     | bart.school.com                      |
+----------+--------------------------------------+
```

```sql
-- Get all mails for user 'homer' 
SELECT mails.address FROM mails JOIN users ON users.id = mails.user_id WHERE users.username = 'homer';
+----------------------------------+
|             address              |
+----------------------------------+
| homer.simpson@springfield.com    |
| homer.powerplant@springfield.com |
+----------------------------------+
```


### LEFT JOIN

```sql
-- Get all usernames with mail addresses, if no mail address is found, return NULL
SELECT users.username, mails.address FROM users LEFT JOIN mails ON users.id = mails.user_id;
+----------+--------------------------------------+
| username |               address                |
+----------+--------------------------------------+
| apu      | apu.nahasapeemapetilon@kwikemart.com |
| bart     | bart.school.com                      |
| bart     | bart.simpson@springfield.com         |
| blinky   |                                      |
| homer    | homer.powerplant@springfield.com     |
| homer    | homer.simpson@springfield.com        |
| krusty   | krusty.clown@krustycorp.com          |
| lisa     | lisa.simpson@springfield.com         |
| maggie   | maggie.simpson@springfield.com       |
| marge    | marge.simpson@springfield.com        |
| milhouse | milhouse.van.houten@springfield.com  |
| moe      | moe.szyslak@springfield.com          |
| ned      | ned.flanders@springfield.com         |
| snowball |                                      |
+----------+--------------------------------------+
```


## Delete Data

```sql
-- Delete user (ON DELETE CASCADE)
SELECT * FROM users WHERE id = 1;
+----+----------+--------------+
| id | username |   password   |
+----+----------+--------------+
| 1  | homer    | d8hGjs6sg.7F |
+----+----------+--------------+

SELECT * FROM mails WHERE user_id = 1;
+----+---------+----------------------------------+
| id | user_id |             address              |
+----+---------+----------------------------------+
| 1  | 1       | homer.simpson@springfield.com    |
| 11 | 1       | homer.powerplant@springfield.com |
+----+---------+----------------------------------+

DELETE FROM users WHERE id = 1;

SELECT * FROM users WHERE id = 1;

SELECT * FROM mails WHERE user_id = 1;
```