package net.lashin.core.beans;

import java.util.HashMap;
import java.util.Map;

public enum Continent {
    ASIA("Asia"),
    EUROPE("Europe"),
    NORTH_AMERICA("North America"),
    AFRICA("Africa"),
    OCEANIA("Oceania"),
    ANTARCTICA("Antarctica"),
    SOUTH_AMERICA("South America");

    private static final Map<String, Continent> map = new HashMap<>();

    static {
        for (Continent c : Continent.values()) {
            map.put(c.toString(), c);
        }
    }

    private String name;

    Continent(String name) {
        this.name = name;
    }

    public static Continent fromString(String name) {
        return map.get(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
