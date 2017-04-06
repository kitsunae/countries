package net.lashin.core.beans;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class CountryLanguage {

    @EmbeddedId
    private CountryLanguageId countryLanguageId;
    @org.hibernate.annotations.Type(type = "true_false")
    private boolean isOfficial;
    private Double percentage;
    private String description;
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

    public void setCountry(Country country) {
        setCountryCode(country.getCode());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryLanguage that = (CountryLanguage) o;

        return countryLanguageId != null ? countryLanguageId.equals(that.countryLanguageId) : that.countryLanguageId == null;

    }

    @Override
    public int hashCode() {
        return countryLanguageId != null ? countryLanguageId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CountryLanguage{" +
                "countryLanguageId=" + countryLanguageId +
                ", isOfficial=" + isOfficial +
                ", percentage=" + percentage +
                '}';
    }
}
