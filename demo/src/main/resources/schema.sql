-- Eliminar tablas si existen
DROP TABLE IF EXISTS detalles_factura CASCADE;
DROP TABLE IF EXISTS facturas;
DROP TABLE IF EXISTS articulos;
DROP TABLE IF EXISTS clientes;

-- Crear tablas nuevamente
CREATE TABLE clientes (
    id_cliente SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE articulos (
    id_articulo SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL
);

CREATE TABLE facturas (
    id_factura SERIAL PRIMARY KEY,
    numero_factura VARCHAR(20) UNIQUE NOT NULL,
    id_cliente INTEGER NOT NULL,
    fecha DATE NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL CHECK (subtotal >= 0),
    iva DECIMAL(10, 2) NOT NULL CHECK (iva >= 0),
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

CREATE TABLE detalles_factura (
    id_detalle SERIAL PRIMARY KEY,
    id_factura INTEGER NOT NULL,
    id_articulo INTEGER NOT NULL,
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10, 2) NOT NULL,
    valor DECIMAL(10, 2) GENERATED ALWAYS AS (cantidad * precio_unitario) STORED,
    FOREIGN KEY (id_factura) REFERENCES facturas(id_factura) ON DELETE CASCADE,
    FOREIGN KEY (id_articulo) REFERENCES articulos(id_articulo)
);

-- Crear índices
CREATE INDEX idx_facturas_cliente ON facturas(id_cliente);
CREATE INDEX idx_detalles_factura ON detalles_factura(id_factura);
