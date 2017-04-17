package net.lashin.core.beans;

import javax.persistence.Embeddable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@Embeddable
public class CountryEconomy {

    @DecimalMax("9999999999.99")
    @DecimalMin("0")
    private Double gnp;
    @DecimalMax("9999999999.99")
    @DecimalMin("0")
    private Double gnpOld;

    public CountryEconomy() {
    }

    public CountryEconomy(Double gnp, Double gnpOld) {
        this.gnp = gnp;
        this.gnpOld = gnpOld;
    }

    public Double getGnp() {
        return gnp;
    }

    public void setGnp(Double gnp) {
        this.gnp = gnp;
    }

    public Double getGnpOld() {
        return gnpOld;
    }

    public void setGnpOld(Double gnpOld) {
        this.gnpOld = gnpOld;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryEconomy that = (CountryEconomy) o;

        //noinspection SimplifiableIfStatement
        if (getGnp() != null ? !getGnp().equals(that.getGnp()) : that.getGnp() != null) return false;
        return getGnpOld() != null ? getGnpOld().equals(that.getGnpOld()) : that.getGnpOld() == null;

    }

    @Override
    public int hashCode() {
        int result = getGnp() != null ? getGnp().hashCode() : 0;
        result = 31 * result + (getGnpOld() != null ? getGnpOld().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CountryEconomy{" +
                "gnp=" + gnp +
                ", gnpOld=" + gnpOld +
                '}';
    }
}
