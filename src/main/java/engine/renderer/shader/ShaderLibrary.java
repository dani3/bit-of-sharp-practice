package engine.renderer.shader;

import engine.core.Logger;

import java.util.Map;

public class ShaderLibrary {

    private static final Logger mLogger = Logger.create(ShaderLibrary.class.getName());

    private Map<String, Shader> mShaders;

    public void add(final Shader shader) {
        var name = shader.getName();
        add(name, shader);
    }

    public void add(final String name, final Shader shader) {
        if (mShaders.containsKey(name)) {
            mLogger.error("Shader already exists");
            assert false;
        }

        mShaders.put(name, shader);
    }

    public Shader load(final String filepath) {
        var shader = new Shader(filepath);
        add(shader);
        return shader;
    }

    public Shader load(final String name, final String filepath) {
        var shader = new Shader(filepath);
        add(name, shader);
        return shader;
    }

    public Shader get(final String name) {
        return mShaders.get(name);
    }

    public boolean exists(final String name) {
        return mShaders.containsKey(name);
    }
}
