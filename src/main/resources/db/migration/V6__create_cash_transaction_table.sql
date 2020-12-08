create table cash_transaction
(
    id uuid not null
        constraint cash_transaction_pkey
            primary key
        constraint fk_cash_transaction
            references transaction
);
