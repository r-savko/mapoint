CREATE SEQUENCE public.subcategories_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.subcategories_id_seq
  OWNER TO postgres;
  
CREATE TABLE subcategories
( id integer NOT NULL DEFAULT nextval('subcategories_id_seq'::regclass),
  name character varying,
  created_at timestamp without time zone NOT NULL,
  updated_at timestamp without time zone NOT NULL,
  category_id integer,
  PRIMARY KEY (id),
  FOREIGN KEY (category_id) REFERENCES categories (id) );

ALTER TABLE events RENAME TO offers;
ALTER TABLE categories_events RENAME TO subcategories_offers;
ALTER TABLE subcategories_offers RENAME COLUMN category_id TO subcategory_id;
ALTER TABLE subcategories_offers RENAME COLUMN event_id TO offer_id;

INSERT INTO categories (name, created_at, updated_at)
VALUES ('Event', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO categories (name, created_at, updated_at)
VALUES ('Food & Drinks', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO categories (name, created_at, updated_at)
VALUES ('Shopping', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO categories (name, created_at, updated_at)
VALUES ('Services', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Concert', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Event'));
INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Party', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Event'));
INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Education', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Event'));
INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Exhibition', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Event'));
INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Theatre', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Event'));
INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Sport', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Event'));
INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Cinema', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Event'));
INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Quest', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Event'));
INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Other events', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Event'));

INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Bar/Pub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Food & Drinks'));
INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Cafe/Restaurant', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Food & Drinks'));
INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Hookah bar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Food & Drinks'));
INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Other', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Food & Drinks'));
																	  
INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Clothes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Shopping'));
INSERT INTO subcategories (name, created_at, updated_at, category_id)
VALUES ('Shoes', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Shopping'));
INSERT INTO subcategories (name, created_at, updated_at, category_id)
VALUES ('Supermarket', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Shopping'));
INSERT INTO subcategories (name, created_at, updated_at, category_id)
VALUES ('Provisions', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Shopping'));
INSERT INTO subcategories (name, created_at, updated_at, category_id)
VALUES ('Sport requisites', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Shopping'));
INSERT INTO subcategories (name, created_at, updated_at, category_id)
VALUES ('Household goods', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Shopping'));
INSERT INTO subcategories (name, created_at, updated_at, category_id)
VALUES ('Jewellery', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Shopping'));
INSERT INTO subcategories (name, created_at, updated_at, category_id)
VALUES ('Books', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Shopping'));
INSERT INTO subcategories (name, created_at, updated_at, category_id)
VALUES ('Other', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Shopping'));

INSERT INTO subcategories (name, created_at, updated_at, category_id) 
VALUES ('Rental saloon', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Services'));
INSERT INTO subcategories (name, created_at, updated_at, category_id)
VALUES ('Beauty saloon', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Services'));
INSERT INTO subcategories (name, created_at, updated_at, category_id)
VALUES ('Hairdressing saloon', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM categories WHERE name = 'Services'));																  