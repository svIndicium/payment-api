create table transaction
(
    id uuid not null
        constraint transaction_pkey
            primary key,
    amount double precision,
    created_at timestamp,
    finished_at timestamp,
    status integer,
    updated_at timestamp,
    payment_id uuid
        constraint fk_transaction_payment
            references payment
);
