package net.lashin.core.hateoas.asm;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;


public abstract class ResourceHandler<S, R extends ResourceSupport> extends ResourceAssemblerSupport<S, R> {

    /**
     * Creates a new {@link ResourceAssemblerSupport} using the given controller class and resource size.
     *
     * @param controllerClass must not be {@literal null}.
     * @param resourceType    must not be {@literal null}.
     */
    ResourceHandler(Class<?> controllerClass, Class<R> resourceType) {
        super(controllerClass, resourceType);
    }

    public abstract S toEntity(R resource);
}
