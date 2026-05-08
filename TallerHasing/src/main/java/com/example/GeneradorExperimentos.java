package com.example;

import java.util.List;

public class GeneradorExperimentos {

    static class CasoPrueba {
        int n;
        int tableSize;

        CasoPrueba(int n, int tableSize) {
            this.n = n;
            this.tableSize = tableSize;
        }
    }

    static final String SEPARADOR = "+-----------+--------------+------------+------------+--------------+------------+----------------+------------+--------------+";
    static final String FORMATO_CABECERA = "| %-9s | %-12s | %-10s | %-10s | %-12s | %-10s | %-14s | %-10s | %-12s |\n";
    static final String FORMATO_DATOS = "| %-9d | %-12d | %-10s | %-10s | %-12.3f | %-10d | %-14d | %-10d | %-12.6f |\n";

    public static void main(String[] args) {
        CasoPrueba[] casos = {
                new CasoPrueba(1000, 211),
                new CasoPrueba(20000, 5039),
                new CasoPrueba(50000, 19090)
        };

        System.out.println("\nIniciando bateria de pruebas de Hashing...\n");

        // Dibujamos la cabecera de la tabla
        System.out.println(SEPARADOR);
        System.out.printf(FORMATO_CABECERA,
                "N (Datos)", "Tamaño Tabla", "Dataset", "Estrategia",
                "Factor Carga", "Colisiones", "Buckets Usados", "Max Bucket", "Tiempo (s)");
        System.out.println(SEPARADOR);

        for (CasoPrueba caso : casos) {
            List<String> randomKeys = TallerHashing.generateRandomKeys(caso.n, 8);
            List<String> sequentialKeys = TallerHashing.generateSequentialKeys(caso.n);
            List<String> clusteredKeys = TallerHashing.generateClusteredKeys(caso.n);

            ejecutarFila(caso, "Aleatorio", randomKeys, "sum");
            ejecutarFila(caso, "Aleatorio", randomKeys, "polynomial");

            ejecutarFila(caso, "Secuencial", sequentialKeys, "sum");
            ejecutarFila(caso, "Secuencial", sequentialKeys, "polynomial");

            ejecutarFila(caso, "Agrupado", clusteredKeys, "sum");
            ejecutarFila(caso, "Agrupado", clusteredKeys, "polynomial");

            System.out.println(SEPARADOR);
        }
        System.out.println("Experimentos finalizados.");
    }

    private static void ejecutarFila(CasoPrueba caso, String nombreDataset, List<String> keys, String strategy) {
        TallerHashing.HashTableChaining ht = new TallerHashing.HashTableChaining(caso.tableSize, strategy);

        long start = System.nanoTime();
        for (int i = 0; i < keys.size(); i++) {
            ht.insert(keys.get(i), i);
        }
        long end = System.nanoTime();
        double elapsedSeconds = (end - start) / 1_000_000_000.0;

        System.out.printf(FORMATO_DATOS,
                caso.n,
                caso.tableSize,
                nombreDataset,
                strategy,
                ht.loadFactor(),
                ht.getCollisions(),
                ht.usedBuckets(),
                ht.maxBucketSize(),
                elapsedSeconds
        );
    }
}