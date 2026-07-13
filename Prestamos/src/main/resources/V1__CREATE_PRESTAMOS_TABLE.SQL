CREATE TABLE IF NOT EXISTS prestamos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    libro_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    dias_prestamo INT DEFAULT 7,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_libro_id (libro_id),
    INDEX idx_usuario_id (usuario_id),
    INDEX idx_estado (estado),
    INDEX idx_fecha_devolucion (fecha_devolucion)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;