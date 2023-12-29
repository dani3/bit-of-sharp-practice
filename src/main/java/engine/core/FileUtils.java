package engine.core;

import engine.renderer.Renderer3D;

/**
 * Class that contains a collection of file utilities functions.
 */
public class FileUtils {

    public static final String RESOURCE_TYPE_TEXTURE = "textures";
    public static final String RESOURCE_TYPE_SHADERS = "shaders";
    public static final String RESOURCE_TYPE_OBJECTS = "objects";

    /**
     * Return the path to the resource given its type and name.
     *
     * @param resourceType resource type.
     * @param resourceName resource name.
     *
     * @return Path to the resource.
     */
    public static String getResourcePath(final String resourceType, final String resourceName) {
        return Renderer3D.class.getClassLoader()
                .getResource(resourceType + "/" + resourceName)
                .getPath()
                .replaceFirst("/", "");
    }
}
