package net.lashin.core.hateoas.asm;

import net.lashin.core.beans.CountryImage;
import net.lashin.core.beans.ImageType;
import net.lashin.core.hateoas.CountryImageResource;
import net.lashin.web.controllers.ImageController;
import org.springframework.stereotype.Component;

@Component
public class CountryImageResourceHandler extends ResourceHandler<CountryImage, CountryImageResource> {

    public CountryImageResourceHandler() {
        super(ImageController.class, CountryImageResource.class);
    }

    @Override
    public CountryImage toEntity(CountryImageResource resource) {
        CountryImage image = new CountryImage();
        image.setId(resource.getIdentity());
        image.setUrl(resource.getUrl());
        image.setType(ImageType.valueOf(resource.getType()));
        image.setDescription(resource.getDescription());
        return image;
    }

    @Override
    public CountryImageResource toResource(CountryImage entity) {
        CountryImageResource resource = new CountryImageResource();
        resource.setIdentity(entity.getId());
        resource.setUrl(entity.getUrl());
        resource.setType(entity.getType().toString());
        resource.setDescription(entity.getDescription());
        return resource;
    }
}
