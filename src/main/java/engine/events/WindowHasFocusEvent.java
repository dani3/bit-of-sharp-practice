package engine.events;

public class WindowHasFocusEvent extends Event {

    public WindowHasFocusEvent() {
    }

    @Override
    public EventType getType() {
        return EventType.WindowHasFocus;
    }

    @Override
    public EventCategory getCategory() {
        return EventCategory.Application;
    }

    @Override
    public String toString() {
        return "WindowHasFocus:Application";
    }
}
