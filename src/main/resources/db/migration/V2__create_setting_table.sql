create table setting
(
    key         varchar(255) not null
        constraint setting_pkey
            primary key,
    description varchar(255),
    permission  varchar(255),
    title       varchar(255),
    updated_at  timestamp,
    updated_by  varchar(255),
    value       varchar(255) not null
);