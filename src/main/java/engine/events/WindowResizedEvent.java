package engine.events;

import java.text.MessageFormat;

public class WindowResizedEvent extends Event {

    private int mHeight;
    private int mWidth;

    public WindowResizedEvent(int height, int width) {
        mHeight = height;
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    @Override
    public EventType getType() {
        return EventType.WindowResized;
    }

    @Override
    public EventCategory getCategory() {
        return EventCategory.Application;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "WindowResizedEvent:Application height={0}, width={1}", mHeight, mWidth);
    }
}
