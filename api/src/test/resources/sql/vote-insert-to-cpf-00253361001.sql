INSERT INTO schedule(code, title, dat_creation, dat_update) VALUES ('8de44aec-d624-44d7-b14b-d342fc0bf142', 'title', '2020-07-29T19:29:50.684+00:00', '2020-07-29T19:29:50.684+00:00')
;
INSERT INTO vote(code, option, dat_creation, cpf, code_schedule) VALUES (RANDOM_UUID(), 'YES', '2020-07-29T19:29:50.684+00:00', '00253361001', (select code from schedule))
;