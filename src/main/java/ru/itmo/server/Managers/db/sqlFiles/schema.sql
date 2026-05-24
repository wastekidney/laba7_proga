CREATE SEQUENCE users_id_seq;
CREATE SEQUENCE address_id_seq;
CREATE SEQUENCE coordinates_id_seq;
CREATE SEQUENCE organization_id_seq;
CREATE SEQUENCE product_id_seq;

CREATE TABLE users (
                       id INTEGER PRIMARY KEY DEFAULT nextval('users_id_seq'),
                       login TEXT UNIQUE NOT NULL,
                       password_hash TEXT NOT NULL
);
ALTER SEQUENCE users_id_seq OWNED BY users.id;

CREATE TABLE address (
                         id INTEGER PRIMARY KEY DEFAULT nextval('address_id_seq'),
                         street TEXT
);
ALTER SEQUENCE address_id_seq OWNED BY address.id;

CREATE TABLE coordinates (
                             id INTEGER PRIMARY KEY DEFAULT nextval('coordinates_id_seq'),
                             x DOUBLE PRECISION NOT NULL,
                             y REAL NOT NULL
);
ALTER SEQUENCE coordinates_id_seq OWNED BY coordinates.id;

CREATE TABLE organization (
                              id INTEGER PRIMARY KEY DEFAULT nextval('organization_id_seq'),
                              name TEXT NOT NULL,
                              annual_turnover DOUBLE PRECISION,
                              type TEXT NOT NULL,
                              official_address_id INTEGER NOT NULL REFERENCES address(id),
                              CONSTRAINT chk_organization_name CHECK (name <> ''),
                              CONSTRAINT chk_annual_turnover_positive CHECK (annual_turnover IS NULL OR annual_turnover > 0),
                              CONSTRAINT chk_organization_type CHECK (type IN ('PUBLIC', 'GOVERNMENT', 'PRIVATE_LIMITED_COMPANY', 'OPEN_JOINT_STOCK_COMPANY'))
);
ALTER SEQUENCE organization_id_seq OWNED BY organization.id;

CREATE TABLE product (
                         id BIGINT PRIMARY KEY DEFAULT nextval('product_id_seq'),
                         name TEXT NOT NULL,
                         coordinates_id INTEGER NOT NULL REFERENCES coordinates(id),
                         creation_date TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                         price REAL NOT NULL,
                         part_number TEXT NOT NULL UNIQUE,
                         manufacture_cost DOUBLE PRECISION,
                         unit_of_measure TEXT,
                         manufacturer_id INTEGER NOT NULL REFERENCES organization(id),
                         user_id INTEGER NOT NULL REFERENCES users(id),
                         CONSTRAINT chk_product_name CHECK (name <> ''),
                         CONSTRAINT chk_price_positive CHECK (price > 0),
                         CONSTRAINT chk_part_number_length CHECK (char_length(part_number) BETWEEN 26 AND 67),
                         CONSTRAINT chk_unit_of_measure CHECK (unit_of_measure IS NULL OR unit_of_measure IN ('KILOGRAMS', 'METERS', 'PCS', 'LITERS', 'GRAMS'))
);
ALTER SEQUENCE product_id_seq OWNED BY product.id;

CREATE INDEX idx_product_user_id ON product(user_id);
CREATE INDEX idx_product_part_number ON product(part_number);