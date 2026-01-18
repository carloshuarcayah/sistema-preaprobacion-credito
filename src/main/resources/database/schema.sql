drop table IF exists historial_prestamos;
drop table IF exists clientes;

CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    score_crediticio INT NOT NULL CHECK ( score_crediticio BETWEEN 0 AND 100), ---- EN ESTE CASO EL SCORE LO HEMOS PUESTO QUE VA DE 0 A 100 no se como va realmente
    sueldo NUMERIC(12, 2) NOT NULL CHECK ( sueldo >= 0 ),
    tiene_deuda BOOLEAN DEFAULT FALSE,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE historial_prestamos (
    id SERIAL PRIMARY KEY,
    cliente_id INT NOT NULL,
    tipo_prestamo VARCHAR(20) NOT NULL,
    monto_solicitado NUMERIC(12, 2) NOT NULL CHECK (monto_solicitado>0), --AUNQUE EL MINIMO LO HEMOS DEFINIDO COMO 500, ESTO LANZARA UN PREAPROBACIONEXCEPTION
    plazo_meses INT NOT NULL CHECK ( plazo_meses > 0 ),
    estado VARCHAR(20) NOT NULL default 'PENDIENTE' check (estado in ('PENDIENTE','APROBADO','RECHAZADO')),
    mensaje_detalle TEXT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_cliente foreign key (cliente_id) references clientes(id)
);

-- Datos iniciales
INSERT INTO clientes (nombre, edad, score_crediticio, sueldo, tiene_deuda)
VALUES
    ('Juan RAmos', 30, 85, 4500.00, false),
    ('Maria Dulanto', 25, 40, 3000.00, true),
    ('Lucia Fernandez', 19, 60, 2500.00, false),
    ('Carlos Rodriguez', 60, 90, 8000.00, false);

INSERT INTO historial_prestamos (cliente_id, tipo_prestamo, monto_solicitado, plazo_meses, estado)
VALUES
    (1, 'PERSONAL', 5000.00, 12, 'PENDIENTE'),
    (2, 'VEHICULAR', 20000.00, 48, 'PENDIENTE'),
    (3, 'HIPOTECARIO', 100000.00, 120, 'PENDIENTE'),
    (4, 'PERSONAL', 8000.00, 24, 'PENDIENTE');
