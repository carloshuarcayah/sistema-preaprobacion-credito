package com.banco.evaluacion.model;

public enum TipoPrestamo {
    HIPOTECARIO(1.08),VEHICULAR(1.12),PERSONAL(1.20);

    private final double interes;

    TipoPrestamo(double interes){
        this.interes=interes;
    }

    public double getInteres(){
        return interes;
    }
}
