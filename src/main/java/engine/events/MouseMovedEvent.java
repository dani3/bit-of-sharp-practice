package engine.events;

import java.text.MessageFormat;

public class MouseMovedEvent extends Event {

    private int mX;
    private int mY;

    public MouseMovedEvent(int x, int y) {
        mX = x;
        mY = y;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    @Override
    public EventType getType() {
        return EventType.MouseMoved;
    }

    @Override
    public EventCategory getCategory() {
        return EventCategory.Mouse;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "MouseMoved:Mouse x={0}, y={1}", getX(), getY());
    }
}
