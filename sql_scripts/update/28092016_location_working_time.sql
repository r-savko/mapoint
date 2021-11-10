CREATE TABLE location_working_times
(
  id SERIAL,
  location_id integer,
  day_number integer,
  start_time time,
  end_time time,
  PRIMARY KEY (id),
  FOREIGN KEY (location_id) REFERENCES locations (id)
);

