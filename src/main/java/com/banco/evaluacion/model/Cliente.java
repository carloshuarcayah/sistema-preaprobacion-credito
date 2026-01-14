package com.banco.evaluacion.model;

//EL historial crediticio lo mediomos de 1-100, pasa con 50
//Edad minima en peru debe ser de 18, puede ser que segun la edad puedas o no pedir un tipo de prestamo pero lo vamos a dejar asi en 18
//USAREMOS EL SUEDO NETO PARA SIMPLIFICAR LOS CALCULOS DEL PRESTAMO (PODRIAMOS TRABAJAR  CON SUELDO BRUTO LUEGo)
public record Cliente(String nombre, int edad, int historialCrediticio, double sueldoNeto, boolean tieneDeuda, boolean activo){}
