create sequence product_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE products (
    id bigint DEFAULT nextval('product_id_seq') NOT NULL,
    code text not null unique,
    name text NOT NULL, 
    description text,
    image_url text,
    price numeric not null,
    PRIMARY KEY (id)
);

