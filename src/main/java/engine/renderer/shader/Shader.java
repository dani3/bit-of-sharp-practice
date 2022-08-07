package engine.renderer.shader;

import engine.core.Logger;
import org.joml.Matrix4d;
import org.joml.Vector3d;
import org.joml.Vector4d;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader implements AutoCloseable {

    private static final Logger mLogger = Logger.create(Shader.class.getName());

    // Name of the shader.
    private final String mName;
    // Id used to reference the shader.
    private IntBuffer mRendererId;

    // Map that contains all the uniforms.
    private Map<String, Integer> mUniformLocationCache;

    /**
     * Construct a shader given the path to the shaders source.
     *
     * @param filepath path to the shaders source.
     */
    public Shader(final String filepath) {
        var name = new File(filepath).getName();
        name = name.substring(0, name.indexOf("."));
        mName = name;

        var source = readFile(filepath);
        var shaderSources = preprocess(source);
        compile(shaderSources);
    }

    /**
     * Construct a shader given the source of the vertex and fragment shader.
     *
     * @param name        name of the shader.
     * @param vertexSrc   source of the vertex shader.
     * @param fragmentSrc source of the fragment shader.
     */
    public Shader(final String name, final String vertexSrc, final String fragmentSrc) {
        mName = name;

        Map<Integer, String> shaders = new HashMap<>();
        shaders.put(GL_VERTEX_SHADER, vertexSrc);
        shaders.put(GL_FRAGMENT_SHADER, fragmentSrc);
        compile(shaders);
    }

    @Override
    public void close() throws Exception {
        glDeleteProgram(mRendererId.get());
    }

    /**
     * Bind the shader.
     */
    public void bind() {
        glUseProgram(mRendererId.get());
    }

    /**
     * Unbind the shader.
     */
    public void unbind() {
        glUseProgram(0);
    }

    public void setInt(final String name, int value) {
        uploadUniformInt(name, value);
    }

    public void setFloat3(final String name, final Vector3d value) {
        uploadUniformFloat3(name, value);
    }

    public void setFloat4(final String name, final Vector4d value) {
        uploadUniformFloat4(name, value);
    }

    public void setMat4(final String name, final Matrix4d value) {
        uploadUniformMat4(name, value);
    }

    public void uploadUniformInt(final String name, int value) {
        int location = getUniformLocation(name);
        glUniform1i(location, value);
    }

    public void uploadUniformFloat3(final String name, final Vector3d value) {
        int location = getUniformLocation(name);
        glUniform3f(location, (float) value.x, (float) value.y, (float) value.z);
    }

    public void uploadUniformFloat4(final String name, final Vector4d value) {
        int location = getUniformLocation(name);
        glUniform4f(location, (float) value.x, (float) value.y, (float) value.z, (float) value.w);
    }

    public void uploadUniformMat4(final String name, final Matrix4d value) {
        int location = getUniformLocation(name);
        FloatBuffer fb = createFloatBuffer(16);

        fb.put(0, (float) value.m00());
        fb.put(1, (float) value.m01());
        fb.put(2, (float) value.m02());
        fb.put(3, (float) value.m03());
        fb.put(4, (float) value.m10());
        fb.put(5, (float) value.m11());
        fb.put(6, (float) value.m12());
        fb.put(7, (float) value.m13());
        fb.put(8, (float) value.m20());
        fb.put(9, (float) value.m21());
        fb.put(10, (float) value.m22());
        fb.put(11, (float) value.m23());
        fb.put(12, (float) value.m30());
        fb.put(13, (float) value.m31());
        fb.put(14, (float) value.m32());
        fb.put(15, (float) value.m33());

        glUniformMatrix4fv(location, false, fb);
    }

    public String getName() {
        return mName;
    }

    private static FloatBuffer createFloatBuffer(int size) {
        return ByteBuffer.allocateDirect(size << 2)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
    }

    private String readFile(final String filepath) {
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(new File(filepath)));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
                result.append("\r\n");
            }

        } catch (FileNotFoundException e) {
            mLogger.error(MessageFormat.format(
                    "file `{0}` not found", filepath));
            return null;

        } catch (IOException e) {
            mLogger.error(MessageFormat.format(
                    "error reading `{0}` ({1})", filepath, e.getMessage()));

        } finally {
            try {
                if (br != null) {
                    br.close();
                }

            } catch (IOException e) {
                mLogger.error(MessageFormat.format(
                        "error closing `{0}` ({1})", filepath, e.getMessage()));
            }
        }

        return result.toString();
    }

    private Map<Integer, String> preprocess(final String source) {
        Map<Integer, String> shaderSources = new HashMap<>();

        String typeToken = "#type";
        int pos = source.indexOf(typeToken);
        while (pos != -1) {
            int eol = source.indexOf("\r\n", pos);
            if (eol == -1) {
                mLogger.error("Syntax error");
                return null;
            }

            int begin = pos + typeToken.length() + 1;
            String type = source.substring(begin, eol);
            int nextLinePos = source.indexOf("\r\n", eol);
            pos = source.indexOf(typeToken, nextLinePos);

            var shader = source.substring(
                    nextLinePos,
                    (pos == -1) ? source.length() : pos);
            shaderSources.put(shaderTypeFromString(type), shader);
        }

        return shaderSources;
    }

    private void compile(final Map<Integer, String> shaderSources) {
        // Get a program object.
        int program = glCreateProgram();

        int[] glShaderIds = new int[2];
        int glShaderIndex = 0;
        for (var kv : shaderSources.entrySet()) {
            var type = kv.getKey();
            final var source = kv.getValue();

            // Create an empty vertex shader handle.
            int shader = glCreateShader(type);

            // Send the vertex shader source code to GL.
            glShaderSource(shader, source);

            // Compìle the shader.
            glCompileShader(shader);

            IntBuffer isCompiled = IntBuffer.allocate(1);
            glGetShaderiv(shader, GL_COMPILE_STATUS, isCompiled);
            if (isCompiled.get() == GL_FALSE) {
                IntBuffer maxLength = IntBuffer.allocate(1);
                glGetShaderiv(shader, GL_INFO_LOG_LENGTH, maxLength);

                ByteBuffer infoLog = ByteBuffer.allocate(maxLength.get());
                glGetShaderInfoLog(shader, maxLength, infoLog);

                // We don't need the shader anymore.
                glDeleteShader(shader);

                mLogger.error(infoLog.toString());
                assert false;

                break;
            }

            // Attach our shaders to our program.
            glAttachShader(program, shader);
            glShaderIds[glShaderIndex++] = shader;
        }

        // Ling our program.
        glLinkProgram(program);

        IntBuffer isLinked = IntBuffer.allocate(1);
        glGetProgramiv(program, GL_LINK_STATUS, isLinked);
        if (isLinked.get() == GL_FALSE) {
            IntBuffer maxLength = IntBuffer.allocate(1);
            glGetProgramiv(program, GL_INFO_LOG_LENGTH, maxLength);

            ByteBuffer infoLog = ByteBuffer.allocate(maxLength.get());
            glGetProgramInfoLog(program, maxLength, infoLog);

            // We don't need the program anymore.
            glDeleteProgram(program);

            // Don't leak the shaders either.
            for (var id : glShaderIds) {
                glDeleteShader(id);
            }

            mLogger.error(infoLog.toString());
            assert false;

            return;
        }

        // Always detach shaders after succesful link.
        for (var id : glShaderIds) {
            glDetachShader(program, id);
        }

        mRendererId = IntBuffer.allocate(1).put(program);
    }

    private int getUniformLocation(final String name) {
        if (mUniformLocationCache.containsKey(name)) {
            return mUniformLocationCache.get(name);
        }

        int location = glGetUniformLocation(mRendererId.get(), name);
        if (location == -1) {
            mLogger.error(MessageFormat.format(
                    "Uniform {0} not found", name));
            return location;
        }

        mUniformLocationCache.put(name, location);
        return location;
    }

    private static int shaderTypeFromString(final String name) {
        if (name.equals("vertex")) {
            return GL_VERTEX_SHADER;
        } else if (name.equals("fragment") || name.equals("pixel")) {
            return GL_FRAGMENT_SHADER;
        }

        mLogger.error("Unknown shader type");
        assert false;

        return 0;
    }
}
