package engine.renderer;

import engine.renderer.buffer.VertexArray;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector4d;

public class RenderCommand {

    public static void init() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
    }

    public static void setViewport(int x, int y, int width, int height) {
        glViewport(x, y, width, height);
    }

    public static void setClearColor(final Vector4d color) {
        glClearColor((float) color.x, (float) color.y, (float) color.z, (float) color.w);
    }

    public static void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void drawIndexed(final VertexArray vertexArray) {
        glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
