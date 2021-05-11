create schema if not exists ticket character set = utf8mb4 collate = utf8mb4_unicode_ci;
create schema if not exists payment character set = utf8mb4 collate = utf8mb4_unicode_ci;

-- #####################################
use payment;
-- #####################################

create table if not exists payment
(
    id             bigint auto_increment primary key,
    created_at     datetime       not null,
    updated_at     datetime       null,
    status         int default 0  not null comment '-1: deleted, 0:passive, 1:active',
    state          varchar(255)   not null comment 'SUCCESS, FAIL, PENDING',
    price          decimal(30, 8) not null comment 'price in try',
    reference_code varchar(255)   not null,
    account_id     bigint         not null
);

create table balance
(
    id         bigint auto_increment primary key,
    created_at datetime       not null,
    updated_at datetime       null,
    status     int default 0  not null comment '-1: deleted, 0:passive, 1:active',
    account_id bigint         not null,
    amount     decimal(19, 2) not null
);

create table balance_transaction
(
    id         bigint auto_increment primary key,
    created_at datetime       not null,
    updated_at datetime(6)    null,
    status     int default 0  not null comment '-1: deleted, 0:passive, 1:active',
    amount     decimal(19, 2) not null,
    balance_id bigint         not null,
    type       varchar(255)   not null
);

-- #####################################
use ticket;
-- #####################################

create table if not exists event
(
    id         bigint auto_increment primary key,
    created_at datetime       not null,
    updated_at datetime       null,
    status     int default 0  not null comment '-1: deleted, 0:passive, 1:active',
    price      decimal(30, 8) not null comment 'price in try',
    name       varchar(255)   not null,
    website    varchar(255)   not null,
    event_date datetime       not null
);

create table if not exists ticket
(
    id          bigint auto_increment primary key,
    created_at  datetime       not null,
    updated_at  datetime       null,
    status      int default 0  not null comment '-1: deleted, 0:passive, 1:active',
    price       decimal(30, 8) not null comment 'price in try',
    count       int            not null,
    payment_date datetime       not null,
    account_id  bigint         not null,
    event_id    bigint         not null
);