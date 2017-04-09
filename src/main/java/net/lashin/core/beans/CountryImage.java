package net.lashin.core.beans;

import javax.persistence.Embeddable;

@Embeddable
public class CountryImage extends Image {

    protected long id;

    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

}
