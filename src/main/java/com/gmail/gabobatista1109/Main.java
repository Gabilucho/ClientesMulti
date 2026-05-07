package com.gmail.gabobatista1109;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static String ruta = "C:\\Users\\Techie18_Mañana\\Programación\\intellij java\\ClientesMulti\\Clientes_LF.txt";
    public static void main(String[] args) {
        File fichero = new File(ruta);
    }

    static void ScannerFichero () {
        Scanner sFi = null;
        File fichero = new File(ruta);
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        try {
            sFi = new Scanner(fichero);
            while (sFi.hasNextLine()) {
                String linea = sFi.nextLine();
                String [] trozos = linea.split(";");
                listaClientes.add(new Cliente(trozos[0],trozos[1],trozos[2],Integer.parseInt(trozos[3]),Double.parseDouble(trozos[4].replace(',','.')),trozos[5],trozos[6],
                        trozos[7],trozos[8],trozos[9],trozos[10],trozos[11],trozos.length == 13 ? trozos[12]: ""));
            }
        } catch (FileNotFoundException e) {
            throw  new RuntimeException(e);
        } catch (NumberFormatException e){
            System.out.println("Parametro de antiguedad o facturacion no se ha podido convertir al tipo correcto");
        } finally {
            if (sFi != null) {
                sFi.close();
            }
        }

    }


    static ArrayList<Cliente> BufferedReaderClientes (String ruta) {
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(ruta));) {

            String linea;


           while ((linea = in.readLine()) != null) {
               if (linea.trim().isEmpty()) continue;


               String[] trozos = linea.split(";");

               listaClientes.add(new Cliente(
                       trozos[0], trozos[1], trozos[2],
                       Integer.parseInt(trozos[3]),
                       Double.parseDouble(trozos[4].replace(',', '.')),
                       trozos[5], trozos[6], trozos[7], trozos[8],
                       trozos[9], trozos[10], trozos[11],
                       trozos.length == 13 ? trozos[12] : ""
               ));
           }
        } catch (NumberFormatException e) {
            System.out.println("Error de formato");
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
        }
        return listaClientes;
    }

    static ArrayList<Cliente> procesarDatos (String texto) {
        if (texto == null || texto.isEmpty()) {
            System.out.println("No hat datos para procesar.");
            return null;
        }
       return null; //o String[] trozos = linea.split(";");
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



