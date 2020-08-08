CREATE TABLE program (
    id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    career_id VARCHAR NOT NULL,
    year_from SMALLINT NOT NULL,
    year_to SMALLINT,
    south_hemisphere BOOLEAN NOT NULL DEFAULT FALSE,
    hours SMALLINT NOT NULL,
    points SMALLINT NOT NULL,
    amount_of_subjects SMALLINT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp,
  	FOREIGN KEY (career_id) REFERENCES career (id)
);
