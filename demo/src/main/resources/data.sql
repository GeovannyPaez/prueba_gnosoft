-- Insertar datos en la tabla de clientes
INSERT INTO clientes (nombre) VALUES 
('Juan Pérez'),
('Ana Gómez'),
('Carlos López'),
('María Fernández');

-- Insertar datos en la tabla de artículos
INSERT INTO articulos (nombre, precio) VALUES 
('Artículo 1', 10.00),
('Artículo 2', 20.50),
('Artículo 3', 30.75),
('Artículo 4', 40.00);

-- Insertar datos en la tabla de facturas
INSERT INTO facturas (numero_factura, id_cliente, fecha, subtotal, iva, total) VALUES 
('FAC001', 1, '2024-08-01', 50.00, 10.00, 60.00),
('FAC002', 2, '2024-08-02', 80.50, 16.10, 96.60),
('FAC003', 3, '2024-08-03', 120.75, 24.15, 144.90),
('FAC004', 4, '2024-08-04', 200.00, 40.00, 240.00);

-- Insertar datos en la tabla de detalles de factura
INSERT INTO detalles_factura (id_factura, id_articulo, cantidad, precio_unitario) VALUES 
(1, 1, 2, 10.00),
(1, 2, 1, 20.50),
(2, 3, 3, 30.75),
(2, 4, 1, 40.00),
(3, 1, 5, 10.00),
(3, 2, 2, 20.50),
(4, 4, 5, 40.00);
