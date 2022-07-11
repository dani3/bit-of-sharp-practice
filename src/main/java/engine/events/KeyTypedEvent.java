package engine.events;

import java.text.MessageFormat;

public class KeyTypedEvent extends KeyEvent {

    public KeyTypedEvent(int keyCode) {
        super(keyCode);
    }

    @Override
    public EventType getType() {
        return EventType.KeyTyped;
    }

    @Override
    public EventCategory getCategory() {
        return EventCategory.Keyboard;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "KeyTypedEvent:Keyboard keycode={0}", super.getKeyCode());
    }
}
