CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(200) NOT NULL,
                          description TEXT,
                          price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
                          stock_quantity INT NOT NULL CHECK (stock_quantity >= 0),
                          category_id BIGINT NOT NULL REFERENCES categories(id),
                          version BIGINT NOT NULL DEFAULT 0
);