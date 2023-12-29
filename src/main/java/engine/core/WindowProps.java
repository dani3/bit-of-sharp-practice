package engine.core;

public class WindowProps {

    private final String mTitle;
    private final int mWidth;
    private final int mHeight;

    public WindowProps(String title, int width, int height) {
        mTitle = title;
        mWidth = width;
        mHeight = height;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }
}
