package engine.renderer.buffer;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL45.*;

public class VertexBuffer implements AutoCloseable {

    private BufferLayout mLayout;
    private final IntBuffer mRendererId;

    public VertexBuffer(float[] vertices) {
        mRendererId = IntBuffer.allocate(1);
        glCreateBuffers(mRendererId);
        glBindBuffer(GL_ARRAY_BUFFER, mRendererId.get());
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, mRendererId.get());
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void setLayout(BufferLayout layout) {
        mLayout = layout;
    }

    public BufferLayout getLayout() {
        return mLayout;
    }

    @Override
    public void close() {
        glDeleteBuffers(mRendererId.get());
    }
}
