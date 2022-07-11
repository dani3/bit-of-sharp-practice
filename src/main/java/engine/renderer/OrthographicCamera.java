package engine.renderer;

import org.joml.Matrix4d;
import org.joml.Vector3d;

public class OrthographicCamera {

    private Matrix4d mProjectionMatrix;
    private Matrix4d mViewMatrix;
    private Matrix4d mViewProjectionMatrix;

    private Vector3d mPosition = new Vector3d(0.0f, 0.0f, 0.0f);
    private float mRotation = 0.0f;

    public OrthographicCamera(float left, float right, float bottom, float top) {
        // TODO
    }

    public void setProjection(float left, float right, float bottom, float top) {
        // TODO
    }

    public Vector3d getPosition() {
        return mPosition;
    }

    public float getRotation() {
        return mRotation;
    }

    public void setPosition(Vector3d position) {
        mPosition = position;
    }

    public void setRotation(float rotation) {
        mRotation = rotation;
    }

    public Matrix4d getProjectionMatrix() {
        return mProjectionMatrix;
    }

    public Matrix4d getViewMatrix() {
        return mViewMatrix;
    }

    public Matrix4d getViewProjectionMatrix() {
        return mViewProjectionMatrix;
    }
}
