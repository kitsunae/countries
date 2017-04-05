package net.lashin.core.filters;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;

import java.time.LocalDate;
import java.util.function.Predicate;

public class CountryFilter implements Predicate<Country> {

    private Continent continent;
    private String region;
    private String governmentForm;
    private double minSurfaceArea;
    private double maxSurfaceArea;
    private int minIndepYear;
    private int maxIndepYear;
    private int minPopulation;
    private int maxPopulation;
    private double minLifeExpectancy;
    private double maxLifeExpectancy;
    private double minGnp;
    private double maxGnp;
    private double minGnpOld;
    private double maxGnpOld;

    public CountryFilter() {
        this.minSurfaceArea = 0;
        this.maxSurfaceArea = 510_072_000;
        this.minIndepYear = -10000;
        this.maxIndepYear = LocalDate.now().getYear();
        this.minPopulation = 0;
        this.maxPopulation = Integer.MAX_VALUE;
        this.minLifeExpectancy = 0;
        this.maxLifeExpectancy = 999.99;
        this.minGnp = 0;
        this.maxGnp = 9999999999.99;
        this.minGnpOld = 0;
        this.maxGnpOld = 9999999999.99;
    }

    private boolean isEnabledYearFilter() {
        return minIndepYear!=-10000 || maxIndepYear != LocalDate.now().getYear();
    }

    private boolean isEnabledLifeExpectFilter() {
        return minLifeExpectancy != 0 || maxLifeExpectancy != 999.99;
    }

    private boolean isEnabledGnpFilter() {
        return minGnp!=0 || maxGnp != 9999999999.99;
    }

    private boolean isEnabledGnpOldFilter() {
        return minGnpOld!=0 || maxGnpOld != 9999999999.99;
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

    @SuppressWarnings("WeakerAccess")
    public String getGovernmentForm() {
        return governmentForm;
    }

    public void setGovernmentForm(String governmentForm) {
        this.governmentForm = governmentForm;
    }

    public double getMinSurfaceArea() {
        return minSurfaceArea;
    }

    public void setMinSurfaceArea(double minSurfaceArea) {
        this.minSurfaceArea = minSurfaceArea;
    }

    public double getMaxSurfaceArea() {
        return maxSurfaceArea;
    }

    public void setMaxSurfaceArea(double maxSurfaceArea) {
        this.maxSurfaceArea = maxSurfaceArea;
    }

    public int getMinPopulation() {
        return minPopulation;
    }

    public void setMinPopulation(int minPopulation) {
        this.minPopulation = minPopulation;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public int getMinIndepYear() {
        return minIndepYear;
    }

    public void setMinIndepYear(int minIndepYear) {
        this.minIndepYear = minIndepYear;
    }

    public int getMaxIndepYear() {
        return maxIndepYear;
    }

    public void setMaxIndepYear(int maxIndepYear) {
        this.maxIndepYear = maxIndepYear;
    }

    public double getMinLifeExpectancy() {
        return minLifeExpectancy;
    }

    public void setMinLifeExpectancy(double minLifeExpectancy) {
        this.minLifeExpectancy = minLifeExpectancy;
    }

    public double getMaxLifeExpectancy() {
        return maxLifeExpectancy;
    }

    public void setMaxLifeExpectancy(double maxLifeExpectancy) {
        this.maxLifeExpectancy = maxLifeExpectancy;
    }

    public double getMinGnp() {
        return minGnp;
    }

    public void setMinGnp(double minGnp) {
        this.minGnp = minGnp;
    }

    public double getMaxGnp() {
        return maxGnp;
    }

    public void setMaxGnp(double maxGnp) {
        this.maxGnp = maxGnp;
    }

    public double getMinGnpOld() {
        return minGnpOld;
    }

    public void setMinGnpOld(double minGnpOld) {
        this.minGnpOld = minGnpOld;
    }

    public double getMaxGnpOld() {
        return maxGnpOld;
    }

    public void setMaxGnpOld(double maxGnpOld) {
        this.maxGnpOld = maxGnpOld;
    }

    @Override
    public boolean test(Country country) {
        return (!(country.getIndepYear() == null && this.isEnabledYearFilter())) &&
                (!(country.getLifeExpectancy() == null && this.isEnabledLifeExpectFilter())) &&
                (!(country.getGnp() == null && this.isEnabledGnpFilter())) &&
                (!(country.getGnpOld() == null && this.isEnabledGnpOldFilter())) &&
                (this.getContinent() == null || country.getContinent() == this.getContinent()) &&
                (this.getRegion() == null || this.getRegion().equals(country.getRegion())) &&
                (this.getGovernmentForm() == null || this.getGovernmentForm().equals(country.getGovernmentForm()));
    }
}
