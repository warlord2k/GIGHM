package io.github.kale_ko.gighm.tests;

import java.io.IOException;
import org.joml.Vector3d;
import io.github.kale_ko.gighm.rendering.Renderer;
import io.github.kale_ko.gighm.rendering.Window;
import io.github.kale_ko.gighm.rendering.shaders.Shader;
import io.github.kale_ko.gighm.rendering.shaders.ShaderLoader;
import io.github.kale_ko.gighm.rendering.textures.Texture2D;
import io.github.kale_ko.gighm.rendering.textures.Texture2DLoader;
import io.github.kale_ko.gighm.scene.GameObject;
import io.github.kale_ko.gighm.scene.Scene;
import io.github.kale_ko.gighm.scene.components.Camera;
import io.github.kale_ko.gighm.scene.components.Mesh;
import io.github.kale_ko.gighm.scene.components.Transform;

public class RendererTest {
    public static void main(String[] args) {
        try {
            int width = 800;
            int height = 600;

            Scene scene = new Scene("Main");
            Shader shader = new Shader(ShaderLoader.loadShaderData(RendererTest.class.getResourceAsStream("/vertex.glsl")), ShaderLoader.loadShaderData(RendererTest.class.getResourceAsStream("/fragment.glsl")));

            GameObject cameraObject = new GameObject("Camera");
            Camera camera = Camera.createOrthographic(width, height, 0.001, 512);
            cameraObject.addComponent(camera);
            cameraObject.getComponent(Transform.class).setPosition(new Vector3d(0, 0, -10));
            scene.addObjects(cameraObject);

            Renderer renderer = new Renderer(scene, camera, shader);

            GameObject object1 = new GameObject("Test Object");
            Texture2D texture1 = Texture2DLoader.loadTexture("C:/Users/Kale Ko/Downloads/54416665.png");
            Mesh mesh1 = new Mesh(new float[] { -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, -0.5f }, 2, texture1, new float[] { 0, 0, 1, 0, 1, 1, 0, 1 }, 2, new int[] { 0, 1, 2, 2, 3, 0 });
            object1.addComponent(mesh1);
            object1.getComponent(Transform.class).setPosition(new Vector3d(125, -65, 10));
            object1.getComponent(Transform.class).setScale(new Vector3d(160));

            GameObject object2 = new GameObject("Test Object 2");
            Texture2D texture2 = Texture2DLoader.loadTexture("C:/Users/Kale Ko/Downloads/54416665.png");
            Mesh mesh2 = new Mesh(new float[] { -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, -0.5f }, 2, texture2, new float[] { 0, 0, 1, 0, 1, 1, 0, 1 }, 2, new int[] { 0, 1, 2, 2, 3, 0 });
            object2.addComponent(mesh2);
            object2.getComponent(Transform.class).setPosition(new Vector3d(-85, 30, 10));
            object2.getComponent(Transform.class).setScale(new Vector3d(100));

            GameObject object3 = new GameObject("Test Object 3");
            Texture2D texture3 = Texture2DLoader.loadTexture("C:/Users/Kale Ko/Downloads/aaaaaaaaaaaaaaaaaaaa.png");
            Mesh mesh3 = new Mesh(new float[] { -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, -0.5f, -0.5f }, 2, texture3, new float[] { 0, 0, 1, 0, 1, 1, 0, 1 }, 2, new int[] { 0, 1, 2, 2, 3, 0 });
            object3.addComponent(mesh3);
            object3.getComponent(Transform.class).setPosition(new Vector3d(45, 240, 10));
            object3.getComponent(Transform.class).setScale(new Vector3d(80));

            scene.addObjects(object1);
            scene.addObjects(object2);
            scene.addObjects(object3);

            new Window(renderer, "Test window", 800, 600, false, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}