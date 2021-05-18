insert into payment (id, created_at, status, updated_at, account_id, price, reference_code, state)
values
       (1, now(), 1, null, 1001, 100.11, 'test ref code', 'SUCCESS'),
       (2, now(), 1, null, 6662, 100.11, 'test ref code', 'SUCCESS');