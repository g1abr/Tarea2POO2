package com.cooprkc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Socio {
    private String nombre;
    private String cedula;
    private List<Cuenta> cuentas;


    public Socio(){
        this.cuentas = new ArrayList<>();
    }
     public Socio(String nombre, String cedula, List<Cuenta> cuentas) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.cuentas = (cuentas != null) ? cuentas : new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

  public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = (cuentas != null) ? cuentas : new ArrayList<>();
    }

    public void abrirCuenta(Cuenta cuenta) {
        boolean existe = cuentas.stream()
                .anyMatch(c -> c.getNumeroCuenta().equals(cuenta.getNumeroCuenta()));
        if (existe) {
            throw new IllegalArgumentException("El número de cuenta ya existe para este socio.");
        }
        cuentas.add(cuenta);
    }

    public double getSaldoTotal() {
        return cuentas.stream()
                .mapToDouble(Cuenta::getSaldo)
                .sum();
    }

    @Override
    public String toString() {
        return "Socio: " + nombre + "Cedula: " + cedula;
    }

}