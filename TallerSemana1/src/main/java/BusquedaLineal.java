public class BusquedaLineal {
    public static void main(String[] args) {

        int[] numeros = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            numeros[i] = i+1;
        }
        long inicio = System.nanoTime();
        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i] == numeros.length) {
                System.out.println(i+1);
                break;
            }

        }

        long fin = System.nanoTime();
        System.out.println((fin - inicio)/1e6+" ms");
    }
}
