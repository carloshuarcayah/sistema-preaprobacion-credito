package com.banco.evaluacion.service;
//DEBE SER MAYOR DE EDAD PARA PEDIR PRESTAMO Y NO SUPERAR LOS 70 AÑOS(RIESGO)
//SCORE CREDITICIO MAYOR A 50 ( PODRIAMOS HACER QUE CADA TIPO DE PRESTAMO TENGA SU PROPIO SCORE MINIMO)
//PRESTAMO MINIMO DE 500 SOLES. (SI PIDE EN OTRA MONEDA QUE NO SEA SOLES IGUAL, DEBE SER MAYOR A 500 SOLES AL PASARLO)
//NO DEBE TENER DEUDAS
//EL PRESTAMO NO DEBERIA SUPERAR EL 30% DE SU SUELDO NETO

//DE MOMENTO SOLO VALIDAREMOS EDAD, SCORE, SI TIENE DEUDA, Y QUE NO SUPERE 30% DE SU SUELDO

import com.banco.evaluacion.exception.PreAprobacionException;
import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.Prestamo;

public class EvaluadorRiesgoService {
    private final CalculadoraPrestamo calculadoraPrestamo = new CalculadoraPrestamo();
    private final BlocHistorialService blocHistorialService;
    private final static double PRESTAMO_MINIMO = 500.0;
    private final static int EDAD_MINIMA = 18;
    private final static int EDAD_MAXIMA = 70;
    private final static int SCORE_MINIMO = 50;

    public EvaluadorRiesgoService(BlocHistorialService b){
        blocHistorialService=b;
    }

    double validarPrestamo(Cliente cliente, Prestamo prestamo){

        //TIENE QUE SER MAYOR DE EDAD Y NO TENER MAS DE 70
        if(cliente.edad()<EDAD_MINIMA||cliente.edad()>EDAD_MAXIMA){
            throw new PreAprobacionException("Error edad: El cliente no cumple con la edad permitida.");
        }

        //TIENE DEUDA
        if(cliente.tieneDeuda()){
            throw new PreAprobacionException("Error deuda: El cliente tiene una deuda pendiente.");
        }

        //NO CUMPLE CON EL SCORE MINIMO
        if(cliente.historialCrediticio()<SCORE_MINIMO){
            throw new PreAprobacionException("Error crediticio: El cliente no cumple con el score minimo.");
        }

        //PRESTAMO MENOR AL MINIMO
        if(prestamo.monto()<PRESTAMO_MINIMO){
            throw new PreAprobacionException("Error monto: El monto solicitado es menor al minimo permitido");
        }

        double limitePrestamo = cliente.sueldoNeto()*0.3;
        double cuotaMensual = Math.round(calculadoraPrestamo.calcularCuotaMensual(prestamo));
        boolean prestamoValido = cuotaMensual<limitePrestamo;

        //SI EL PRESTAMO SUPERA EL 30% DEL SUELDO NETO
        if(!prestamoValido){
            throw new PreAprobacionException("Error crediticio: La cuota mensual de S/."+cuotaMensual+", es demasiado alta para un sueldo de s/."+cliente.sueldoNeto());
        }

        System.out.println("Validacion exitosa: ¡Su credito ha sido aprobado!");
        return cuotaMensual;
    }

    public void evaluar(Cliente cliente, Prestamo prestamo){

        System.out.println("\nCliente:");
        System.out.println("Nombre: " + cliente.nombre());
        System.out.println("Sueldo Neto: S/ " + cliente.sueldoNeto());
        System.out.println("Historial Crediticio: " + cliente.historialCrediticio() + "/100");

        System.out.println("\n--- Detalles prestamo ---");
        System.out.println("Monto solicitado: S/ " + prestamo.monto());
        System.out.println("Tipo: " + prestamo.tipoPrestamo());
        System.out.println("Plazo: " + prestamo.plazoMeses() + " meses");

        try{
            double cuotaMensual =  validarPrestamo(cliente,prestamo);
            System.out.println("\nCuota mensual: S/ " + String.format("%.2f", cuotaMensual));
            blocHistorialService.actualizar(cliente,prestamo,true,"Prestamo Aprobado");
        }
        catch(PreAprobacionException e){
            System.err.println(e.getMessage());
            blocHistorialService.actualizar(cliente,prestamo,false,e.getMessage());
        }
    }
}
