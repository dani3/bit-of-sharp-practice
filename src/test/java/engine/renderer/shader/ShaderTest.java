package engine.renderer.shader;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShaderTest {

    @Test
    void getName() {
        var shaderPath = Objects.requireNonNull(
                Objects.requireNonNull(
                        ShaderTest.class.getClassLoader().getResource("shaders/OkTexture.glsl")).getPath());

        var shader = new Shader(shaderPath);
        assertEquals("OkTexture", shader.getName());
    }
}
