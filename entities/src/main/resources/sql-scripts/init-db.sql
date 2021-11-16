INSERT INTO persone (id_persone, user_name, email) VALUES (1, 'user1', 'krneki@krneki.com');
INSERT INTO item (id_item, title, description, daily_price, id_persone, tag) VALUES (11, 'neki', 'neki', 5, 1, 'neki');
-- INSERT INTO persone (id_persone, user_name, email) VALUES (1, 'user1', 'krneki@krneki.com');
INSERT INTO event (id_event, title, description, start_date, end_date, id_persone, tag) VALUES (21, 'title', 'description', '2021-05-05', '2021-06-06', 1, 'tag');