package net.lashin.core.filters;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.CountryLanguage;

import java.util.function.Predicate;

public class LanguageFilter implements Predicate<CountryLanguage> {

    private Continent continent;
    private String region;
    private double minPercentage;
    private double maxPercentage;
    private Boolean isOfficial;

    public LanguageFilter() {
        this.minPercentage = 0;
        this.maxPercentage = 100;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    @SuppressWarnings("WeakerAccess")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getMinPercentage() {
        return minPercentage;
    }

    public void setMinPercentage(double minPercentage) {
        this.minPercentage = minPercentage;
    }

    public double getMaxPercentage() {
        return maxPercentage;
    }

    public void setMaxPercentage(double maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    @SuppressWarnings("WeakerAccess")
    public Boolean isOfficial() {
        return isOfficial;
    }

    public void setOfficial(Boolean official) {
        isOfficial = official;
    }

    @Override
    public boolean test(CountryLanguage countryLanguage) {
        return (this.getContinent() == null || countryLanguage.getCountry().getContinent() == this.getContinent()) &&
                (this.getRegion() == null || this.getRegion().equals(countryLanguage.getCountry().getRegion())) &&
                (this.isOfficial() == null || countryLanguage.isOfficial() == this.isOfficial());
    }
}
