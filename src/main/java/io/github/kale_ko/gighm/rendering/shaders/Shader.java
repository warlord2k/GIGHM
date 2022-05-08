package io.github.kale_ko.gighm.rendering.shaders;

import static org.lwjgl.opengl.GL20.*;
import java.nio.FloatBuffer;
import javax.validation.constraints.NotNull;
import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;
import org.joml.Vector4i;
import org.lwjgl.BufferUtils;

/**
 * A shader
 * 
 * @version 1.2.0
 * @since 1.0.0
 */
public class Shader {
    /**
     * The source data of the vertex shader
     * 
     * @since 1.0.0
     */
    private @NotNull String vertexSource;

    /**
     * The source data of the fragment shader
     * 
     * @since 1.0.0
     */
    private @NotNull String fragmentSource;

    /**
     * Weather the window is initialized
     * 
     * @since 1.0.0
     */
    private @NotNull boolean initialized = false;

    /**
     * The id of the shader program
     * 
     * @since 1.0.0
     */
    private int programId;

    /**
     * The id of the vertex shader
     * 
     * @since 1.0.0
     */
    private int vertexId;

    /**
     * The id of the fragment shader
     * 
     * @since 1.0.0
     */
    private int fragmentId;

    /**
     * Create a shader
     * 
     * @param vertexSource The source data of the vertex shader
     * @param fragmentSource The source data of the fragment shader
     * 
     * @see ShaderLoader#loadShader
     * 
     * @since 1.0.0
     */
    public Shader(@NotNull String vertexSource, @NotNull String fragmentSource) {
        this.vertexSource = vertexSource;
        this.fragmentSource = fragmentSource;
    }

    /**
     * Initialize the shader (Must be called from a {@link Renderer})
     * 
     * @throws RuntimeException If the renderer is already initialized
     * 
     * @since 1.0.0
     */
    public void init() {
        if (this.initialized) {
            throw new RuntimeException("The texture is already initialized");
        }

        this.initialized = true;

        this.programId = glCreateProgram();

        this.vertexId = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(this.vertexId, this.vertexSource);
        glCompileShader(this.vertexId);
        if (glGetShaderi(this.vertexId, GL_COMPILE_STATUS) != GL_TRUE) {
            System.err.println(glGetShaderInfoLog(this.vertexId));
            throw new RuntimeException("Failed to compile vertex shader");
        }

        this.fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(this.fragmentId, this.fragmentSource);
        glCompileShader(this.fragmentId);
        if (glGetShaderi(this.fragmentId, GL_COMPILE_STATUS) != GL_TRUE) {
            System.err.println(glGetShaderInfoLog(this.fragmentId));
            throw new RuntimeException("Failed to compile fragment shader");
        }

        glAttachShader(this.programId, this.vertexId);
        glAttachShader(this.programId, this.fragmentId);

        glBindAttribLocation(this.programId, 0, "vertices");
        glBindAttribLocation(this.programId, 1, "uvs");

        glLinkProgram(this.programId);
        if (glGetProgrami(this.programId, GL_LINK_STATUS) != GL_TRUE) {
            System.err.println(glGetProgramInfoLog(this.programId));
            throw new RuntimeException("Failed to compile shader program");
        }
        glValidateProgram(this.programId);
        if (glGetProgrami(this.programId, GL_VALIDATE_STATUS) != GL_TRUE) {
            System.err.println(glGetProgramInfoLog(this.programId));
            throw new RuntimeException("Failed to compile shader program");
        }
    }

    /**
     * Bind the shader for use (Must be called from a {@link Renderer})
     * 
     * @since 1.0.0
     */
    public void bind() {
        glUseProgram(this.programId);
    }

    /**
     * Get the source of the vertex shader
     * 
     * @return The source of the vertex shader
     * 
     * @since 1.2.0
     */
    public @NotNull String getVertexSource() {
        return this.vertexSource;
    }

    /**
     * Get the source of the fragment shader
     * 
     * @return The source of the fragment shader
     * 
     * @since 1.2.0
     */
    public @NotNull String getFragmentSource() {
        return this.fragmentSource;
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull int value) {
        int loc = glGetUniformLocation(this.programId, id);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniform1i(loc, value);
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull int[] value) {
        int loc = glGetUniformLocation(this.programId, id);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniform1iv(loc, value);
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull Vector2i value) {
        int loc = glGetUniformLocation(this.programId, id);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniform2i(loc, value.x, value.y);
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull Vector3i value) {
        int loc = glGetUniformLocation(this.programId, id);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniform3i(loc, value.x, value.y, value.z);
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull Vector4i value) {
        int loc = glGetUniformLocation(this.programId, id);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniform4i(loc, value.x, value.y, value.z, value.w);
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull float value) {
        int loc = glGetUniformLocation(this.programId, id);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniform1f(loc, value);
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull float[] value) {
        int loc = glGetUniformLocation(this.programId, id);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniform1fv(loc, value);
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull Vector2f value) {
        int loc = glGetUniformLocation(this.programId, id);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniform2f(loc, value.x, value.y);
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull Vector3f value) {
        int loc = glGetUniformLocation(this.programId, id);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniform3f(loc, value.x, value.y, value.z);
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull Vector4f value) {
        int loc = glGetUniformLocation(this.programId, id);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniform4f(loc, value.x, value.y, value.z, value.w);
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull Matrix2f value) {
        int loc = glGetUniformLocation(this.programId, id);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        value.get(buffer);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniformMatrix2fv(loc, false, buffer);
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull Matrix3f value) {
        int loc = glGetUniformLocation(this.programId, id);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
        value.get(buffer);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniformMatrix3fv(loc, false, buffer);
    }

    /**
     * Set a uniform on the shader
     * 
     * @param id The uniform's id
     * @param value The value of the uniform
     * 
     * @since 1.0.0
     */
    public void setUniform(@NotNull String id, @NotNull Matrix4f value) {
        int loc = glGetUniformLocation(this.programId, id);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);
        if (loc == -1) {
            throw new RuntimeException("Invalid uniform location: " + id);
        }

        glUniformMatrix4fv(loc, false, buffer);
    }

    /**
     * Get weather the window is initialized
     * 
     * @return Weather the window is initialized
     * 
     * @since 1.0.0
     */
    public @NotNull boolean getInitialized() {
        return this.initialized;
    }

    /**
     * Get the id of the shader program
     * 
     * @return The id of the shader program
     * 
     * @since 1.0.0
     */
    public @NotNull int getProgramId() {
        return this.programId;
    }

    /**
     * Get the id of the vertex shader
     * 
     * @return The id of the vertex shader
     * 
     * @since 1.0.0
     */
    public @NotNull int getVertexId() {
        return this.vertexId;
    }

    /**
     * Get the id of the fragment shader
     * 
     * @return The id of the fragment shader
     * 
     * @since 1.0.0
     */
    public @NotNull int getFragmentId() {
        return this.fragmentId;
    }
}