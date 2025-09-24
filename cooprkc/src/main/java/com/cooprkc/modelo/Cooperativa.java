package com.cooprkc.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cooperativa {
    private final String NOMBRE = "Cooperativa CoopRKC";
    private List<Socio> socios;

    public Cooperativa() {
        this.socios = new ArrayList<>();
    }

    public void registrarSocio(Socio socio) {
        boolean socioExiste = socios.stream()
                .anyMatch(s -> s.getCedula().equals(socio.getCedula()));

        if (socioExiste) {
            throw new IllegalArgumentException("Ya existe un socio con la cédula: " + socio.getCedula());
        }

        socios.add(socio);
        System.out.println("Socio " + socio.getNombre() + " registrado exitosamente.");
    }

    public void listarSocios() {
        System.out.println("----------------------------------------");
        System.out.println("\n Listados de socios registrados ");
        System.out.println(NOMBRE);
        System.out.println("Total de socios: " + socios.size());
        System.out.println("----------------------------------------");

        if (socios.isEmpty()) {
            System.out.println("No hay socios registrados.");
            System.err.println("\n");

            return;
        }

        for (int i = 0; i < socios.size(); i++) {
            Socio socio = socios.get(i);
            System.out.println((i + 1) + " Nombre: " + socio.getNombre() +
                    " Cédula: " + socio.getCedula() +
                    " Cuentas: " + socio.getCuentas().size() +
                    " Saldo total: " + String.format("$%,.2f", socio.getSaldoTotal()));
        }
    }


    public void listarSociosConStream() {
        System.out.println("\n Listado de socios usando programación Funcional con Stream  y foreahc");

        socios.stream()
                .map(socio -> "Socio: " + socio.getNombre() + " Cédula: " + socio.getCedula())
                .forEach(System.out::println);
    }

    public void filtrarCuentasConSaldoMayorA(double saldoMinimo) {
        System.out.println("\n=== Cuantas con saldo mayor" + String.format("$%,.2f", saldoMinimo));

        long cantidad = socios.stream()
                .flatMap(socio -> socio.getCuentas().stream())
                .filter(cuenta -> cuenta.getSaldo() > saldoMinimo)
                .peek(cuenta -> System.out.println("Cuentas " + cuenta))
                .count();

        System.out.println("Total de cuentas que superan " + String.format("$%,.2f", saldoMinimo) + ": " + cantidad);
    }

    public double obtenerSumaTotalSaldos() {
        return socios.stream()
                .flatMap(socio -> socio.getCuentas().stream())
                .mapToDouble(Cuenta::getSaldo)
                .reduce(0.0, Double::sum);
    }

    public Optional<Socio> buscarSocioPorCedula(String cedula) {
        return socios.stream()
                .filter(socio -> socio.getCedula().equals(cedula))
                .findFirst();
    }

    public List<Cuenta> obtenerTodasLasCuentas() {
        return socios.stream()
                .flatMap(socio -> socio.getCuentas().stream())
                .toList();
    }

    public long contarTotalCuentas() {
        return socios.stream()
                .mapToLong(socio -> socio.getCuentas().size())
                .sum();
    }

    public String getNombre() {
        return NOMBRE;
    }

    public List<Socio> getSocios() {
        return socios;
    }

    public int getTotalSocios() {
        return socios.size();
    }

    @Override
    public String toString() {
        return "Cooperativa{" +
                "nombre='" + NOMBRE + '\'' +
                ", totalSocios=" + socios.size() +
                ", totalCuentas=" + contarTotalCuentas() +
                ", saldoTotal=" + String.format("$%,.2f", obtenerSumaTotalSaldos()) +
                '}';
    }
}
