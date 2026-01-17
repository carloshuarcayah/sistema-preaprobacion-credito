package com.banco.evaluacion.model;

import java.math.BigDecimal;

//Para el prestamo no nos complicaremos será algo sencillo, de momento trabajaremos con soles, luego agregaremos otras monedas y haremos los calculos necesarios
public record Prestamo(int id, int cliente_id, BigDecimal monto, int plazoMeses, TipoPrestamo tipoPrestamo, EstadoPrestamo estado) {
}