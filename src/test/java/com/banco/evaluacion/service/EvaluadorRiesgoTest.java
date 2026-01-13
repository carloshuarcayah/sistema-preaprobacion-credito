package com.banco.evaluacion.service;

import com.banco.evaluacion.exception.PreAprobacionException;
import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.model.TipoPrestamo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;


public class EvaluadorRiesgoTest {
    EvaluadorRiesgoService evaluadorRiesgoService;
    @BeforeEach
    void inicializar(){
        evaluadorRiesgoService= new EvaluadorRiesgoService(new BlocHistorialService());
    }

    //TESTS A PROBAR
    //ES MENOR DE EDAD O SUPERA LOS 70, LANZA ERROR
    //ES MAYOR DE EDAD, OK
    @ParameterizedTest
    @CsvSource({
            "15, INVALIDO",
            "17, INVALIDO",
            "18, VALIDO",
            "46, VALIDO",
            "70, VALIDO",
            "75, INVALIDO",
    })
    void testValidarPorEdad(int edad, String estado){
        Cliente cliente = new Cliente("Luis",edad,90,3000,false);
        Prestamo prestamo = new Prestamo(600,12, TipoPrestamo.transformar("HIPOTECARIO"));

        switch (estado.toUpperCase().trim()){
            case "VALIDO"->{
                assertDoesNotThrow(()->{
                    evaluadorRiesgoService.validarPrestamo(cliente,prestamo);
                },"Se esperaba que la edad:"+edad+" sea valida.");
            }
            case "INVALIDO"->{
                assertThrows(PreAprobacionException.class,()->{
                    evaluadorRiesgoService.validarPrestamo(cliente,prestamo);
                },"Se esperaba que la edad:"+edad+" sea invalido.");
            }
        }
    }

    //TIENE DEUDA,  ERROR
    //NO TIENE DEUDA, OK

    //PIDE UN PRESTAMO MENOR AL MONTO MINIMO, ERROR
    //PIDE UN PRESTAMO MAYOR AL MINIMO, OK

    //SCORE CREDITICIO MENOR AL MINIMO, ERROR
    //SCORE CREDITICIO SUPERIOR AL MINIMO, OK

    //PRESTAMO SUPERA EL 30% DEL SUELDO NETO, ERROR
    //PRESTAMO NO SUPERA EL 30% DEL SUELDO TENO, OK

}
