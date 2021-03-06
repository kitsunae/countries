package net.lashin.core.beans.converters;

import net.lashin.core.beans.Continent;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ContinentConverter implements AttributeConverter<Continent, String> {
    @Override
    public String convertToDatabaseColumn(Continent attribute) {
        return attribute.toString();
    }

    @Override
    public Continent convertToEntityAttribute(String dbData) {
        return Continent.fromString(dbData);
    }
}
