package engine.events;

public class WindowLostFocusEvent extends Event {

    public WindowLostFocusEvent() {
    }

    @Override
    public EventType getType() {
        return EventType.WindowLostFocus;
    }

    @Override
    public EventCategory getCategory() {
        return EventCategory.Application;
    }

    @Override
    public String toString() {
        return "WindowLostFocus:Application";
    }
}
