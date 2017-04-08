package net.lashin.core.beans;

import javax.persistence.*;


@Entity
@Table(name = "country_image")
public class CountryImage extends Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CountryCode", nullable = false)
    private Country country;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CountryImage image = (CountryImage) o;

        return getCountry() != null ? getCountry().equals(image.getCountry()) : image.getCountry() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        return result;
    }
}
