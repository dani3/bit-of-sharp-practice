package engine.renderer.shader;

public enum ShaderDataType {
    None,
    Float,
    Float2,
    Float3,
    Float4,
    Mat3,
    Mat4,
    Int,
    Int2,
    Int3,
    Int4,
    Bool;

    public static int getSize(ShaderDataType type) {
        switch (type) {
            case Float:
                return 4;
            case Float2:
                return 4 * 2;
            case Float3:
                return 4 * 3;
            case Float4:
                return 4 * 4;
            case Mat3:
                return 4 * 3 * 3;
            case Mat4:
                return 4 * 4 * 4;
            case Int:
                return 4;
            case Int2:
                return 4 * 2;
            case Int3:
                return 4 * 3;
            case Int4:
                return 4 * 4;
            case Bool:
                return 1;
            default:
                return 0;
        }
    }
}
