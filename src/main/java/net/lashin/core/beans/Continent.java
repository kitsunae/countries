package net.lashin.core.beans;

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

    public static Continent fromString(String name){
        for (Continent c : values()){
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }
}
