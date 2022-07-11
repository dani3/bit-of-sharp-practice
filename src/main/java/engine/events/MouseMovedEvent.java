package engine.events;

import java.text.MessageFormat;

public class MouseMovedEvent extends Event {

    private float mX;
    private float mY;

    public MouseMovedEvent(float x, float y) {
        mX = x;
        mY = y;
    }

    public float getX() {
        return mX;
    }

    public float getY() {
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
