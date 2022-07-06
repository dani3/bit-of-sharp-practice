package engine.events;

public class WindowClosedEvent extends Event {

    public WindowClosedEvent() {
    }

    @Override
    public EventType getType() {
        return EventType.WindowClosed;
    }

    @Override
    public EventCategory getCategory() {
        return EventCategory.Application;
    }

    @Override
    public String toString() {
        return "WindowClosedEvent:Application";
    }
}
