package net.lashin.core.filters;

import net.lashin.core.beans.Continent;

/**
 * Created by lashi on 24.03.2017.
 */
public class LanguageFilter {

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

    public Boolean isOfficial() {
        return isOfficial;
    }

    public void setOfficial(Boolean official) {
        isOfficial = official;
    }
}
