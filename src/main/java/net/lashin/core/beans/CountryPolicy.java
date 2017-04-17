package net.lashin.core.beans;


import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class CountryPolicy {

    @Size(max = 45)
    @NotNull
    private String governmentForm;
    @Size(max = 60)
    private String headOfState;

    protected CountryPolicy() {
    }

    public CountryPolicy(String governmentForm) {
        this.governmentForm = governmentForm;
    }

    public CountryPolicy(String governmentForm, String headOfState) {
        this.governmentForm = governmentForm;
        this.headOfState = headOfState;
    }

    public String getGovernmentForm() {
        return governmentForm;
    }

    public void setGovernmentForm(String governmentForm) {
        this.governmentForm = governmentForm;
    }

    public String getHeadOfState() {
        return headOfState;
    }

    public void setHeadOfState(String headOfState) {
        this.headOfState = headOfState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryPolicy that = (CountryPolicy) o;

        //noinspection SimplifiableIfStatement
        if (getGovernmentForm() != null ? !getGovernmentForm().equals(that.getGovernmentForm()) : that.getGovernmentForm() != null)
            return false;
        return getHeadOfState() != null ? getHeadOfState().equals(that.getHeadOfState()) : that.getHeadOfState() == null;

    }

    @Override
    public int hashCode() {
        int result = getGovernmentForm() != null ? getGovernmentForm().hashCode() : 0;
        result = 31 * result + (getHeadOfState() != null ? getHeadOfState().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CountryPolicy{" +
                "governmentForm='" + governmentForm + '\'' +
                ", headOfState='" + headOfState + '\'' +
                '}';
    }
}
