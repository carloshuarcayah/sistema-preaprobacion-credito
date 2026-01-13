package com.banco.evaluacion.service;

import com.banco.evaluacion.exception.PreAprobacionException;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.model.TipoPrestamo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraPrestamoTest {
    //UNA PRUEBA POSITIVA UNA PRUEBA NEGATIVA
    @ParameterizedTest
    @CsvSource({
            "vehicular,valido",
            "hipotecario,valido",
            "personal,valido",
            "operacion,invalido",
            "vacaciones,invalido"
    })
    void testTipoPrestamoValidacion(String tipo, String estado) {
        switch (estado){
            case "VALIDO"->{
                assertDoesNotThrow(()->{
                            TipoPrestamo.transformar(tipo);

                        }, "Se esperaba que "+tipo+" sea valido."
                );
            }
            case "INVALIDO"->{
                assertThrows(PreAprobacionException.class,()->{
                    TipoPrestamo.transformar(tipo);
                },"Se esperaba un error para: "+tipo);
            }
        }

    }
}
