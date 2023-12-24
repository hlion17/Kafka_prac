CREATE TABLE sourcedb.accounts
(
    account_id       VARCHAR(255),
    role_id          VARCHAR(255),
    user_name        VARCHAR(255),
    user_description VARCHAR(255),
    update_date      DATETIME DEFAULT CURRENT_TIMESTAMP not null,
    PRIMARY KEY (account_id)
);

DROP table sourcedb.accounts;

CREATE TABLE sinkdb.sink_test
(
    account_id       VARCHAR(255),
    role_id          VARCHAR(255),
    user_name        VARCHAR(255),
    user_description VARCHAR(255),
    update_date      DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (account_id)
);

CREATE TABLE sinkdb.message_sink
(
    id            int not null auto_increment,
    topic_name    VARCHAR(100),
    payload       json,
    creation_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE kafkasub.received_message
(
    id            int not null auto_increment,
    topic_name    VARCHAR(100),
    payload       json,
    creation_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

select * from sourcedb.accounts;
select * from sinkdb.sink_test;
select * from sinkdb.message_sink;
select * from kafkasub.received_message;

-- insert into sourcedb.accounts (account_id, role_id, user_name, user_description) values ('tester02', 'admin', 'tester02', 'user_tester');
