CREATE TABLE IF NOT EXISTS autores (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE,
    biografia VARCHAR(500),
    nacionalidad VARCHAR(50),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_nombre_apellido (nombre, apellido),
    INDEX idx_nacionalidad (nacionalidad)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;