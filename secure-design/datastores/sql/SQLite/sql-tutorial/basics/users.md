# Example: users Table

## Setup Table

```sql
CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
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
```

## Query Data 

```sql
.mode table
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
SELECT username FROM users WHERE username LIKE 's%';
+----------+
| username |
+----------+
| selma    |
| skinner  |
| smithers |
+----------+
```

```sql
SELECT username FROM users WHERE username LIKE '%e';
+----------+
| username |
+----------+
| maggie   |
| marge    |
| milhouse |
| moe      |
| willie   |
+----------+
```

```sql
SELECT username FROM users ORDER BY username DESC;
+----------+
| username |
+----------+
| willie   |
| smithers |
| skinner  |
| selma    |
| ralph    |
| patty    |
| otto     |
| nelson   |
| ned      |
| moe      |
| milhouse |
| marge    |
| maggie   |
| lisa     |
| lenny    |
| krusty   |
| homer    |
| edna     |
| carl     |
| burns    |
| bart     |
| barney   |
| apu      |
+----------+
```

```sql
SELECT username FROM users ORDER BY username LIMIT 10;
+----------+
| username |
+----------+
| apu      |
| barney   |
| bart     |
| burns    |
| carl     |
| edna     |
| homer    |
| krusty   |
| lenny    |
| lisa     |
+----------+
```

```sql
SELECT * FROM users WHERE LENGTH(username) < 5;
+----+----------+--------------+
| id | username |   password   |
+----+----------+--------------+
| 3  | bart     | rE2vLmP93*Wq |
| 4  | lisa     | Mz4FgQt1@zU8 |
| 6  | moe      | Bc8KpYi7!5Lo |
| 7  | apu      | Gh1ZxRt5^cE2 |
| 8  | ned      | Ue3NpHw9%tPq |
| 16 | otto     | Zy5KwXc4$uYq |
| 18 | edna     | Qa3LtVs2@jPw |
| 21 | carl     | Jq8XyZt3#pLk |
+----+----------+--------------+
```

```sql
SELECT * FROM users WHERE username = 'otto' AND password = 'Zy5KwXc4$uYq';
+----+----------+--------------+
| id | username |   password   |
+----+----------+--------------+
| 16 | otto     | Zy5KwXc4$uYq |
+----+----------+--------------+
```

```sql
SELECT * FROM users WHERE id >= 10 AND id <= 20;
+----+----------+--------------+
| id | username |   password   |
+----+----------+--------------+
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
+----+----------+--------------+
```

## Update Data 

```sql
UPDATE users SET password = 'burger' WHERE username = 'krusty';
SELECT * FROM users WHERE username = 'krusty';
+----+----------+----------+
| id | username | password |
+----+----------+----------+
| 10 | krusty   | burger   |
+----+----------+----------+
```

```sql
UPDATE users SET password = 'duffbeer' WHERE id = 17;
SELECT * FROM users WHERE id = 17;
+----+----------+----------+
| id | username | password |
+----+----------+----------+
| 17 | barney   | duffbeer |
+----+----------+----------+
```



## Delete Data 

```sql
DELETE FROM users WHERE id = 11;
```

```sql
DROP TABLE users;
.tables
```

*Egon Teiniker, 2020-2025, GPL v3.0*
