package sandbox;

import engine.core.Logger;
import engine.renderer.Texture;
import org.joml.Vector2d;
import org.joml.Vector4d;

import engine.core.Layer;
import engine.core.Timestep;
import engine.events.Event;
import engine.renderer.OthographicCameraController;
import engine.renderer.RenderCommand;
import engine.renderer.Renderer2D;

import java.io.IOException;
import java.util.Objects;

public class Sandbox3D extends Layer {

    private static final Logger mLogger = Logger.create(Sandbox3D.class.getName());

    private final OthographicCameraController mCameraController;

    private static Texture mTexture;

    public Sandbox3D() {
        super("Sandbox3D");
        mCameraController = new OthographicCameraController(1280.f / 720.f);
    }

    @Override
    public void onAttach() {
        try {
            mTexture = new Texture(
                    Objects.requireNonNull(
                            Renderer2D.class.getClassLoader().getResource("textures/Checkerboard.png")).getPath().replaceFirst("/", ""));
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

        Renderer2D.beginScene(mCameraController.getCamera());
        Renderer2D.drawQuad(new Vector2d(0.5f, 0.5f), new Vector2d(0.5f, 1.0f), mTexture);
        Renderer2D.endScene();
    }

    @Override
    public void onEvent(Event event) {
        mCameraController.onEvent(event);
    }
}
