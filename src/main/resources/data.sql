INSERT INTO BASE_USER (ID, EMAIL, NAME) VALUES
    (1, 'lorenzo.galvao@mail.com', 'Lorenzo Alexandre Arthur Galvao'),
    (2, 'nathan.paz@mail.com', 'Nathan Manoel da Paz'),
    (3, 'bento.dias@mail.com', 'Bento Vinicius Dias'),
    (4, 'evelyn.moura@mail.com', 'Evelyn Sebastiana Moura'),
    (5, 'betina.mata@mail.com', 'Betina Yasmin da Mata'),
    (6, 'arthur.assuncao@mail.com', 'Arthur Filipe Nelson Assunção');

INSERT INTO ADMIN (USER_ID, PHONE_NUMBER) VALUES
    (1, '(67) 99819-5683'),
    (3, '(48) 99872-5476'),
    (5, '(61) 99458-5135');

INSERT INTO ATTENDEE (USER_ID, BALANCE) VALUES
    (2,752.0),
    (4,2980.0),
    (6, 1882.0);

INSERT INTO EVENT (ID, NAME, DESCRIPTION, EMAIL_CONTACT, START_DATE, END_DATE, START_TIME, END_TIME, AMOUNT_FREE_TICKETS, AMOUNT_PAYED_TICKETS, PRICE_TICKET, ADMIN_ID) VALUES
    (1, 'Habitasse vivamus', 'Id nulla interdum curae, habitasse', 'boical@mail.com', '2021-07-21', '2021-07-25', '19:00:00', '23:59:59', 965, 986, 220.0, 5),
    (2, 'Convallis dolor', 'Potenti habitasse vivamus aenean, tristique', 'tudaur@mail.com', '2021-10-16', '2021-10-17', '15:00:00', '23:59:59', 891, 513, 892.0, 1),
    (3, 'Vivamus potenti', 'Interdum vel dui fusce, facilisis', 'piriwa@mail.com', '2021-12-26', '2022-01-01', '00:00:00', '23:59:59', 85, 952, 650.0, 3);

INSERT INTO PLACE (ID, NAME, ADDRESS) VALUES
    (1, 'Ad risus', 'Rua Blandina Castelo Branco, Jardim Floresta - Boa Vista/RR'),
    (2, 'Phasellus tortor', 'Rua Maria Gomes de Souza, Dinamérica - Campina Grande/PB'),
    (3, 'Fringilla aliquam', 'Rua da Arca, Jardim Colégio de Passos - Passos/MG');

INSERT INTO EVENT_PLACE (EVENT_ID, PLACE_ID) VALUES
    (1, 2),
    (3, 1),
    (2, 3);

INSERT INTO TICKET (ID, DATE, TYPE, PRICE, ATTENDEE_ID, EVENT_ID) VALUES
    (1, '2021-09-07', 'FREE', 0.0, 2, 2),
    (2, '2021-10-29', 'PAID', 650.0, 4, 3),
    (3, '2021-06-09', 'PAID', 220.0, 6, 1);
