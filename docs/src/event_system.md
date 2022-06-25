# Event system

An **event system** is a way of notifying other components of the system about certain events that might happen, such as mouse clicks, window resizing, key presses, etc. withouth coupling the different components. For example, the `Application` will contain a `Window`, and the `Window` will have to notify about events that happen during execution. The naive and easy way of solving this by keeping a reference to the `Application` in the `Window` module. This is bad, a `Window` should not have the knowledge of the existence of an `Application`. With an event system however, `Window` will have a list of **subscribers** (`IEventListener` implementors) which will be notified once an event is triggered.

![Diagram](./diagrams/event_system.png)

To be notified about an event, a module will have to implement the following interface:

```java
interface IEventListener {
    public void onEvent(final Event event);
}
```

An example of this can be seen in [this chapter](./architecture.md).

## References

* [Planning the Event System | Game Engine series](https://www.youtube.com/watch?v=5mlziHwq90k&list=PLlrATfBNZ98dC-V-N3m0Go4deliWHPFwT&index=9)
* [Event System | Game Engine series](https://www.youtube.com/watch?v=xnopUoZbMEk&list=PLlrATfBNZ98dC-V-N3m0Go4deliWHPFwT&index=9)
