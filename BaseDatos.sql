CREATE DATABASE `devsu_clientes`;
CREATE DATABASE `devsu_cuentas`;

-- devsu_clientes.cliente definition

CREATE TABLE `cliente` (
  `id` char(36) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `genero` varchar(100) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `identificacion` varchar(100) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `telefono` varchar(100) NOT NULL,
  `usuario` varchar(100) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- devsu_clientes.item_reporte definition

CREATE TABLE `item_reporte` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cliente_id` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `cliente_nombres` varchar(100) NOT NULL,
  `numero_cuenta` varchar(100) NOT NULL,
  `tipo_cuenta` varchar(100) NOT NULL,
  `saldo_inicial` varchar(100) NOT NULL,
  `estado` varchar(100) NOT NULL,
  `movimiento` varchar(100) NOT NULL,
  `saldo_disponible` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `item_reporte_cliente_FK` (`cliente_id`),
  CONSTRAINT `item_reporte_cliente_FK` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- devsu_cuentas.cuenta definition

CREATE TABLE `cuenta` (
  `id` char(36) NOT NULL,
  `cliente_id` char(36) NOT NULL,
  `numero_cuenta` varchar(100) NOT NULL,
  `tipo_cuenta` varchar(100) NOT NULL,
  `saldo` decimal(10,2) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- devsu_cuentas.movimiento definition

CREATE TABLE `movimiento` (
  `id` char(36) NOT NULL,
  `cuenta_id` char(36) NOT NULL,
  `fecha` datetime NOT NULL,
  `tipo_movimiento` varchar(100) NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `saldo_inicial` decimal(10,2) NOT NULL,
  `saldo_final` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `movimiento_cuenta_FK` (`cuenta_id`),
  CONSTRAINT `movimiento_cuenta_FK` FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;