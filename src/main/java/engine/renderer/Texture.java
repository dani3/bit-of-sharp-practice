package engine.renderer;

import engine.core.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture implements AutoCloseable {

    private static final Logger mLogger = Logger.create(Texture.class.getName());

    private final int mHeight;
    private final int mWidth;

    private final int mRendererId;

    private final int mInternalFormat;
    private final int mDataFormat;

    public Texture(int width, int height) {
        mInternalFormat = GL_RGBA8;
        mDataFormat = GL_RGBA;

        mWidth = width;
        mHeight = height;

        mRendererId = glCreateTextures(GL_TEXTURE_2D);
        glTextureStorage2D(mRendererId, 1, mInternalFormat, mWidth, mHeight);

        glTextureParameteri(mRendererId, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTextureParameteri(mRendererId, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTextureParameteri(mRendererId, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTextureParameteri(mRendererId, GL_TEXTURE_WRAP_T, GL_REPEAT);
    }

    public Texture(final String path) throws IOException {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(true);
        var data = stbi_load(path, width, height, channels, STBI_rgb_alpha);

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

        mInternalFormat = internalFormat;
        mDataFormat = dataFormat;

        mRendererId = glCreateTextures(GL_TEXTURE_2D);
        glTextureStorage2D(mRendererId, 1, mInternalFormat, mWidth, mHeight);

        glTextureParameteri(mRendererId, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTextureParameteri(mRendererId, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTextureParameteri(mRendererId, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTextureParameteri(mRendererId, GL_TEXTURE_WRAP_T, GL_REPEAT);

        glTextureSubImage2D(
                mRendererId, 0, 0, 0, mWidth, mHeight, mDataFormat, GL_UNSIGNED_BYTE, data);

        stbi_image_free(data);
    }

    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    @Override
    public void close() throws Exception {
        glDeleteTextures(mRendererId);
    }

    public void setData(ByteBuffer data) {
        int bpp = (mDataFormat == GL_RGBA) ? 4 : 3;

        if (data.capacity() < mWidth * mHeight * bpp) {
            mLogger.error("Data must be entire texture");
            assert false;
        }

        glTextureSubImage2D(
                mRendererId, 0, 0, 0, mWidth, mHeight, mDataFormat, GL_UNSIGNED_BYTE, data);
    }

    public void bind(int slot) {
        glBindTextureUnit(slot, mRendererId);
    }
}
