package engine.entities;

import engine.renderer.Texture;
import engine.renderer.buffer.VertexArray;
import org.joml.Vector3d;

public class Entity {

    private final VertexArray mVertexArray;
    private final Texture mTexture;
    private final Vector3d mPosition;
    private final Vector3d mRotation;
    private final float mScale;

    public Entity(VertexArray model, Texture texture, Vector3d position, Vector3d rotation, float scale) {
        mVertexArray = model;
        mTexture = texture;
        mPosition = position;
        mRotation = rotation;
        mScale = scale;
    }

    public VertexArray getVertexArray() {
        return mVertexArray;
    }

    public Texture getTexture() {
        return mTexture;
    }

    public float getScale() {
        return mScale;
    }

    public Vector3d getPosition() {
        return mPosition;
    }

    public Vector3d getRotation() {
        return mRotation;
    }
}
