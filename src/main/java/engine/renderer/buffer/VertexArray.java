package engine.renderer.buffer;

import engine.renderer.shader.ShaderDataType;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL20.GL_BOOL;
import static org.lwjgl.opengl.GL45.*;

/**
 * Class that represents a raw model of a 2D/3D object.
 * It contains a list of VertexBuffer and an IndexBuffer.
 */
public class VertexArray implements AutoCloseable {

    private final List<VertexBuffer> mVertexBuffers;
    private IndexBuffer mIndexBuffer;
    private final int mRendererId;

    /**
     * Construct a new VertexArray object.
     */
    public VertexArray() {
        mVertexBuffers = new ArrayList<>();
        mRendererId = glGenVertexArrays();
    }

    /**
     * Bind the VertexArray.
     */
    public void bind() {
        glBindVertexArray(mRendererId);
    }

    /**
     * Unbind the VertexArray.
     */
    public void unbind() {
        glBindVertexArray(0);
    }

    /**
     * Add a VertexBuffer.
     *
     * @param vertexBuffer VertexBuffer object to add.
     */
    public void addVertexBuffer(VertexBuffer vertexBuffer) {
        assert !vertexBuffer.getLayout().getElements().isEmpty();

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

    /**
     * Set an IndexBuffer.
     *
     * @param indexBuffer IndexBuffer object to add.
     */
    public void setIndexBuffer(IndexBuffer indexBuffer) {
        mIndexBuffer = indexBuffer;
    }

    /**
     * Get the list of VertexBuffers.
     * @return List of VertexBuffers.
     */
    public List<VertexBuffer> getVertexBuffers() {
        return mVertexBuffers;
    }

    /**
     * Get the IndexBuffer.
     * @return IndexBuffer.
     */
    public IndexBuffer getIndexBuffer() {
        return mIndexBuffer;
    }

    @Override
    public void close() {
        glDeleteVertexArrays(mRendererId);
    }

    private int shaderDataTypeToOpenGLBaseType(ShaderDataType type) {
        return switch (type) {
            case Float, Float2, Float3, Mat3, Mat4 -> GL_FLOAT;
            case Int, Int2, Int3, Int4 -> GL_INT;
            case Bool -> GL_BOOL;
            default -> 0;
        };
    }
}
