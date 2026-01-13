package com.banco.evaluacion.model;

import com.banco.evaluacion.exception.PreAprobacionException;

public enum TipoPrestamo {
    HIPOTECARIO(1.08),VEHICULAR(1.12),PERSONAL(1.20);

    private final double interes;

    TipoPrestamo(double interes){
        this.interes=interes;
    }

    public double getInteres(){
        return interes;
    }

    public static TipoPrestamo transformar(String aPasar){
        for(TipoPrestamo valor: TipoPrestamo.values()){
            if(aPasar.equalsIgnoreCase(valor.name())){
                return valor;
            }
        }
        throw new PreAprobacionException("Tipo de préstamo no reconocido: "+aPasar);
    }
}
