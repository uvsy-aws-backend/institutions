CREATE TABLE career (
    id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    codename VARCHAR  NOT NULL,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    institution_id VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp,
  	FOREIGN KEY (institution_id) REFERENCES institution (id)
);
