package com.example;

import java.util.logging.Logger;

public class ConstruyendoString {
    private static final Logger logger = Logger.getLogger(ConstruyendoString.class.getName());

    public String concatenacionRepetida(int n) {
        String result = "";
        for (int i = 0; i < n; i++) {
            result += "a";
        }
        return result;
    }

    public String concatenacionBuilder(int n) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder.append("a");
        }
        return builder.toString();
    }

    static void main(String[] args) {
        int[] casosDePrueba = {10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000, 500000};
        ConstruyendoString contruir = new ConstruyendoString();

        for (int n : casosDePrueba) {
            long inicioConcatenacionRepetida = System.nanoTime();
            contruir.concatenacionRepetida(n);
            long finConcatenacionRepetida = System.nanoTime();
            double segundosConcatenacionRepetida = (finConcatenacionRepetida - inicioConcatenacionRepetida) / 1_000_000_000.0;
            logger.info(String.format("Repetida    - Tiempo: %.6f segundos", segundosConcatenacionRepetida));

            try {
                long inicioconcatenacionBuilder = System.nanoTime();
                contruir.concatenacionBuilder(10);
                long finConcatenacionBuilder = System.nanoTime();
                double segundosConcatenacionBuilder = (finConcatenacionBuilder - inicioconcatenacionBuilder) / 1_000_000_000.0;
                logger.info(String.format("Builder    - Tiempo: %.6f segundos", segundosConcatenacionBuilder));
            } catch (OutOfMemoryError e) {
                logger.severe("Error: Se lleno la memoria n = " + n);
            }
        }
    }
}