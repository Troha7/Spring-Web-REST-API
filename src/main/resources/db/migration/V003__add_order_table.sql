-- Creating a table
CREATE TABLE IF NOT EXISTS my_store.order
(
    id      SERIAL CONSTRAINT order_id_pkey PRIMARY KEY,
    date    DATE,
    price   DOUBLE PRECISION
);