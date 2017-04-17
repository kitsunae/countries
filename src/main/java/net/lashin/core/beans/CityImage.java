package net.lashin.core.beans;

import javax.persistence.Embeddable;

@Embeddable
public class CityImage extends Image {

    private long id;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

}
