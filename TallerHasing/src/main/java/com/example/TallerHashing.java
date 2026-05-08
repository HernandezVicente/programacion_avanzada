//Enviar por GitHub
package com.example;

import java.util.*;

public class TallerHashing {
    static class Pair {
        String key;//esta es la llave
        int value; //esta es el valor
        //funcion de pares ordenados
        Pair(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    static class HashTableChaining {
        private List<List<Pair>> table; //Lista de pares
        private int size;// tamaño
        private int count;//Contador
        private int collisions;//Coliciones
        private String hashStrategy;//Tipo de estrategia

        //Se inicialización del constructor
        public HashTableChaining(int size, String hashStrategy) {
            this.size = size;
            this.hashStrategy = hashStrategy;
            this.count = 0;
            this.collisions = 0;
            this.table = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                table.add(new LinkedList<>());
            }
        }


        //Calcula el índice mediante suma de caracteres.
        private int hashSum(String key) {
            int total = 0;
            for (int i = 0; i < key.length(); i++) {
                total += key.charAt(i);
            }
            return Math.floorMod(total, size);//obtener el index
        }

        //Calcula el índice mediante acumulación polinomial con base 31.
        private int hashPolynomial(String key) {
            int h = 0;
            int base = 31;
            for (int i = 0; i < key.length(); i++) {
                h = Math.floorMod(h * base + key.charAt(i), size);
            }
            return h;//obtener el index
        }

        private int hash(String key) {
            if (hashStrategy.equals("sum")) {
                return hashSum(key);
            } else if (hashStrategy.equals("polynomial")) {
                return hashPolynomial(key);
            }
            throw new IllegalArgumentException("Unknown hash strategy");
        }

        //Inserta un nuevo par o actualiza el valor si la clave ya existe
        public void insert(String key, int value) {
            int idx = hash(key);
            List<Pair> bucket = table.get(idx);
            for (Pair p : bucket) {
                if (p.key.equals(key)) {
                    p.value = value;
                    return;
                }
            }
            if (!bucket.isEmpty()) {
                collisions++;
            }
            bucket.add(new Pair(key, value));
            count++;
        }

        //Busca una clave y retorna su valor. Si no existe, retorna null/None.
        public Integer search(String key) {
            int idx = hash(key);
            for (Pair p : table.get(idx)) {
                if (p.key.equals(key)) return p.value;
            }
            return null;
        }

        //Elimina una clave si existe y retorna true/True; si no existe retorna false/False.
        public boolean delete(String key) {
            int idx = hash(key);
            Iterator<Pair> iterator = table.get(idx).iterator();
            while (iterator.hasNext()) {
                Pair p = iterator.next();
                if (p.key.equals(key)) {
                    iterator.remove();
                    count--;
                    return true;
                }
            }
            return false;
        }

        //
        public double loadFactor() {
            return (double) count / size;
        }

        //
        public int usedBuckets() {
            int used = 0;
            for (List<Pair> bucket : table) {
                if (!bucket.isEmpty()) used++;
            }
            return used;
        }

        //
        public int maxBucketSize() {
            int max = 0;
            for (List<Pair> bucket : table) {
                max = Math.max(max, bucket.size());
            }
            return max;
        }

        //Entrega tamaño, elementos, factor de carga, colisiones, buckets usados, tamaño máximo de bucket y tiempo de inserción.
        public void printReport(double elapsedSeconds) {
            System.out.println("strategy=" + hashStrategy
                    + ", size=" + size
                    + ", elements=" + count
                    + ", loadFactor=" + String.format("%.3f", loadFactor())
                    + ", collisions=" + collisions
                    + ", usedBuckets=" + usedBuckets()
                    + ", maxBucketSize=" + maxBucketSize()
                    + ", insertTimeSeconds=" + String.format("%.6f", elapsedSeconds));
        }
    }

    //genera datos de forma alearoria
    static List<String> generateRandomKeys(int n, int length) {
        Random random = new Random(42);
        List<String> keys = new ArrayList<>();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < length; j++) {
                sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
            }
            keys.add(sb.toString());
        }
        return keys;
    }

    static List<String> generateSequentialKeys(int n) {
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < n; i++) keys.add("user" + i);
        return keys;
    }

    static List<String> generateClusteredKeys(int n) {
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < n; i++) keys.add("aaa" + i);
        return keys;
    }

    //para probar el código
    static void runExperiment(String datasetName, List<String> keys, int tableSize) {
        System.out.println("\nDataset: " + datasetName);
        for (String strategy : Arrays.asList("sum", "polynomial")) {
            HashTableChaining ht = new HashTableChaining(tableSize, strategy);
            long start = System.nanoTime();
            for (int i = 0; i < keys.size(); i++) {
                ht.insert(keys.get(i), i);
            }
            long end = System.nanoTime();
            double elapsedSeconds = (end - start) / 1_000_000_000.0;
            ht.printReport(elapsedSeconds);
        }
    }

    //La clase principal
    public static void main(String[] args) {
        int n = 1000;
        int tableSize = 211;
        runExperiment("random", generateRandomKeys(n, 8), tableSize);
        runExperiment("sequential", generateSequentialKeys(n), tableSize);
        runExperiment("clustered", generateClusteredKeys(n), tableSize);
    }
}
