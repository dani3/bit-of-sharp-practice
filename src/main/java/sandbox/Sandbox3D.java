package sandbox;

import org.joml.Vector2d;
import org.joml.Vector4d;

import engine.core.Layer;
import engine.core.Timestep;
import engine.events.Event;
import engine.renderer.OthographicCameraController;
import engine.renderer.RenderCommand;
import engine.renderer.Renderer2D;

public class Sandbox3D extends Layer {

    private OthographicCameraController mCameraController;

    public Sandbox3D() {
        super("Sandbox3D");
        mCameraController = new OthographicCameraController(1280.f / 720.f);
    }

    @Override
    public void onAttach() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onDettach() {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpdate(Timestep ts) {
        mCameraController.onUpdate(ts);

        RenderCommand.setClearColor(new Vector4d(0.1f, 0.1f, 0.1f, 1));
        RenderCommand.clear();

        Renderer2D.beginScene(mCameraController.getCamera());
        Renderer2D.drawQuad(new Vector2d(0.5f, 0.5f), new Vector2d(0.5f, 1.0f), new Vector4d(0.1f, 0.9f, 0.3f, 1.0f));
        Renderer2D.endScene();
    }

    @Override
    public void onEvent(Event event) {
        mCameraController.onEvent(event);
    }
}
