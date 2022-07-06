package engine.events;

import java.text.MessageFormat;

public class MouseButtonPressedEvent extends MouseButtonEvent {

    public MouseButtonPressedEvent(MouseButton button) {
        super(button);
    }

    @Override
    public EventType getType() {
        return EventType.MouseButtonPressed;
    }

    @Override
    public EventCategory getCategory() {
        return EventCategory.MouseButton;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "MouseButtonPressed:Mouse button={0}", super.getButton());
    }
}
