alter table setting drop column description;

alter table setting rename column permission to read_permission;

alter table setting drop column title;

alter table setting
    add write_permission varchar(255);

alter table setting
    add write_only boolean default false;