package com.cooprkc.transacciones;

import com.cooprkc.modelo.Cuenta;

public class Retiro implements Transaccion {
    private Cuenta cuenta;
    private double monto;

    public Retiro(Cuenta cuenta, double monto) {
        this.cuenta = cuenta;
        this.monto = monto;
    }

    @Override
    public void ejecutar() {
        try {
    
            if (cuenta == null) {
                throw new IllegalArgumentException("La cuenta no puede ser nula para realizar un retiro");
            }

      
            if (monto <= 0) {
                throw new IllegalArgumentException("El monto del retiro debe ser mayor a 0");
            }

          
            if (monto > cuenta.getSaldo()) {
                throw new IllegalStateException("Saldo insuficiente. Saldo actual: $" +
                        cuenta.getSaldo() + ", Monto solicitado: $" + monto);
            }

        
            if (monto > 5000000) { 
                throw new IllegalArgumentException("El monto excede el límite máximo de retiro de $5,000,000");
            }

          
            cuenta.retirar(monto);
            System.out.println(
                    "Retiro de $" + monto + " realizado exitosamente de la cuenta " + cuenta.getNumeroCuenta());
            System.out.println("Saldo restante: $" + cuenta.getSaldo());

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("Error en retiro: " + e.getMessage());
            throw e; 

        } catch (Exception e) {
        
            System.err.println("Error inesperado durante el retiro: " + e.getMessage());
            throw new RuntimeException("Fallo en la transacción de retiro", e);
        }
    }

    @Override
    public double getMonto() {
        return monto;
    }

    // Método para verificar si el retiro es posible
    public boolean puedeEjecutarse() {
        try {
            if (cuenta == null)
                return false;
            if (monto <= 0)
                return false;
            if (monto > cuenta.getSaldo())
                return false;
            if (monto > 5000000)
                return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Retiro[Cuenta: " + (cuenta != null ? cuenta.getNumeroCuenta() : "N/A") +
                ", Monto: $" + monto + "]";
    }
}