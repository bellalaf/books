CREATE TABLE IF NOT EXISTS BOOKS_2 (
                          ID                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
                          TITLE                  VARCHAR      NOT NULL,
                          AUTHOR                 VARCHAR,
                          RATING                 VARCHAR,
                          NUM_PAGES              VARCHAR,
                          DATE_READ              VARCHAR,
                          REVIEW                 VARCHAR,
                          SHELVES                VARCHAR
);