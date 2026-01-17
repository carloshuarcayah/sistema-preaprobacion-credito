package com.banco.evaluacion.service;

import com.banco.evaluacion.exception.PreAprobacionException;
import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.EstadoPrestamo;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.repository.ClienteRepository;
import com.banco.evaluacion.repository.PrestamoRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class EvaluadorRiesgoService {
    private final static BigDecimal PRESTAMO_MINIMO = BigDecimal.valueOf(500.0);
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

    BigDecimal validarPrestamo(Cliente cliente, Prestamo prestamo){

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
        if(prestamo.monto().compareTo(PRESTAMO_MINIMO)<0){
            throw new PreAprobacionException("Error monto: El monto solicitado es menor al minimo permitido");
        }

        BigDecimal limiteSueldo = BigDecimal.valueOf(cliente.sueldoNeto()*0.3);
        BigDecimal cuotaMensual = calculadoraPrestamo.calcularCuotaMensual(prestamo);

        //SI EL PRESTAMO SUPERA EL 30% DEL SUELDO NETO
        if(limiteSueldo.compareTo(cuotaMensual)<0){
            throw new PreAprobacionException("Error crediticio: La cuota mensual de S/."+cuotaMensual+", es demasiado alta para un sueldo de s/."+cliente.sueldoNeto());
        }
        System.out.println("Validacion exitosa: ¡Su credito ha sido aprobado!");

        return cuotaMensual;
    }

    private BigDecimal obtenerCuota(Cliente cliente, Prestamo prestamo){
        System.out.println("\nCLIENTE EVALUADO: "+cliente.nombre().toUpperCase());
        System.out.println("Solicitud: "+prestamo.tipoPrestamo()+" | Monto: S/."+prestamo.monto());
        System.out.println("-----------------------------------------");
        return validarPrestamo(cliente,prestamo);
    }

    public void evaluar(Cliente cliente, Prestamo prestamo){
        try{
            BigDecimal cuotaMensual = obtenerCuota(cliente,prestamo);
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
            BigDecimal cuotaMensual =  obtenerCuota(cliente,prestamo);
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
