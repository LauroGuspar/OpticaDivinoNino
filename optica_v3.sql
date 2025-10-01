-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-10-2025 a las 18:38:09
-- Versión del servidor: 9.3.0
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `optica`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id_categoria` bigint NOT NULL,
  `categ_nombre` varchar(100) NOT NULL,
  `categ_estado` int NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id_categoria`, `categ_nombre`, `categ_estado`) VALUES
(1, 'Niños', 1),
(2, 'Hombre', 1),
(3, 'Mujer', 1),
(4, 'Unisex', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` bigint NOT NULL,
  `cli_nombre` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cli_nombreuser` varchar(50) NOT NULL,
  `cli_apellido_paterno` varchar(100) NOT NULL,
  `cli_apellido_materno` varchar(100) NOT NULL,
  `cli_correo` varchar(60) NOT NULL,
  `cli_contrasena` varchar(150) NOT NULL,
  `cli_telefono` varchar(9) NOT NULL,
  `cli_direccion` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cli_estado` int NOT NULL DEFAULT '1',
  `cli_ndocumento` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `id_tipodocumento` bigint NOT NULL,
  `id_perfil` bigint NOT NULL DEFAULT '4',
  `cli_nombre_empresa` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `cli_direccion_empresa` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `compania_transporte`
--

CREATE TABLE `compania_transporte` (
  `id_transporte` bigint NOT NULL,
  `transport_nombre` varchar(100) NOT NULL,
  `transport_estado` int NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contacto`
--

CREATE TABLE `contacto` (
  `id_contacto` bigint NOT NULL,
  `contac_nombre` varchar(100) NOT NULL,
  `contac_apellido_paterno` varchar(100) NOT NULL,
  `contac_apellido_materno` varchar(100) NOT NULL,
  `contac_telefono` char(9) NOT NULL,
  `contac_observaciones` varchar(300) DEFAULT NULL,
  `id_proveedor` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_orden`
--

CREATE TABLE `detalle_orden` (
  `id_producto` bigint NOT NULL,
  `id_orden` bigint NOT NULL,
  `detalleorden_precio_original` decimal(10,0) NOT NULL,
  `detalleorden_cantidad` int NOT NULL,
  `detalleorden_estado` int NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_venta`
--

CREATE TABLE `detalle_venta` (
  `id_detalle` bigint NOT NULL,
  `detal_venta_cantidad` int NOT NULL,
  `detal_venta_precio` decimal(10,0) DEFAULT '0',
  `detal_venta_subtotal` decimal(10,0) DEFAULT '0',
  `id_venta` bigint NOT NULL,
  `id_producto` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `direccion_entrega`
--

CREATE TABLE `direccion_entrega` (
  `id_entrega` bigint NOT NULL,
  `entregadirec_mombre_cliente` varchar(100) NOT NULL,
  `entregadirec_telefono` char(9) NOT NULL,
  `entregadirec_provincia` varchar(100) NOT NULL,
  `entregadirec_direccion` varchar(150) NOT NULL,
  `id_cliente` bigint NOT NULL,
  `id_venta` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `id_empleado` bigint NOT NULL,
  `emple_nombre` varchar(100) NOT NULL,
  `emple_nombreuser` varchar(50) NOT NULL,
  `emple_apellido_paterno` varchar(100) NOT NULL,
  `emple_apellido_materno` varchar(100) NOT NULL,
  `emple_correo` varchar(60) NOT NULL,
  `emple_contrasena` varchar(150) NOT NULL,
  `emple_telefono` varchar(9) NOT NULL,
  `emple_direccion` varchar(100) NOT NULL,
  `emple_estado` int NOT NULL DEFAULT '1',
  `emple_ndocumento` varchar(20) NOT NULL,
  `id_tipodocumento` bigint NOT NULL,
  `id_perfil` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`id_empleado`, `emple_nombre`, `emple_nombreuser`, `emple_apellido_paterno`, `emple_apellido_materno`, `emple_correo`, `emple_contrasena`, `emple_telefono`, `emple_direccion`, `emple_estado`, `emple_ndocumento`, `id_tipodocumento`, `id_perfil`) VALUES
(1, 'Daryl', 'admin', 'Zamora', 'Zamora', 'luis@ejemplo.com', '$2a$10$OZuN1MJlw/01gIodlwqaQOKk.d5XhfbWAD8X2adyG9pkKtpDlVN1O', '111111111', 'Direccion Ficticia', 1, '11111111', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `forma_pago`
--

CREATE TABLE `forma_pago` (
  `id_fpago` bigint NOT NULL,
  `fpago_metodo` varchar(15) DEFAULT NULL,
  `fpago_estado` int NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE `marca` (
  `id_marca` bigint NOT NULL,
  `marca_nombre` varchar(100) NOT NULL,
  `marca_fecha` date NOT NULL,
  `marca_estado` int NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`id_marca`, `marca_nombre`, `marca_fecha`, `marca_estado`) VALUES
(1, 'Marca01', '2025-08-26', 1),
(2, 'Marca02', '2025-08-26', 1),
(3, 'Marca03', '2025-08-26', 1),
(4, 'Marca04', '2025-08-26', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `opcion`
--

CREATE TABLE `opcion` (
  `id_opcion` bigint NOT NULL,
  `opcion_nombre` varchar(100) NOT NULL,
  `opcion_ruta` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `opcion`
--

INSERT INTO `opcion` (`id_opcion`, `opcion_nombre`, `opcion_ruta`) VALUES
(1, 'Dashboard', '/'),
(2, 'Gestión de Usuarios', '/usuarios/listar'),
(3, 'Gestión de Perfiles', '/perfiles/listar'),
(4, 'Gestión de Categorías', '/categorias/listar'),
(5, 'Gestión de Marcas', '/marcas/listar'),
(6, 'Gestión de Productos', '/productos/listar'),
(7, 'Catálogo', '/catalogo'),
(8, 'Carrito', '/carrito');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden`
--

CREATE TABLE `orden` (
  `id_orden` bigint NOT NULL,
  `order_fecha` date NOT NULL,
  `order_fecha_requerida` date NOT NULL,
  `order_fecha_envio` date DEFAULT NULL,
  `order_nombre_transportista` varchar(150) NOT NULL,
  `order_direccion_envio` varchar(250) NOT NULL,
  `order_ciudad` varchar(100) NOT NULL,
  `order_provincia` varchar(100) NOT NULL,
  `id_transporte` bigint NOT NULL,
  `id_proveedor` bigint NOT NULL,
  `id_empleado` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfil`
--

CREATE TABLE `perfil` (
  `id_perfil` bigint NOT NULL,
  `perfil_nombre` varchar(50) NOT NULL,
  `perfil_descripcion` varchar(255) DEFAULT NULL,
  `perfil_estado` int NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `perfil`
--

INSERT INTO `perfil` (`id_perfil`, `perfil_nombre`, `perfil_descripcion`, `perfil_estado`) VALUES
(1, 'Administrador', 'Acceso total al sistema.', 1),
(2, 'Editor', 'Puede gestionar usuarios pero no perfiles.', 1),
(3, 'Supervisor', 'Solo puede visualizar información.', 1),
(4, 'Cliente', 'Solo puede ver los catálogos y realizar compras.', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfil_opcion`
--

CREATE TABLE `perfil_opcion` (
  `id_perfil` bigint NOT NULL,
  `id_opcion` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `perfil_opcion`
--

INSERT INTO `perfil_opcion` (`id_perfil`, `id_opcion`) VALUES
(1, 1),
(2, 1),
(3, 1),
(1, 2),
(2, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(4, 7),
(1, 8),
(4, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id_producto` bigint NOT NULL,
  `produc_nombre` varchar(255) NOT NULL,
  `produc_codigo` varchar(255) NOT NULL,
  `produc_modelo` varchar(255) DEFAULT NULL,
  `produc_descripcion` varchar(255) NOT NULL,
  `produc_precio` decimal(38,2) DEFAULT NULL,
  `produc_fecha_creacion` date DEFAULT NULL,
  `produc_fecha_vencimiento` date DEFAULT NULL,
  `produc_stock` int DEFAULT '0',
  `produc_stock_minimo` int DEFAULT '1',
  `produc_imagen` varchar(255) DEFAULT NULL,
  `produc_estado` int NOT NULL DEFAULT '1',
  `id_unidad` int NOT NULL,
  `id_categoria` bigint NOT NULL,
  `id_marca` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id_producto`, `produc_nombre`, `produc_codigo`, `produc_modelo`, `produc_descripcion`, `produc_precio`, `produc_fecha_creacion`, `produc_fecha_vencimiento`, `produc_stock`, `produc_stock_minimo`, `produc_imagen`, `produc_estado`, `id_unidad`, `id_categoria`, `id_marca`) VALUES
(1, 'Lentes de sol', '11223344', NULL, 'Test', 100.00, '2025-09-30', NULL, 20, 10, 'Unisex\\5f8589a3-8564-4d19-ae14-f317456aa87b_gatoTraje.jpg', 1, 1, 4, 3),
(2, 'Lentes ópticos', '55667788', NULL, 'Lentes ópticos graduados', 250.00, '2025-10-01', NULL, 15, 3, 'Mujer\\0d1fcd57-68bf-4586-810a-d6849bfceef5_cat-sand.png', 1, 1, 3, 3),
(3, 'Lentes de lectura', '33445566', NULL, 'Lentes de lectura ligeros', 90.00, '2025-10-01', NULL, 25, 5, 'Mujer\\c1dc448b-824f-45d0-96e1-e8aa98fcc8f6_memecatfacincam.jpg', 1, 1, 3, 1),
(4, 'Gafas polarizadas', '22334455', NULL, 'Protección UV y polarización', 120.00, '2025-10-01', NULL, 18, 4, 'Unisex\\e618de2f-1dda-40c4-9224-ae29a75ed391_pana-miguel.jpeg', 1, 1, 4, 2),
(5, 'Prueba', '11335566', NULL, 'Prueba', 100.00, '2025-10-01', NULL, 10, 5, 'Unisex\\3aac76cc-ece0-40b6-9f07-65da4fbd5edc_gatoTraje.jpg', 1, 1, 4, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `id_proveedor` bigint NOT NULL,
  `provee_nombre` varchar(100) NOT NULL,
  `provee_nombre_comercial` varchar(100) NOT NULL,
  `provee_nacionalidad` varchar(100) NOT NULL,
  `provee_direccion` varchar(100) NOT NULL,
  `provee_telefono` char(9) NOT NULL,
  `provee_correo` varchar(150) NOT NULL,
  `provee_correo_adicional` varchar(150) DEFAULT NULL,
  `provee_estado` int NOT NULL DEFAULT '1',
  `provee_ndocumento` varchar(20) NOT NULL,
  `id_tipodocumento` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_documento`
--

CREATE TABLE `tipo_documento` (
  `id_tipodocumento` bigint NOT NULL,
  `tipodoc_nombre` varchar(255) DEFAULT NULL,
  `tipodoc_estado` int NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `tipo_documento`
--

INSERT INTO `tipo_documento` (`id_tipodocumento`, `tipodoc_nombre`, `tipodoc_estado`) VALUES
(1, 'DNI', 1),
(2, 'RUC', 1),
(3, 'Carné de Extranjería', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_venta`
--

CREATE TABLE `tipo_venta` (
  `id_tipoventa` bigint NOT NULL,
  `tipoventa_nombre` varchar(50) NOT NULL,
  `tipoventa_estado` int NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidad`
--

CREATE TABLE `unidad` (
  `id_unidad` int NOT NULL,
  `uni_nombre` varchar(255) NOT NULL,
  `uni_estado` int NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `unidad`
--

INSERT INTO `unidad` (`id_unidad`, `uni_nombre`, `uni_estado`) VALUES
(1, 'Unidades', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `id_venta` bigint NOT NULL,
  `venta_fecha` date NOT NULL,
  `venta_subtotal` decimal(10,0) DEFAULT '0',
  `venta_igv` decimal(10,0) DEFAULT '0',
  `venta_total` decimal(10,0) DEFAULT '0',
  `id_cliente` bigint NOT NULL,
  `id_fpago` bigint NOT NULL,
  `id_empleado` bigint NOT NULL,
  `id_tipoventa` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id_categoria`),
  ADD UNIQUE KEY `categ_nombre` (`categ_nombre`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD KEY `FK_TipoDoc_Cliente` (`id_tipodocumento`),
  ADD KEY `FKrpwisd8jpg0agtv9wm2vdhawi` (`id_perfil`);

--
-- Indices de la tabla `compania_transporte`
--
ALTER TABLE `compania_transporte`
  ADD PRIMARY KEY (`id_transporte`),
  ADD UNIQUE KEY `transport_nombre` (`transport_nombre`);

--
-- Indices de la tabla `contacto`
--
ALTER TABLE `contacto`
  ADD PRIMARY KEY (`id_contacto`),
  ADD KEY `FK_Proveedor_Contacto` (`id_proveedor`);

--
-- Indices de la tabla `detalle_orden`
--
ALTER TABLE `detalle_orden`
  ADD PRIMARY KEY (`id_producto`,`id_orden`),
  ADD KEY `FK_Orden_DetalleOrden` (`id_orden`);

--
-- Indices de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD PRIMARY KEY (`id_detalle`),
  ADD KEY `FK_Venta_DetalletVenta` (`id_venta`),
  ADD KEY `FK_Produc_DetalletVenta` (`id_producto`);

--
-- Indices de la tabla `direccion_entrega`
--
ALTER TABLE `direccion_entrega`
  ADD PRIMARY KEY (`id_entrega`),
  ADD KEY `FK_Cliente_Entrega` (`id_cliente`),
  ADD KEY `FK_Venta_Entrega` (`id_venta`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`id_empleado`),
  ADD UNIQUE KEY `emple_nombreuser` (`emple_nombreuser`),
  ADD UNIQUE KEY `emple_correo` (`emple_correo`),
  ADD UNIQUE KEY `emple_telefono` (`emple_telefono`),
  ADD UNIQUE KEY `emple_ndocumento` (`emple_ndocumento`),
  ADD KEY `FK_TipoDoc_Empleado` (`id_tipodocumento`),
  ADD KEY `FK_Perfil_Empleado` (`id_perfil`);

--
-- Indices de la tabla `forma_pago`
--
ALTER TABLE `forma_pago`
  ADD PRIMARY KEY (`id_fpago`),
  ADD UNIQUE KEY `fpago_metodo` (`fpago_metodo`);

--
-- Indices de la tabla `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`id_marca`);

--
-- Indices de la tabla `opcion`
--
ALTER TABLE `opcion`
  ADD PRIMARY KEY (`id_opcion`),
  ADD UNIQUE KEY `opcion_nombre` (`opcion_nombre`);

--
-- Indices de la tabla `orden`
--
ALTER TABLE `orden`
  ADD PRIMARY KEY (`id_orden`),
  ADD KEY `FK_Transporte_Orden` (`id_transporte`),
  ADD KEY `FK_Proveedor_Orden` (`id_proveedor`),
  ADD KEY `FK_Empleado_Orden` (`id_empleado`);

--
-- Indices de la tabla `perfil`
--
ALTER TABLE `perfil`
  ADD PRIMARY KEY (`id_perfil`),
  ADD UNIQUE KEY `perfil_nombre` (`perfil_nombre`);

--
-- Indices de la tabla `perfil_opcion`
--
ALTER TABLE `perfil_opcion`
  ADD PRIMARY KEY (`id_perfil`,`id_opcion`),
  ADD KEY `FK_Opcion` (`id_opcion`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id_producto`),
  ADD KEY `FK_Unidad_Produc` (`id_unidad`),
  ADD KEY `FK_Catego_Produc` (`id_categoria`),
  ADD KEY `FK_Marca_Produc` (`id_marca`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`id_proveedor`),
  ADD UNIQUE KEY `provee_nombre` (`provee_nombre`),
  ADD KEY `FK_TipoDoc_Proveedor` (`id_tipodocumento`);

--
-- Indices de la tabla `tipo_documento`
--
ALTER TABLE `tipo_documento`
  ADD PRIMARY KEY (`id_tipodocumento`),
  ADD UNIQUE KEY `tipodoc_nombre` (`tipodoc_nombre`);

--
-- Indices de la tabla `tipo_venta`
--
ALTER TABLE `tipo_venta`
  ADD PRIMARY KEY (`id_tipoventa`),
  ADD UNIQUE KEY `tipoventa_nombre` (`tipoventa_nombre`);

--
-- Indices de la tabla `unidad`
--
ALTER TABLE `unidad`
  ADD PRIMARY KEY (`id_unidad`),
  ADD UNIQUE KEY `uni_nombre` (`uni_nombre`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`id_venta`),
  ADD KEY `FK_Cliente_Venta` (`id_cliente`),
  ADD KEY `FK_FPago_Venta` (`id_fpago`),
  ADD KEY `FK_Empleado_Venta` (`id_empleado`),
  ADD KEY `FK_TipoVenta_Venta` (`id_tipoventa`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id_categoria` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `compania_transporte`
--
ALTER TABLE `compania_transporte`
  MODIFY `id_transporte` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `contacto`
--
ALTER TABLE `contacto`
  MODIFY `id_contacto` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  MODIFY `id_detalle` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `direccion_entrega`
--
ALTER TABLE `direccion_entrega`
  MODIFY `id_entrega` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `id_empleado` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `forma_pago`
--
ALTER TABLE `forma_pago`
  MODIFY `id_fpago` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `marca`
--
ALTER TABLE `marca`
  MODIFY `id_marca` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `opcion`
--
ALTER TABLE `opcion`
  MODIFY `id_opcion` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `orden`
--
ALTER TABLE `orden`
  MODIFY `id_orden` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `perfil`
--
ALTER TABLE `perfil`
  MODIFY `id_perfil` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id_producto` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id_proveedor` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tipo_documento`
--
ALTER TABLE `tipo_documento`
  MODIFY `id_tipodocumento` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `tipo_venta`
--
ALTER TABLE `tipo_venta`
  MODIFY `id_tipoventa` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `unidad`
--
ALTER TABLE `unidad`
  MODIFY `id_unidad` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `id_venta` bigint NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `FK_TipoDoc_Cliente` FOREIGN KEY (`id_tipodocumento`) REFERENCES `tipo_documento` (`id_tipodocumento`),
  ADD CONSTRAINT `FKrpwisd8jpg0agtv9wm2vdhawi` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id_perfil`);

--
-- Filtros para la tabla `contacto`
--
ALTER TABLE `contacto`
  ADD CONSTRAINT `FK_Proveedor_Contacto` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id_proveedor`);

--
-- Filtros para la tabla `detalle_orden`
--
ALTER TABLE `detalle_orden`
  ADD CONSTRAINT `FK_Orden_DetalleOrden` FOREIGN KEY (`id_orden`) REFERENCES `orden` (`id_orden`),
  ADD CONSTRAINT `FK_Producto_DetalleOrden` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`);

--
-- Filtros para la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD CONSTRAINT `FK_Produc_DetalletVenta` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`),
  ADD CONSTRAINT `FK_Venta_DetalletVenta` FOREIGN KEY (`id_venta`) REFERENCES `venta` (`id_venta`);

--
-- Filtros para la tabla `direccion_entrega`
--
ALTER TABLE `direccion_entrega`
  ADD CONSTRAINT `FK_Cliente_Entrega` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`),
  ADD CONSTRAINT `FK_Venta_Entrega` FOREIGN KEY (`id_venta`) REFERENCES `venta` (`id_venta`);

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `FK_Perfil_Empleado` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id_perfil`),
  ADD CONSTRAINT `FK_TipoDoc_Empleado` FOREIGN KEY (`id_tipodocumento`) REFERENCES `tipo_documento` (`id_tipodocumento`);

--
-- Filtros para la tabla `orden`
--
ALTER TABLE `orden`
  ADD CONSTRAINT `FK_Empleado_Orden` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`),
  ADD CONSTRAINT `FK_Proveedor_Orden` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id_proveedor`),
  ADD CONSTRAINT `FK_Transporte_Orden` FOREIGN KEY (`id_transporte`) REFERENCES `compania_transporte` (`id_transporte`);

--
-- Filtros para la tabla `perfil_opcion`
--
ALTER TABLE `perfil_opcion`
  ADD CONSTRAINT `FK_Opcion` FOREIGN KEY (`id_opcion`) REFERENCES `opcion` (`id_opcion`),
  ADD CONSTRAINT `FK_Perfil` FOREIGN KEY (`id_perfil`) REFERENCES `perfil` (`id_perfil`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `FK_Catego_Produc` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`),
  ADD CONSTRAINT `FK_Marca_Produc` FOREIGN KEY (`id_marca`) REFERENCES `marca` (`id_marca`),
  ADD CONSTRAINT `FK_Unidad_Produc` FOREIGN KEY (`id_unidad`) REFERENCES `unidad` (`id_unidad`);

--
-- Filtros para la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD CONSTRAINT `FK_TipoDoc_Proveedor` FOREIGN KEY (`id_tipodocumento`) REFERENCES `tipo_documento` (`id_tipodocumento`);

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `FK_Cliente_Venta` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`),
  ADD CONSTRAINT `FK_Empleado_Venta` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`),
  ADD CONSTRAINT `FK_FPago_Venta` FOREIGN KEY (`id_fpago`) REFERENCES `forma_pago` (`id_fpago`),
  ADD CONSTRAINT `FK_TipoVenta_Venta` FOREIGN KEY (`id_tipoventa`) REFERENCES `tipo_venta` (`id_tipoventa`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
