# Sistema de pre-aprobacion de creditos
Este proyecto es un sistema de evaluacion crediticio desarrollado en **Java 21**. Evalua solicitudes enviadas y basandonos en ciertas reglas valida si el credito es aprobado o rechazado.
## Tecnologias:
- Java 21 (Uso de records y Switch Expresions).
- Maven
- Testing: JUnit 5.
- JDBC
- PostgreSQL

## Funcionalidades
- Evaluacion automatizada: Filtra por edad (18-70 años), score crediticio del cliente (mayor a 50 para ser candidato a prestamo), verifica deudas y que el prestamo sea mayor al minimo establecido. 
- Calcular cuotas: Aplica intereses segun el tipo de prestamo que se desea sacar haciendo uso del ENUM TipoPrestamo.
- Persistencia en archivos: Registro de las evaluaciones realizadas y si han sido aprobadas o rechazadas. (historial_evaluaciones.txt)
- Reporte de datos: Muestra un resumen de ccuantos prestamos han sido solicitados y de esos cuantos han sido aprobados y rechazados.

## Reglas de evaluación
- Edad: Cliente debe tener entre 18 y 70 años.
- Historial: El score crediticio tiene que ser mayor 50 puntos.
- Deuda: El cliente no debe poseer ninguna deuda activa.
- Cuotas: La cuota mensual no debe superar el 30% del sueldo neto del cliente.

## Como ejecutar