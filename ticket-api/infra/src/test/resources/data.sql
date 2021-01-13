insert into event(id, created_at, status, name, website, price, event_date)
values (100, now(), 1, 'Biggest Event In The World', 'www.biggestevent.com', 119.99, DATE_ADD(now(), interval 1 month));