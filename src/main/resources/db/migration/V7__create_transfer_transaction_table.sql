create table transfer_transaction
(
    description varchar(255),
    transferred_at timestamp,
    id uuid not null
        constraint transfer_transaction_pkey
            primary key
        constraint fk_transfer_transaction
            references transaction
);

