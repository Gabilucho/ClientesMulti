package com.gmail.gabobatista1109;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static String ruta = "Clientes_LF.txt";
    static File fichero = new File(ruta);

    public static void main(String[] args) {
        ;
        System.out.println("funciono");
        ArrayList<Cliente> clientes = scannerFichero();
        for (Cliente c : clientes) {
            System.out.println(c); // llama a toString() automáticamente
        }
    }

    static ArrayList<Cliente> scannerFichero() {

        StringBuilder sb = new StringBuilder();
        try (Scanner sFi = new Scanner(fichero)) {
            while (sFi.hasNextLine()) {
                sb.append(sFi.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo en: " + fichero.getAbsolutePath());
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de números (antigüedad o facturación)");
        }

        return procesarDatos(sb);

    }


    static ArrayList<Cliente> bufferedReaderClientes() {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new FileReader(fichero))) {
            String linea;
            while ((linea = in.readLine()) != null) {
                if (linea.trim().isEmpty()) continue; // salta líneas vacías
                sb.append(linea).append("\n");         // acumula con salto de línea
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return new ArrayList<>();
        }

        return procesarDatos(sb);


    }

    static ArrayList<Cliente> procesarDatos(StringBuilder texto) {
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        if (texto == null || texto.isEmpty()) {
            System.out.println("No hay datos para procesar.");
            return listaClientes;
        }


        String[] lineas = texto.toString().split("\n");

        for (String linea : lineas) {
            if (linea.trim().isEmpty()) continue;

            String[] trozos = linea.split(";");

            try {
                listaClientes.add(new Cliente(
                        trozos[0], trozos[1], trozos[2],
                        Integer.parseInt(trozos[3].trim()),
                        Double.parseDouble(trozos[4].replace(',', '.')),
                        trozos[5], trozos[6], trozos[7], trozos[8],
                        trozos[9], trozos[10], trozos[11],
                        trozos.length >= 13 ? trozos[12] : ""
                ));
            } catch (NumberFormatException e) {
                System.out.println("Error de formato numérico en línea: " + linea);
            }
        }

        return listaClientes;
    }



    static String Menu = "***** Métodos de lectura de fichero de texto *****" +
            "\n1. Scanner" +
            "\n2. FileReader" +
            "\n3.BufferReader" +
            "\n4.Emitir Informe" +
            "\n5. Salir";


    public static int verificarNumero () {
        Scanner sc = new Scanner(System.in);
        int numero = 0;
        while (true) {
            if (sc.hasNextInt()) {
                numero = sc.nextInt();
            }
            if (numero >= 1 && numero <= 5) {
                break;
            }
            else {
                sc.next();
            }
            System.out.println("Error. Introduce solo una de las cuatro opciones (1,2,3,4 o 5)");
        }
        return numero ;
    }

    static Scanner lectorFicheroScanner (String ruta) throws FileNotFoundException {
        return new Scanner(new File(ruta));
    }

    static FileReader lectorFicheroFilereader (String ruta) throws FileNotFoundException {
        return new FileReader(ruta);
    }

    static BufferedReader lectorFicheroBufferReader (String ruta) throws FileNotFoundException {
        return new BufferedReader (new FileReader(ruta));
    }


    public static void MenuPrincipal () {
        System.out.println(Menu);

        boolean salir = false;




        }
    }



