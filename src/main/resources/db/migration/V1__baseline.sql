create table audit_log
(
    id       serial primary key,
    type     text         not null,
    subtype  text         not null,
    date     timestamp    not null,
    message  text         not null,
    username varchar(255) not null,
    hash     varchar(255) not null
);

create table last_hash
(
    id   serial primary key,
    hash varchar(255) not null
);
