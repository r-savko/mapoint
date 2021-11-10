CREATE TABLE offer_dates
(
  id SERIAL,
  offer_id integer,
  start_date DATE,
  end_date date,
  PRIMARY KEY (id),
  FOREIGN KEY (offer_id) REFERENCES offers (id)
);

CREATE TABLE offer_sessions
(
  id SERIAL,
  offer_date_id integer,
  time TIME,
  PRIMARY KEY (id),
  FOREIGN KEY (offer_date_id) REFERENCES offer_dates (id)
);

ALTER TABLE offers DROP COLUMN start_date, DROP COLUMN end_date, DROP COLUMN price;

