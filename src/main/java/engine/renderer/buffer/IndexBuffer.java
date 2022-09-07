package engine.renderer.buffer;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL45.*;

public class IndexBuffer implements AutoCloseable {

    private final int mCount;
    private final IntBuffer mRendererId;

    public IndexBuffer(int[] indices) {
        mCount = indices.length;
        mRendererId = IntBuffer.allocate(1);
        glCreateBuffers(mRendererId);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mRendererId.get());
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mRendererId.get());
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getCount() {
        return mCount;
    }

    @Override
    public void close() {
        glDeleteBuffers(mRendererId.get());
    }
}
