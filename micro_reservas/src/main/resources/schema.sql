CREATE TABLE Salon(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombreSalon VARCHAR(100),
    ubicacion VARCHAR(100)
);

CREATE TABLE Reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100),
    apellidos VARCHAR(100),
    cantidadPersonas INT,
    fechaReserva DATE,
    horaInicio TIME,
    horaFin TIME,
    estadoReserva VARCHAR(20),
    idSalon INT,
    FOREIGN KEY (idSalon) REFERENCES Salon(id)
);

