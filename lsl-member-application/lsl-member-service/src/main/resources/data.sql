INSERT INTO PROGRAM
(PROGRAM_ID,
 PROGRAM_NAME,
 PROGRAM_DESCRIPTION)
VALUES ('FF', 'Frequent Flyer', 'Our frequent flyers program rewards you with every flight you take and for purchases with our partners'),
       ('PL', 'Premiere Lounge', 'Our Premier Lounge program provides you access to our airport lounges'),
       ('ER', 'Extra Rewards', 'With Extra Rewards you will earn more points for both flights and purchases');

INSERT INTO MEMBER
(MEMBER_ID,
 ACCOUNT_STATUS,
 GIVEN_NAME,
 SURNAME,
 ENROLLED_SINCE,
 PREFERENCE)
VALUES ('110020', 'ACTIVE', 'Fred', 'Flintstone', '2017-08-12', 'CITY'),
       ('110021', 'ACTIVE', 'Wilma', 'Flintstone', '2017-09-15', 'BEACH'),
       ('110022', 'SUSPENDED', 'Barney', 'Rubble', '2017-09-16', 'NATURE'),
       ('110023', 'PENDING', 'Betty', 'Rubble', '2018-11-20', 'MOUNTAIN'),
       ('110024', 'PENDING', 'Bambam', 'Flintstone', '2018-12-31', 'SNOW');

INSERT INTO MEMBER_PROGRAM
(MEMBER_ID,
 PROGRAM_ID)
VALUES ('110020', 'FF'),
       ('110020', 'PL'),
       ('110020', 'ER'),
       ('110021', 'FF'),
       ('110021', 'PL'),
       ('110022', 'FF'),
       ('110023', 'FF');

INSERT INTO COUNTRY
(COUNTRY_ID,
 COUNTRY_CODE_1,
 COUNTRY_CODE_2,
 COUNTRY_NAME)
VALUES (36,  'AU', 'AUS', 'Australia'),
       (112, 'BY', 'BLR', 'Belarus'),
       (392,  'JP', 'JPN', 'Japan'),
       (620,  'PT', 'PRT', 'Portugal'),
       (840,  'US', 'USA', 'United States of America');

INSERT INTO ADDRESS
(ADDRESS_ID,
 ADDRESS_1,
 ADDRESS_2,
 CITY,
 POSTCODE,
 STATE,
 COUNTRY_ID)
VALUES (1, 'Qantas', '10 Bourke road', 'Mascot', '2020', 'NSW', '36'),
       (2, 'Sydney Opera House', 'Bennelong Point', 'Sydney', '2000', 'NSW', '36'),
       (3, 'Arrival Dr', NULL, 'Melbourne Airport', '3045', 'VIC', '36');

INSERT INTO MEMBER_ADDRESS
(MEMBER_ID,
 ADDRESS_ID,
 ADDRESS_CATEGORY)
VALUES ('110020', 1, 'POSTAL'),
       ('110020', 1, 'RESIDENTIAL'),
       ('110020', 2, 'POSTAL'),
       ('110021', 3, 'RESIDENTIAL');
