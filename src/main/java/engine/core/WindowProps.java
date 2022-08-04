package engine.core;

public class WindowProps {

    private String mTitle;
    private int mWidth;
    private int mHeight;

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
