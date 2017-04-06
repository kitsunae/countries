package net.lashin.core.beans;

import javax.persistence.*;


@Entity(name = "city_image")
public class CityImage extends Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
