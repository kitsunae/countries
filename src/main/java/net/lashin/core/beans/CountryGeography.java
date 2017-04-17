package net.lashin.core.beans;


import net.lashin.core.beans.converters.ContinentConverter;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class CountryGeography {

    @NotNull
    @Convert(converter = ContinentConverter.class)
    private Continent continent;
    @Size(max = 26)
    @NotNull
    private String region;
    @DecimalMax("9999999999.99")
    @DecimalMin("0.01")
    private double surfaceArea;

    protected CountryGeography() {
    }

    public CountryGeography(Continent continent, String region, double surfaceArea) {
        this.continent = continent;
        this.region = region;
        this.surfaceArea = surfaceArea;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public String getRegion() {
        return region;
    }

    @SuppressWarnings("WeakerAccess")
    public void setRegion(String region) {
        this.region = region;
    }

    public double getSurfaceArea() {
        return surfaceArea;
    }

    @SuppressWarnings("WeakerAccess")
    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryGeography that = (CountryGeography) o;

        if (Double.compare(that.getSurfaceArea(), getSurfaceArea()) != 0) return false;
        //noinspection SimplifiableIfStatement
        if (getContinent() != that.getContinent()) return false;
        return getRegion() != null ? getRegion().equals(that.getRegion()) : that.getRegion() == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getContinent() != null ? getContinent().hashCode() : 0;
        result = 31 * result + (getRegion() != null ? getRegion().hashCode() : 0);
        temp = Double.doubleToLongBits(getSurfaceArea());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "CountryGeography{" +
                "continent=" + continent +
                ", region='" + region + '\'' +
                ", surfaceArea=" + surfaceArea +
                '}';
    }
}
