package engine.renderer;

import engine.core.Logger;
import engine.renderer.buffer.*;
import engine.renderer.shader.Shader;
import engine.renderer.shader.ShaderDataType;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4d;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Renderer2D {

    private static final Logger mLogger = Logger.create(Renderer2D.class.getName());

    private static VertexArray mQuadVertexArray;
    private static Shader mTextureShader;

    public static void init() {
        mQuadVertexArray = new VertexArray();

        float[] squareVertices = {
                // Positions         // Texture coords
                -0.5f, -0.5f, 0.0f,   1.0f, 1.0f,
                 0.5f, -0.5f, 0.0f,   1.0f, 0.0f,
                 0.5f,  0.5f, 0.0f,   0.0f, 0.0f,
                -0.5f,  0.5f, 0.0f,   0.0f, 1.0f,
        };

        var squareVertexBuffer = new VertexBuffer(squareVertices);
        BufferElement[] elements = {
                new BufferElement("a_Position", ShaderDataType.Float3),
                new BufferElement("a_TexCoord", ShaderDataType.Float2),
        };
        var squareLayout = new BufferLayout(Arrays.asList(elements));
        squareVertexBuffer.setLayout(squareLayout);

        mQuadVertexArray.addVertexBuffer(squareVertexBuffer);
        int[] squareIndices = { 0, 1, 2, 2, 3, 0 };
        var squareIndexBuffer = new IndexBuffer(squareIndices);
        mQuadVertexArray.setIndexBuffer(squareIndexBuffer);

        var shaderPath = Objects.requireNonNull(
                Objects.requireNonNull(
                        Renderer2D.class.getClassLoader().getResource("shaders/Texture.glsl")).getPath());
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

    public static void drawQuad(final @NotNull Vector2d position, final Vector2d size, final Vector4d color) {
        Renderer2D.drawQuad(new Vector3d(position.x, position.y, 0.0f), size, color);
    }

    public static void drawQuad(final @NotNull Vector3d position, final Vector2d size, final Vector4d color) {
        mTextureShader.setFloat4("u_Color", new Vector4d(1.0f));

        Matrix4d scale = new Matrix4d().identity().scale(new Vector3d(position.x, position.y, 1.0f));
        Matrix4d transform = new Matrix4d().identity().translate(position).mul(scale);
        mTextureShader.setMat4("u_Transform", transform);

        mQuadVertexArray.bind();
        RenderCommand.drawIndexed(mQuadVertexArray);
    }

    public static void drawQuad(final @NotNull Vector2d position, final Vector2d size, final Texture texture) {
        Renderer2D.drawQuad(new Vector3d(position.x, position.y, 0.0f), size, texture);
    }

    public static void drawQuad(final @NotNull Vector3d position, final Vector2d size, final @NotNull Texture texture) {
        mTextureShader.setFloat4("u_Color", new Vector4d(1.0f));
        texture.bind(0);

        Matrix4d scale = new Matrix4d().identity().scale(new Vector3d(position.x, position.y, 1.0f));
        Matrix4d transform = new Matrix4d().identity().translate(position).mul(scale);
        mTextureShader.setMat4("u_Transform", transform);

        mQuadVertexArray.bind();
        RenderCommand.drawIndexed(mQuadVertexArray);
    }
}
