CREATE TABLE program (
    id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    career_id VARCHAR NOT NULL,
    valid_from DATE NOT NULL,
    valid_to DATE,
    hours SMALLSERIAL NOT NULL,
    points SMALLSERIAL NOT NULL,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp,
    deactivated_at TIMESTAMP,
  	FOREIGN KEY (career_id) REFERENCES career (id)
);
