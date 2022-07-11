package engine.events;

import java.text.MessageFormat;

public class MouseButtonReleasedEvent extends MouseButtonEvent {

    public MouseButtonReleasedEvent(int button) {
        super(button);
    }

    @Override
    public EventType getType() {
        return EventType.MouseButtonReleased;
    }

    @Override
    public EventCategory getCategory() {
        return EventCategory.MouseButton;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "MouseButtonReleased:Mouse button={0}", super.getButton());
    }
}
