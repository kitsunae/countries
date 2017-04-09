package net.lashin.core.hateoas;

import org.springframework.hateoas.ResourceSupport;

public class ImageResource extends ResourceSupport {

    private long identity;
    private String size;
    private String description;
    private ImageResourceType type;

    public long getIdentity() {
        return identity;
    }

    public void setIdentity(long identity) {
        this.identity = identity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageResourceType getType() {
        return type;
    }

    public void setType(ImageResourceType type) {
        this.type = type;
    }
}
