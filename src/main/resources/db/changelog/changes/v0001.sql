CREATE TABLE IF NOT EXISTS report (
    report_id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY,
    missing_person_name VARCHAR(100) NOT NULL,
    reporter_name VARCHAR(100) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    report_time TIMESTAMP WITH TIME ZONE NOT NULL,
    last_seen_time TIMESTAMP WITH TIME ZONE NOT NULL,
    last_known_location VARCHAR(1000) NOT NULL,
    additional_info VARCHAR(1000) NOT NULL,
    image_uri VARCHAR(100) NOT NULL,
    is_found BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS sighting (
    sighting_id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY,
    report_id INTEGER NOT NULL,
    sighting_time TIMESTAMP WITH TIME ZONE NOT NULL,
    sighting_location VARCHAR(1000) NOT NULL,
    image_uri VARCHAR(100) NOT NULL,
    additional_info VARCHAR(1000) NOT NULL,
    CONSTRAINT fk_sighting
        FOREIGN KEY (report_id)
        REFERENCES report (report_id)
        ON DELETE CASCADE
);
