package engine.events;

public abstract class Event {

    public boolean mHandled;

    public abstract EventType getType();

    public abstract EventCategory getCategory();

    public boolean isCategory(EventCategory category) {
        return (category == getCategory());
    }
}
