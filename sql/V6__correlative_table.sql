CREATE TABLE correlative (
    id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid(),
    subject_id VARCHAR NOT NULL,
    correlative_subject_id VARCHAR NOT NULL,
    correlative_condition VARCHAR NOT NULL,
    correlative_restriction VARCHAR NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    updated_at TIMESTAMP DEFAULT current_timestamp,
  	FOREIGN KEY (subject_id) REFERENCES subject (id),
  	FOREIGN KEY (correlative_subject_id) REFERENCES subject (id)
);
