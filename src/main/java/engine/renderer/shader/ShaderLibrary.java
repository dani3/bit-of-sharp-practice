package engine.renderer.shader;

import engine.core.Logger;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

/**
 * Class to manage shaders.
 */
public class ShaderLibrary {

    private static final Logger mLogger = Logger.create(ShaderLibrary.class.getName());

    // Map that contains the shaders used by the engine.
    private Map<String, Shader> mShaders;

    /**
     * Add a new shader to the library.
     *
     * @param shader shader to add.
     */
    public void add(@NotNull final Shader shader) {
        var name = shader.getName();
        add(name, shader);
    }

    /**
     * Add a new shader to the library.
     *
     * @param name   name of the shader.
     * @param shader shader to add.
     */
    public void add(@NotNull final String name, @NotNull final Shader shader) {
        if (mShaders.containsKey(name)) {
            mLogger.error("Shader already exists");
            assert false;
        }

        mShaders.put(name, shader);
    }

    /**
     * Load a new shader given the path to it.
     *
     * @param filepath path to the GLSL shader.
     * @return new Shader object.
     */
    public Shader load(@NotNull final String filepath) {
        var shader = new Shader(filepath);
        add(shader);
        return shader;
    }

    /**
     * Load a new shader given the path and its name.
     *
     * @param name     shader name.
     * @param filepath path to the GLSL shader.
     * @return new Shader object.
     */
    public Shader load(@NotNull final String name, @NotNull final String filepath) {
        var shader = new Shader(filepath);
        add(name, shader);
        return shader;
    }

    /**
     * Return an existing shader given its name.
     *
     * @param name shader name.
     * @return shader object.
     */
    public Shader get(@NotNull final String name) {
        return mShaders.get(name);
    }

    /**
     * Return true if the shader exists.
     *
     * @param name name of the shader to check if it exists.
     * @return true if the shader exists, false otherwise.
     */
    public boolean exists(@NotNull final String name) {
        return mShaders.containsKey(name);
    }
}
