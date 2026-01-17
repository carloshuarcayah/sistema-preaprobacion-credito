    package com.banco.evaluacion.service;

    import com.banco.evaluacion.exception.PreAprobacionException;
    import com.banco.evaluacion.model.Prestamo;

    import java.math.BigDecimal;
    import java.math.RoundingMode;

    //AQUI VIENE LA LOGICA QUE USAREMOS PARA EL CALCULO DE INTERESES SEGUN EL TIPO DE PRESTAMO
    public class CalculadoraPrestamo {
        public BigDecimal calcularCuotaMensual(Prestamo prestamo){
            if(prestamo==null) throw new PreAprobacionException("Error de dato: Objeto invalido o null");
            if(prestamo.monto().compareTo(BigDecimal.ZERO)<=0) throw new PreAprobacionException("Error al calcular: Se ha introducido un monto invalido");

            BigDecimal interes = BigDecimal.valueOf(prestamo.tipoPrestamo().getInteres());
            BigDecimal montoTotalConInteres = prestamo.monto().multiply(interes);
            return (montoTotalConInteres).divide(BigDecimal.valueOf(prestamo.plazoMeses()), 2, RoundingMode.HALF_UP);
        }
    }
