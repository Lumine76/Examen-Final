CREATE TABLE IF NOT EXISTS resenas (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
libro_id BIGINT NOT NULL,
usuario_id BIGINT NOT NULL,
calificacion INT NOT NULL CHECK (calificacion BETWEEN 1 AND 5),
recomendado BOOLEAN,
fecha_resena TIMESTAMP NOT NULL,
fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
CONSTRAINT unique_libro_usuario UNIQUE (libro_id, usuario_id),
    );
INSERT INTO resenas (libro_id, usuario_id, calificacion, recomendado, fecha_resena) VALUES
(1, 1, 5, TRUE, true, NOW()),
(1, 2, 4, TRUE, true, NOW()),
(2, 1, 3, TRUE, false, NOW()),
(3, 3, 5, TRUE, true, NOW()),
(4, 2, 4, FALSE, true, NOW());