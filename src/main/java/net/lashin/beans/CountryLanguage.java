package net.lashin.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by lashi on 24.01.2017.
 */

@Entity
public class CountryLanguage {

    @EmbeddedId
    private CountryLanguageId countryLanguageId;
    @org.hibernate.annotations.Type(type = "true_false")
    private boolean isOfficial;
    private Double percentage;
    @ManyToOne
    @JoinColumn(name = "countryCode", insertable = false, updatable = false)
    private Country country;

    protected CountryLanguage() {
    }

    public CountryLanguage(String countryCode, String language, boolean isOfficial, double percentage) {
        this.countryLanguageId = new CountryLanguageId(countryCode,language);
        this.isOfficial = isOfficial;
        this.percentage = percentage;
    }

    public CountryLanguage(boolean isOfficial, Double percentage, Country country, String language) {
        this.isOfficial = isOfficial;
        this.percentage = percentage;
        this.countryLanguageId = new CountryLanguageId(null, language);
        country.addLanguage(this);
    }

    public Country getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryLanguageId.getCountryCode();
    }

    public void setCountryCode(String countryCode) {
        this.countryLanguageId.setCountryCode(countryCode);
    }

    public String getLanguage() {
        return countryLanguageId.getLanguage();
    }

    public void setLanguage(String language) {
        this.countryLanguageId.setLanguage(language);
    }

    public boolean isOfficial() {
        return isOfficial;
    }

    public void setOfficial(boolean official) {
        isOfficial = official;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public CountryLanguageId getCountryLanguageId() {
        return countryLanguageId;
    }

    public void setCountryLanguageId(CountryLanguageId countryLanguageId) {
        this.countryLanguageId = countryLanguageId;
    }

    public void setCountry(Country country) {
        this.country = country;
        setCountryCode(country.getCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryLanguage language = (CountryLanguage) o;

        if (isOfficial != language.isOfficial()) return false;
        if (!countryLanguageId.equals(language.getCountryLanguageId())) return false;
        return percentage != null ? percentage.equals(language.getPercentage()) : language.getPercentage() == null;

    }

    @Override
    public int hashCode() {
        int result = countryLanguageId.hashCode();
        result = 31 * result + (isOfficial ? 1 : 0);
        result = 31 * result + (percentage != null ? percentage.hashCode() : 0);
        return result;
    }
}
