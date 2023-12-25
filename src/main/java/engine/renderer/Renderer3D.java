package engine.renderer;

import engine.core.Logger;
import engine.entities.Entity;
import engine.renderer.buffer.*;
import engine.renderer.shader.Shader;
import engine.renderer.shader.ShaderDataType;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4d;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;

import java.util.Arrays;
import java.util.Objects;

public class Renderer3D {

    private static final Logger mLogger = Logger.create(Renderer3D.class.getName());

    private static Shader mTextureShader;

    public static void init() {
        var shaderPath = Objects.requireNonNull(
                Objects.requireNonNull(
                        Renderer3D.class.getClassLoader().getResource("shaders/Texture.glsl")).getPath());
        mTextureShader = new Shader(shaderPath);

        mTextureShader.bind();
        mTextureShader.uploadUniformInt("u_Texture", 0);
    }

    public static void shutdown() {
        // TODO
    }

    public static void beginScene(final @NotNull OrthographicCamera camera) {
        mTextureShader.bind();
        mTextureShader.setMat4("u_ViewProjection", camera.getViewProjectionMatrix());
    }

    public static void endScene() {
    }

    // --------------------------------------------------------------------------------------------
    // Primitives
    // --------------------------------------------------------------------------------------------

    public static void drawEntity(Entity entity) {
        mTextureShader.setFloat4("u_Color", new Vector4d(1.0f));
        entity.getTexture().bind(0);

        var position = entity.getPosition();
        Matrix4d scale = new Matrix4d().identity().scale(entity.getScale());
        Matrix4d transform = new Matrix4d().identity().translate(position).mul(scale);
        mTextureShader.setMat4("u_Transform", transform);

        entity.getVertexArray().bind();
        RenderCommand.drawIndexed(entity.getVertexArray());
    }
}
