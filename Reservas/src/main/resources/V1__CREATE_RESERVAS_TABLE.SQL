CREATE TABLE IF NOT EXISTS reservas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    libro_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    fecha_reserva TIMESTAMP NOT NULL,
    fecha_expiracion TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'ACTIVA',
    dias_validez INT DEFAULT 3,
    observaciones VARCHAR(255),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_libro_id (libro_id),
    INDEX idx_usuario_id (usuario_id),
    INDEX idx_estado (estado),
    INDEX idx_fecha_expiracion (fecha_expiracion),
    INDEX idx_libro_usuario (libro_id, usuario_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;