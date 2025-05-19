-- Insertar Materias
INSERT INTO materia (nombre, creditos) VALUES
('Matemáticas', 4),
('Física', 3),
('Programación', 4),
('Base de Datos', 3),
('Inglés', 2);

-- Insertar Estudiantes
INSERT INTO estudiante (nombre, apellido, correo) VALUES
('Juan', 'Pérez', 'juan.perez@email.com'),
('María', 'González', 'maria.gonzalez@email.com'),
('Carlos', 'Rodríguez', 'carlos.rodriguez@email.com'),
('Ana', 'Martínez', 'ana.martinez@email.com'),
('Pedro', 'López', 'pedro.lopez@email.com');

-- Insertar Notas
INSERT INTO nota (observacion, valor, porcentaje, materia_id, estudiante_id) VALUES
('Primer Parcial', 4.5, 30.0, 1, 1),
('Segundo Parcial', 4.0, 30.0, 1, 1),
('Final', 4.2, 40.0, 1, 1),
('Primer Parcial', 3.8, 30.0, 2, 1),
('Segundo Parcial', 4.2, 30.0, 2, 1),
('Final', 4.0, 40.0, 2, 1),
('Primer Parcial', 4.7, 30.0, 3, 2),
('Segundo Parcial', 4.5, 30.0, 3, 2),
('Final', 4.8, 40.0, 3, 2),
('Primer Parcial', 3.9, 30.0, 4, 2),
('Segundo Parcial', 4.1, 30.0, 4, 2),
('Final', 4.0, 40.0, 4, 2),
('Primer Parcial', 4.2, 30.0, 5, 3),
('Segundo Parcial', 4.4, 30.0, 5, 3),
('Final', 4.3, 40.0, 5, 3),
('Primer Parcial', 4.0, 30.0, 1, 4),
('Segundo Parcial', 4.2, 30.0, 1, 4),
('Final', 4.1, 40.0, 1, 4),
('Primer Parcial', 4.5, 30.0, 2, 5),
('Segundo Parcial', 4.3, 30.0, 2, 5),
('Final', 4.4, 40.0, 2, 5); 