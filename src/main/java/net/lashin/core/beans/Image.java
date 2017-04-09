package net.lashin.core.beans;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Image {

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    protected ImageSize size;
    private String url;
    private String description;


    public abstract long getId();

    public abstract void setId(long id);

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageSize getSize() {
        return size;
    }

    public void setSize(ImageSize size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        //noinspection SimplifiableIfStatement
        if (getUrl() != null ? !getUrl().equals(image.getUrl()) : image.getUrl() != null) return false;
        return getSize() == image.getSize();

    }

    @Override
    public int hashCode() {
        int result = getUrl() != null ? getUrl().hashCode() : 0;
        result = 31 * result + (getSize() != null ? getSize().hashCode() : 0);
        return result;
    }
}
