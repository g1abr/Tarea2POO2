package com.cooprkc.modelo;

import java.util.Objects;

/** Super Clase* */
public abstract class Cuenta {
    protected String numeroCuenta;
    protected double saldo;

    public Cuenta(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    public Cuenta() {
        this.numeroCuenta = "";
        this.saldo = 0.0;
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto ingresado es negativo");
        }
        saldo += monto;
    }

    public void retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del retiro debe ser mayor que 0");
        }
        if (monto > saldo) {
            throw new IllegalStateException("Saldo insuficiente. Saldo actual: " + saldo);
        }
        saldo -= monto;
    }

    public abstract void aplicarInteres();

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /* Validación para no repetir cuentas */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(numeroCuenta, cuenta.numeroCuenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroCuenta);
    }

    @Override
    public String toString() {
        return "Cuenta #" + numeroCuenta + " | Saldo: " + saldo;
    }
}