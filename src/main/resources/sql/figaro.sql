-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-12-2017 a las 00:06:07
-- Versión del servidor: 10.1.28-MariaDB
-- Versión de PHP: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `figaro`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `ID_CATEGORIA` int(11) NOT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`ID_CATEGORIA`, `NOMBRE`) VALUES
(1, 'Turnos'),
(2, 'Luz'),
(3, 'Gas'),
(4, 'Agua'),
(6, 'Stock'),
(7, 'Ventas'),
(5, 'AFIP');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad`
--

CREATE TABLE `ciudad` (
  `ID_CIUDAD` int(11) NOT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ciudad`
--

INSERT INTO `ciudad` (`ID_CIUDAD`, `NOMBRE`) VALUES
(1, 'La Plata'),
(2, 'Ensenada'),
(3, 'Berisso');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `ID_USUARIO` int(11) NOT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL,
  `APELLIDO` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `SEXO` varchar(255) DEFAULT NULL,
  `TELEFONO` varchar(255) DEFAULT NULL,
  `INGRESO` date DEFAULT NULL,
  `CIUDAD` varchar(255) DEFAULT NULL,
  `CALLE` varchar(255) DEFAULT NULL,
  `NUMERO` int(11) DEFAULT NULL,
  `PISO` int(11) DEFAULT NULL,
  `DPTO` varchar(255) DEFAULT NULL,
  `ULT_VISITA` date DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`ID_USUARIO`, `NOMBRE`, `APELLIDO`, `EMAIL`, `SEXO`, `TELEFONO`, `INGRESO`, `CIUDAD`, `CALLE`, `NUMERO`, `PISO`, `DPTO`, `ULT_VISITA`) VALUES
(34, 'Oscar', 'Osorio', NULL, 'hombre', NULL, '2017-10-30', 'Berisso', NULL, 0, 0, NULL, '2017-10-30'),
(33, 'German', 'Gaviria', NULL, 'hombre', NULL, '2017-10-30', 'Berisso', NULL, 0, 0, NULL, '2017-10-30'),
(32, 'Cesar', 'Cano', NULL, 'hombre', NULL, '2017-10-30', 'Ensenada', NULL, 0, 0, NULL, '2017-10-30'),
(31, 'Jaime', 'Palacio', NULL, 'hombre', NULL, '2017-10-30', 'Ensenada', NULL, 0, 0, NULL, '2017-10-30'),
(30, 'Carlos', 'Duque', NULL, 'hombre', NULL, '2017-10-30', 'Ensenada', NULL, 0, 0, NULL, '2017-10-30'),
(23, 'Julia', 'Perez', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(24, 'Juliana', 'Arias', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(25, 'Juana', 'Bocanumenth', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(26, 'Ana', 'Correa', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(27, 'Sonia', 'Giraldo', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(28, 'Sofia', 'Laverde', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-12-02'),
(29, 'Roberto', 'Lotero', NULL, 'hombre', NULL, '2017-10-30', 'Ensenada', NULL, 0, 0, NULL, '2017-10-30'),
(7, 'Maria', 'Castro', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(8, 'Maria', 'Morales', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(9, 'Maria', 'Castro', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(10, 'Victoria', 'Escobar', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(11, 'Alejandra', 'Gonzalez', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(12, 'Ivana', 'Mosquera', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(13, 'Carla', 'Naranjo', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(14, 'Augusto', 'Rodas', NULL, 'hombre', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(15, 'Alberto', 'Victoria', NULL, 'hombre', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(16, 'Jorge', 'Alvarez', NULL, 'hombre', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(17, 'Gonzalo', 'Trujillo', NULL, 'hombre', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(18, 'Luis', 'Puerta', NULL, 'hombre', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(19, 'Luisa', 'Pineda', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(20, 'Roberto', 'Ruiz', NULL, 'hombre', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(21, 'Antonia', 'Monsalve', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(22, 'Julio', 'Martinez', NULL, 'hombre', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(6, 'Fabia', 'Saldarriaga', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(5, 'Eugenia', 'Molina', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(4, 'Elena', 'Morales', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(3, 'Gloria', 'Castro', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(2, 'Ivana', 'Saldarriaga', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(1, 'Beatriz', 'Trujillo', NULL, 'mujer', NULL, '2017-10-30', 'La Plata', NULL, 0, 0, NULL, '2017-10-30'),
(35, 'Gloria', 'Villegas', NULL, 'mujer', NULL, '2017-10-30', 'Berisso', NULL, 0, 0, NULL, '2017-10-30'),
(36, 'Elena', 'Laverde', NULL, 'mujer', NULL, '2017-10-30', 'Berisso', NULL, 0, 0, NULL, '2017-10-30'),
(37, 'Beatriz', 'Hurtado', NULL, 'mujer', NULL, '2017-10-30', 'Berisso', NULL, 0, 0, NULL, '2017-10-30'),
(38, 'Alberto', 'Rodriguez Saa', 'saa@gmail.com', 'hombre', '234563567', '2017-10-03', 'La Plata', NULL, 0, 0, NULL, '2017-10-25'),
(39, 'Sergio', 'Aguero', 'cun@gmail.com', 'hombre', '2345234', '2017-10-07', 'Ensenada', NULL, 0, 0, NULL, '2017-10-07'),
(40, 'Carina', 'Olga', 'kolga@gmail.com', 'mujer', '234567765', '2017-10-16', 'Berisso', NULL, 0, 0, NULL, '2017-10-16'),
(41, 'Aidan', 'Godoy', 'nunc.risus@gmail.com', 'hombre', '18090924', '2017-10-03', 'La Plata', NULL, 0, 0, NULL, '2017-10-03'),
(42, 'Jack', 'Guzman', 'pharetra.Quisque@gmail.com', 'hombre', '115367', '2017-10-03', 'La Plata', NULL, 0, 0, NULL, '2017-10-03'),
(43, 'Ronaldo', 'Flores', 'pharetra.sed.hendrerit@gmail.com', 'hombre', '8357108', '2017-10-03', 'La Plata', NULL, 0, 0, NULL, '2017-10-03'),
(44, 'Miley', 'Carvajal', 'Maecenas@gmail.com', 'mujer', '63147786', '2017-10-05', 'La Plata', NULL, 0, 0, NULL, '2017-10-05'),
(45, 'Anna', 'Carvajal', 'dignissim@gmail.com', 'mujer', '85504775', '2017-10-03', 'Berisso', NULL, 0, 0, NULL, '2017-10-03'),
(46, 'Robert', 'Campos', 'molestie@gmail.com', 'hombre', '63147786', '2017-10-15', 'Ensenada', NULL, 0, 0, NULL, '2017-10-15'),
(47, 'Cain', 'Orellana', 'sociis@gmail.com', 'hombre', '73111161', '2017-10-21', 'La Plata', NULL, 0, 0, NULL, '2017-10-21'),
(48, 'Arantza', 'Saez', 'ipsum@gmail.com', 'mujer', '98542893', '2017-10-22', 'La Plata', NULL, 0, 0, NULL, '2017-10-22'),
(49, 'Marthina', 'Gonzalez', 'Integer@gmail.com', 'mujer', '38983609', '2017-10-22', 'La Plata', NULL, 0, 0, NULL, '2017-12-01'),
(50, 'Antwan', 'Perez', 'auctor@gmail.com', 'mujer', '55997810', '2017-10-15', 'Ensenada', NULL, 0, 0, NULL, '2017-10-15'),
(51, 'Adriana', 'Reyes', 'placerat@gmail.com', 'mujer', '85360427', '2017-10-15', 'Ensenada', NULL, 0, 0, NULL, '2017-10-15'),
(52, 'Camilo', 'Maldonado', 'faucibus@gmail.com', 'hombre', '59631013', '2017-10-15', 'La Plata', NULL, 0, 0, NULL, '2017-10-15'),
(53, 'Axcel', 'Campos', 'euplacerat@gmail.com', 'hombre', '1125009', '2017-10-15', 'Berisso', NULL, 0, 0, NULL, '2017-10-15'),
(54, 'Yerko', 'Romero', 'sed@gmail.com', 'mujer', '33822848', '2017-10-15', 'La Plata', NULL, 0, 0, NULL, '2017-10-15'),
(55, 'Xavier', 'Flores', 'bibendum@gmail.com', 'hombre', '73632589', '2017-10-15', 'La Plata', NULL, 0, 0, NULL, '2017-10-15'),
(56, 'Kamila', 'Rivera', 'vitae@gmail.com', 'mujer', '23140233', '2017-10-15', 'La Plata', NULL, 0, 0, NULL, '2017-10-15'),
(57, 'Hugo', 'Paredes', 'fermentum@gmail.com', 'hombre', '26433768', '2017-10-15', 'La Plata', NULL, 0, 0, NULL, '2017-10-15'),
(58, 'Juliette', 'Castillo', 'Nulla@gmail.com', 'mujer', '61384339', '2017-10-15', 'La Plata', NULL, 0, 0, NULL, '2017-10-15'),
(59, 'Piera', 'Rodríguez', 'dignissim@gmail.com', 'mujer', '61649962', '2017-10-15', 'La Plata', NULL, 0, 0, NULL, '2017-10-15'),
(60, 'Consuelo', 'Orellana', 'consequat@gmail.com', 'mujer', '73817044', '2017-10-15', 'La Plata', NULL, 0, 0, NULL, '2017-10-15'),
(61, 'Darling', 'Integer', 'Integer@gmail.com', 'mujer', '80073754', '2017-10-15', 'Ensenada', NULL, 0, 0, NULL, '2017-10-15'),
(62, 'Anahi', 'Castro', 'semper@gmail.com', 'mujer', '33271634', '2017-10-15', 'Ensenada', NULL, 0, 0, NULL, '2017-10-15'),
(63, 'Elisa', 'Ortiz', 'nonummy@gmail.com', 'mujer', '12973752', '2017-10-15', 'Ensenada', NULL, 0, 0, NULL, '2017-10-15'),
(64, 'Yasna', 'Garcia', 'vulputate@gmail.com', 'mujer', '1761250', '2017-10-15', 'Berisso', NULL, 0, 0, NULL, '2017-10-15'),
(65, 'Natasha', 'Ortiz', 'sapien@gmail.com', 'mujer', '66702431', '2017-10-15', 'Berisso', NULL, 0, 0, NULL, '2017-10-15'),
(66, 'Nayeli', 'Morales', 'fermentum@gmail.com', 'mujer', '41611704', '2017-10-15', 'Berisso', NULL, 0, 0, NULL, '2017-10-15'),
(67, 'Natalie', 'Venegas', 'Praesent@gmail.com', 'mujer', '32129967', '2017-10-15', 'Berisso', NULL, 0, 0, NULL, '2017-10-15');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimiento`
--

CREATE TABLE `movimiento` (
  `ID_MOVIMIENTO` int(11) NOT NULL,
  `PRECIO` decimal(19,2) DEFAULT NULL,
  `FECHA` date DEFAULT NULL,
  `DETALLE` varchar(255) DEFAULT NULL,
  `CATEGORIA` varchar(255) DEFAULT NULL,
  `ISGASTO` bit(1) DEFAULT NULL,
  `TIPOPAGO` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `movimiento`
--

INSERT INTO `movimiento` (`ID_MOVIMIENTO`, `PRECIO`, `FECHA`, `DETALLE`, `CATEGORIA`, `ISGASTO`, `TIPOPAGO`) VALUES
(95, '300.00', '2017-12-15', 'Peinado', 'Turnos', b'0', NULL),
(94, '150.00', '2017-12-15', 'Lavado', 'Turnos', b'0', NULL),
(93, '150.00', '2017-12-14', 'Lavado', 'Turnos', b'0', NULL),
(92, '150.00', '2017-12-14', 'Crema de enjuague', 'Ventas', b'0', NULL),
(91, '200.00', '2017-12-14', 'Corte', 'Turnos', b'0', NULL),
(90, '200.00', '2017-12-14', 'Corte', 'Turnos', b'0', NULL),
(89, '200.00', '2017-12-14', 'Corte', 'Turnos', b'0', NULL),
(88, '300.00', '2017-12-14', 'Peinado', 'Turnos', b'0', NULL),
(87, '200.00', '2017-12-13', 'Corte', 'Turnos', b'0', NULL),
(86, '250.00', '2017-12-13', 'Color', 'Turnos', b'0', NULL),
(85, '200.00', '2017-12-13', 'Corte', 'Turnos', b'0', NULL),
(84, '150.00', '2017-12-13', 'Lavado', 'Turnos', b'0', NULL),
(83, '200.00', '2017-12-13', 'Corte', 'Turnos', b'0', NULL),
(82, '150.00', '2017-12-13', 'Crema de enjuague', 'Ventas', b'0', NULL),
(81, '200.00', '2017-12-13', 'Corte', 'Turnos', b'0', NULL),
(80, '300.00', '2017-12-12', 'Peinado', 'Turnos', b'0', NULL),
(79, '200.00', '2017-12-12', 'Corte', 'Turnos', b'0', NULL),
(78, '200.00', '2017-12-12', 'Corte', 'Turnos', b'0', NULL),
(77, '500.00', '2017-12-12', 'Crema', 'Stock', b'1', NULL),
(76, '300.00', '2017-12-08', 'Shampoo', 'Stock', b'1', NULL),
(75, '200.00', '2017-12-08', 'Corte', 'Turnos', b'0', NULL),
(74, '200.00', '2017-12-08', 'Corte', 'Turnos', b'0', NULL),
(73, '250.00', '2017-12-08', 'Color', 'Turnos', b'0', NULL),
(72, '100.00', '2017-12-08', 'Alisado', 'Turnos', b'0', NULL),
(71, '100.00', '2017-12-07', 'Alisado', 'Turnos', b'0', NULL),
(70, '200.00', '2017-12-07', 'Shampoo', 'Ventas', b'0', NULL),
(69, '250.00', '2017-12-07', 'Color', 'Turnos', b'0', NULL),
(68, '200.00', '2017-12-06', 'Productos de limpieza', 'Stock', b'1', NULL),
(67, '200.00', '2017-12-06', 'Shampoo', 'Stock', b'1', NULL),
(66, '200.00', '2017-12-06', 'Corte', 'Turnos', b'0', NULL),
(65, '200.00', '2017-12-06', 'Corte', 'Turnos', b'0', NULL),
(64, '250.00', '2017-12-05', 'Color', 'Turnos', b'0', NULL),
(63, '250.00', '2017-12-05', 'Color', 'Stock', b'1', NULL),
(62, '300.00', '2017-12-05', 'Peinado', 'Turnos', b'0', NULL),
(61, '200.00', '2017-12-05', 'Corte', 'Turnos', b'0', NULL),
(60, '150.00', '2017-12-05', '', 'Gas', b'1', NULL),
(59, '200.00', '2017-12-05', '', 'Luz', b'1', NULL),
(58, '200.00', '2017-12-01', 'Corte', 'Turnos', b'0', NULL),
(57, '200.00', '2017-12-01', 'Corte', 'Turnos', b'0', NULL),
(56, '200.00', '2017-12-01', 'Corte', 'Turnos', b'0', NULL),
(55, '100.00', '2017-12-01', 'Alisado', 'Turnos', b'0', NULL),
(54, '250.00', '2017-12-01', 'Color', 'Turnos', b'0', NULL),
(53, '200.00', '2017-11-30', 'Corte', 'Turnos', b'0', NULL),
(52, '100.00', '2017-11-30', 'Alisado', 'Turnos', b'0', NULL),
(51, '150.00', '2017-11-30', 'Lavado', 'Turnos', b'0', NULL),
(50, '200.00', '2017-11-30', 'Corte', 'Ventas', b'0', NULL),
(49, '200.00', '2017-11-30', 'Corte', 'Turnos', b'0', NULL),
(48, '200.00', '2017-11-30', 'Corte', 'Turnos', b'0', NULL),
(47, '100.00', '2017-11-30', 'Alisado', 'Turnos', b'0', NULL),
(46, '250.00', '2017-11-29', 'Color', 'Turnos', b'0', NULL),
(45, '150.00', '2017-11-29', 'Lavado', 'Turnos', b'0', NULL),
(44, '200.00', '2017-11-29', 'Corte', 'Turnos', b'0', NULL),
(43, '200.00', '2017-11-29', 'Corte', 'Turnos', b'0', NULL),
(42, '200.00', '2017-11-29', 'Corte', 'Turnos', b'0', NULL),
(41, '60.00', '2017-11-28', 'Shampoo', 'Ventas', b'0', NULL),
(40, '200.00', '2017-11-28', 'Corte', 'Turnos', b'0', NULL),
(39, '200.00', '2017-11-28', 'Corte', 'Turnos', b'0', NULL),
(38, '250.00', '2017-11-28', 'Color', 'Turnos', b'0', NULL),
(37, '100.00', '2017-11-28', 'Alisado', 'Turnos', b'0', NULL),
(36, '200.00', '2017-11-25', 'Corte', 'Turnos', b'0', NULL),
(35, '200.00', '2017-11-25', 'Corte', 'Turnos', b'0', NULL),
(34, '300.00', '2017-11-25', 'Peinado', 'Turnos', b'0', NULL),
(33, '250.00', '2017-11-25', 'Color', 'Turnos', b'0', NULL),
(32, '100.00', '2017-11-24', 'Alisado', 'Turnos', b'0', NULL),
(31, '250.00', '2017-11-24', 'Color', 'Turnos', b'0', NULL),
(30, '150.00', '2017-11-24', 'Lavado', 'Turnos', b'0', NULL),
(29, '200.00', '2017-11-24', 'Corte', 'Turnos', b'0', NULL),
(28, '200.00', '2017-11-24', 'Corte', 'Turnos', b'0', NULL),
(27, '150.00', '2017-11-23', 'Shampoo', 'Ventas', b'0', NULL),
(26, '200.00', '2017-11-23', 'Corte', 'Turnos', b'0', NULL),
(25, '200.00', '2017-11-23', 'Corte', 'Turnos', b'0', NULL),
(24, '200.00', '2017-11-23', 'Corte', 'Turnos', b'0', NULL),
(23, '50.00', '2017-11-22', 'Shampoo', 'Ventas', b'0', NULL),
(22, '200.00', '2017-11-22', 'Corte', 'Turnos', b'0', NULL),
(21, '450.00', '2017-11-22', 'Lavado Peinado', 'Turnos', b'0', NULL),
(20, '100.00', '2017-11-22', 'Alisado', 'Turnos', b'0', NULL),
(19, '100.00', '2017-11-21', 'Crema depiladora', 'Ventas', b'0', NULL),
(18, '200.00', '2017-11-21', 'Corte', 'Turnos', b'0', NULL),
(17, '100.00', '2017-11-21', 'Alisado', 'Turnos', b'0', NULL),
(15, '600.00', '2017-11-17', 'Shampoo', 'Stock', b'1', NULL),
(16, '100.00', '2017-11-17', 'Alisado', 'Turnos', b'0', NULL),
(14, '250.00', '2017-11-17', 'Color', 'Turnos', b'0', NULL),
(13, '200.00', '2017-11-17', 'Corte', 'Turnos', b'0', NULL),
(12, '300.00', '2017-11-16', 'Peinado', 'Turnos', b'0', NULL),
(11, '250.00', '2017-11-16', 'Color', 'Stock', b'1', NULL),
(10, '200.00', '2017-11-16', 'Corte', 'Turnos', b'0', NULL),
(9, '200.00', '2017-11-16', 'Corte', 'Turnos', b'0', NULL),
(8, '300.00', '2017-11-16', 'Crema depiladora', 'Stock', b'1', NULL),
(7, '200.00', '2017-11-15', 'Corte', 'Turnos', b'0', NULL),
(6, '300.00', '2017-11-15', 'Peinado', 'Turnos', b'0', NULL),
(5, '500.00', '2017-11-15', 'Color', 'Stock', b'1', NULL),
(4, '200.00', '2017-11-14', 'Corte', 'Turnos', b'0', NULL),
(3, '200.00', '2017-11-14', 'Corte', 'Turnos', b'0', NULL),
(2, '200.00', '2017-11-14', '', 'Luz', b'1', NULL),
(1, '160.00', '2017-11-14', '', 'Gas', b'1', NULL),
(96, '150.00', '2017-12-15', 'Crema de enjuague', 'Ventas', b'0', NULL),
(97, '250.00', '2017-12-15', 'Color', 'Turnos', b'0', NULL),
(98, '200.00', '2017-12-15', 'Corte', 'Turnos', b'0', NULL),
(99, '200.00', '2017-12-15', 'Corte', 'Turnos', b'0', NULL),
(100, '200.00', '2017-12-15', 'Corte', 'Turnos', b'0', NULL),
(179, '120.00', '2017-09-16', 'Crema Depiladora', 'Ventas', b'0', NULL),
(180, '100.00', '2017-09-26', 'Shampoo', 'Ventas', b'0', NULL),
(178, '90.00', '2017-09-12', 'Crema de Enjuague', 'Ventas', b'0', NULL),
(177, '90.00', '2017-09-06', 'Crema de Enjuague', 'Ventas', b'0', NULL),
(176, '100.00', '2017-09-04', 'Shampoo', 'Ventas', b'0', NULL),
(167, '250.00', '2017-10-05', 'Shampoo', 'Stock', b'1', NULL),
(168, '900.00', '2017-10-09', 'Tintura', 'Stock', b'1', NULL),
(175, '90.00', '2017-08-28', 'Crema de Enjuague', 'Ventas', b'0', NULL),
(174, '100.00', '2017-08-24', 'Crema para el Cabello', 'Ventas', b'0', NULL),
(173, '100.00', '2017-08-23', 'Shampoo', 'Ventas', b'0', NULL),
(172, '50.00', '2017-08-17', 'Gel', 'Ventas', b'0', NULL),
(171, '120.00', '2017-08-14', 'Crema Depiladora', 'Ventas', b'0', NULL),
(169, '90.00', '2017-08-05', 'Crema de Enjuague', 'Ventas', b'0', NULL),
(166, '50.00', '2017-10-04', 'Guantes', 'Stock', b'1', NULL),
(165, '500.00', '2017-09-07', 'Cremas', 'Stock', b'1', NULL),
(164, '290.00', '2017-09-03', 'Shampoo', 'Stock', b'1', NULL),
(163, '800.00', '2017-09-01', 'Tintura', 'Stock', b'1', NULL),
(162, '200.00', '2017-08-08', 'Shampoo', 'Stock', b'1', NULL),
(161, '800.00', '2017-08-05', 'Tintura', 'Stock', b'1', NULL),
(160, '500.00', '2017-08-02', 'Cremas', 'Stock', b'1', NULL),
(159, '215.00', '2017-10-09', '', 'AFIP', b'1', NULL),
(158, '245.00', '2017-09-08', '', 'AFIP', b'1', NULL),
(157, '230.00', '2017-08-05', '', 'AFIP', b'1', NULL),
(156, '250.00', '2017-07-04', '', 'AFIP', b'1', NULL),
(155, '210.00', '2017-06-03', '', 'AFIP', b'1', NULL),
(154, '180.00', '2017-10-05', '', 'Agua', b'1', NULL),
(153, '180.00', '2017-09-08', '', 'Agua', b'1', NULL),
(152, '180.00', '2017-08-02', '', 'Agua', b'1', NULL),
(125, '45.00', '2017-06-05', NULL, 'Luz', b'1', NULL),
(124, '69.00', '2017-07-02', NULL, 'Gas', b'1', NULL),
(123, '100.00', '2017-08-02', NULL, 'Gas', b'1', NULL),
(122, '100.00', '2017-09-01', NULL, 'Gas', b'1', NULL),
(121, '130.00', '2017-10-03', NULL, 'Gas', b'1', NULL),
(120, '80.00', '2017-07-04', NULL, 'Luz', b'1', NULL),
(119, '57.00', '2017-08-03', NULL, 'Luz', b'1', NULL),
(118, '87.00', '2017-09-04', NULL, 'Luz', b'1', NULL),
(117, '100.00', '2017-10-03', '', 'Luz', b'1', NULL),
(116, '200.00', '2017-10-05', 'Cremas', 'Stock', b'1', NULL),
(114, '300.00', '2017-09-11', 'Shampoo', 'Stock', b'1', NULL),
(115, '200.00', '2017-09-23', 'Tintura', 'Stock', b'1', NULL),
(151, '180.00', '2017-07-06', '', 'Agua', b'1', NULL),
(150, '180.00', '2017-06-04', '', 'Agua', b'1', NULL),
(138, '200.00', '2017-06-02', '', 'Gas', b'1', NULL),
(137, '150.00', '2017-10-25', 'Peinado', 'Turnos', b'0', NULL),
(136, '150.00', '2017-10-26', 'Peinado', 'Turnos', b'0', NULL),
(135, '150.00', '2017-10-26', 'Peinado', 'Turnos', b'0', NULL),
(113, '200.00', '2017-08-29', 'Shampoo', 'Stock', b'1', NULL),
(112, '150.00', '2017-08-14', 'Guantes', 'Stock', b'1', NULL),
(111, '500.00', '2017-08-17', 'Tintura', 'Stock', b'1', NULL),
(110, '60.00', '2017-10-10', 'Crema de Enjuague', 'Ventas', b'0', NULL),
(134, '350.00', '2017-10-26', 'Peinado Tintura', 'Turnos', b'0', NULL),
(108, '200.00', '2017-08-08', 'Gel', 'Ventas', b'0', NULL),
(107, '400.00', '2017-10-06', 'Tijeras', 'Ventas', b'0', NULL),
(106, '100.00', '2017-08-24', 'Crema para el Cabello', 'Ventas', b'0', NULL),
(104, '50.00', '2017-08-02', 'Perfume', 'Ventas', b'0', NULL),
(105, '65.00', '2017-08-17', 'Shampoo', 'Ventas', b'0', NULL),
(102, '200.00', '2017-09-06', 'Crema Depiladora', 'Ventas', b'0', NULL),
(101, '300.00', '2017-08-15', 'Shampoo', 'Ventas', b'0', NULL),
(103, '100.00', '2017-09-13', 'Cremá de Enjuague', 'Ventas', b'0', NULL),
(190, '450.00', '2017-10-30', 'Tintura Peinado', 'Turnos', b'0', NULL),
(189, '200.00', '2017-10-30', 'Corte', 'Turnos', b'0', NULL),
(188, '300.00', '2017-10-30', 'Alisado', 'Turnos', b'0', NULL),
(187, '200.00', '2017-10-30', 'Corte', 'Turnos', b'0', NULL),
(186, '120.00', '2017-10-23', 'Crema Depiladora', 'Ventas', b'0', NULL),
(185, '50.00', '2017-10-15', 'Gel', 'Ventas', b'0', NULL),
(184, '100.00', '2017-10-14', 'Shampoo', 'Ventas', b'0', NULL),
(183, '90.00', '2017-10-06', 'Crema de Enjuague', 'Ventas', b'0', NULL),
(182, '120.00', '2017-10-04', 'Crema Depiladora', 'Ventas', b'0', NULL),
(181, '50.00', '2017-10-01', 'Gel', 'Ventas', b'0', NULL),
(191, '200.00', '2017-10-30', 'Corte', 'Turnos', b'0', NULL),
(192, '200.00', '2017-10-30', 'Corte', 'Turnos', b'0', NULL),
(193, '300.00', '2017-10-31', 'Permanente', 'Turnos', b'0', NULL),
(194, '200.00', '2017-10-31', 'Corte', 'Turnos', b'0', NULL),
(195, '200.00', '2017-10-31', 'Corte', 'Turnos', b'0', NULL),
(196, '200.00', '2017-10-31', 'Corte', 'Turnos', b'0', NULL),
(197, '450.00', '2017-10-31', 'Tintura Peinado', 'Turnos', b'0', NULL),
(198, '450.00', '2017-10-31', 'Tintura Peinado', 'Turnos', b'0', NULL),
(199, '200.00', '2017-11-01', 'Corte', 'Turnos', b'0', NULL),
(200, '200.00', '2017-11-01', 'Corte', 'Turnos', b'0', NULL),
(201, '200.00', '2017-11-01', 'Corte', 'Turnos', b'0', NULL),
(202, '200.00', '2017-11-01', 'Corte', 'Turnos', b'0', NULL),
(203, '200.00', '2017-11-01', 'Tintura', 'Turnos', b'0', NULL),
(204, '300.00', '2017-11-01', 'Permanente', 'Turnos', b'0', NULL),
(205, '150.00', '2017-11-02', 'Peinado', 'Turnos', b'0', NULL),
(206, '200.00', '2017-11-02', 'Corte', 'Turnos', b'0', NULL),
(207, '200.00', '2017-11-02', 'Tintura', 'Turnos', b'0', NULL),
(208, '200.00', '2017-11-02', 'Corte', 'Turnos', b'0', NULL),
(209, '450.00', '2017-11-02', 'Tintura Peinado', 'Turnos', b'0', NULL),
(210, '200.00', '2017-11-02', 'Corte', 'Turnos', b'0', NULL),
(211, '100.00', '2017-11-03', 'Lavado', 'Turnos', b'0', NULL),
(212, '200.00', '2017-11-03', 'Corte', 'Turnos', b'0', NULL),
(213, '200.00', '2017-11-03', 'Corte', 'Turnos', b'0', NULL),
(214, '300.00', '2017-11-03', 'Permanente', 'Turnos', b'0', NULL),
(215, '200.00', '2017-11-03', 'Corte', 'Turnos', b'0', NULL),
(216, '150.00', '2017-11-03', 'Peinado', 'Turnos', b'0', NULL),
(401, '300.00', '2017-11-15', 'Shampoo', 'Ventas', b'0', NULL),
(402, '200.00', '2017-12-06', 'Crema Depiladora', 'Ventas', b'0', NULL),
(403, '100.00', '2017-12-13', 'Cremá de Enjuague', 'Ventas', b'0', NULL),
(405, '65.00', '2017-11-17', 'Shampoo', 'Ventas', b'0', NULL),
(404, '50.00', '2017-11-02', 'Perfume', 'Ventas', b'0', NULL),
(406, '100.00', '2017-11-24', 'Crema para el Cabello', 'Ventas', b'0', NULL),
(407, '400.00', '2017-12-06', 'Tijeras', 'Ventas', b'0', NULL),
(408, '200.00', '2017-11-08', 'Gel', 'Ventas', b'0', NULL),
(334, '350.00', '2017-12-26', 'Peinado Tintura', 'Turnos', b'0', NULL),
(310, '60.00', '2017-12-10', 'Crema de Enjuague', 'Ventas', b'0', NULL),
(311, '500.00', '2017-11-17', 'Tintura', 'Stock', b'1', NULL),
(312, '150.00', '2017-11-14', 'Guantes', 'Stock', b'1', NULL),
(313, '200.00', '2017-11-29', 'Shampoo', 'Stock', b'1', NULL),
(314, '300.00', '2017-12-11', 'Shampoo', 'Stock', b'1', NULL),
(315, '200.00', '2017-12-23', 'Tintura', 'Stock', b'1', NULL),
(316, '200.00', '2017-12-05', 'Cremas', 'Stock', b'1', NULL),
(317, '100.00', '2017-12-03', '', 'Luz', b'1', NULL),
(318, '87.00', '2017-12-04', NULL, 'Luz', b'1', NULL),
(319, '57.00', '2017-11-03', NULL, 'Luz', b'1', NULL),
(320, '80.00', '2017-07-04', NULL, 'Luz', b'1', NULL),
(321, '130.00', '2017-12-03', NULL, 'Gas', b'1', NULL),
(322, '100.00', '2017-12-01', NULL, 'Gas', b'1', NULL),
(323, '100.00', '2017-11-02', NULL, 'Gas', b'1', NULL),
(324, '69.00', '2017-07-02', NULL, 'Gas', b'1', NULL),
(325, '45.00', '2017-06-05', NULL, 'Luz', b'1', NULL),
(335, '150.00', '2017-12-26', 'Peinado', 'Turnos', b'0', NULL),
(336, '150.00', '2017-12-26', 'Peinado', 'Turnos', b'0', NULL),
(337, '150.00', '2017-12-25', 'Peinado', 'Turnos', b'0', NULL),
(338, '200.00', '2017-06-02', '', 'Gas', b'0', NULL),
(350, '180.00', '2017-06-04', '', 'Agua', b'0', NULL),
(351, '180.00', '2017-07-06', '', 'Agua', b'1', NULL),
(352, '180.00', '2017-11-02', '', 'Agua', b'1', NULL),
(353, '180.00', '2017-12-08', '', 'Agua', b'1', NULL),
(354, '180.00', '2017-12-05', '', 'Agua', b'1', NULL),
(355, '210.00', '2017-06-03', '', 'AFIP', b'0', NULL),
(356, '250.00', '2017-07-04', '', 'AFIP', b'1', NULL),
(357, '230.00', '2017-11-05', '', 'AFIP', b'1', NULL),
(358, '245.00', '2017-12-08', '', 'AFIP', b'1', NULL),
(359, '215.00', '2017-12-09', '', 'AFIP', b'1', NULL),
(360, '500.00', '2017-11-02', 'Cremas', 'Stock', b'1', NULL),
(361, '800.00', '2017-11-05', 'Tintura', 'Stock', b'1', NULL),
(362, '200.00', '2017-11-08', 'Shampoo', 'Stock', b'1', NULL),
(363, '800.00', '2017-12-01', 'Tintura', 'Stock', b'1', NULL),
(364, '290.00', '2017-12-03', 'Shampoo', 'Stock', b'1', NULL),
(365, '500.00', '2017-12-07', 'Cremas', 'Stock', b'1', NULL),
(366, '50.00', '2017-12-04', 'Guantes', 'Stock', b'1', NULL),
(367, '250.00', '2017-12-05', 'Shampoo', 'Stock', b'1', NULL),
(368, '900.00', '2017-12-09', 'Tintura', 'Stock', b'1', NULL),
(369, '90.00', '2017-11-05', 'Crema de Enjuague', 'Ventas', b'0', NULL),
(371, '120.00', '2017-11-14', 'Crema Depiladora', 'Ventas', b'0', NULL),
(372, '50.00', '2017-11-17', 'Gel', 'Ventas', b'0', NULL),
(373, '100.00', '2017-11-23', 'Shampoo', 'Ventas', b'0', NULL),
(374, '100.00', '2017-11-24', 'Crema para el Cabello', 'Ventas', b'0', NULL),
(375, '90.00', '2017-11-28', 'Crema de Enjuague', 'Ventas', b'0', NULL),
(376, '100.00', '2017-12-04', 'Shampoo', 'Ventas', b'0', NULL),
(377, '90.00', '2017-12-06', 'Crema de Enjuague', 'Ventas', b'0', NULL),
(378, '90.00', '2017-12-12', 'Crema de Enjuague', 'Ventas', b'0', NULL),
(379, '120.00', '2017-12-16', 'Crema Depiladora', 'Ventas', b'0', NULL),
(380, '100.00', '2017-12-26', 'Shampoo', 'Ventas', b'0', NULL),
(381, '50.00', '2017-12-01', 'Gel', 'Ventas', b'0', NULL),
(382, '120.00', '2017-12-04', 'Crema Depiladora', 'Ventas', b'0', NULL),
(383, '90.00', '2017-12-06', 'Crema de Enjuague', 'Ventas', b'0', NULL),
(384, '100.00', '2017-12-14', 'Shampoo', 'Ventas', b'0', NULL),
(385, '50.00', '2017-12-15', 'Gel', 'Ventas', b'0', NULL),
(386, '120.00', '2017-12-23', 'Crema Depiladora', 'Ventas', b'0', NULL),
(387, '200.00', '2017-12-30', 'Corte', 'Turnos', b'0', NULL),
(388, '300.00', '2017-12-30', 'Alisado', 'Turnos', b'0', NULL),
(389, '200.00', '2017-12-30', 'Corte', 'Turnos', b'0', NULL),
(390, '450.00', '2017-12-30', 'Tintura Peinado', 'Turnos', b'0', NULL),
(391, '200.00', '2017-12-30', 'Corte', 'Turnos', b'0', NULL),
(392, '200.00', '2017-12-30', 'Corte', 'Turnos', b'0', NULL),
(393, '300.00', '2017-12-31', 'Permanente', 'Turnos', b'0', NULL),
(394, '200.00', '2017-12-31', 'Corte', 'Turnos', b'0', NULL),
(395, '200.00', '2017-12-31', 'Corte', 'Turnos', b'0', NULL),
(396, '200.00', '2017-12-31', 'Corte', 'Turnos', b'0', NULL),
(397, '450.00', '2017-12-31', 'Tintura Peinado', 'Turnos', b'0', NULL),
(398, '450.00', '2017-12-31', 'Tintura Peinado', 'Turnos', b'0', NULL),
(399, '200.00', '2017-11-01', 'Corte', 'Turnos', b'0', NULL),
(340, '200.00', '2017-11-01', 'Corte', 'Turnos', b'0', NULL),
(341, '200.00', '2017-11-01', 'Corte', 'Turnos', b'0', NULL),
(342, '200.00', '2017-11-01', 'Corte', 'Turnos', b'0', NULL),
(343, '200.00', '2017-11-01', 'Tintura', 'Turnos', b'0', NULL),
(344, '300.00', '2017-11-01', 'Permanente', 'Turnos', b'0', NULL),
(345, '150.00', '2017-11-02', 'Peinado', 'Turnos', b'0', NULL),
(346, '200.00', '2017-11-02', 'Corte', 'Turnos', b'0', NULL),
(347, '200.00', '2017-11-02', 'Tintura', 'Turnos', b'0', NULL),
(348, '200.00', '2017-11-02', 'Corte', 'Turnos', b'0', NULL),
(349, '450.00', '2017-11-02', 'Tintura Peinado', 'Turnos', b'0', NULL),
(427, '1.00', '2017-12-02', NULL, 'Gas', b'1', '3'),
(426, '1.00', '2017-12-02', NULL, 'AFIP', b'0', 'D'),
(425, '1.00', '2017-12-02', NULL, 'AFIP', b'0', ''),
(423, '1.00', '2017-12-02', NULL, 'AFIP', b'1', 'D'),
(424, '1.00', '2017-12-02', NULL, 'AFIP', b'1', '3');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `peluquero`
--

CREATE TABLE `peluquero` (
  `ID_CIUDAD` int(11) NOT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `peluquero`
--

INSERT INTO `peluquero` (`ID_CIUDAD`, `NOMBRE`) VALUES
(1, 'Jorge'),
(2, 'Raul'),
(3, 'Romina');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `ID_PRODUCTO` int(11) NOT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL,
  `DESCRIPCION` varchar(255) DEFAULT NULL,
  `CANTIDAD` int(11) DEFAULT NULL,
  `CANTIDAD_MINIMA` int(11) DEFAULT NULL,
  `PRECIO` decimal(19,2) DEFAULT NULL,
  `PRODUCTO_VENTA` bit(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`ID_PRODUCTO`, `NOMBRE`, `DESCRIPCION`, `CANTIDAD`, `CANTIDAD_MINIMA`, `PRECIO`, `PRODUCTO_VENTA`) VALUES
(1, 'Tintura Roja', 'Pallmolive', 5, 4, '45.00', b'0'),
(2, 'Tintura Verde', 'Pallmolive', 5, 4, '45.00', b'0'),
(3, 'Tintura Rubio', 'Pallmolive', 2, 4, '45.00', b'0'),
(4, 'Tintura Castaño', 'Pallmolive', 5, 4, '45.00', b'0'),
(5, 'Tintura Morocha', 'Pallmolive', 5, 4, '45.00', b'0'),
(6, 'Shampoo', 'Sedal', 5, 4, '70.00', b'0'),
(7, 'Crema de enjuague', 'Sedal', 5, 4, '70.00', b'0'),
(8, 'Gel', 'Pallmolive', 5, 4, '50.00', b'0'),
(9, 'Cera de pelo', 'Pallmolive', 5, 4, '30.00', b'0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajo`
--

CREATE TABLE `trabajo` (
  `ID_TRABAJO` int(11) NOT NULL,
  `DESCRIPCION` varchar(255) DEFAULT NULL,
  `PRECIO` decimal(19,2) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `trabajo`
--

INSERT INTO `trabajo` (`ID_TRABAJO`, `DESCRIPCION`, `PRECIO`) VALUES
(1, 'Corte', '200.00'),
(2, 'Lavado', '150.00'),
(3, 'Peinado', '300.00'),
(4, 'Color', '250.00'),
(5, 'Alisado', '100.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajo_turno`
--

CREATE TABLE `trabajo_turno` (
  `ID_TRABAJO` int(11) NOT NULL,
  `DESCRIPCION` varchar(255) DEFAULT NULL,
  `PRECIO` decimal(19,2) DEFAULT NULL,
  `ID_TURNO` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `trabajo_turno`
--

INSERT INTO `trabajo_turno` (`ID_TRABAJO`, `DESCRIPCION`, `PRECIO`, `ID_TURNO`) VALUES
(1, 'Corte', '200.00', 1),
(2, 'Corte', '200.00', 2),
(3, 'Peinado', '300.00', 3),
(4, 'Lavado', '150.00', 4),
(5, 'Peinado', '300.00', 5),
(6, 'Peinado', '300.00', 6),
(7, 'Color', '250.00', 7),
(8, 'Color', '250.00', 8),
(9, 'Corte', '200.00', 9),
(10, 'Peinado', '300.00', 10),
(11, 'Lavado', '150.00', 11),
(12, 'Peinado', '300.00', 12),
(13, 'Color', '250.00', 13),
(14, 'Color', '250.00', 14),
(15, 'Peinado', '300.00', 15),
(16, 'Corte', '200.00', 16),
(17, 'Peinado', '300.00', 17),
(18, 'Peinado', '300.00', 18),
(19, 'Corte', '200.00', 19),
(20, 'Lavado', '150.00', 20),
(21, 'Peinado', '300.00', 21),
(22, 'Color', '250.00', 22),
(23, 'Color', '250.00', 23),
(24, 'Peinado', '300.00', 24),
(25, 'Lavado', '150.00', 25),
(26, 'Peinado', '300.00', 26),
(27, 'Lavado', '150.00', 27),
(28, 'Color', '250.00', 28),
(29, 'Corte', '200.00', 29),
(30, 'Corte', '200.00', 30),
(31, 'Peinado', '300.00', 31),
(32, 'Color', '250.00', 32),
(33, 'Color', '250.00', 33),
(34, 'Corte', '200.00', 34),
(35, 'Color', '250.00', 35),
(36, 'Color', '250.00', 36),
(37, 'Peinado', '300.00', 37),
(38, 'Peinado', '300.00', 38),
(39, 'Corte', '200.00', 39),
(40, 'Lavado', '150.00', 40),
(41, 'Corte', '200.00', 41),
(42, 'Corte', '200.00', 42),
(43, 'Lavado', '150.00', 43),
(44, 'Peinado', '300.00', 44),
(45, 'Lavado', '150.00', 45),
(46, 'Color', '250.00', 46),
(47, 'Corte', '200.00', 47),
(48, 'Corte', '200.00', 48),
(49, 'Lavado', '150.00', 49),
(50, 'Color', '250.00', 50),
(51, 'Peinado', '300.00', 51),
(52, 'Color', '250.00', 52),
(53, 'Color', '250.00', 53),
(54, 'Corte', '200.00', 54),
(55, 'Corte', '200.00', 55),
(56, 'Peinado', '300.00', 56),
(57, 'Color', '250.00', 57),
(58, 'Color', '250.00', 58),
(59, 'Lavado', '150.00', 59),
(60, 'Lavado', '150.00', 60),
(61, 'Peinado', '300.00', 61),
(62, 'Peinado', '300.00', 62),
(63, 'Corte', '200.00', 63),
(64, 'Color', '250.00', 64),
(65, 'Color', '250.00', 65),
(66, 'Color', '250.00', 66),
(67, 'Lavado', '150.00', 67),
(68, 'Color', '250.00', 68),
(69, 'Peinado', '300.00', 69),
(70, 'Color', '250.00', 70),
(71, 'Color', '250.00', 71),
(72, 'Lavado', '150.00', 72),
(73, 'Color', '250.00', 73),
(74, 'Corte', '200.00', 74),
(75, 'Corte', '200.00', 75),
(76, 'Corte', '200.00', 76),
(77, 'Lavado', '150.00', 77),
(78, 'Lavado', '150.00', 78),
(79, 'Lavado', '150.00', 79),
(80, 'Peinado', '300.00', 80),
(81, 'Lavado', '150.00', 81),
(82, 'Color', '250.00', 82),
(83, 'Lavado', '150.00', 83),
(84, 'Color', '250.00', 84),
(85, 'Lavado', '150.00', 85),
(86, 'Peinado', '300.00', 86),
(87, 'Color', '250.00', 87),
(88, 'Peinado', '300.00', 88),
(89, 'Lavado', '150.00', 89),
(90, 'Lavado', '150.00', 90),
(91, 'Lavado', '150.00', 91),
(92, 'Corte', '200.00', 92),
(93, 'Corte', '200.00', 93),
(94, 'Peinado', '300.00', 94),
(95, 'Color', '250.00', 95),
(96, 'Corte', '200.00', 96),
(97, 'Color', '250.00', 97),
(98, 'Peinado', '300.00', 98),
(99, 'Peinado', '300.00', 99),
(100, 'Corte', '200.00', 100),
(101, 'Peinado', '300.00', 101),
(102, 'Peinado', '300.00', 102),
(103, 'Lavado', '150.00', 103),
(104, 'Peinado', '300.00', 104),
(105, 'Peinado', '300.00', 105),
(106, 'Corte', '200.00', 106),
(107, 'Corte', '200.00', 107),
(108, 'Lavado', '150.00', 108),
(109, 'Corte', '200.00', 109),
(110, 'Color', '250.00', 110),
(111, 'Lavado', '150.00', 111),
(112, 'Lavado', '150.00', 112),
(113, 'Corte', '200.00', 113),
(114, 'Peinado', '300.00', 114),
(115, 'Corte', '200.00', 115),
(116, 'Peinado', '300.00', 116),
(117, 'Color', '250.00', 117),
(118, 'Peinado', '300.00', 118),
(119, 'Color', '250.00', 119),
(120, 'Color', '250.00', 120),
(121, 'Peinado', '300.00', 121),
(122, 'Corte', '200.00', 122),
(123, 'Corte', '200.00', 123),
(124, 'Peinado', '300.00', 124),
(125, 'Color', '250.00', 125),
(126, 'Corte', '200.00', 126),
(127, 'Corte', '200.00', 127),
(128, 'Lavado', '150.00', 128),
(129, 'Color', '250.00', 129),
(130, 'Lavado', '150.00', 130),
(131, 'Corte', '200.00', 131),
(132, 'Color', '250.00', 132),
(133, 'Color', '250.00', 133),
(134, 'Lavado', '150.00', 134),
(135, 'Peinado', '300.00', 135),
(136, 'Corte', '200.00', 136),
(137, 'Lavado', '150.00', 137),
(138, 'Color', '250.00', 138),
(139, 'Color', '250.00', 139),
(140, 'Corte', '200.00', 140),
(141, 'Peinado', '300.00', 141),
(142, 'Color', '250.00', 142),
(143, 'Peinado', '300.00', 143),
(144, 'Lavado', '150.00', 144),
(145, 'Peinado', '300.00', 145),
(146, 'Color', '250.00', 146),
(147, 'Corte', '200.00', 147),
(148, 'Corte', '200.00', 148),
(149, 'Color', '250.00', 149),
(150, 'Peinado', '300.00', 150),
(151, 'Peinado', '300.00', 151),
(152, 'Peinado', '300.00', 152),
(153, 'Color', '250.00', 153),
(154, 'Color', '250.00', 154),
(155, 'Color', '250.00', 155),
(156, 'Peinado', '300.00', 156),
(157, 'Lavado', '150.00', 157),
(158, 'Peinado', '300.00', 158),
(159, 'Color', '250.00', 159),
(160, 'Peinado', '300.00', 160),
(161, 'Color', '250.00', 161),
(162, 'Color', '250.00', 162),
(163, 'Lavado', '150.00', 163),
(164, 'Corte', '200.00', 164),
(165, 'Corte', '200.00', 165),
(166, 'Peinado', '300.00', 166),
(167, 'Color', '250.00', 167),
(168, 'Peinado', '300.00', 168),
(169, 'Lavado', '150.00', 169),
(170, 'Corte', '200.00', 170),
(171, 'Peinado', '300.00', 171),
(172, 'Peinado', '300.00', 172),
(173, 'Lavado', '150.00', 173),
(174, 'Lavado', '150.00', 174),
(175, 'Corte', '200.00', 175),
(176, 'Lavado', '150.00', 176),
(177, 'Peinado', '300.00', 177),
(178, 'Corte', '200.00', 178),
(179, 'Lavado', '150.00', 179),
(180, 'Color', '250.00', 180),
(181, 'Lavado', '150.00', 181),
(182, 'Corte', '200.00', 182),
(183, 'Peinado', '300.00', 183),
(184, 'Corte', '200.00', 184),
(185, 'Color', '250.00', 185),
(186, 'Lavado', '150.00', 186),
(187, 'Corte', '200.00', 187),
(188, 'Corte', '200.00', 188),
(189, 'Lavado', '150.00', 189),
(190, 'Lavado', '150.00', 190),
(191, 'Peinado', '300.00', 191),
(192, 'Color', '250.00', 192),
(193, 'Lavado', '150.00', 193),
(194, 'Peinado', '300.00', 194),
(195, 'Lavado', '150.00', 195),
(196, 'Color', '250.00', 196),
(197, 'Lavado', '150.00', 197),
(198, 'Peinado', '300.00', 198),
(199, 'Lavado', '150.00', 199),
(200, 'Lavado', '150.00', 200),
(201, 'Peinado', '300.00', 201),
(202, 'Corte', '200.00', 202),
(203, 'Lavado', '150.00', 203),
(204, 'Peinado', '300.00', 204);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `turnos`
--

CREATE TABLE `turnos` (
  `ID_TURNO` int(11) NOT NULL,
  `CLIENTE_ID` int(11) NOT NULL,
  `PELUQUERO` varchar(255) DEFAULT NULL,
  `DESDE` datetime DEFAULT NULL,
  `HASTA` datetime DEFAULT NULL,
  `COBRADO` bit(1) DEFAULT NULL,
  `ID_MOVIMIENTO` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `turnos`
--

INSERT INTO `turnos` (`ID_TURNO`, `CLIENTE_ID`, `PELUQUERO`, `DESDE`, `HASTA`, `COBRADO`, `ID_MOVIMIENTO`) VALUES
(1, 49, 'Jorge', '2017-12-01 08:15:00', '2017-12-01 08:30:00', b'0', NULL),
(2, 28, 'Jorge', '2017-12-02 08:15:00', '2017-12-02 09:00:00', b'0', NULL),
(3, 57, 'Jorge', '2017-12-01 08:00:00', '2017-12-01 08:45:00', b'0', NULL),
(4, 52, 'Jorge', '2017-12-01 09:00:00', '2017-12-01 09:30:00', b'0', NULL),
(5, 11, 'Romina', '2017-12-01 10:00:00', '2017-12-01 10:30:00', b'0', NULL),
(6, 56, 'Raul', '2017-12-01 10:45:00', '2017-12-01 12:00:00', b'0', NULL),
(7, 22, 'Jorge', '2017-12-01 12:15:00', '2017-12-01 12:45:00', b'0', NULL),
(8, 58, 'Romina', '2017-12-01 15:00:00', '2017-12-01 15:30:00', b'0', NULL),
(9, 20, 'Raul', '2017-12-01 15:45:00', '2017-12-01 16:00:00', b'0', NULL),
(10, 40, 'Jorge', '2017-12-01 16:30:00', '2017-12-01 17:00:00', b'0', NULL),
(11, 52, 'Romina', '2017-12-01 16:45:00', '2017-12-01 17:15:00', b'0', NULL),
(12, 24, 'Raul', '2017-12-01 17:30:00', '2017-12-01 17:45:00', b'0', NULL),
(13, 31, 'Jorge', '2017-12-01 18:00:00', '2017-12-01 18:30:00', b'0', NULL),
(14, 24, 'Romina', '2017-12-02 09:00:00', '2017-12-02 09:45:00', b'0', NULL),
(15, 20, 'Raul', '2017-12-02 10:00:00', '2017-12-02 10:15:00', b'0', NULL),
(16, 36, 'Jorge', '2017-12-02 10:15:00', '2017-12-02 10:45:00', b'0', NULL),
(17, 59, 'Jorge', '2017-12-02 10:45:00', '2017-12-02 11:15:00', b'0', NULL),
(18, 32, 'Romina', '2017-12-02 11:30:00', '2017-12-02 12:00:00', b'0', NULL),
(19, 57, 'Raul', '2017-12-02 15:00:00', '2017-12-02 15:30:00', b'0', NULL),
(20, 21, 'Jorge', '2017-12-02 15:45:00', '2017-12-02 16:15:00', b'0', NULL),
(21, 45, 'Jorge', '2017-12-02 16:30:00', '2017-12-02 17:00:00', b'0', NULL),
(22, 54, 'Romina', '2017-12-02 17:00:00', '2017-12-02 17:15:00', b'0', NULL),
(23, 25, 'Jorge', '2017-12-02 17:30:00', '2017-12-02 18:00:00', b'0', NULL),
(24, 43, 'Jorge', '2017-12-02 18:15:00', '2017-12-02 18:45:00', b'0', NULL),
(25, 22, 'Raul', '2017-12-05 09:15:00', '2017-12-05 09:45:00', b'0', NULL),
(26, 51, 'Romina', '2017-12-05 09:45:00', '2017-12-05 10:15:00', b'0', NULL),
(27, 50, 'Jorge', '2017-12-05 10:30:00', '2017-12-05 10:45:00', b'0', NULL),
(28, 11, 'Jorge', '2017-12-05 11:00:00', '2017-12-05 11:30:00', b'0', NULL),
(29, 41, 'Raul', '2017-12-05 12:00:00', '2017-12-05 12:15:00', b'0', NULL),
(30, 33, 'Raul', '2017-12-05 15:15:00', '2017-12-05 15:30:00', b'0', NULL),
(31, 35, 'Jorge', '2017-12-05 16:00:00', '2017-12-05 16:15:00', b'0', NULL),
(32, 25, 'Raul', '2017-12-05 16:45:00', '2017-12-05 17:15:00', b'0', NULL),
(33, 31, 'Romina', '2017-12-05 18:00:00', '2017-12-05 18:15:00', b'0', NULL),
(34, 48, 'Jorge', '2017-12-05 18:45:00', '2017-12-05 19:00:00', b'0', NULL),
(35, 55, 'Romina', '2017-12-06 08:00:00', '2017-12-06 08:15:00', b'0', NULL),
(36, 46, 'Raul', '2017-12-06 08:45:00', '2017-12-06 09:00:00', b'0', NULL),
(37, 44, 'Jorge', '2017-12-06 09:00:00', '2017-12-06 09:30:00', b'0', NULL),
(38, 56, 'Romina', '2017-12-06 09:30:00', '2017-12-06 09:45:00', b'0', NULL),
(39, 48, 'Raul', '2017-12-06 10:00:00', '2017-12-06 10:15:00', b'0', NULL),
(40, 22, 'Jorge', '2017-12-06 10:15:00', '2017-12-06 10:30:00', b'0', NULL),
(41, 36, 'Jorge', '2017-12-06 11:15:00', '2017-12-06 11:30:00', b'0', NULL),
(42, 19, 'Jorge', '2017-12-06 15:45:00', '2017-12-06 16:00:00', b'0', NULL),
(43, 29, 'Romina', '2017-12-06 16:45:00', '2017-12-06 17:15:00', b'0', NULL),
(44, 42, 'Jorge', '2017-12-06 17:45:00', '2017-12-06 18:00:00', b'0', NULL),
(45, 53, 'Jorge', '2017-12-06 18:15:00', '2017-12-06 18:45:00', b'0', NULL),
(46, 50, 'Romina', '2017-12-07 08:30:00', '2017-12-07 08:45:00', b'0', NULL),
(47, 53, 'Jorge', '2017-12-07 09:15:00', '2017-12-07 09:30:00', b'0', NULL),
(48, 49, 'Romina', '2017-12-07 09:45:00', '2017-12-07 10:00:00', b'0', NULL),
(49, 30, 'Jorge', '2017-12-07 10:30:00', '2017-12-07 10:45:00', b'0', NULL),
(50, 40, 'Romina', '2017-12-07 15:15:00', '2017-12-07 15:45:00', b'0', NULL),
(51, 13, 'Raul', '2017-12-07 16:00:00', '2017-12-07 16:30:00', b'0', NULL),
(52, 28, 'Romina', '2017-12-07 17:30:00', '2017-12-07 17:45:00', b'0', NULL),
(53, 37, 'Jorge', '2017-12-07 18:15:00', '2017-12-07 18:30:00', b'0', NULL),
(54, 47, 'Romina', '2017-12-08 08:45:00', '2017-12-08 09:15:00', b'0', NULL),
(55, 15, 'Jorge', '2017-12-08 10:00:00', '2017-12-08 10:15:00', b'0', NULL),
(56, 14, 'Jorge', '2017-12-08 11:00:00', '2017-12-08 11:15:00', b'0', NULL),
(57, 55, 'Romina', '2017-12-08 11:45:00', '2017-12-08 12:15:00', b'0', NULL),
(58, 57, 'Jorge', '2017-12-08 15:00:00', '2017-12-08 15:15:00', b'0', NULL),
(59, 48, 'Jorge', '2017-12-08 16:15:00', '2017-12-08 16:30:00', b'0', NULL),
(60, 22, 'Romina', '2017-12-08 16:45:00', '2017-12-08 17:00:00', b'0', NULL),
(61, 26, 'Raul', '2017-12-08 17:30:00', '2017-12-08 18:00:00', b'0', NULL),
(62, 44, 'Romina', '2017-12-08 18:30:00', '2017-12-08 19:00:00', b'0', NULL),
(63, 40, 'Jorge', '2017-12-09 08:30:00', '2017-12-09 09:00:00', b'0', NULL),
(64, 29, 'Jorge', '2017-12-09 09:30:00', '2017-12-09 09:45:00', b'0', NULL),
(65, 11, 'Romina', '2017-12-09 10:15:00', '2017-12-09 10:30:00', b'0', NULL),
(66, 37, 'Raul', '2017-12-09 11:45:00', '2017-12-09 12:30:00', b'0', NULL),
(67, 39, 'Jorge', '2017-12-09 15:00:00', '2017-12-09 15:45:00', b'0', NULL),
(68, 41, 'Raul', '2017-12-09 16:45:00', '2017-12-09 17:30:00', b'0', NULL),
(69, 36, 'Jorge', '2017-12-09 17:45:00', '2017-12-09 18:00:00', b'0', NULL),
(70, 27, 'Romina', '2017-12-09 18:30:00', '2017-12-09 19:00:00', b'0', NULL),
(71, 27, 'Jorge', '2017-12-12 08:15:00', '2017-12-12 08:30:00', b'0', NULL),
(72, 21, 'Jorge', '2017-12-12 09:00:00', '2017-12-12 09:45:00', b'0', NULL),
(73, 32, 'Romina', '2017-12-12 09:45:00', '2017-12-12 10:15:00', b'0', NULL),
(74, 13, 'Raul', '2017-12-12 10:30:00', '2017-12-12 11:00:00', b'0', NULL),
(75, 56, 'Romina', '2017-12-12 12:15:00', '2017-12-12 12:45:00', b'0', NULL),
(76, 60, 'Jorge', '2017-12-12 15:00:00', '2017-12-12 15:15:00', b'0', NULL),
(77, 17, 'Romina', '2017-12-12 15:45:00', '2017-12-12 16:00:00', b'0', NULL),
(78, 40, 'Raul', '2017-12-12 16:45:00', '2017-12-12 17:00:00', b'0', NULL),
(79, 42, 'Raul', '2017-12-12 17:15:00', '2017-12-12 17:30:00', b'0', NULL),
(80, 37, 'Jorge', '2017-12-12 18:15:00', '2017-12-12 18:30:00', b'0', NULL),
(81, 56, 'Romina', '2017-12-12 18:45:00', '2017-12-12 19:00:00', b'0', NULL),
(82, 41, 'Jorge', '2017-12-13 09:00:00', '2017-12-13 09:30:00', b'0', NULL),
(83, 39, 'Raul', '2017-12-13 09:45:00', '2017-12-13 10:00:00', b'0', NULL),
(84, 34, 'Jorge', '2017-12-13 10:30:00', '2017-12-13 11:00:00', b'0', NULL),
(85, 47, 'Romina', '2017-12-13 16:15:00', '2017-12-13 16:45:00', b'0', NULL),
(86, 26, 'Jorge', '2017-12-13 17:45:00', '2017-12-13 18:00:00', b'0', NULL),
(87, 40, 'Romina', '2017-12-13 18:00:00', '2017-12-13 18:15:00', b'0', NULL),
(88, 32, 'Jorge', '2017-12-13 18:15:00', '2017-12-13 18:30:00', b'0', NULL),
(89, 19, 'Jorge', '2017-12-14 08:45:00', '2017-12-14 09:15:00', b'0', NULL),
(90, 30, 'Romina', '2017-12-14 09:45:00', '2017-12-14 10:15:00', b'0', NULL),
(91, 51, 'Raul', '2017-12-14 11:15:00', '2017-12-14 11:45:00', b'0', NULL),
(92, 58, 'Jorge', '2017-12-14 12:15:00', '2017-12-14 12:45:00', b'0', NULL),
(93, 21, 'Romina', '2017-12-14 15:45:00', '2017-12-14 16:30:00', b'0', NULL),
(94, 56, 'Raul', '2017-12-14 17:15:00', '2017-12-14 17:45:00', b'0', NULL),
(95, 43, 'Romina', '2017-12-14 18:30:00', '2017-12-14 19:00:00', b'0', NULL),
(96, 43, 'Raul', '2017-12-15 08:30:00', '2017-12-15 08:45:00', b'0', NULL),
(97, 48, 'Jorge', '2017-12-15 10:00:00', '2017-12-15 10:30:00', b'0', NULL),
(98, 52, 'Romina', '2017-12-15 10:45:00', '2017-12-15 11:15:00', b'0', NULL),
(99, 37, 'Jorge', '2017-12-15 15:00:00', '2017-12-15 15:15:00', b'0', NULL),
(100, 35, 'Jorge', '2017-12-15 16:15:00', '2017-12-15 16:30:00', b'0', NULL),
(101, 33, 'Jorge', '2017-12-15 16:45:00', '2017-12-15 17:00:00', b'0', NULL),
(102, 54, 'Raul', '2017-12-15 17:15:00', '2017-12-15 17:45:00', b'0', NULL),
(103, 19, 'Jorge', '2017-12-15 18:00:00', '2017-12-15 19:00:00', b'0', NULL),
(104, 26, 'Romina', '2017-12-16 09:30:00', '2017-12-16 10:00:00', b'0', NULL),
(105, 25, 'Raul', '2017-12-16 10:45:00', '2017-12-16 11:15:00', b'0', NULL),
(106, 39, 'Jorge', '2017-12-16 11:30:00', '2017-12-16 12:15:00', b'0', NULL),
(107, 55, 'Romina', '2017-12-16 12:15:00', '2017-12-16 15:00:00', b'0', NULL),
(108, 35, 'Raul', '2017-12-16 15:30:00', '2017-12-16 15:45:00', b'0', NULL),
(109, 46, 'Jorge', '2017-12-16 16:30:00', '2017-12-16 17:00:00', b'0', NULL),
(110, 59, 'Raul', '2017-12-16 17:15:00', '2017-12-16 17:45:00', b'0', NULL),
(111, 11, 'Jorge', '2017-12-16 18:15:00', '2017-12-16 18:30:00', b'0', NULL),
(112, 32, 'Romina', '2017-12-16 18:45:00', '2017-12-16 19:00:00', b'0', NULL),
(113, 11, 'Jorge', '2017-12-19 08:00:00', '2017-12-19 08:15:00', b'0', NULL),
(114, 36, 'Raul', '2017-12-19 08:30:00', '2017-12-19 08:45:00', b'0', NULL),
(115, 10, 'Jorge', '2017-12-19 09:15:00', '2017-12-19 09:30:00', b'0', NULL),
(116, 24, 'Jorge', '2017-12-19 10:00:00', '2017-12-19 10:45:00', b'0', NULL),
(117, 47, 'Romina', '2017-12-19 15:00:00', '2017-12-19 15:45:00', b'0', NULL),
(118, 41, 'Raul', '2017-12-19 16:00:00', '2017-12-19 17:00:00', b'0', NULL),
(119, 21, 'Jorge', '2017-12-19 17:15:00', '2017-12-19 17:45:00', b'0', NULL),
(120, 12, 'Raul', '2017-12-19 18:15:00', '2017-12-19 18:30:00', b'0', NULL),
(121, 52, 'Jorge', '2017-12-19 18:45:00', '2017-12-19 19:00:00', b'0', NULL),
(122, 59, 'Romina', '2017-12-20 08:30:00', '2017-12-20 08:45:00', b'0', NULL),
(123, 31, 'Jorge', '2017-12-20 10:15:00', '2017-12-20 11:00:00', b'0', NULL),
(124, 16, 'Raul', '2017-12-20 12:15:00', '2017-12-20 12:45:00', b'0', NULL),
(125, 21, 'Romina', '2017-12-20 15:45:00', '2017-12-20 16:15:00', b'0', NULL),
(126, 55, 'Jorge', '2017-12-20 16:45:00', '2017-12-20 17:00:00', b'0', NULL),
(127, 30, 'Jorge', '2017-12-20 17:45:00', '2017-12-20 18:15:00', b'0', NULL),
(128, 20, 'Romina', '2017-12-20 18:30:00', '2017-12-20 19:00:00', b'0', NULL),
(129, 13, 'Raul', '2017-12-21 08:15:00', '2017-12-21 08:45:00', b'0', NULL),
(130, 16, 'Romina', '2017-12-21 09:30:00', '2017-12-21 09:45:00', b'0', NULL),
(131, 43, 'Jorge', '2017-12-21 10:15:00', '2017-12-21 10:45:00', b'0', NULL),
(132, 12, 'Romina', '2017-12-21 11:45:00', '2017-12-21 12:00:00', b'0', NULL),
(133, 23, 'Raul', '2017-12-21 15:15:00', '2017-12-21 15:45:00', b'0', NULL),
(134, 16, 'Romina', '2017-12-21 16:30:00', '2017-12-21 17:15:00', b'0', NULL),
(135, 36, 'Raul', '2017-12-21 18:00:00', '2017-12-21 18:30:00', b'0', NULL),
(136, 17, 'Raul', '2017-12-22 09:30:00', '2017-12-22 10:00:00', b'0', NULL),
(137, 53, 'Romina', '2017-12-22 10:45:00', '2017-12-22 11:30:00', b'0', NULL),
(138, 29, 'Jorge', '2017-12-22 12:00:00', '2017-12-22 12:45:00', b'0', NULL),
(139, 13, 'Raul', '2017-12-22 15:45:00', '2017-12-22 16:15:00', b'0', NULL),
(140, 45, 'Raul', '2017-12-22 16:30:00', '2017-12-22 16:45:00', b'0', NULL),
(141, 29, 'Jorge', '2017-12-22 17:45:00', '2017-12-22 18:15:00', b'0', NULL),
(142, 58, 'Romina', '2017-12-22 18:15:00', '2017-12-22 19:00:00', b'0', NULL),
(143, 59, 'Jorge', '2017-12-23 09:45:00', '2017-12-23 10:15:00', b'0', NULL),
(144, 26, 'Romina', '2017-12-23 11:00:00', '2017-12-23 11:30:00', b'0', NULL),
(145, 29, 'Jorge', '2017-12-23 12:00:00', '2017-12-23 12:30:00', b'0', NULL),
(146, 56, 'Raul', '2017-12-23 15:30:00', '2017-12-23 15:45:00', b'0', NULL),
(147, 40, 'Raul', '2017-12-23 16:30:00', '2017-12-23 16:45:00', b'0', NULL),
(148, 33, 'Jorge', '2017-12-23 18:45:00', '2017-12-23 19:00:00', b'0', NULL),
(149, 55, 'Romina', '2017-12-26 10:15:00', '2017-12-26 10:30:00', b'0', NULL),
(150, 29, 'Raul', '2017-12-26 12:00:00', '2017-12-26 12:30:00', b'0', NULL),
(151, 21, 'Jorge', '2017-12-26 17:00:00', '2017-12-26 17:30:00', b'0', NULL),
(152, 58, 'Raul', '2017-12-26 18:15:00', '2017-12-26 18:30:00', b'0', NULL),
(153, 26, 'Jorge', '2017-12-27 08:45:00', '2017-12-27 09:00:00', b'0', NULL),
(154, 55, 'Jorge', '2017-12-27 09:45:00', '2017-12-27 10:00:00', b'0', NULL),
(155, 28, 'Raul', '2017-12-27 11:45:00', '2017-12-27 12:15:00', b'0', NULL),
(156, 22, 'Jorge', '2017-12-27 15:15:00', '2017-12-27 15:45:00', b'0', NULL),
(157, 27, 'Romina', '2017-12-27 16:45:00', '2017-12-27 17:15:00', b'0', NULL),
(158, 20, 'Jorge', '2017-12-27 18:30:00', '2017-12-27 18:45:00', b'0', NULL),
(159, 37, 'Raul', '2017-12-28 09:45:00', '2017-12-28 10:15:00', b'0', NULL),
(160, 42, 'Romina', '2017-12-28 11:30:00', '2017-12-28 11:45:00', b'0', NULL),
(161, 13, 'Jorge', '2017-12-28 15:00:00', '2017-12-28 15:15:00', b'0', NULL),
(162, 60, 'Romina', '2017-12-28 16:00:00', '2017-12-28 16:15:00', b'0', NULL),
(163, 12, 'Raul', '2017-12-28 18:15:00', '2017-12-28 18:45:00', b'0', NULL),
(164, 46, 'Romina', '2017-12-28 18:45:00', '2017-12-28 19:00:00', b'0', NULL),
(165, 22, 'Romina', '2017-12-29 09:00:00', '2017-12-29 09:30:00', b'0', NULL),
(166, 50, 'Jorge', '2017-12-29 11:00:00', '2017-12-29 11:30:00', b'0', NULL),
(167, 34, 'Raul', '2017-12-29 15:30:00', '2017-12-29 16:00:00', b'0', NULL),
(168, 60, 'Romina', '2017-12-29 17:15:00', '2017-12-29 17:45:00', b'0', NULL),
(169, 17, 'Jorge', '2017-12-29 18:45:00', '2017-12-29 19:00:00', b'0', NULL),
(170, 25, 'Raul', '2017-12-30 08:00:00', '2017-12-30 08:15:00', b'0', NULL),
(171, 36, 'Romina', '2017-12-30 09:15:00', '2017-12-30 09:30:00', b'0', NULL),
(172, 18, 'Jorge', '2017-12-30 09:30:00', '2017-12-30 09:45:00', b'0', NULL),
(173, 43, 'Romina', '2017-12-30 09:45:00', '2017-12-30 10:00:00', b'0', NULL),
(174, 14, 'Jorge', '2017-12-30 10:45:00', '2017-12-30 11:00:00', b'0', NULL),
(175, 11, 'Raul', '2017-12-30 12:15:00', '2017-12-30 12:30:00', b'0', NULL),
(176, 21, 'Romina', '2017-12-30 15:45:00', '2017-12-30 16:00:00', b'0', NULL),
(177, 46, 'Romina', '2017-12-30 17:45:00', '2017-12-30 18:15:00', b'0', NULL),
(178, 33, 'Jorge', '2017-12-30 18:15:00', '2017-12-30 18:30:00', b'0', NULL),
(179, 27, 'Raul', '2018-01-02 09:00:00', '2018-01-02 09:30:00', b'0', NULL),
(180, 54, 'Romina', '2018-01-02 11:30:00', '2018-01-02 12:00:00', b'0', NULL),
(181, 58, 'Jorge', '2018-01-02 12:30:00', '2018-01-02 12:45:00', b'0', NULL),
(182, 28, 'Romina', '2018-01-02 16:00:00', '2018-01-02 16:15:00', b'0', NULL),
(183, 57, 'Raul', '2018-01-02 18:15:00', '2018-01-02 18:30:00', b'0', NULL),
(184, 25, 'Romina', '2018-01-03 08:15:00', '2018-01-03 08:45:00', b'0', NULL),
(185, 14, 'Romina', '2018-01-03 10:00:00', '2018-01-03 10:15:00', b'0', NULL),
(186, 56, 'Romina', '2018-01-03 12:00:00', '2018-01-03 12:15:00', b'0', NULL),
(187, 54, 'Jorge', '2018-01-03 16:15:00', '2018-01-03 16:45:00', b'0', NULL),
(188, 60, 'Romina', '2018-01-03 18:00:00', '2018-01-03 18:45:00', b'0', NULL),
(189, 12, 'Jorge', '2018-01-04 09:30:00', '2018-01-04 09:45:00', b'0', NULL),
(190, 16, 'Raul', '2018-01-04 12:00:00', '2018-01-04 12:30:00', b'0', NULL),
(191, 11, 'Romina', '2018-01-04 16:30:00', '2018-01-04 17:00:00', b'0', NULL),
(192, 11, 'Romina', '2018-01-04 18:15:00', '2018-01-04 18:45:00', b'0', NULL),
(193, 30, 'Jorge', '2018-01-05 08:45:00', '2018-01-05 09:00:00', b'0', NULL),
(194, 43, 'Raul', '2018-01-05 10:00:00', '2018-01-05 10:15:00', b'0', NULL),
(195, 18, 'Romina', '2018-01-05 12:15:00', '2018-01-05 12:30:00', b'0', NULL),
(196, 19, 'Jorge', '2018-01-05 15:30:00', '2018-01-05 16:00:00', b'0', NULL),
(197, 10, 'Romina', '2018-01-05 17:30:00', '2018-01-05 18:15:00', b'0', NULL),
(198, 17, 'Raul', '2018-01-05 18:45:00', '2018-01-05 19:00:00', b'0', NULL),
(199, 27, 'Romina', '2018-01-06 09:00:00', '2018-01-06 09:30:00', b'0', NULL),
(200, 30, 'Raul', '2018-01-06 10:45:00', '2018-01-06 11:15:00', b'0', NULL),
(201, 48, 'Romina', '2018-01-06 12:00:00', '2018-01-06 12:15:00', b'0', NULL),
(202, 42, 'Raul', '2018-01-06 15:45:00', '2018-01-06 16:15:00', b'0', NULL),
(203, 58, 'Romina', '2018-01-06 16:45:00', '2018-01-06 17:00:00', b'0', NULL),
(204, 34, 'Jorge', '2018-01-06 18:15:00', '2018-01-06 18:45:00', b'0', NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`ID_CATEGORIA`),
  ADD UNIQUE KEY `UK_m0dy99c26sx6xs0wjy3svl4js` (`NOMBRE`);

--
-- Indices de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD PRIMARY KEY (`ID_CIUDAD`),
  ADD UNIQUE KEY `UK_10d28mt31txl4sr8afv3mxtrm` (`NOMBRE`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`ID_USUARIO`);

--
-- Indices de la tabla `movimiento`
--
ALTER TABLE `movimiento`
  ADD PRIMARY KEY (`ID_MOVIMIENTO`);

--
-- Indices de la tabla `peluquero`
--
ALTER TABLE `peluquero`
  ADD PRIMARY KEY (`ID_CIUDAD`),
  ADD UNIQUE KEY `UK_8d7tvv8w6cfgarwb5hvp5dw38` (`NOMBRE`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`ID_PRODUCTO`);

--
-- Indices de la tabla `trabajo`
--
ALTER TABLE `trabajo`
  ADD PRIMARY KEY (`ID_TRABAJO`);

--
-- Indices de la tabla `trabajo_turno`
--
ALTER TABLE `trabajo_turno`
  ADD PRIMARY KEY (`ID_TRABAJO`),
  ADD KEY `FKa0722rh64n5k7tj7u81qm32sx` (`ID_TURNO`);

--
-- Indices de la tabla `turnos`
--
ALTER TABLE `turnos`
  ADD PRIMARY KEY (`ID_TURNO`),
  ADD KEY `FK7h4n58byoj5fnu7wbkkrp4drs` (`CLIENTE_ID`),
  ADD KEY `FKh6q2c1ejrqmr60or1tfhocjn3` (`ID_MOVIMIENTO`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `trabajo`
--
ALTER TABLE `trabajo`
  MODIFY `ID_TRABAJO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
