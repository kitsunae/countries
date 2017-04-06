package net.lashin.core.beans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@SuppressWarnings("WeakerAccess")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    @NotNull
    protected String url;
    @NotNull
    @Enumerated(EnumType.STRING)
    protected ImageType type;
    protected String description;

    protected Image() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
