
CREATE TABLE user 
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(32) NOT NULL
)ENGINE = INNODB;

INSERT INTO user (firstname,lastname,username,password) VALUES ('Homer', 'Simpson', 'homer', 'homer');
INSERT INTO user (firstname,lastname,username,password) VALUES ('Marge', 'Simpson', 'marge', 'marge');
INSERT INTO user (firstname,lastname,username,password) VALUES ('Bart', 'Simpson', 'bart', 'bart');
INSERT INTO user (firstname,lastname,username,password) VALUES ('Lisa', 'Simpson', 'lisa', 'lisa');
INSERT INTO user (firstname,lastname,username,password) VALUES ('Maggie', 'Simpson', 'magie', 'maggie');
INSERT INTO user (firstname,lastname,username,password) VALUES ('Montgomery', 'Burns', 'monty', 'monty');
	