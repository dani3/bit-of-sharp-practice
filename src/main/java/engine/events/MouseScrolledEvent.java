package engine.events;

import java.text.MessageFormat;

public class MouseScrolledEvent extends Event {

    private float mXOffset;
    private float mYOffset;

    public MouseScrolledEvent(float xOffset, float yOffset) {
        mXOffset = xOffset;
        mYOffset = yOffset;
    }

    public float getXOffset() {
        return mXOffset;
    }

    public float getYOffset() {
        return mYOffset;
    }

    @Override
    public EventType getType() {
        return EventType.MouseScrolled;
    }

    @Override
    public EventCategory getCategory() {
        return EventCategory.Mouse;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "MouseScrolled:Mouse xOffset={0}, yOffset={1}", getXOffset(), getYOffset());
    }
}
