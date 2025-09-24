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
            // Validar que la cuenta exista
            if (cuenta == null) {
                throw new IllegalArgumentException("La cuenta no ha sido encontrada ");
            }

            // Validar que el monto sea positivo
            if (monto <= 0) {
                throw new IllegalArgumentException("El monto del depósito debe ser mayor a 0");
            }

            // Validar monto máximo (opcional - buena práctica)
            if (monto > 100000000) { // 100 millones
                throw new IllegalArgumentException("Monto de depósito excede el límite permitido, debe llamar a gerencia para acompañamiento del proceso de grandes sumas de dinero");
            }

            // Ejecutar el depósito
            cuenta.depositar(monto);
            System.out.println(
                    "Depósito de $" + monto + " realizado exitosamente en cuenta " + cuenta.getNumeroCuenta());

        } catch (IllegalArgumentException e) {
            // Capturar errores de validación
            System.err.println("Error en depósito: " + e.getMessage());
            throw e; // Relanzar para que el caller sepa que falló

        } catch (Exception e) {
            // Capturar cualquier otro error inesperado
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