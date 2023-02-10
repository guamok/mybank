INSERT INTO fruit(id, name) VALUES (nextval('hibernate_sequence'), 'Cherry');
INSERT INTO fruit(id, name) VALUES (nextval('hibernate_sequence'), 'Apple');
INSERT INTO fruit(id, name) VALUES (nextval('hibernate_sequence'), 'Banana');
INSERT INTO transact (id, reference,account_iban,date,amount,fee,description) VALUES (nextval('hibernate_sequence'), '12345A','ES9820385778983000760236','2019-07-16T16:55:42.000Z',193.38,3.18,'Restaurant payment');
INSERT INTO transact (id, reference,account_iban,date,amount,fee,description) VALUES (nextval('hibernate_sequence'), '12345B','ES9820385778983000760236','2019-07-17T16:55:42.000Z',50.38,3.18,'Same account iban R.payment');
INSERT INTO transact (id, reference,account_iban,date,amount,fee,description) VALUES (nextval('hibernate_sequence'), '12345C','ES9820385778983000760236','2019-07-17T16:55:42.000Z',3.5,3.18,'Same account iban R.payment');
INSERT INTO transact (id, reference,account_iban,date,amount,fee,description) VALUES (nextval('hibernate_sequence'), '12345D','ES9820385778983000760237','2019-07-17T16:55:42.000Z',20.4,3.18,'Housekeeping payment');
INSERT INTO status(id, reference, status) VALUES (nextval('hibernate_sequence'), '12345D', 'INVALID');