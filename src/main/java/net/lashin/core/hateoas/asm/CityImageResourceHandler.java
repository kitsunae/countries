package net.lashin.core.hateoas.asm;

import net.lashin.core.beans.CityImage;
import net.lashin.core.beans.ImageType;
import net.lashin.core.hateoas.CityImageResource;
import net.lashin.web.controllers.ImageController;
import org.springframework.stereotype.Component;

@Component
public class CityImageResourceHandler extends ResourceHandler<CityImage, CityImageResource> {

    public CityImageResourceHandler() {
        super(ImageController.class, CityImageResource.class);
    }

    @Override
    public CityImage toEntity(CityImageResource resource) {
        CityImage image = new CityImage();
        image.setId(resource.getIdentity());
        image.setDescription(resource.getDescription());
        image.setType(ImageType.valueOf(resource.getType()));
        image.setUrl(resource.getUrl());
        return image;
    }

    @Override
    public CityImageResource toResource(CityImage entity) {
        CityImageResource resource = new CityImageResource();
        resource.setUrl(entity.getUrl());
        resource.setDescription(entity.getDescription());
        resource.setIdentity(entity.getId());
        resource.setType(entity.getType().toString());
        return resource;
    }
}
