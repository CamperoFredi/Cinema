CREATE DATABASE Cinema;
USE Cinema;
SHOW TABLES;

CREATE TABLE Usuarios (
    Id INT NOT NULL AUTO_INCREMENT,
	nomUsuario varchar(64) NOT NULL,
	nya varchar(64) NOT NULL,
	dni varchar(64) NOT NULL,
	contra varchar(64) NOT NULL,
	nroTarjeta varchar(100),
	fecha varchar(64) NOT NULL,
	admin bool,
    PRIMARY KEY (Id)
)ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE Peliculas (
    Id INT NOT NULL AUTO_INCREMENT,
    NomPelicula VARCHAR(100) NOT NULL,
    fecha VARCHAR(64) NULL,
    PRIMARY KEY (Id)
)ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE Salas (
    Id INT NOT NULL AUTO_INCREMENT,
    SalaNro VARCHAR(64) NOT NULL,
    NombreSala VARCHAR(64) NOT NULL,
    PeliculaId INT NOT NULL,
    Horario TIME null,
    Capacidad INT null,
    Tipo VARCHAR(20) null,
    Precio VARCHAR(64) NOT NULL,
	FechaCreacion VARCHAR(64) null,
    PRIMARY KEY (Id),
    CONSTRAINT FOREIGN KEY fk_Id_pelicula (PeliculaId) REFERENCES Peliculas (Id)
)ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE Descuentos (
    Id INT NOT NULL AUTO_INCREMENT,
    DiaDescuento VARCHAR(64) NULL,
    Porcentaje INT,
    FechaCreacion VARCHAR(64) NULL,
    PRIMARY KEY (Id)
)ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE Reservas (
    Id INT NOT NULL AUTO_INCREMENT,
	UsuarioId INT NOT NULL,
	SalaId INT NOT NULL, 
	DescuentoId INT,
	DiaFuncion VARCHAR(64),
	Modificada VARCHAR(10) NULL,
    Precio VARCHAR(64),
    ConfirmaPago VARCHAR(10) NULL,
	FechaCreacion VARCHAR(64),
    PRIMARY KEY (Id),
    CONSTRAINT FOREIGN KEY fk_id_usuario (UsuarioId) REFERENCES Usuarios (Id),
    CONSTRAINT FOREIGN KEY fk_id_sala (SalaId) REFERENCES Salas (Id),
    CONSTRAINT FOREIGN KEY fk_id_descuento (DescuentoId) REFERENCES Descuentos (Id)
)ENGINE=InnoDB AUTO_INCREMENT=1;
