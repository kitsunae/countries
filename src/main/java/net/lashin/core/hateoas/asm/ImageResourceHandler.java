package net.lashin.core.hateoas.asm;

import net.lashin.core.beans.Image;
import net.lashin.core.beans.ImageSize;
import net.lashin.core.hateoas.ImageResource;
import net.lashin.core.hateoas.ImageResourceType;
import net.lashin.web.controllers.IndexController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ImageResourceHandler extends ResourceHandler<Image, ImageResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageResourceHandler.class);

    public ImageResourceHandler() {
        super(IndexController.class, ImageResource.class);
    }

    @Override
    public Image toEntity(ImageResource resource) {
        LOGGER.trace("Translating to entity resource {}", resource);
        Image image;
        try {
            image = resource.getType().getClazz().newInstance();
            image.setId(resource.getIdentity());
            image.setDescription(resource.getDescription());
            image.setSize(ImageSize.valueOf(resource.getSize()));
            image.setUrl(resource.getId().getHref().replaceAll("http.*://.*?/", ""));
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("Error translating resource {} to entity {}", resource, resource.getType().getClazz().getSimpleName());
            throw new IllegalArgumentException("Non instantiable class of image", e);
        }
        return image;
    }

    @Override
    public ImageResource toResource(Image entity) {
        LOGGER.trace("Translating {} to resource", entity);
        ImageResource resource = new ImageResource();
        resource.setIdentity(entity.getId());
        resource.setDescription(entity.getDescription());
        resource.setSize(entity.getSize().toString());
        resource.setType(ImageResourceType.fromClass(entity.getClass()));
        Link self = linkTo(methodOn(IndexController.class).getImage())
                .slash(entity.getUrl()).withSelfRel();
        resource.add(self);
        return resource;
    }
}
