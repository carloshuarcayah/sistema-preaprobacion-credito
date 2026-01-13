package com.banco.evaluacion.service;

import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.Prestamo;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BlocHistorialService {
    private final static String RUTA_ARCHIVO="historial_evaluaciones.txt";

    void actualizar(Cliente cliente, Prestamo prestamo, boolean resultado, String mensaje){
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

    public void mostrarResumen(){
        int totalSolicitudes = 0;
        int aprobados = 0;
        int rechazados = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))){
            String linea;
            while((linea = reader.readLine())!=null){
                totalSolicitudes++;

                if(linea.contains("Estado: APROBADO")) {
                    aprobados++;
                }else if(linea.contains("Estado: RECHAZADO")) {
                    rechazados++;
                }
            }

            System.out.println("------reporte de solicitudes-------");
            System.out.println("Total solicitudes: "+totalSolicitudes);
            System.out.println("Aprobados: "+aprobados);
            System.out.println("Rechazados: "+rechazados);
            System.out.println("-----------------------------------");

        }
        catch (FileNotFoundException e){
            System.err.println("Aun no existe un archivo de historial.");
        }
        catch (IOException e){
            System.err.println("Error al leer el historial: "+e.getMessage());
        }

//        catch (FileNotFoundException e){
//            System.err.println(e.getMessage());
//        }
    }
}
