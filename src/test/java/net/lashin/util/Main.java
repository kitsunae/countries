package net.lashin.util;


import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> getenv = System.getenv();
        for (Map.Entry<String, String> entry : getenv.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
