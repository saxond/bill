
CREATE TABLE contacts(
    id varchar(80) NOT NULL,
    avatar varchar(255),
    first varchar(255) NOT NULL,
    last varchar(255) NOT NULL,
    twitter varchar(255),
    notes varchar(1024),
    PRIMARY KEY (id)
);
