package net.lashin.core.hateoas;

import net.lashin.core.beans.Country;
import net.lashin.core.beans.CountryLanguage;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by lashi on 15.02.2017.
 */
public class CountryLanguageResource extends ResourceSupport {

    private String countryCode;
    private String language;
    private boolean isOfficial;
    private Double percentage;

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

    public CountryLanguage toCountryLanguage(Country country){
        return new CountryLanguage(isOfficial, percentage, country, language);
    }

    public CountryLanguage toCountryLanguage(){
        return new CountryLanguage(countryCode,language,isOfficial, percentage);
    }
}
