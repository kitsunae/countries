package net.lashin.core.hateoas;

import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CountryLanguageResource extends ResourceSupport {

    @NotNull
    @Size(max = 3)
    private String countryCode;
    @NotNull
    @Size(max = 30)
    private String language;
    private boolean isOfficial;
    @DecimalMax("100.00")
    @DecimalMin("0.00")
    private Double percentage;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CountryLanguageResource{" +
                "countryCode='" + countryCode + '\'' +
                ", language='" + language + '\'' +
                ", isOfficial=" + isOfficial +
                ", percentage=" + percentage +
                ", description='" + description + '\'' +
                '}';
    }
}
