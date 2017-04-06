package net.lashin.core.beans;

import javax.persistence.*;


@Entity(name = "country_image")
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
}
