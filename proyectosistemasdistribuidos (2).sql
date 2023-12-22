-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-11-2023 a las 02:53:35
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectosistemasdistribuidos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `ID` varchar(11) NOT NULL,
  `APELLIDO` varchar(25) NOT NULL,
  `NOMBRE` varchar(25) NOT NULL,
  `DIRECCION` varchar(25) NOT NULL,
  `DNI` varchar(25) NOT NULL,
  `TELEFONO` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`ID`, `APELLIDO`, `NOMBRE`, `DIRECCION`, `DNI`, `TELEFONO`) VALUES
('C00001', 'jimenez', 'jose', 'asdasdasd', '111111111', '21423213123');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `ID` varchar(11) NOT NULL,
  `DESCRIPCION` varchar(20) NOT NULL,
  `PRECIO` double NOT NULL,
  `STOCK` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`ID`, `DESCRIPCION`, `PRECIO`, `STOCK`) VALUES
('C00001', '3213Q', 2321, 34);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `ID` varchar(11) NOT NULL,
  `NOMBRE` varchar(25) NOT NULL,
  `INGRESO` varchar(25) NOT NULL,
  `CONTRASENA` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`ID`, `NOMBRE`, `INGRESO`, `CONTRASENA`) VALUES
('C00001', 'bruno', 'admin', '123'),
('C00002', 'Jose', 'admin', 'admin'),
('C00003', 'Diego', 'admin', 'admin'),
('C00004', 'Fernando', 'admin', 'admin'),
('C00005', 'Jose', 'sadas', 'dasdsa'),
('C00006', 'prueba', 'priega', 'admin');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
