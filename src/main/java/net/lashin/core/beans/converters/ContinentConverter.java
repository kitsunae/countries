package net.lashin.core.beans.converters;

import net.lashin.core.beans.Continent;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by lashi on 09.02.2017.
 */
@Converter(autoApply = true)
public class ContinentConverter implements AttributeConverter<Continent, String> {
    @Override
    public String convertToDatabaseColumn(Continent attribute) {
        return attribute.getName();
    }

    @Override
    public Continent convertToEntityAttribute(String dbData) {
        for (Continent continent : Continent.values())
            if (continent.getName().equals(dbData))
                return continent;
        return null;
    }
}
