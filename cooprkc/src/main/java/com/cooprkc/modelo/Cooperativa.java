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
        System.out.println("\n=== LISTA DE SOCIOS REGISTRADOS ===");
        System.out.println("Cooperativa: " + NOMBRE);
        System.out.println("Total de socios: " + socios.size());
        System.out.println("----------------------------------------");

        if (socios.isEmpty()) {
            System.out.println("No hay socios registrados.");
            return;
        }

        for (int i = 0; i < socios.size(); i++) {
            Socio socio = socios.get(i);
            System.out.println((i + 1) + ". " + socio.getNombre() +
                    " - Cédula: " + socio.getCedula() +
                    " - Cuentas: " + socio.getCuentas().size() +
                    " - Saldo total: " + String.format("$%,.2f", socio.getSaldoTotal()));
        }
    }

    // ================= PROGRAMACIÓN FUNCIONAL CON STREAMS =================

    /**
     * Listar socios registrados usando stream() y forEach (Requerimiento)
     */
    public void listarSociosConStream() {
        System.out.println("\n=== SOCIOS (USANDO STREAM + FOREACH) ===");

        socios.stream()
                .map(socio -> "SOCIO:" + socio.getNombre() + " - Cédula: " + socio.getCedula())
                .forEach(System.out::println);
    }

    /**
     * Filtrar cuentas con saldo mayor a un valor específico usando stream()
     * (Requerimiento)
     */
    public void filtrarCuentasConSaldoMayorA(double saldoMinimo) {
        System.out.println("\n=== CUENTAS CON SALDO > " + String.format("$%,.2f", saldoMinimo) + " (FILTER) ===");

        long cantidad = socios.stream()
                .flatMap(socio -> socio.getCuentas().stream())
                .filter(cuenta -> cuenta.getSaldo() > saldoMinimo)
                .peek(cuenta -> System.out.println("💰 " + cuenta))
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
