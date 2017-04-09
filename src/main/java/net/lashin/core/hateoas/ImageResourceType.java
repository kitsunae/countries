package net.lashin.core.hateoas;

import net.lashin.core.beans.CityImage;
import net.lashin.core.beans.CountryImage;
import net.lashin.core.beans.Image;

import java.util.HashMap;
import java.util.Map;

public enum ImageResourceType {
    CITY(CityImage.class),
    COUNTRY(CountryImage.class);

    private static final Map<Class<? extends Image>, ImageResourceType> map = new HashMap<>();

    static {
        for (ImageResourceType type : ImageResourceType.values()) {
            map.put(type.getClazz(), type);
        }
    }

    private final Class<? extends Image> clazz;

    ImageResourceType(Class<? extends Image> clazz) {
        this.clazz = clazz;
    }

    public static ImageResourceType fromClass(Class<? extends Image> clazz) {
        return map.get(clazz);
    }

    public Class<? extends Image> getClazz() {
        return clazz;
    }
}
