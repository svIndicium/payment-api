create table ideal_transaction
(
    checkout_url varchar(255),
    expires_at timestamp,
    external_id varchar(255),
    payment_provider varchar(255),
    redirect_url varchar(255),
    webhook_url varchar(255),
    id uuid not null
        constraint ideal_transaction_pkey
            primary key
        constraint fk_ideal_transaction
            references transaction
);
