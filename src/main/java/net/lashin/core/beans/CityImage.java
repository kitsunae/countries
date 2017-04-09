package net.lashin.core.beans;

import javax.persistence.Embeddable;

@Embeddable
public class CityImage extends Image {

    protected long id;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

}
