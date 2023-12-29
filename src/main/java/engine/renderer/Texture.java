package engine.renderer;

import engine.core.FileUtils;
import engine.core.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.*;

/**
 * Class that represents a texture.
 */
public class Texture implements AutoCloseable {

    private static final Logger mLogger = Logger.create(Texture.class.getName());

    private final int mRendererId;

    private final int mHeight;
    private final int mWidth;
    private final int mDataFormat;

    /**
     * Construct a new Texture given its filename.
     *
     * @param filename name of the file.
     * @throws IOException Thrown when an error occurs when loading the texture.
     */
    public Texture(final String filename) throws IOException {
        var path = FileUtils.getResourcePath(FileUtils.RESOURCE_TYPE_TEXTURE, filename);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(true);
        var data = stbi_load(path, width, height, channels, 0);

        if (data == null) {
            mLogger.error("Failed to load image");
            throw new IOException(STBImage.stbi_failure_reason());
        }

        mWidth = width.get();
        mHeight = height.get();

        int internalFormat = 0;
        int num_channels = channels.get(0);
        int dataFormat = 0;
        if (num_channels == 4) {
            internalFormat = GL_RGBA8;
            dataFormat = GL_RGBA;
        } else if (num_channels == 3) {
            internalFormat = GL_RGB8;
            dataFormat = GL_RGB;
        }

        mDataFormat = dataFormat;

        mRendererId = glCreateTextures(GL_TEXTURE_2D);
        glTextureStorage2D(mRendererId, 1, internalFormat, mWidth, mHeight);

        glTextureParameteri(mRendererId, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTextureParameteri(mRendererId, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTextureParameteri(mRendererId, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTextureParameteri(mRendererId, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTextureSubImage2D(
                mRendererId, 0, 0, 0, mWidth, mHeight, mDataFormat, GL_UNSIGNED_BYTE, data);

        stbi_image_free(data);
    }

    /**
     * Get the height of the texture.
     * @return Height of the texture.
     */
    public int getHeight() {
        return mHeight;
    }

    /**
     * Get the width of the texture.
     * @return width of the texture.
     */
    public int getWidth() {
        return mWidth;
    }

    @Override
    public void close() throws Exception {
        glDeleteTextures(mRendererId);
    }

    /**
     * Bind the texture.
     * @param slot OpenGL slot to bind the texture to,
     */
    public void bind(int slot) {
        glBindTextureUnit(slot, mRendererId);
    }
}
