package com.banco.evaluacion.service;

import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.Prestamo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BlocHistorialService {
    private final static String RUTA_ARCHIVO="historial_evaluaciones.txt";

    public void actualizar(Cliente cliente, Prestamo prestamo, boolean resultado, String mensaje){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO,true))){
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            String informacion = "["+fecha+"] | Cliente: "+cliente.nombre()+" | Tipo prestamo: "+prestamo.tipoPrestamo()+" | Monto solicitado: "+ prestamo.monto()+"| Estado: "+ (resultado?"APROBADO":"RECHAZADO")+" | Mensaje: "+mensaje;
            writer.write(informacion);
            writer.newLine();
            System.out.println("Historial de prestamos actualizado. Revisar: "+RUTA_ARCHIVO);
        }catch(IOException e){
            System.err.println("No se puede ingresar al historial: "+e);
        }
    }

}
