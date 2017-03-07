
CREATE TABLE user 
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(64) NOT NULL
)ENGINE = INNODB;

INSERT INTO user (firstname,lastname,username,password) VALUES ('student', 'student', 'student', '$2a$10$D3pgXBhfT4BzMozzUBQWG.s1cwlGNrU0CAeiMA.xLSNGnU9niLBDq');
	