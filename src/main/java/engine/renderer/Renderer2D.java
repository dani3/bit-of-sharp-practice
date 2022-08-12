package engine.renderer;

import engine.core.Logger;
import engine.renderer.buffer.VertexArray;
import engine.renderer.shader.Shader;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;

public class Renderer2D {

    private static final Logger mLogger = Logger.create(Renderer2D.class.getName());

    private VertexArray mQuadVertexArray;
    private Shader mTextureShader;
    private Texture mWhiteTexture;

    public static void init() {
        // TODO
    }

    public static void shutdown() {
        // TODO
    }

    public static void beginScene(final OrthographicCamera camera) {
        // TODO
    }

    public static void endScene() {
        // TODO
    }

    // --------------------------------------------------------------------------------------------
    // Primitives
    // --------------------------------------------------------------------------------------------

    public static void drawQuad(
            final Vector2d position, final Vector2d size, final Vector4d color) {
        // TODO
    }

    public static void drawQuad(
            final Vector3d position, final Vector2d size, final Vector4d color) {
        // TODO
    }
}
