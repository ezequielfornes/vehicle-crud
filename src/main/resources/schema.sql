CREATE TABLE IF NOT EXISTS vehicle (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    license_plate VARCHAR(255) NOT NULL,
    color VARCHAR(255),
    year INT
    );
