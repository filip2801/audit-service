create table audit
(
    id       serial primary key,
    type     text         not null,
    subtype  text         not null,
    date     timestamp    not null,
    message  text         not null,
    username varchar(255) not null,
    hash     varchar(255) not null
);
