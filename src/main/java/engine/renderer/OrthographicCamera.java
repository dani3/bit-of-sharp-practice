package engine.renderer;

import org.joml.Math;
import org.joml.Matrix4d;
import org.joml.Vector3d;

public class OrthographicCamera {

    private Matrix4d mProjectionMatrix;
    private Matrix4d mViewMatrix;
    private Matrix4d mViewProjectionMatrix;

    private Vector3d mPosition = new Vector3d(0.0f, 0.0f, 0.0f);
    private float mRotation = 0.0f;

    public OrthographicCamera(float left, float right, float bottom, float top) {
        mProjectionMatrix = new Matrix4d().ortho(left, right, bottom, top, -1.0, 1.0);
        mViewMatrix = new Matrix4d().identity();
        mViewProjectionMatrix = mProjectionMatrix.mul(mViewMatrix);
    }

    public void setProjection(float left, float right, float bottom, float top) {
        mProjectionMatrix = new Matrix4d().ortho(left, right, bottom, top, -1.0, 1.0);
        mViewProjectionMatrix = mProjectionMatrix.mul(mViewMatrix);
    }

    public Vector3d getPosition() {
        return mPosition;
    }

    public float getRotation() {
        return mRotation;
    }

    public void setPosition(Vector3d position) {
        mPosition = position;
        recalculateViewMatrix();
    }

    public void setRotation(float rotation) {
        mRotation = rotation;
        recalculateViewMatrix();
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

    private void recalculateViewMatrix() {
        Matrix4d translation = new Matrix4d().identity().translate(mPosition);
        Matrix4d rotation = new Matrix4d().identity().rotate(Math.toRadians(mRotation), new Vector3d(0.0, 0.0, 1.0));
        Matrix4d transform = translation.mul(rotation);

        mViewMatrix = transform.invert();
        Matrix4d projectionMatrix = new Matrix4d(mProjectionMatrix);
        mViewProjectionMatrix = projectionMatrix.mul(mViewMatrix);
    }
}
