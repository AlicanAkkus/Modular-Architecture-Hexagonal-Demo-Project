insert into meetup(id, created_at, status, name, website, price, event_date)
values (2001, now(), 1, 'Biggest Event In The World', 'www.biggestevent.com', 119.99, DATE_ADD(now(), interval 1 month));