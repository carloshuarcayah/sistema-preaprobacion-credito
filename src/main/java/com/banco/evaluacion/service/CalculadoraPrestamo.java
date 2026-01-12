    package com.banco.evaluacion.service;

    import com.banco.evaluacion.exception.PreAprobacionException;
    import com.banco.evaluacion.model.Prestamo;

    //AQUI VIENE LA LOGICA QUE USAREMOS PARA EL CALCULO DE INTERESES SEGUN EL TIPO DE PRESTAMO
    public class CalculadoraPrestamo {
        public double calcularCuotaMensual(Prestamo prestamo){
            double interes = prestamo.tipoPrestamo().getInteres();

            return (interes* prestamo.monto())/ prestamo.plazoMeses();
        }
    }
