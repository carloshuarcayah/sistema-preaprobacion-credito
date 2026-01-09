    package com.banco.evaluacion.service;

    import com.banco.evaluacion.exception.PreAprobacionException;
    import com.banco.evaluacion.model.Prestamo;

    //AQUI VIENE LA LOGICA QUE USAREMOS PARA EL CALCULO DE INTERESES SEGUN EL TIPO DE PRESTAMO
    public class CalculadoraPrestamo {
        private static final double interesHipotecario = 1.08;
        private static final double interesVehicular = 1.12;
        private static final double interesPersonal = 1.20;

        public double calcularCuotaMensual(Prestamo prestamo){
            double total;

            switch (prestamo.tipoPrestamo().toUpperCase()){
                //8%
                case "HIPOTECARIO"->{
                    total =prestamo.monto()*interesHipotecario;
                    return total/ prestamo.plazoMeses();
                }

                //12%
                case "VEHICULAR"->{
                    total =prestamo.monto()*interesVehicular;
                    return total/ prestamo.plazoMeses();
                }

                //20%
                case "PERSONAL"->{
                    total =prestamo.monto()*interesPersonal;
                    return total/ prestamo.plazoMeses();
                }
                default -> throw new PreAprobacionException("El tipo de prestamo no es valido:"+ prestamo.tipoPrestamo());
            }
        }
    }
