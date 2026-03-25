package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 1; i <= 100000; i++) {
            map.put(i + "", i + "");
        }

        long inicio = System.nanoTime();
        String ultimoEncontrado = null;

        for (Map.Entry<String, String> entrada : map.entrySet()) {
            String llave = entrada.getKey();
            String valor = entrada.getValue();

            if (llave.equals(valor)) {
                ultimoEncontrado = llave;
            }
        }

        if (ultimoEncontrado != null) {
            System.out.println("Último procesado: " + ultimoEncontrado);
        }

        long fin = System.nanoTime();
        System.out.println((fin - inicio) / 1e6 + " ms");
    }
}