package engine.core;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.BufferUtils;

public class Input {

    public static boolean isKeyPressed(int keyCode) {
        var window = Application.get().getWindow().getNativeWindow();
        var state = glfwGetKey(window, keyCode);
        return state == GLFW_PRESS || state == GLFW_REPEAT;
    }

    public static boolean isMouseButtonPressed(int button) {
        var window = Application.get().getWindow().getNativeWindow();
        var state = glfwGetMouseButton(window, button);
        return state == GLFW_PRESS;
    }

    public static float getMouseX() {
        var window = Application.get().getWindow().getNativeWindow();
        var xpos = BufferUtils.createDoubleBuffer(1);
        var ypos = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, xpos, ypos);
        return (float) xpos.get();
    }

    public static float getMouseY() {
        var window = Application.get().getWindow().getNativeWindow();
        var xpos = BufferUtils.createDoubleBuffer(1);
        var ypos = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, xpos, ypos);
        return (float) ypos.get();
    }
}
