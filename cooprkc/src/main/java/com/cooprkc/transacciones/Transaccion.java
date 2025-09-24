package com.cooprkc.transacciones;

public interface Transaccion {
    void ejecutar();

    default double getMonto(){
        return 0.0;
    }

}
