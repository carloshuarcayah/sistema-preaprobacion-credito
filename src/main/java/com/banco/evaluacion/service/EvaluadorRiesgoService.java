package com.banco.evaluacion.service;
//DEBE SER MAYOR DE EDAD PARA PEDIR PRESTAMO Y NO SUPERAR LOS 70 AÑOS(RIESGO)
//SCORE CREDITICIO MAYOR A 50 ( PODRIAMOS HACER QUE CADA TIPO DE PRESTAMO TENGA SU PROPIO SCORE MINIMO)
//PRESTAMO MINIMO DE 500 SOLES. (SI PIDE EN OTRA MONEDA QUE NO SEA SOLES IGUAL, DEBE SER MAYOR A 500 SOLES AL PASARLO)
//NO DEBE TENER DEUDAS
//EL PRESTAMO NO DEBERIA SUPERAR EL 30% DE SU SUELDO NETO
//DE MOMENTO SOLO VALIDAREMOS EDAD, SCORE, SI TIENE DEUDA, Y QUE NO SUPERE 30% DE SU SUELDO

import com.banco.evaluacion.exception.PreAprobacionException;
import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.EstadoPrestamo;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.repository.ClienteRepository;
import com.banco.evaluacion.repository.PrestamoRepository;

import java.sql.SQLException;
import java.util.List;

public class EvaluadorRiesgoService {
    private final static double PRESTAMO_MINIMO = 500.0;
    private final static int EDAD_MINIMA = 18;
    private final static int EDAD_MAXIMA = 70;
    private final static int SCORE_MINIMO = 50;

    private final CalculadoraPrestamo calculadoraPrestamo;
    private final BlocHistorialService blocHistorialService;
    private final ClienteRepository clienteRepository;
    private final PrestamoRepository prestamoRepository;

    public EvaluadorRiesgoService(BlocHistorialService b, CalculadoraPrestamo calculadora, ClienteRepository repoCliente, PrestamoRepository repoPrestamo){
        blocHistorialService=b;
        calculadoraPrestamo = calculadora;
        clienteRepository= repoCliente;
        prestamoRepository=repoPrestamo;
    }

    double validarPrestamo(Cliente cliente, Prestamo prestamo){

        if(!cliente.activo()){
            throw new PreAprobacionException("Error cliente: EL cliente no se ha encontrado o no esta habilitado.");
        }
        if(prestamo.estado()!=EstadoPrestamo.PENDIENTE){
            throw new PreAprobacionException("Error estado: Solo se puede evaluar prestamos con estado PENDIENTE.");
        }

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

        double limiteSueldo = cliente.sueldoNeto()*0.3;
        double cuotaMensual = Math.round(calculadoraPrestamo.calcularCuotaMensual(prestamo));

        //SI EL PRESTAMO SUPERA EL 30% DEL SUELDO NETO
        if(cuotaMensual>limiteSueldo){
            throw new PreAprobacionException("Error crediticio: La cuota mensual de S/."+cuotaMensual+", es demasiado alta para un sueldo de s/."+cliente.sueldoNeto());
        }
        System.out.println("Validacion exitosa: ¡Su credito ha sido aprobado!");

        return cuotaMensual;
    }

    private double obtenerCuota(Cliente cliente, Prestamo prestamo){
        System.out.println("\nCLIENTE EVALUADO: "+cliente.nombre().toUpperCase());
        System.out.println("Solicitud: "+prestamo.tipoPrestamo()+" | Monto: S/."+prestamo.monto());
        System.out.println("-----------------------------------------");
        return validarPrestamo(cliente,prestamo);
    }

    public void evaluar(Cliente cliente, Prestamo prestamo){
        try{
            double cuotaMensual = obtenerCuota(cliente,prestamo);
            System.out.println("Prestamo APROBADO...");
            System.out.println("\nCuota mensual: S/ " + String.format("%.2f", cuotaMensual));
            blocHistorialService.actualizar(cliente,prestamo,true,"Prestamo Aprobado");
        }
        catch(PreAprobacionException e){
            System.err.println(e.getMessage());
            blocHistorialService.actualizar(cliente,prestamo,false,e.getMessage());
        }
    }

    public void evaluarPostgres(Cliente cliente, Prestamo prestamo) throws SQLException {
        try{
            double cuotaMensual =  obtenerCuota(cliente,prestamo);
            System.out.println("\nCuota mensual: S/ " + String.format("%.2f", cuotaMensual));
            prestamoRepository.actualizarEstado(prestamo.id(), EstadoPrestamo.APROBADO,"El prestamo fue aprobado");
        }
        catch(PreAprobacionException e){
            System.err.println("Prestamo RECHAZADO: "+ e.getMessage());
            prestamoRepository.actualizarEstado(prestamo.id(),EstadoPrestamo.RECHAZADO,e.getMessage());
        }
    }
    public void revisarPendientes() throws SQLException {
            List<Prestamo> pendientes = prestamoRepository.obtenerPendientes().orElse(List.of());

            if(pendientes.isEmpty()){
                System.out.println("No hay prestamos que necesiten ser revisados.");
                return;
            }

            for(Prestamo prestamo: pendientes){
                try {
                    Cliente cliente = (clienteRepository.buscarPorId(prestamo.cliente_id())
                            .orElseThrow(
                                    () -> new PreAprobacionException("Cliente ID: " + prestamo.cliente_id() + " no encontrado")
                            )
                    );
                    evaluarPostgres(cliente, prestamo);
                }catch(PreAprobacionException e){
                    System.err.println("Error prestamo"+prestamo.id()+": "+e.getMessage());
                } catch (Exception e) {
                    System.err.println("Fallo inesperado en prestamo "+prestamo.id()+": "+e.getMessage());
                }

            }
    }
}
