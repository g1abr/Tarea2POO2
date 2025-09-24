package com.cooprkc.transacciones;

import com.cooprkc.modelo.Cuenta;

public class Deposito implements Transaccion {
    private Cuenta cuenta;
    private double monto;

    public Deposito(Cuenta cuenta, double monto) {
        this.cuenta = cuenta;
        this.monto = monto;
    }

    @Override
    public void ejecutar() {
        try {
        
            if (cuenta == null) {
                throw new IllegalArgumentException("La cuenta no ha sido encontrada ");
            }

            if (monto <= 0) {
                throw new IllegalArgumentException("El monto del depósito debe ser mayor a 0");
            }

            if (monto > 100000000) {
                throw new IllegalArgumentException("Monto de depósito excede el límite permitido, debe llamar a gerencia para acompañamiento del proceso de grandes sumas de dinero");
            }

            cuenta.depositar(monto);
            System.out.println(
                    "Depósito de $" + monto + " realizado exitosamente en cuenta " + cuenta.getNumeroCuenta());

        } catch (IllegalArgumentException e) {
            System.err.println("Error en depósito: " + e.getMessage());
            throw e; 

        } catch (Exception e) {
            System.err.println(" Error inesperado durante el depósito: " + e.getMessage());
            throw new RuntimeException("Fallo en la transacción de depósito", e);
        }
    }

    @Override
    public double getMonto() {
        return monto;
    }

    // Método para validar antes de ejecutar
    public boolean validar() {
        try {
            if (cuenta == null)
                return false;
            if (monto <= 0)
                return false;
            if (monto > 100000000)
                return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Depósito[Cuenta: " + (cuenta != null ? cuenta.getNumeroCuenta() : "N/A") +
                ", Monto: $" + monto + "]";
    }
}