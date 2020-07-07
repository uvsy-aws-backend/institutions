CREATE TABLE subject (
    id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    codename VARCHAR,
    program_id VARCHAR NOT NULL,
    level SMALLINT NOT NULL,
    hours SMALLINT NOT NULL,
    points SMALLINT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    optative BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp,
   	FOREIGN KEY (program_id) REFERENCES program (id)
);
