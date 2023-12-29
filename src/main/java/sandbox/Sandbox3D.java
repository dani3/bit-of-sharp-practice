package sandbox;

import engine.core.Logger;
import engine.entities.Entity;
import engine.renderer.Texture;
import engine.renderer.buffer.*;
import engine.renderer.shader.ShaderDataType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector4d;

import engine.core.Layer;
import engine.core.Timestep;
import engine.events.Event;
import engine.renderer.OrthographicCameraController;
import engine.renderer.RenderCommand;
import engine.renderer.Renderer3D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sandbox3D extends Layer {

    private static final Logger mLogger = Logger.create(Sandbox3D.class.getName());

    private final OrthographicCameraController mCameraController;
    private final List<Entity> mEntities;

    public Sandbox3D() {
        super("Sandbox3D");

        mCameraController = new OrthographicCameraController(1280.f / 720.f);
        mEntities = new ArrayList<>();
    }

    @Override
    public void onAttach() {
        try {
            var quad = generateQuad();
            int[] squareIndices = { 0, 1, 2, 2, 3, 0 };
            var squareIndexBuffer = new IndexBuffer(squareIndices);
            quad.setIndexBuffer(squareIndexBuffer);

            var texture = new Texture("Checkerboard.png");
            var entity = new Entity(quad, texture, new Vector3d(0.5f, 0.5f, 1.0f), new Vector3d(0.0f, 0.0f,0.0f), 1.0f);

            mEntities.add(entity);

        } catch (IOException e) {
            mLogger.error("Failed to load texture");
            assert(false);
        }
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpdate(Timestep ts) {
        mCameraController.onUpdate(ts);

        RenderCommand.setClearColor(new Vector4d(0.1f, 0.1f, 0.1f, 1));
        RenderCommand.clear();

        Renderer3D.beginScene(mCameraController.getCamera());
        for (var entity : mEntities) {
            Renderer3D.drawEntity(entity);
        }
        Renderer3D.endScene();
    }

    @Override
    public void onEvent(Event event) {
        mCameraController.onEvent(event);
    }

    @NotNull
    private static VertexArray generateQuad() {
        var quad = new VertexArray();

        float[] squareVertices = {
                // Positions          // Texture coords
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

        quad.addVertexBuffer(squareVertexBuffer);
        return quad;
    }
}
