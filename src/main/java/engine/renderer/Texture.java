package engine.renderer;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL45.*;

public class Texture implements AutoCloseable {

    private String mPath;

    private int mHeight;
    private int mWidth;

    private IntBuffer mRendererId;

    private int mInternalFormat;
    private int mDataFormat;

    public Texture(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public Texture(final String path) {
        mPath = path;
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
}
