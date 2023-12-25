package engine.renderer;

import engine.core.Logger;
import engine.events.Event;
import engine.events.IEventListener;
import engine.events.WindowResizedEvent;
import engine.renderer.buffer.VertexArray;
import engine.renderer.shader.Shader;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4d;

public class Renderer implements IEventListener, AutoCloseable {

    private static final Logger mLogger = Logger.create(Renderer.class.getName());

    private Matrix4d mViewProjectionMatrix;

    public Renderer() {
        RenderCommand.init();
        Renderer3D.init();
    }

    public void beginScene(final @NotNull OrthographicCamera camera) {
        mViewProjectionMatrix = camera.getViewProjectionMatrix();
    }

    public void endScene() {
        // TODO
    }

    public void submit(
            final @NotNull Shader shader, final @NotNull VertexArray vertexArray, final Matrix4d transform) {
        shader.bind();
        shader.setMat4("u_ViewProjection", mViewProjectionMatrix);
        shader.setMat4("u_Transform", transform);

        vertexArray.bind();
        RenderCommand.drawIndexed(vertexArray);
    }

    @Override
    public void close() throws Exception {
        Renderer3D.shutdown();
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof WindowResizedEvent e) {
            onWindowResized(e);
        }
    }

    private void onWindowResized(final @NotNull WindowResizedEvent e) {
        RenderCommand.setViewport(0, 0, e.getWidth(), e.getHeight());
    }
}
