package engine.renderer.shader;

/**
 * Enum that abstracts the different GLSL types.
 */
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

    /**
     * Return the size of a given GLSL type.
     *
     * @return size in bytes of the given GLSL type.
     */
    public static int getSize(ShaderDataType type) {
        return switch (type) {
            case Float, Int -> 4;
            case Float2, Int2 -> 4 * 2;
            case Float3, Int3 -> 4 * 3;
            case Float4, Int4 -> 4 * 4;
            case Mat3 -> 4 * 3 * 3;
            case Mat4 -> 4 * 4 * 4;
            case Bool -> 1;
            default -> 0;
        };
    }
}
