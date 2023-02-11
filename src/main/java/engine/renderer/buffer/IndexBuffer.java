package engine.renderer.buffer;

import static org.lwjgl.opengl.GL45.*;

public class IndexBuffer implements AutoCloseable {

    private final int mCount;
    private final int mRendererId;

    public IndexBuffer(int[] indices) {
        mCount = indices.length;
        mRendererId = glCreateBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mRendererId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mRendererId);
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getCount() {
        return mCount;
    }

    @Override
    public void close() {
        glDeleteBuffers(mRendererId);
    }
}
