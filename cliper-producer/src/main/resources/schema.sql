create table cliper (
    id bigserial primary key auto_increment,
    entity_id varchar(100) not null,
    identifier varchar not null,
    status varchar not null,
    message_creation_time BIGINT
);

create table cliper_param (
    id bigserial primary key auto_increment,
    key varchar(100) not null,
    value varchar(100) not null,
    cliper_id bigint REFERENCES cliper(id)
);
