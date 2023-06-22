create table cliper (
    id bigint primary key auto_increment,
    entity_id varchar(100) not null,
    identifier varchar not null,
    status varchar not null,
    message_creation_time BIGINT
);

create table cliper_param (
    id bigint primary key auto_increment,
    param_key varchar(100) not null,
    param_value varchar(100) not null,
    cliper_id bigint REFERENCES cliper(id)
);
