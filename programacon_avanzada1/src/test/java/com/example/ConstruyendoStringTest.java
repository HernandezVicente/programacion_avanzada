package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class ConstruyendoStringTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void concatenacionRepetida() {
    }

    @Test
    void concatenacionBuilder() {
    }

    @Test
    @DisplayName("Comparar rendimiento 1")
    void testPerformance50000() {
        int iterationes = 50000;

        assertTimeout(Duration.ofMillis(100), () -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < iterationes; i++) {
                sb.append("a");
            }
            String result = sb.toString();
        });

        assertTimeout(Duration.ofMillis(1000), () -> {
            String s = "";
            for (int i = 0; i < iterationes; i++) {
                s += "a";
            }
        });
    }

    @Test
    @DisplayName("Comparar rendimiento 2")
    void testPerformance5000() {
        int iterationes = 5000;

        assertTimeout(Duration.ofMillis(100), () -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < iterationes; i++) {
                sb.append("a");
            }
            String result = sb.toString();
        });

        assertTimeout(Duration.ofMillis(1000), () -> {
            String s = "";
            for (int i = 0; i < iterationes; i++) {
                s += "a";
            }
        });
    }

    @Test
    @DisplayName("Comparar rendimiento 3")
    void testPerformance500000() {
        int iterationes = 500000;

        assertTimeout(Duration.ofMillis(100), () -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < iterationes; i++) {
                sb.append("a");
            }
            String result = sb.toString();
        });

        assertTimeout(Duration.ofMillis(10000), () -> {
            String s = "";
            for (int i = 0; i < iterationes; i++) {
                s += "a";
            }
        });
    }

    @Test
    @DisplayName("Comparar rendimiento 4")
    void testPerformance10000() {
        int iterationes = 10000;

        assertTimeout(Duration.ofMillis(100), () -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < iterationes; i++) {
                sb.append("a");
            }
            String result = sb.toString();
        });

        assertTimeout(Duration.ofMillis(1000), () -> {
            String s = "";
            for (int i = 0; i < iterationes; i++) {
                s += "a";
            }
        });
    }

    @Test
    @DisplayName("Comparar rendimiento 5")
    void testPerformance1000() {
        int iterationes = 1000;

        assertTimeout(Duration.ofMillis(100), () -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < iterationes; i++) {
                sb.append("a");
            }
            String result = sb.toString();
        });

        assertTimeout(Duration.ofMillis(1000), () -> {
            String s = "";
            for (int i = 0; i < iterationes; i++) {
                s += "a";
            }
        });
    }

    @Test
    @DisplayName("Comparar rendimiento 6")
    void testPerformance100000() {
        int iterationes = 100000;

        assertTimeout(Duration.ofMillis(100), () -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < iterationes; i++) {
                sb.append("a");
            }
            String result = sb.toString();
        });

        assertTimeout(Duration.ofMillis(1000), () -> {
            String s = "";
            for (int i = 0; i < iterationes; i++) {
                s += "a";
            }
        });
    }
}