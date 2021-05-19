use payment;
truncate table balance;
truncate table balance_transaction;
truncate table payment;

use ticket;
truncate table meetup;
truncate table ticket;

insert into meetup(id, created_at, updated_at, status, name, website, price, event_date)
values (1, now(), null, 1, 'Java Day Istanbul 2021', 'javaday.istanbul', 90.00, '2021-05-28 15:00:00');