create table auth.user
(
    id            varchar(36) not null,
    created_date  timestamp,
    date_of_birth date,
    email         varchar(255),
    first_name    varchar(255),
    last_name     varchar(255),
    password      varchar(255),
    phone_number  int4,
    primary key (id)
)
