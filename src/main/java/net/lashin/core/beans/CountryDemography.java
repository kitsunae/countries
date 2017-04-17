package net.lashin.core.beans;

import javax.persistence.Embeddable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Embeddable
public class CountryDemography {

    @Max(Integer.MAX_VALUE)
    @Min(0)
    private int population;
    @DecimalMax("999.9")
    @DecimalMin("0.1") //// TODO: 17.04.2017 check
    private Double lifeExpectancy;

    public CountryDemography() {
    }

    public CountryDemography(int population, Double lifeExpectancy) {
        this.population = population;
        this.lifeExpectancy = lifeExpectancy;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(Double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryDemography that = (CountryDemography) o;

        //noinspection SimplifiableIfStatement
        if (getPopulation() != that.getPopulation()) return false;
        return getLifeExpectancy() != null ? getLifeExpectancy().equals(that.getLifeExpectancy()) : that.getLifeExpectancy() == null;

    }

    @Override
    public int hashCode() {
        int result = getPopulation();
        result = 31 * result + (getLifeExpectancy() != null ? getLifeExpectancy().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CountryDemography{" +
                "population=" + population +
                ", lifeExpectancy=" + lifeExpectancy +
                '}';
    }
}
