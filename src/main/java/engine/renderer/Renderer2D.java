package engine.renderer;

import engine.core.Logger;
import engine.renderer.buffer.*;
import engine.renderer.shader.Shader;
import engine.renderer.shader.ShaderDataType;
import org.joml.Matrix4d;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

public class Renderer2D {

    private static final Logger mLogger = Logger.create(Renderer2D.class.getName());

    private static VertexArray mQuadVertexArray;
    private static Shader mTextureShader;
    private static Texture mWhiteTexture;

    public static void init() {
        mQuadVertexArray = new VertexArray();

        float[] squareVertices = {
                -0.5f, -0.5f, 0.0f, 0.0f, 0.0f,
                0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
                0.5f, 0.5f, 0.0f, 1.0f, 1.0f,
                -0.5f, 0.5f, 0.0f, 0.0f, 1.0f
        };

        VertexBuffer squareVertexBuffer = new VertexBuffer(squareVertices);
        BufferElement[] elements = {
                new BufferElement("a_Position", ShaderDataType.Float3),
                new BufferElement("a_TexCoord", ShaderDataType.Float2)};
        BufferLayout squareLayout = new BufferLayout(Arrays.asList(elements));
        squareVertexBuffer.setLayout(squareLayout);

        mQuadVertexArray.addVertexBuffer(squareVertexBuffer);
        int[] squareIndices = {0, 1, 2, 2, 3, 0};
        IndexBuffer squareIndexBuffer = new IndexBuffer(squareIndices);
        mQuadVertexArray.setIndexBuffer(squareIndexBuffer);

        mWhiteTexture = new Texture(1, 1);
        byte[] buffer = {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        ByteBuffer whiteTextureData = ByteBuffer.wrap(buffer);
        mWhiteTexture.setData(whiteTextureData);

        var shaderPath = Objects.requireNonNull(
                Objects.requireNonNull(
                        Renderer2D.class.getClassLoader().getResource("shaders/Texture.glsl")).getPath());
        mTextureShader = new Shader(shaderPath);
        mTextureShader.bind();
        mTextureShader.setInt("u_Texture", 0);
    }

    public static void shutdown() {
        // TODO
    }

    public static void beginScene(final OrthographicCamera camera) {
        mTextureShader.bind();
        mTextureShader.setMat4("u_ViewProjection", camera.getViewProjectionMatrix());
    }

    public static void endScene() {
    }

    // --------------------------------------------------------------------------------------------
    // Primitives
    // --------------------------------------------------------------------------------------------

    public static void drawQuad(final Vector2d position, final Vector2d size, final Vector4d color) {
        Renderer2D.drawQuad(new Vector3d(position.x, position.y, 0.0f), size, color);
    }

    public static void drawQuad(final Vector3d position, final Vector2d size, final Vector4d color) {
        mTextureShader.setFloat4("u_Color", new Vector4d(1.0f));
        mWhiteTexture.bind(0);

        Matrix4d scale = new Matrix4d().identity().scale(new Vector3d(position.x, position.y, 1.0f));
        Matrix4d transform = new Matrix4d().identity().translate(position).mul(scale);
        mTextureShader.setMat4("u_Transform", transform);

        mQuadVertexArray.bind();
        RenderCommand.drawIndexed(mQuadVertexArray);
    }

    public static void drawQuad(final Vector2d position, final Vector2d size, final Texture texture) {
        Renderer2D.drawQuad(new Vector3d(position.x, position.y, 0.0f), size, texture);
    }

    public static void drawQuad(final Vector3d position, final Vector2d size, final Texture texture) {
        mTextureShader.setFloat4("u_Color", new Vector4d(1.0f));
        texture.bind(0);

        Matrix4d scale = new Matrix4d().identity().scale(new Vector3d(position.x, position.y, 1.0f));
        Matrix4d transform = new Matrix4d().identity().translate(position).mul(scale);
        mTextureShader.setMat4("u_Transform", transform);

        mQuadVertexArray.bind();
        RenderCommand.drawIndexed(mQuadVertexArray);
    }
}
