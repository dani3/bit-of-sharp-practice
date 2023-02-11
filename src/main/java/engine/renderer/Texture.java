package engine.renderer;

import engine.core.Logger;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture implements AutoCloseable {

    private static final Logger mLogger = Logger.create(Texture.class.getName());

    private int mHeight;
    private int mWidth;

    private final int mRendererId;

    private int mInternalFormat;
    private int mDataFormat;

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

    public Texture(final String path) {
        IntBuffer width = ByteBuffer.allocateDirect(4).asIntBuffer();
        IntBuffer height = ByteBuffer.allocateDirect(4).asIntBuffer();
        IntBuffer channels = ByteBuffer.allocateDirect(4).asIntBuffer();

        stbi_set_flip_vertically_on_load(true);
        var data = stbi_load(path, width, height, channels, 0);

        if (data == null) {
            mLogger.error("Failed to load image");
            assert data != null;
        }

        mWidth = width.get();
        mHeight = height.get();

        int internalFormat = 0;
        int dataFormat = 0;
        if (channels.get() == 4) {
            internalFormat = GL_RGBA8;
            dataFormat = GL_RGBA;
        } else if (channels.get() == 3) {
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
