package engine.core;

public class Timestep {

    private float mTime;

    public Timestep() {
        mTime = 0.0f;
    }

    public Timestep(float time) {
        mTime = time;
    }

    public float getSeconds() {
        return mTime;
    }

    public float getMilliseconds() {
        return mTime * 1000.0f;
    }
}
