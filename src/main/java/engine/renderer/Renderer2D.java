package engine.renderer;

import engine.core.Logger;
import engine.renderer.buffer.BufferElement;
import engine.renderer.buffer.BufferLayout;
import engine.renderer.buffer.IndexBuffer;
import engine.renderer.buffer.VertexArray;
import engine.renderer.buffer.VertexBuffer;
import engine.renderer.shader.Shader;
import engine.renderer.shader.ShaderDataType;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;

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
                new BufferElement("a_TexCoord", ShaderDataType.Float2) };
        BufferLayout squareLayout = new BufferLayout(Arrays.asList(elements));
        squareVertexBuffer.setLayout(squareLayout);

        mQuadVertexArray.addVertexBuffer(squareVertexBuffer);
        int[] squareIndices = { 0, 1, 2, 2, 3, 0 };
        IndexBuffer squareIndexBuffer = new IndexBuffer(squareIndices);
        mQuadVertexArray.setIndexBuffer(squareIndexBuffer);

        mWhiteTexture = new Texture(1, 1);
        byte[] buffer = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
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

    public static void drawQuad(
            final Vector2d position, final Vector2d size, final Vector4d color) {
        // TODO
    }

    public static void drawQuad(
            final Vector3d position, final Vector2d size, final Vector4d color) {
        // TODO
    }
}
