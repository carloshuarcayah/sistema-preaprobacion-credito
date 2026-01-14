package com.banco.evaluacion.model;

//Para el prestamo no nos complicaremos será algo sencillo, de momento trabajaremos con soles, luego agregaremos otras monedas y haremos los calculos necesarios
public record Prestamo(int id, int cliente_id, double monto, int plazoMeses, TipoPrestamo tipoPrestamo, EstadoPrestamo estado) {
    public Prestamo{
        if(estado==null) estado=EstadoPrestamo.PENDIENTE;
    }
}