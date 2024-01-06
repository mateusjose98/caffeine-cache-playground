package com.mateus98.caffeine;

public class Utils {

    public static void espera(int i) {
        try {
            Thread.sleep(i * 1000L);
        } catch (InterruptedException e) {

        }
    }
}
