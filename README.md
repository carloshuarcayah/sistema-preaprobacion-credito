# Sistema de Pre-aprobación de Créditos

Este proyecto es un sistema automatizado de evaluación crediticia desarrollado en **Java 21**. El motor evalúa solicitudes enviadas y, basándose en reglas de negocio estrictas, valida si el crédito es aprobado o rechazado, calculando además la cuota mensual precisa.

## Tecnologías
- **Lenguaje:** Java 21.
- **Gestión de dependencias:** Maven.
- **Testing:** JUnit 5.
- **Conexión:** JDBC.
- **Base de Datos:** PostgreSQL.

## Características Técnicas
- Implementación de `BigDecimal` en lugar de `float/double` para garantizar cálculos exactos y evitar errores en calculos que deben ser precisos.
- Separación de responsabilidades en capas (Servicios, Repositorios, Modelos).
- Sistema de logs que registra todas las evaluaciones en el archivo plano (`historial_evaluaciones.txt`).

## Lógica de los Servicios
### 1. Evaluador de Riesgo (`EvaluadorRiesgoService`)
Es el núcleo de la lógica de negocio. Aplica los siguientes filtros:
- **Edad:** Cliente debe tener entre 18 y 70 años.
- **Historial:** El score crediticio debe ser mayor a 50 puntos.
- **Salud Financiera:** El cliente no debe poseer ninguna deuda activa.
- **Capacidad de Pago:** La cuota mensual no debe superar el 30% del sueldo neto del cliente.

### 2. Calculadora Financiera (`CalculadoraPrestamo`)
- **Validaciones:** Se implementó validaciones para asegurar que los montos sean positivos y los objetos no sean nulos.
- **Cálculo de Interés:** Aplica tasas específicas según el tipo de préstamo y devuelve valores para su uso en los calculos.

## Cómo ejecutar el proyecto

### 1. Prerrequisitos
- Tener instalado Java JDK 21.
- Tener instalado PostgreSQL 16+.

### 2. Base de Datos
1. Crear una base de datos en PostgreSQL llamada `banco_prueba`.
2. Ejecutar el script SQL ubicado en `src/main/resources/database/schema.sql`. Este script genera las tablas e inserta datos semilla para pruebas.

### 3. Configuración
1. Navega al archivo de configuración: `src/main/java/.../config/DataBaseConfig.java`.
2. Actualiza las constantes `USER` y `PASS` con tus credenciales de PostgreSQL:
   ```java
   private static final String USER = "postgres"; // Tu usuario
   private static final String PASS = "TU_CONTRASEÑA"; // Tu contraseña