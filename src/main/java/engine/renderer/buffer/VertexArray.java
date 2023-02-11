package engine.renderer.buffer;

import engine.renderer.shader.ShaderDataType;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL20.GL_BOOL;
import static org.lwjgl.opengl.GL45.*;

public class VertexArray implements AutoCloseable {

    private final List<VertexBuffer> mVertexBuffers;
    private IndexBuffer mIndexBuffer;
    private final int mRendererId;

    public VertexArray() {
        mVertexBuffers = new ArrayList<>();
        mRendererId = glGenVertexArrays();
    }

    public void bind() {
        glBindVertexArray(mRendererId);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    public void addVertexBuffer(VertexBuffer vertexBuffer) {
        assert vertexBuffer.getLayout().getElements().size() > 0;

        glBindVertexArray(mRendererId);
        vertexBuffer.bind();
        var index = 0;
        var layout = vertexBuffer.getLayout();
        for (var element : layout) {
            glEnableVertexAttribArray(index);
            glVertexAttribPointer(index, element.getComponentCount(), shaderDataTypeToOpenGLBaseType(element.mType),
                    element.mNormalized, layout.getStride(), element.mOffset);

            index++;
        }

        mVertexBuffers.add(vertexBuffer);
    }

    public void setIndexBuffer(IndexBuffer indexBuffer) {
        mIndexBuffer = indexBuffer;
    }

    public List<VertexBuffer> getVertexBuffers() {
        return mVertexBuffers;
    }

    public IndexBuffer getIndexBuffer() {
        return mIndexBuffer;
    }

    private int shaderDataTypeToOpenGLBaseType(ShaderDataType type) {
        return switch (type) {
            case Float, Float2, Float3, Mat3, Mat4 -> GL_FLOAT;
            case Int, Int2, Int3, Int4 -> GL_INT;
            case Bool -> GL_BOOL;
            default -> 0;
        };
    }

    @Override
    public void close() {
        glDeleteVertexArrays(mRendererId);
    }
}
