set @schedule_id = '8de44aec-d624-44d7-b14b-d342fc0bf14e';

INSERT INTO schedule(code, title, dat_creation, dat_update) VALUES (@schedule_id, 'title', '2020-07-29T19:29:50.684+00:00', '2020-07-29T19:29:50.684+00:00');
INSERT INTO vote(code, option, dat_creation, cpf, code_schedule) VALUES (RANDOM_UUID(), 'YES', '2020-07-29T19:29:50.684+00:00', '00253361001', @schedule_id);