package com.gmail.gabobatista1109;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;

/**
 * Clase principal que gestiona un menú para cargar y procesar datos de clientes
 * desde un fichero. Permite la configuración de la aplicación y la generación
 * de informes.
 */
public class Main {

    private static ArrayList<Cliente> clientes = null;
    private static Configuracion configuracion;
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Punto de entrada de la aplicación. Inicializa la configuración y muestra el menú principal.
     */
    public static void main(String[] args) {
        configuracion = new Configuracion();

        if (args != null && args.length > 0) {
            System.out.println("Tomando ruta del parámetro de entrada...");
            configuracion.setValor("default_location", args[0]);
        } else {
            System.out.println("Fichero de configuración leído...");
            System.out.println("Tomando ruta del fichero de configuración...");
            System.out.println("Ruta: " + configuracion.getValor("default_location"));
        }

        menuPrincipal();
        sc.close();
    }

    /**
     * Muestra el menú principal y gestiona las opciones del usuario.
     */
    public static void menuPrincipal() {
        boolean salir = false;

        while (!salir) {
            String caracter = configuracion.getValor("menu_character");
            String separador = caracter.repeat(5);
            System.out.println("\n" + separador + " Menú Principal " + separador);
            System.out.println("1. Scanner");
            System.out.println("2. FileReader");
            System.out.println("3. BufferedReader");
            System.out.println("4. Emitir Informes");
            System.out.println("5. Configuración");
            System.out.println("6. Salir");
            System.out.print("Escribe número de opción y pulsa Intro: ");

            int opcion = leerEntero(1, 6);

            switch (opcion) {
                case 1:
                    cargarClientes(1);
                    break;
                case 2:
                    cargarClientes(2);
                    break;
                case 3:
                    cargarClientes(3);
                    break;
                case 4:
                    menuInformes();
                    break;
                case 5:
                    menuConfiguracion();
                    break;
                case 6:
                    salir = true;
                    System.out.println("Hasta luego.");
                    break;
            }
        }
    }

    /**
     * Carga los clientes desde un fichero utilizando uno de los tres métodos disponibles.
     * @param metodo El metodo de lectura a utilizar (1: Scanner, 2: FileReader, 3: BufferedReader).
     */
    private static void cargarClientes(int metodo) {
        String ruta = configuracion.getValor("default_location");
        ArrayList<Cliente> resultado;

        switch (metodo) {
            case 1:
                resultado = LectorFicheros.cargarClientesConScanner(ruta);
                break;
            case 2:
                resultado = LectorFicheros.cargarClientesConFileReader(ruta);
                break;
            case 3:
            default:
                resultado = LectorFicheros.cargarClientesConBufferedReader(ruta);
                break;
        }

        if (resultado.isEmpty()) {
            System.out.println("No se encontró el fichero o no contiene datos: " + ruta);
        } else {
            clientes = resultado;
            System.out.println("Clientes leídos: " + clientes.size());
        }
    }

    /**
     * Muestra el menú de informes y gestiona las opciones del usuario.
     */
    private static void menuInformes() {
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("No se puede generar ningún informe porque no se dispone de información de clientes");
            return;
        }

        boolean volver = false;
        while (!volver) {
            String caracter = configuracion.getValor("menu_character");
            String separador = caracter.repeat(5);
            System.out.println("\n" + separador + " Emitir Informes " + separador);
            System.out.println("1. Ordenado por Facturación descendente.");
            System.out.println("2. Ordenado por Nombre de Contacto ascendente.");
            System.out.println("3. Menú anterior");
            System.out.print("Escribe número de opción y pulsa Intro: ");

            int opcion = leerEntero(1, 3);

            switch (opcion) {
                case 1:
                    generarInformes(true);
                    break;
                case 2:
                    generarInformes(false);
                    break;
                case 3:
                    volver = true;
                    break;
            }
        }
    }

    /**
     * Genera los informes de clientes para España y Alemania.
     * @param porFacturacion true para ordenar por facturación, false para ordenar por nombre de contacto.
     */
    private static void generarInformes(boolean porFacturacion) {
        StringBuilder sb = new StringBuilder();
        sb.append(generarInformePais("España", porFacturacion));
        sb.append(generarInformePais("Alemania", porFacturacion));

        boolean guardar = "true".equalsIgnoreCase(configuracion.getValor("save_report"));

        if (guardar) {
            String rutaInforme = configuracion.getValor("file_report");
            try (PrintWriter pw = new PrintWriter(new FileWriter(rutaInforme, false))) {
                pw.print(sb.toString());
                System.out.println("Informe guardado en: " + rutaInforme);
            } catch (IOException e) {
                System.err.println("Error al guardar el informe: " + e.getMessage());
            }
        } else {
            System.out.print(sb.toString());
        }
    }

    /**
     * Genera un informe para un país específico.
     * @param pais El país para el que se genera el informe.
     * @param porFacturacion true para ordenar por facturación, false para ordenar por nombre de contacto.
     * @return El informe como una cadena de texto.
     */
    private static String generarInformePais(String pais, boolean porFacturacion) {
        ArrayList<Cliente> filtrados = new ArrayList<>();
        for (Cliente c : clientes) {
            if (pais.equalsIgnoreCase(c.getPais())) {
                filtrados.add(c);
            }
        }

        for (int i = 0; i < filtrados.size() - 1; i++) {
            for (int j = 0; j < filtrados.size() - 1 - i; j++) {
                Cliente a = filtrados.get(j);
                Cliente b = filtrados.get(j + 1);
                boolean intercambiar;
                if (porFacturacion) {
                    intercambiar = a.getFacturacion() < b.getFacturacion();
                } else {
                    intercambiar = a.getNombreContacto().compareTo(b.getNombreContacto()) > 0;
                }
                if (intercambiar) {
                    filtrados.set(j, b);
                    filtrados.set(j + 1, a);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Informe - ").append(pais).append("\n");
        double totalFacturacion = 0;

        for (int i = 0; i < filtrados.size(); i++) {
            Cliente c = filtrados.get(i);
            String etiqueta = "Alemania".equalsIgnoreCase(pais)
                    ? "Registro " + (i + 1) + " (Alemania)"
                    : "Registro " + (i + 1);

            sb.append(etiqueta).append("\n");
            sb.append("  Id. Cliente: ").append(c.getId()).append("\n");
            sb.append("  Nombre Contacto: ").append(c.getNombreContacto()).append("\n");
            sb.append("  Antigüedad: ").append(c.getAntiguedad()).append("\n");
            sb.append("  Facturación: ").append(c.getFacturacion()).append("\n");
            sb.append("  Nombre Compañía: ").append(c.getNombreEmpresa()).append("\n");
            sb.append("  Nombre Ciudad: ").append(c.getCiudad()).append("\n");
            totalFacturacion += c.getFacturacion();
        }

        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("es", "ES"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        sb.append(" Total clientes: ").append(filtrados.size()).append("\n");
        sb.append(" Total facturación (€): ").append(nf.format(totalFacturacion)).append("\n");

        return sb.toString();
    }

    /**
     * Muestra el menú de configuración y permite modificar los parámetros.
     */
    private static void menuConfiguracion() {
        Configuracion temporal = new Configuracion();
        temporal.setValor("default_location", configuracion.getValor("default_location"));
        temporal.setValor("menu_character",   configuracion.getValor("menu_character"));
        temporal.setValor("save_report",      configuracion.getValor("save_report"));
        temporal.setValor("file_report",      configuracion.getValor("file_report"));

        String[] claves    = {"default_location", "file_report", "menu_character", "save_report"};
        String[] etiquetas = {"DEFAULT_LOCATION", "FILE_REPORT", "MENU_CHARACTER", "SAVE_REPORT"};

        boolean salir = false;
        while (!salir) {
            String caracter = configuracion.getValor("menu_character");
            String separador = caracter.repeat(5);
            System.out.println("\n" + separador + " Menú Configuración " + separador);

            for (int i = 0; i < claves.length; i++) {
                System.out.println((i + 1) + ". " + etiquetas[i] + " : " + temporal.getValor(claves[i]));
            }
            System.out.println((claves.length + 1) + ". Guardar nueva configuración y regresar al menú principal");
            System.out.println((claves.length + 2) + ". Volver al Menú Principal sin guardar nueva configuración");
            System.out.print("Escribe número de opción y pulsa Intro: ");

            int opcion = leerEntero(1, claves.length + 2);

            if (opcion >= 1 && opcion <= claves.length) {
                String clave    = claves[opcion - 1];
                String etiqueta = etiquetas[opcion - 1];
                System.out.print("Escriba el nuevo valor de la variable de configuración (" + etiqueta + "): ");
                String nuevoValor = sc.nextLine().trim();
                temporal.setValor(clave, nuevoValor);

            } else if (opcion == claves.length + 1) {
                System.out.println("GRABANDO NUEVA CONFIGURACIÓN...");
                for (String clave : claves) {
                    configuracion.setValor(clave, temporal.getValor(clave));
                }
                configuracion.guardarEnFichero();
                salir = true;

            } else {
                System.out.println("MENÚ ANTERIOR SIN GRABAR CONFIGURACIÓN...");
                salir = true;
            }
        }
    }

    /**
     * Lee un número entero del usuario dentro de un rango especificado.
     * @param min El valor mínimo permitido.
     * @param max El valor máximo permitido.
     * @return El número entero leído.
     */
    private static int leerEntero(int min, int max) {
        while (true) {
            String linea = sc.nextLine().trim();
            try {
                int numero = Integer.parseInt(linea);
                if (numero >= min && numero <= max) {
                    return numero;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.printf("Opción no válida. Introduce un número entre %d y %d: ", min, max);
        }
    }
}
