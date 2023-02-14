-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
insert into account (id, amount, customerid, createdat)
values (nextval('hibernate_sequence'), 500.00, 1, '2023.01.01');
insert into account (id, amount, customerid, createdat, deletedat)
values (nextval('hibernate_sequence'), 600.00, 3, '2023.01.01', '2023.01.01');
insert into account (id, amount, customerid, createdat)
values (nextval('hibernate_sequence'), 700.00, 2, '2023.01.01');

insert into credit (id, amount, balance, customerid, dues, paymentduedate, createdat)
values (nextval('hibernate_sequence'), 800.00, 500.00, 1, 8, '10', '2023.01.01');
insert into credit (id, amount, balance, customerid, dues, paymentduedate, createdat, deletedat)
values (nextval('hibernate_sequence'), 500.00, 500.00, 2, 5, '12', '2023.01.01', '2023.01.01');
insert into credit (id, amount, balance, customerid, dues, paymentduedate, createdat)
values (nextval('hibernate_sequence'), 1000.00, 500.00, 3, 10, '15', '2023.01.01');

insert into lineofcredit (id, amount, available, closingdate, costs, customerid, paymentduedate, createdat)
values (nextval('hibernate_sequence'), 800.00, 500.00, '10', 300.00, 1, '12', '2023.01.01');
insert into lineofcredit (id, amount, available, closingdate, costs, customerid, paymentduedate, createdat, deletedat)
values (nextval('hibernate_sequence'), 500.00, 200.00, '12', 300.00, 2, '15', '2023.01.01', '2023.01.01');
insert into lineofcredit (id, amount, available, closingdate, costs, customerid, paymentduedate, createdat)
values (nextval('hibernate_sequence'), 1000.00, 500.00, '15', 500.00, 3, '20', '2023.01.01');