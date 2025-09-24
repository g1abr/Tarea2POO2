package com.cooprkc.app;

import com.cooprkc.modelo.*;
import com.cooprkc.transacciones.*;

import java.util.Scanner;

public class Main {
    private static Cooperativa cooperativa = new Cooperativa();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            try {
                switch (opcion) {
                    case 1 -> registrarSocio();
                    case 2 -> cooperativa.listarSociosConStream();
                    case 3 -> abrirCuentaSocio();
                    case 4 -> realizarDeposito();
                    case 5 -> realizarRetiro();
                    case 6 -> aplicarInteresCuentas();
                    case 7 -> cooperativa.listarSociosConStream();
                    case 8 -> filtrarCuentasPorSaldo();
                    case 9 -> mostrarSumaTotalSaldos();
                    case 10 -> mostrarTodasLasCuentas();
                    case 11 -> contarTotalCuentas();
                    case 12 -> consultarSaldoTotalSocio();
                    case 13 -> buscarSocioPorCedula();
                    case 0 -> System.out.println(" Saliendo del sistema. ¡Hasta luego!");
                    default -> System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.err.println("⚠ Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }



    private static void registrarSocio() {
        String nombre = leerTexto("Ingrese nombre del socio: ");
        String cedula = leerTexto("Ingrese cédula del socio: ");
        Socio socio = new Socio(nombre, cedula, null);
        cooperativa.registrarSocio(socio);
    }

    private static void abrirCuentaSocio() {
        String cedula = leerTexto("Ingrese cédula del socio: ");
        cooperativa.buscarSocioPorCedula(cedula).ifPresentOrElse(socio -> {
            String numeroCuenta = leerTexto("Ingrese número de la nueva cuenta: ");
            CuentaAhorros cuenta = new CuentaAhorros(numeroCuenta, 0.0);
            try {
                socio.abrirCuenta(cuenta);
                System.out.println("✅ Cuenta creada exitosamente.");
            } catch (Exception e) {
                System.err.println("❌ " + e.getMessage());
            }
        }, () -> System.out.println("⚠ No existe socio con esa cédula."));
    }

    private static void realizarDeposito() {
        String cedula = leerTexto("Ingrese cédula del socio: ");
        cooperativa.buscarSocioPorCedula(cedula).ifPresentOrElse(socio -> {
            String numeroCuenta = leerTexto("Ingrese número de cuenta: ");
            socio.getCuentas().stream()
                    .filter(c -> c.getNumeroCuenta().equals(numeroCuenta))
                    .findFirst()
                    .ifPresentOrElse(cuenta -> {
                        double monto = leerDouble("Ingrese monto a depositar: ");
                        Transaccion deposito = new Deposito(cuenta, monto);
                        deposito.ejecutar();
                    }, () -> System.out.println("⚠ Cuenta no encontrada."));
        }, () -> System.out.println("⚠ No existe socio con esa cédula."));
    }

    private static void realizarRetiro() {
        String cedula = leerTexto("Ingrese cédula del socio: ");
        cooperativa.buscarSocioPorCedula(cedula).ifPresentOrElse(socio -> {
            String numeroCuenta = leerTexto("Ingrese número de cuenta: ");
            socio.getCuentas().stream()
                    .filter(c -> c.getNumeroCuenta().equals(numeroCuenta))
                    .findFirst()
                    .ifPresentOrElse(cuenta -> {
                        double monto = leerDouble("Ingrese monto a retirar: ");
                        Transaccion retiro = new Retiro(cuenta, monto);
                        retiro.ejecutar();
                    }, () -> System.out.println("⚠ Cuenta no encontrada."));
        }, () -> System.out.println("⚠ No existe socio con esa cédula."));
    }

    private static void aplicarInteresCuentas() {
        cooperativa.getSocios().forEach(socio -> socio.getCuentas().forEach(cuenta -> {
            if (cuenta instanceof CuentaAhorros ahorro) {
                ahorro.aplicarInteres();
                System.out.println("✅ Interés aplicado a " + ahorro.getNumeroCuenta());
            }
        }));
    }

    private static void filtrarCuentasPorSaldo() {
        System.out.println("\n=== CUENTAS CON SALDO MAYOR A $500,000 ===");
        cooperativa.filtrarCuentasConSaldoMayorA(500000);
    }

    private static void mostrarSumaTotalSaldos() {
        double total = cooperativa.obtenerSumaTotalSaldos();
        System.out.println("💰 Suma total de saldos en la cooperativa: $" + total);
    }

    private static void mostrarTodasLasCuentas() {
        System.out.println("\n=== TODAS LAS CUENTAS ===");
        cooperativa.obtenerTodasLasCuentas().forEach(System.out::println);
    }

    private static void contarTotalCuentas() {
        long total = cooperativa.contarTotalCuentas();
        System.out.println("🔢 Total de cuentas en la cooperativa: " + total);
    }

    private static void consultarSaldoTotalSocio() {
        String cedula = leerTexto("Ingrese cédula del socio: ");
        cooperativa.buscarSocioPorCedula(cedula).ifPresentOrElse(socio -> {
            System.out.println("Socio " + socio.getNombre() + " - Saldo total: $" + socio.getSaldoTotal());
        }, () -> System.out.println("⚠ No existe socio con esa cédula."));
    }

    private static void buscarSocioPorCedula() {
        String cedula = leerTexto("Ingrese cédula a buscar: ");
        cooperativa.buscarSocioPorCedula(cedula).ifPresentOrElse(System.out::println,
                () -> System.out.println("⚠ No existe socio con esa cédula."));
    }

    // ===================== UTILIDADES =====================

    private static void mostrarMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL - COOPERATIVA COOPRKC");
        System.out.println("1. Registrar socio");
        System.out.println("2. Listar socios");
        System.out.println("3. Abrir cuenta de ahorro a socio");
        System.out.println("4. Depositar en cuenta");
        System.out.println("5. Retirar de cuenta");
        System.out.println("6. Aplicar interés a cuentas de ahorro");
        System.out.println("7. Listar socios con stream()");
        System.out.println("8. Filtrar cuentas con saldo mayor a $500.000");
        System.out.println("9. Mostrar suma total de saldos en la cooperativa");
        System.out.println("10. Mostrar todas las cuentas de la cooperativa");
        System.out.println("11. Contar total de cuentas en la cooperativa");
        System.out.println("12. Consultar saldo total de un socio");
        System.out.println("13. Buscar socio por cédula");
        System.out.println("0. Salir");
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Integer.parseInt(scanner.nextLine());
    }

    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        return Double.parseDouble(scanner.nextLine());
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }
}
