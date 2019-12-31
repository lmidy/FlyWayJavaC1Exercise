-- write sql to create member table
CREATE TABLE member
(   id INT AUTO_INCREMENT,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    age INT(100) NOT NULL,
    gender CHAR(1),
    balance DOUBLE NOT NULL,
    primary key(id)
);
