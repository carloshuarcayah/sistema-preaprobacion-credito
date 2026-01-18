# Sistema de pre-aprobacion de creditos
Este proyecto es un sistema automatizado de evaluacion crediticio desarrollado en **Java 17**. Evalua solicitudes enviadas y basandonos en ciertas reglas valida si el credito es aprobado o rechazado.
## Tecnologias:
- Lenguaje: Java 17 (Uso de records y Switch Expresions).
- Maven
- Testing: JUnit 5.
- Conexion: JDBC
- DB: PostgreSQL

## Caracteristicas
- Uso de BigDecimal para calculos precisos (pasamos de float a BigDecimal más que nada como reto personal).
- Separacinode responsabilidades (Servicios, Repositorios, Modelos)
- Posibilidad de guardar todos los prestamos solicitados en un archivo de texto plano (`historial_evaluaciones.txt`) para cualquier uso (no habilitado en este momento porque hacemos uso de una base de datos).

## Funcionalidades
- Evaluacion automatizada: Filtra por edad (18-70 años), score crediticio del cliente (mayor a 50 para ser candidato a prestamo), verifica deudas y que el prestamo sea mayor al minimo establecido. 
- Calcular cuotas: Aplica intereses segun el tipo de prestamo que se desea sacar haciendo uso del ENUM TipoPrestamo.
- Persistencia en archivos: Registro de las evaluaciones realizadas y si han sido aprobadas o rechazadas. (historial_evaluaciones.txt)
- Reporte de datos: Muestra un resumen de ccuantos prestamos han sido solicitados y de esos cuantos han sido aprobados y rechazados.

## Servicios
1. Evaluacion Automatizada de prestamo (EvaluadorRiesgoService): Filtra a los candidatos siguiendo ciertas reglas.
   - Edad: Cliente debe tener entre 18 y 70 años.
   - Historial: El score crediticio tiene que ser mayor 50 puntos.
   - Deuda: El cliente no debe poseer ninguna deuda activa.
   - Cuotas: La cuota mensual no debe superar el 30% del sueldo neto del cliente.
2. Calculo de las cuotas (CalculadoraPrestamo):
   - Encontramos dos validaciones para asegurarnos que se ha entregado un monto mayor a 0 y que el no se haya entregado un null para evitar errores graves.
   - Aplica una tasa de interes segun el tipo de prestamo solicitado (PERSONAL, HIPOTECARIO, VEHICULAR).

## Como ejecutar