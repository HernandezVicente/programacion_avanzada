//Enviar por GitHub
package com.example;

import java.util.*;

public class TallerHashing {
    //Esta es la clase de pares donde vamos a contener la key y value
    static class Pair {
        String key;//La llave es un elemento unico
        int value; //El valor asosiado a la llave
        //El constructor de pares para poder inicializar con los datos que le vamos a pasar
        Pair(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    //Esta clase encadena a los hash que tienen una simulitud
    static class HashTableChaining {
        private List<List<Pair>> table; //Lista de los pares hash que hacen una tabla, y eviatar colisiones
        private int size;// tamaño total de la Lista
        private int count;//Condador de pares insertados
        private int collisions;//Cuenta cuandas veces sucedio una colisión
        private String hashStrategy;//Tipo de estrategia que se usara

        //Se inicialización del constructor
        public HashTableChaining(int size, String hashStrategy) {
            this.size = size;
            this.hashStrategy = hashStrategy;
            this.count = 0;
            this.collisions = 0;
            this.table = new ArrayList<>();//Se inicializa la tabla principal
            for (int i = 0; i < size; i++) {//Se agregamos listas vacias en la tablas
                table.add(new LinkedList<>());
            }
        }


        //Calcula el índice mediante suma de caracteres.
        private int hashSum(String key) {
            int total = 0;
            for (int i = 0; i < key.length(); i++) {
                total += key.charAt(i);//para convertirlo a un numero ASCII
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
        //Metodo en el cual se decique que estragia usar
        private int hash(String key) {
            if (hashStrategy.equals("sum")) {
                return hashSum(key);
            } else if (hashStrategy.equals("polynomial")) {
                return hashPolynomial(key);
            }
            //Lanza una exección cuando es una estrategia que no esta
            throw new IllegalArgumentException("Unknown hash strategy");
        }

        //Inserta un nuevo par o actualiza el valor si la clave ya existe
        public void insert(String key, int value) {
            int idx = hash(key);//Se calcula la llave
            List<Pair> bucket = table.get(idx);//Ingresamos a la Lista
            for (Pair p : bucket) {//Vemos si la llave existe
                if (p.key.equals(key)) {//si existe de actualiza su valor
                    p.value = value;
                    return;
                }
            }
            if (!bucket.isEmpty()) {//Se registra un colisión
                collisions++;
            }
            bucket.add(new Pair(key, value));//Agregamos un nuevo par a la lista
            count++;
        }

        //Busca una clave y retorna su valor. Si no existe, retorna null/None.
        public Integer search(String key) {
            int idx = hash(key);//Calculamos el index
            for (Pair p : table.get(idx)) {//recorremos la sub-lista
                if (p.key.equals(key)) return p.value;//Si se encuantra mostramos su valor
            }
            return null;//En caso de que no se encuantre
        }

        //Elimina una clave si existe y retorna true/True; si no existe retorna false/False.
        public boolean delete(String key) {
            int idx = hash(key);
            //Recorremos la lista y borramos el elmento
            Iterator<Pair> iterator = table.get(idx).iterator();
            while (iterator.hasNext()) {
                Pair p = iterator.next();
                if (p.key.equals(key)) {//En caso que encontremos la llave
                    iterator.remove();//Borramos la llave
                    count--;//Reducimos el contador
                    return true;
                }
            }
            return false;
        }

        //Si existe más que solo un elmento
        public double loadFactor() {
            return (double) count / size;
        }

        //Cuenta elmentes que estan en la sub-lista
        public int usedBuckets() {
            int used = 0;
            for (List<Pair> bucket : table) {
                if (!bucket.isEmpty()) used++;
            }
            return used;
        }

        //Buscar la sub-lista más larga
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
    //Genera datos de forma secuelcual
    static List<String> generateSequentialKeys(int n) {
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < n; i++) keys.add("user" + i);
        return keys;
    }
    //Genera datos que se pueden agrupar
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