# Examples: One-to-One Relationship

## Setup Database Tables 

```sql
PRAGMA foreign_keys = ON;

-- Create database tabes
CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

CREATE TABLE mails (
    id INTEGER PRIMARY KEY,
    user_id INTEGER UNIQUE,  -- Enforces one-to-one relationship
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
INSERT INTO users (id, username, password) VALUES (11, 'skinner', 'Lm9BjTp1$kLx');
INSERT INTO users (id, username, password) VALUES (12, 'ralph', 'Xe1TzQw7&uVo');
INSERT INTO users (id, username, password) VALUES (13, 'willie', 'Og6SpMw3%zRt');
INSERT INTO users (id, username, password) VALUES (14, 'smithers', 'Pv8LsGh0^mZs');
INSERT INTO users (id, username, password) VALUES (15, 'burns', 'Kt2MfPj6*zNp');
INSERT INTO users (id, username, password) VALUES (16, 'otto', 'Zy5KwXc4$uYq');
INSERT INTO users (id, username, password) VALUES (17, 'barney', 'Dj7NqEe9^hKw');
INSERT INTO users (id, username, password) VALUES (18, 'edna', 'Qa3LtVs2@jPw');
INSERT INTO users (id, username, password) VALUES (19, 'nelson', 'Xv4HyCg1!eZn');
INSERT INTO users (id, username, password) VALUES (20, 'lenny', 'Fs9BwTr6&kVm');
INSERT INTO users (id, username, password) VALUES (21, 'carl', 'Jq8XyZt3#pLk');
INSERT INTO users (id, username, password) VALUES (22, 'patty', 'Wm2QvJg5^nXo');
INSERT INTO users (id, username, password) VALUES (23, 'selma', 'Yh6KpRz8@qJw');
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
INSERT INTO mails (id, user_id, address) VALUES (11, 11, 'seymour.skinner@springfieldschool.edu');
INSERT INTO mails (id, user_id, address) VALUES (12, 12, 'ralph.wiggum@springfieldschool.edu');
INSERT INTO mails (id, user_id, address) VALUES (13, 13, 'groundskeeper.willie@springfieldschool.edu');
INSERT INTO mails (id, user_id, address) VALUES (14, 14, 'waylon.smithers@burnsindustries.com');
INSERT INTO mails (id, user_id, address) VALUES (15, 15, 'montgomery.burns@burnsindustries.com');
INSERT INTO mails (id, user_id, address) VALUES (16, 16, 'otto.busdriver@springfield.com');
INSERT INTO mails (id, user_id, address) VALUES (17, 17, 'barney.gumble@moesbar.com');
INSERT INTO mails (id, user_id, address) VALUES (18, 18, 'edna.krabappel@springfieldschool.edu');
INSERT INTO mails (id, user_id, address) VALUES (19, 19, 'nelson.muntz@springfieldschool.edu');
INSERT INTO mails (id, user_id, address) VALUES (20, 20, 'lenny.leonard@powerplant.com');
INSERT INTO mails (id, user_id, address) VALUES (21, 21, 'carl.carlson@powerplant.com');
INSERT INTO mails (id, user_id, address) VALUES (22, 22, 'patty.bouvier@dmv.gov');
INSERT INTO mails (id, user_id, address) VALUES (23, 23, 'selma.bouvier@dmv.gov');
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
| 11 | skinner  | Lm9BjTp1$kLx |
| 12 | ralph    | Xe1TzQw7&uVo |
| 13 | willie   | Og6SpMw3%zRt |
| 14 | smithers | Pv8LsGh0^mZs |
| 15 | burns    | Kt2MfPj6*zNp |
| 16 | otto     | Zy5KwXc4$uYq |
| 17 | barney   | Dj7NqEe9^hKw |
| 18 | edna     | Qa3LtVs2@jPw |
| 19 | nelson   | Xv4HyCg1!eZn |
| 20 | lenny    | Fs9BwTr6&kVm |
| 21 | carl     | Jq8XyZt3#pLk |
| 22 | patty    | Wm2QvJg5^nXo |
| 23 | selma    | Yh6KpRz8@qJw |
+----+----------+--------------+
```

```sql
SELECT * FROM mails;
+----+---------+--------------------------------------------+
| id | user_id |                  address                   |
+----+---------+--------------------------------------------+
| 1  | 1       | homer.simpson@springfield.com              |
| 2  | 2       | marge.simpson@springfield.com              |
| 3  | 3       | bart.simpson@springfield.com               |
| 4  | 4       | lisa.simpson@springfield.com               |
| 5  | 5       | maggie.simpson@springfield.com             |
| 6  | 6       | moe.szyslak@springfield.com                |
| 7  | 7       | apu.nahasapeemapetilon@kwikemart.com       |
| 8  | 8       | ned.flanders@springfield.com               |
| 9  | 9       | milhouse.van.houten@springfield.com        |
| 10 | 10      | krusty.clown@krustycorp.com                |
| 11 | 11      | seymour.skinner@springfieldschool.edu      |
| 12 | 12      | ralph.wiggum@springfieldschool.edu         |
| 13 | 13      | groundskeeper.willie@springfieldschool.edu |
| 14 | 14      | waylon.smithers@burnsindustries.com        |
| 15 | 15      | montgomery.burns@burnsindustries.com       |
| 16 | 16      | otto.busdriver@springfield.com             |
| 17 | 17      | barney.gumble@moesbar.com                  |
| 18 | 18      | edna.krabappel@springfieldschool.edu       |
| 19 | 19      | nelson.muntz@springfieldschool.edu         |
| 20 | 20      | lenny.leonard@powerplant.com               |
| 21 | 21      | carl.carlson@powerplant.com                |
| 22 | 22      | patty.bouvier@dmv.gov                      |
| 23 | 23      | selma.bouvier@dmv.gov                      |
+----+---------+--------------------------------------------+
```

### INNER JOIN 

```sql
-- Get all users who do have email addresses
SELECT users.username, users.password, mails.address FROM users JOIN mails ON users.id = mails.user_id;
+----------+--------------+--------------------------------------------+
| username |   password   |                  address                   |
+----------+--------------+--------------------------------------------+
| homer    | d8hGjs6sg.7F | homer.simpson@springfield.com              |
| marge    | Nf7JgR02pl!A | marge.simpson@springfield.com              |
| bart     | rE2vLmP93*Wq | bart.simpson@springfield.com               |
| lisa     | Mz4FgQt1@zU8 | lisa.simpson@springfield.com               |
| maggie   | Tx93LqvV#nF1 | maggie.simpson@springfield.com             |
| moe      | Bc8KpYi7!5Lo | moe.szyslak@springfield.com                |
| apu      | Gh1ZxRt5^cE2 | apu.nahasapeemapetilon@kwikemart.com       |
| ned      | Ue3NpHw9%tPq | ned.flanders@springfield.com               |
| milhouse | Yi7DnWb2@fR3 | milhouse.van.houten@springfield.com        |
| krusty   | Wc5XsLa8@qJz | krusty.clown@krustycorp.com                |
| skinner  | Lm9BjTp1$kLx | seymour.skinner@springfieldschool.edu      |
| ralph    | Xe1TzQw7&uVo | ralph.wiggum@springfieldschool.edu         |
| willie   | Og6SpMw3%zRt | groundskeeper.willie@springfieldschool.edu |
| smithers | Pv8LsGh0^mZs | waylon.smithers@burnsindustries.com        |
| burns    | Kt2MfPj6*zNp | montgomery.burns@burnsindustries.com       |
| otto     | Zy5KwXc4$uYq | otto.busdriver@springfield.com             |
| barney   | Dj7NqEe9^hKw | barney.gumble@moesbar.com                  |
| edna     | Qa3LtVs2@jPw | edna.krabappel@springfieldschool.edu       |
| nelson   | Xv4HyCg1!eZn | nelson.muntz@springfieldschool.edu         |
| lenny    | Fs9BwTr6&kVm | lenny.leonard@powerplant.com               |
| carl     | Jq8XyZt3#pLk | carl.carlson@powerplant.com                |
| patty    | Wm2QvJg5^nXo | patty.bouvier@dmv.gov                      |
| selma    | Yh6KpRz8@qJw | selma.bouvier@dmv.gov                      |
+----------+--------------+--------------------------------------------+
```

```sql
-- Count how many users have email addresses
SELECT COUNT(*) FROM users JOIN mails ON users.id = mails.user_id;
+----------+
| COUNT(*) |
+----------+
| 23       |
+----------+
```

```sql
-- Find users whose email contains 'simpson'
SELECT users.username, mails.address FROM users JOIN mails ON users.id = mails.user_id WHERE mails.address LIKE '%simpson%';
+----------+--------------------------------+
| username |            address             |
+----------+--------------------------------+
| homer    | homer.simpson@springfield.com  |
| marge    | marge.simpson@springfield.com  |
| bart     | bart.simpson@springfield.com   |
| lisa     | lisa.simpson@springfield.com   |
| maggie   | maggie.simpson@springfield.com |
+----------+--------------------------------+
```


### LEFT JOIN 

```sql
-- Get all users and their email addresses (including users without emails)
SELECT users.username, users.password, mails.address FROM users LEFT JOIN mails ON users.id = mails.user_id;
+----------+--------------+--------------------------------------------+
| username |   password   |                  address                   |
+----------+--------------+--------------------------------------------+
| homer    | d8hGjs6sg.7F | homer.simpson@springfield.com              |
| marge    | Nf7JgR02pl!A | marge.simpson@springfield.com              |
| bart     | rE2vLmP93*Wq | bart.simpson@springfield.com               |
| lisa     | Mz4FgQt1@zU8 | lisa.simpson@springfield.com               |
| maggie   | Tx93LqvV#nF1 | maggie.simpson@springfield.com             |
| moe      | Bc8KpYi7!5Lo | moe.szyslak@springfield.com                |
| apu      | Gh1ZxRt5^cE2 | apu.nahasapeemapetilon@kwikemart.com       |
| ned      | Ue3NpHw9%tPq | ned.flanders@springfield.com               |
| milhouse | Yi7DnWb2@fR3 | milhouse.van.houten@springfield.com        |
| krusty   | Wc5XsLa8@qJz | krusty.clown@krustycorp.com                |
| skinner  | Lm9BjTp1$kLx | seymour.skinner@springfieldschool.edu      |
| ralph    | Xe1TzQw7&uVo | ralph.wiggum@springfieldschool.edu         |
| willie   | Og6SpMw3%zRt | groundskeeper.willie@springfieldschool.edu |
| smithers | Pv8LsGh0^mZs | waylon.smithers@burnsindustries.com        |
| burns    | Kt2MfPj6*zNp | montgomery.burns@burnsindustries.com       |
| otto     | Zy5KwXc4$uYq | otto.busdriver@springfield.com             |
| barney   | Dj7NqEe9^hKw | barney.gumble@moesbar.com                  |
| edna     | Qa3LtVs2@jPw | edna.krabappel@springfieldschool.edu       |
| nelson   | Xv4HyCg1!eZn | nelson.muntz@springfieldschool.edu         |
| lenny    | Fs9BwTr6&kVm | lenny.leonard@powerplant.com               |
| carl     | Jq8XyZt3#pLk | carl.carlson@powerplant.com                |
| patty    | Wm2QvJg5^nXo | patty.bouvier@dmv.gov                      |
| selma    | Yh6KpRz8@qJw | selma.bouvier@dmv.gov                      |
| snowball | Qw2ErTy6&uIo |                                            |
| blinky   | Zy5KwXc4$uYq |                                            |
+----------+--------------+--------------------------------------------+
```
The LEFT JOIN includes all records from the left table (users) and the 
matched records from the right table (mails). 

```sql
-- Find users who do NOT have an email address
SELECT users.username FROM users LEFT JOIN mails ON users.id = mails.user_id WHERE mails.id IS NULL;
+----------+
| username |
+----------+
| blinky   |
| snowball |
+----------+
```

```sql
-- Count how many users do NOT have email addresses
SELECT COUNT(*) FROM users LEFT JOIN mails ON users.id = mails.user_id WHERE mails.id IS NULL;
+----------+
| COUNT(*) |
+----------+
| 2        |
+----------+
```


## Update Data

```sql
-- Change a user’s email address
UPDATE mails SET address = 'homer.simpson@powerplant.com' WHERE user_id = (SELECT id FROM users WHERE username = 'homer');
SELECT users.username, mails.address FROM users JOIN mails ON users.id = mails.user_id WHERE users.username='homer';
+----------+------------------------------+
| username |           address            |
+----------+------------------------------+
| homer    | homer.simpson@powerplant.com |
+----------+------------------------------+
```

## Delete Data

```sql
-- Delete user (ON DELETE CASCADE)
SELECT * FROM users WHERE id = 6;
+----+----------+--------------+
| id | username |   password   |
+----+----------+--------------+
| 6  | moe      | Bc8KpYi7!5Lo |
+----+----------+--------------+

SELECT * FROM mails WHERE user_id = 6;
+----+---------+-----------------------------+
| id | user_id |           address           |
+----+---------+-----------------------------+
| 6  | 6       | moe.szyslak@springfield.com |
+----+---------+-----------------------------+

DELETE FROM users WHERE id = 6;

SELECT * FROM users WHERE id = 6;

SELECT * FROM mails WHERE user_id = 6;
```

*Egon Teiniker, 2020-2025, GPL v3.0*