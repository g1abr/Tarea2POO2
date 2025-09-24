package com.cooprkc.modelo;

public class CuentaAhorros extends Cuenta {
    private double interes;
    // Constructor vacío

    public CuentaAhorros() {
        super();
        this.interes = 0.02;

    }

    public CuentaAhorros(String numeroCuenta) {
        super(numeroCuenta, 0.0);
        this.interes = 0.02;
    }

    public CuentaAhorros(String numeroCuenta, double saldoInicial) {
        super(numeroCuenta, saldoInicial);
        this.interes = 0.02; // Interés fijo del 2%
    }

    public CuentaAhorros(String numeroCuenta, double saldoInicial, double interes) {
        super(numeroCuenta, saldoInicial);
        this.interes = interes;
    }

    // Impletamos metodo abstracto que viene de la clase padre
    @Override
    public void aplicarInteres() {
        double interesCalculado = saldo * interes;
        saldo += interesCalculado;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        if (interes < 0) {
            throw new IllegalArgumentException("El interés no puede ser negativo");
        }
        this.interes = interes;
    }

    @Override
    public String toString() {
        return "Cuenta de Ahorros #" + numeroCuenta +
                "  Saldo: " + saldo +
                "  Interés: " + (interes * 100) + "%";
    }

}
