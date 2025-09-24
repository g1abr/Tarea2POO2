package com.cooprkc.modelo;

public class Cuenta {
    protected String numeroCuenta;
    protected double saldo;

    public Cuenta(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    public void depositar(double monto) {
        saldo += monto;
    }

    public void retirar(double monto) throws Exception {
        if (saldo < monto) {
            throw new Exception("Saldo insuficiente.");
        }
        saldo -= monto;
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }

    @Override
    public String toString() {
        return "Cuenta #" + numeroCuenta + " | Saldo: " + saldo;
    }
}