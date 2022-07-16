package engine.renderer.buffer;

import engine.renderer.shader.ShaderDataType;

public class BufferElement {

    String mName;
    ShaderDataType mType;
    int mSize;
    int mOffset;
    boolean mNormalized;

    public BufferElement(String name, ShaderDataType type) {
        this(name, type, false);
    }

    public BufferElement(String name, ShaderDataType type, boolean normalized) {
        mName = name;
        mType = type;
        mSize = ShaderDataType.getSize(type);
        mOffset = 0;
        mNormalized = normalized;
    }

    public int getComponentCount() {
        switch (mType) {
            case Float:
                return 1;
            case Float2:
                return 2;
            case Float3:
                return 3;
            case Float4:
                return 4;
            case Mat3:
                return 3 * 3;
            case Mat4:
                return 4 * 4;
            case Int:
                return 1;
            case Int2:
                return 2;
            case Int3:
                return 3;
            case Int4:
                return 4;
            case Bool:
                return 1;
            default:
                return 0;
        }
    }
}
