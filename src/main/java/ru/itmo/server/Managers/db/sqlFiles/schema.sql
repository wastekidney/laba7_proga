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
CREATE SEQUENCE product_id_seq;

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
                         id BIGSERIAL PRIMARY KEY,
                         name TEXT NOT NULL,
                         x DOUBLE PRECISION NOT NULL,
                         y REAL NOT NULL,
                         creation_date TIMESTAMPTZ DEFAULT NOW(),
                         price REAL NOT NULL,
                         part_number TEXT NOT NULL UNIQUE,
                         manufacture_cost DOUBLE PRECISION,
                         unit_of_measure TEXT,
                         organization_name TEXT NOT NULL,
                         annual_turnover DOUBLE PRECISION,
                         organization_type TEXT,
                         street TEXT,
                         user_id INTEGER NOT NULL REFERENCES users(id)
);
ALTER SEQUENCE product_id_seq OWNED BY product.id;

CREATE INDEX idx_product_user_id ON product(user_id);
CREATE INDEX idx_product_part_number ON product(part_number);