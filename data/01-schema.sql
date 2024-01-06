
CREATE TABLE contacts(
    id varchar(80) NOT NULL,
    avatar varchar(255),
    first varchar(255) NOT NULL,
    last varchar(255) NOT NULL,
    twitter varchar(255),
    notes varchar(1024),
    PRIMARY KEY (id)
);

CREATE TABLE users(
    email varchar(255) NOT NULL,
    family_name varchar(255) NOT NULL,
    given_name varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    id varchar(80) NOT NULL,
    locale varchar(10) NOT NULL,
    picture varchar(1024),
    verified_email BOOLEAN,
    enabled BOOLEAN,
    admin BOOLEAN,
    PRIMARY KEY (email)
);
