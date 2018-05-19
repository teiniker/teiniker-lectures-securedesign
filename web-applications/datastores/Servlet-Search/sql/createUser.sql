
CREATE TABLE user 
(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL
)ENGINE = INNODB;

INSERT INTO user (firstname,lastname,username,password) VALUES ('Homer', 'Simpson', 'homer', 'Kqq3lbODaQT4LvxsoihdknrtdSBiFOHaODQY65DJBS8=');
INSERT INTO user (firstname,lastname,username,password) VALUES ('Marge', 'Simpson', 'marge', 'tLgR+kBQUymuhx5S8DUnw3IMmvf7hgeBllhTXFSExB4=');
INSERT INTO user (firstname,lastname,username,password) VALUES ('Bart', 'Simpson', 'bart', 'lVHa2/dqJ0V5RucNGuvr4hMvjTvOY3jSFsEYU1JN06Y=');
INSERT INTO user (firstname,lastname,username,password) VALUES ('Lisa', 'Simpson', 'lisa', '2E/n4HvtsifP//EACRUdlvyUT2ob03z/YOjkYmoescM=');
INSERT INTO user (firstname,lastname,username,password) VALUES ('Maggie', 'Simpson', 'magie', 'quW+X2R0kEtob2OeD8/SvkQBIc2In6OBqUtxdQdYNF4=');
INSERT INTO user (firstname,lastname,username,password) VALUES ('Montgomery', 'Burns', 'monty', '+OsxFkIi1zQuErlJ5D+0zF5r8t46FvxITh4E0HjeLOo=');
	