package net.lashin.core.beans;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
public class CountryLanguageId implements Serializable {
    @NotNull
    @Size(max = 3)
    private String countryCode;
    @NotNull
    @Size(max = 30)
    private String language;

    protected CountryLanguageId(){
    }

    public CountryLanguageId(String countryCode, String language) {
        this.countryCode = countryCode;
        this.language = language;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryLanguageId that = (CountryLanguageId) o;

        return countryCode.equals(that.getCountryCode()) && language.equals(that.getLanguage());

    }

    @Override
    public int hashCode() {
        int result = countryCode.hashCode();
        result = 31 * result + language.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CountryLanguageId{" +
                "countryCode='" + countryCode + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
