package net.lashin.core.beans;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;


@SuppressWarnings("WeakerAccess")
@MappedSuperclass
public abstract class Image {

    @NotNull
    protected String url;
    @NotNull
    @Enumerated(EnumType.STRING)
    protected ImageType type;
    protected String description;

    protected Image() {
    }

    public abstract long getId();

    public abstract void setId(long id);

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (getId() != image.getId()) return false;
        if (getUrl() != null ? !getUrl().equals(image.getUrl()) : image.getUrl() != null) return false;
        return getType() == image.getType();

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }
}
