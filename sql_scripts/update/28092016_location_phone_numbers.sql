CREATE TABLE location_phones
(
  id SERIAL,
  location_id integer,
  phone_number varchar(100),
  PRIMARY KEY (id),
  FOREIGN KEY (location_id) REFERENCES locations (id)
);

