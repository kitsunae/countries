package net.lashin.beans;

public enum Continent {
    ASIA("Asia"),
    EUROPE("Europe"),
    NORTH_AMERICA("North America"),
    AFRICA("Africa"),
    OCEANIA("Oceania"),
    ANTARCTICA("Antarctica"),
    SOUTH_AMERICA("South America");

    private String name;

    Continent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
