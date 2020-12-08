create table payment
(
    id uuid not null
        constraint payment_pkey
            primary key,
    amount double precision,
    payment_details_created_at timestamp,
    payment_details_description varchar(255),
    payment_status integer
);

