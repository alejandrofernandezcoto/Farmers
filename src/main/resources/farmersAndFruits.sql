-- Eliminar tablas si existen
DROP TABLE IF EXISTS fruit;
DROP TABLE IF EXISTS farmer;

-- Crear tabla farmer
CREATE TABLE farmer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255)
);

-- Crear tabla fruit
CREATE TABLE fruit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    farmer_id BIGINT NOT NULL,
    CONSTRAINT fk_fruit_farmer FOREIGN KEY (farmer_id) REFERENCES farmer(id) 
        ON DELETE RESTRICT ON UPDATE RESTRICT
);

-- Insertar datos en la tabla farmer
INSERT INTO farmer (id, name, location) 
VALUES
    (1000, 'Farmer Rick', 'Sa Pobla'),
    (2000, 'Morty Vegan', 'Es Vivero');

-- Insertar datos en la tabla fruit
INSERT INTO fruit (id, name, description, farmer_id) 
VALUES 
    (1000, 'Apple', 'Winter fruit', 1000),
    (2000, 'Pineapple', 'Tropical fruit', 2000);