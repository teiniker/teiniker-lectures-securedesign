
CREATE TABLE article 
(
	ID integer not null,
	DESCRIPTION varchar(255),
	PRICE bigint,
	primary key (ID)
) ENGINE = INNODB;

CREATE TABLE hibernate_sequence 
(
	next_val bigint
) ENGINE = INNODB;

INSERT INTO article (id, description, price) VALUES (1, 'Design Patterns', 4295);
INSERT INTO article (id, description, price) VALUES (2, 'Effective Java (2nd Edition)', 3336);

insert into hibernate_sequence values ( 100 );	