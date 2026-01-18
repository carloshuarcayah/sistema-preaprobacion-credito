package com.banco.evaluacion.service;

import com.banco.evaluacion.exception.PreAprobacionException;
import com.banco.evaluacion.model.EstadoPrestamo;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.model.TipoPrestamo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraPrestamoTest {
    //UNA PRUEBA POSITIVA UNA PRUEBA NEGATIVA
    @ParameterizedTest
    @DisplayName("Los calculos de los prestamos son correctos, la logica siempre va a ser valida")
    @CsvSource({
            "PERSONAL,1000,10000",//EL RESULTADO DE ESTA OPERACION NOS DEBERIA DE DAR 1000, VAMOS SACAAR UN PRESTAMO DE 10000 QUE SERIA UN PAGO DE 12000 PORQUE ES UN PRESTAMO PERSONAL EN 12 MESES LO CUAL NUESTRO CUOTA SERIA DE 1000
            "VEHICULAR, 933.33,10000",
            "HIPOTECARIO,504,5600"
    })
    void testsPrestamoExactoValido(String tipo, BigDecimal esperado, BigDecimal monto) {
        CalculadoraPrestamo calculadora = new CalculadoraPrestamo();
        Prestamo prestamo = new Prestamo(1,1,monto,12,TipoPrestamo.valueOf(tipo), EstadoPrestamo.PENDIENTE);
        assertEquals(0, esperado.compareTo(calculadora.calcularCuotaMensual(prestamo)), "Se esperaba que la calculadora diera un calculo perfecto.");
    }

    @ParameterizedTest
    @DisplayName("Se solicito un prestamo con un monto negativo o invalido, la aplicacion debe lanzar una excepcion")
    @ValueSource(doubles = {-5000,-100, 0})
    void testMontoInvalido(double monto){
        CalculadoraPrestamo calculadora = new CalculadoraPrestamo();
        Prestamo prestamo = new Prestamo(1,1,BigDecimal.valueOf(monto),12,TipoPrestamo.PERSONAL, EstadoPrestamo.PENDIENTE);
        assertThrows(PreAprobacionException.class,()->{calculadora.calcularCuotaMensual(prestamo);},"Se esperaba una excepcion por monto invalido");
    }
}
