
CREATE TABLE user 
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(32) NOT NULL
)ENGINE = INNODB;

INSERT INTO user (firstname,lastname,username,password) VALUES ('student', 'student', 'student', 'student');
	