package com.banco.evaluacion.model;

//Para el prestamo no nos complicaremos será algo sencillo, de momento trabajaremos con soles, luego agregaremos otras monedas y haremos los calculos necesarios
public record Prestamo(double monto, int plazoMeses, String tipoPrestamo) { }
