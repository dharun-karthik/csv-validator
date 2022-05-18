CREATE TABLE IF NOT EXISTS csv_configuration
(
    config_id   SERIAL PRIMARY KEY,
    config_name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS fields
(
    field_id        SERIAL PRIMARY KEY,
    config_id       INT,
    field_name      VARCHAR NOT NULL,
    field_type      VARCHAR NOT NULL,
    is_null_allowed VARCHAR(2),
    pattern         VARCHAR,
    fixed_length    INT,
    min_length      INT,
    max_length      INT,
    CONSTRAINT fk_config
        FOREIGN KEY (config_id)
            REFERENCES csv_configuration (config_id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS allowed_values
(
    value_id   SERIAL PRIMARY KEY,
    field_id   INT,
    value_name VARCHAR NOT NULL,
    CONSTRAINT fk_field_id
        FOREIGN KEY (field_id)
            REFERENCES fields (field_id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dependencies
(
    dependency_id            SERIAL PRIMARY KEY,
    field_id                 INT,
    dependent_on             VARCHAR NOT NULL,
    expected_dependent_value VARCHAR,
    expected_current_value   VARCHAR,
    CONSTRAINT fk_field_id
        FOREIGN KEY (field_id)
            REFERENCES fields (field_id)
            ON DELETE CASCADE
);