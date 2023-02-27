package engine.renderer;

import org.joml.Vector3d;

import engine.core.Input;
import engine.core.KeyCodes;
import engine.core.Timestep;
import engine.events.Event;
import engine.events.IEventListener;
import engine.events.MouseScrolledEvent;
import engine.events.WindowResizedEvent;

public class OthographicCameraController implements IEventListener {

    private float mZoomLevel;
    private float mAspectRatio;

    private boolean mRotation;
    private OrthographicCamera mCamera;

    private Vector3d mCameraPosition;
    private float mCameraRotation;

    private float mCameraTranslationSpeed;
    private float mCameraRotationSpeed;

    public OthographicCameraController(float aspectRatio) {
        this(aspectRatio, false);
    }

    public OthographicCameraController(float aspectRatio, boolean rotation) {
        mAspectRatio = aspectRatio;
        mRotation = rotation;

        mZoomLevel = 1.0f;
        mCameraPosition = new Vector3d();
        mCameraTranslationSpeed = 2.0f;
        mCameraRotationSpeed = 180.0f;

        mCamera = new OrthographicCamera(
                -mAspectRatio * mZoomLevel, mAspectRatio * mZoomLevel, -mZoomLevel, mZoomLevel);
    }

    public void onUpdate(Timestep ts) {
        if (Input.isKeyPressed(KeyCodes.KEY_A)) {
            mCameraPosition.x -= mCameraTranslationSpeed * ts.getSeconds();
        } else if (Input.isKeyPressed(KeyCodes.KEY_D)) {
            mCameraPosition.x += mCameraTranslationSpeed * ts.getSeconds();
        }

        if (Input.isKeyPressed(KeyCodes.KEY_W)) {
            mCameraPosition.y -= mCameraTranslationSpeed * ts.getSeconds();
        } else if (Input.isKeyPressed(KeyCodes.KEY_S)) {
            mCameraPosition.y -= mCameraTranslationSpeed * ts.getSeconds();
        }

        if (mRotation) {
            if (Input.isKeyPressed(KeyCodes.KEY_Q)) {
                mCameraRotation += mCameraRotationSpeed * ts.getSeconds();
            } else if (Input.isKeyPressed(KeyCodes.KEY_E)) {
                mCameraRotation -= mCameraRotationSpeed * ts.getSeconds();
            }

            mCamera.setRotation(mCameraRotation);
            mCameraTranslationSpeed = mZoomLevel;
        }

        mCamera.setPosition(mCameraPosition);
    }

    public OrthographicCamera getCamera() {
        return mCamera;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof MouseScrolledEvent e) {
            onMouseScrolled(e);
        } else if (event instanceof WindowResizedEvent e) {
            onWindowResized(e);
        }
    }

    private void onMouseScrolled(MouseScrolledEvent e) {
        mZoomLevel -= e.getYOffset() * 0.25f;
        mZoomLevel = Math.max(mZoomLevel, 0.25f);
        mCamera.setProjection(
                -mAspectRatio * mZoomLevel, mAspectRatio * mZoomLevel, -mZoomLevel, mZoomLevel);
    }

    private void onWindowResized(WindowResizedEvent e) {
        mAspectRatio = (float) e.getWidth() / (float) e.getHeight();
        mCamera.setProjection(
                -mAspectRatio * mZoomLevel, mAspectRatio * mZoomLevel, -mZoomLevel, mZoomLevel);
    }
}
