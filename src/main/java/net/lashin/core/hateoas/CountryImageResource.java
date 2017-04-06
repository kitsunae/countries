package net.lashin.core.hateoas;

import org.springframework.hateoas.ResourceSupport;

public class CountryImageResource extends ResourceSupport {

    private long identity;
    private String url;
    private String description;
    private String type;

    public long getIdentity() {
        return identity;
    }

    public void setIdentity(long identity) {
        this.identity = identity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
