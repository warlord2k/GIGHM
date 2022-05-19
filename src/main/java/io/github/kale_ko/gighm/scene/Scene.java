package io.github.kale_ko.gighm.scene;

import java.util.ArrayList;
import java.util.List;

/**
 * A scene that can be rendered
 * 
 * @version 1.7.0
 * @since 1.0.0
 */
public class Scene {
    /**
     * The name of the scene
     * 
     * @since 1.0.0
     */
    private String name;

    /**
     * The objects inside the scene
     * 
     * @since 1.0.0
     */
    private List<GameObject> objects;

    /**
     * Create a scene that can be rendered
     * 
     * @since 1.4.0
     */
    public Scene() {
        this("Main");
    }

    /**
     * Create a scene that can be rendered
     * 
     * @param name The name of the scene
     * 
     * @since 1.0.0
     */
    public Scene(String name) {
        this.objects = new ArrayList<GameObject>();
    }

    /**
     * Get the name of the scene
     * 
     * @return The name of the scene
     * 
     * @since 1.0.0
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the objects inside the scene
     * 
     * @return The objects inside the scene
     * 
     * @since 1.0.0
     */
    public List<GameObject> getObjects() {
        return this.objects;
    }

    /**
     * Get a list of objects inside the scene by name
     * 
     * @param name The name of the objects
     * 
     * @return The gameobjects requested
     * 
     * @since 1.0.0
     */
    public List<GameObject> getObjects(String name) {
        return this.getObjects(name, true);
    }

    /**
     * Get a list of objects inside the scene by name
     * 
     * @param name The name of the objects
     * @param caseSensitive Weather the name check is case sensitive or not
     * 
     * @return The gameobjects requested
     * 
     * @since 1.0.0
     */
    public List<GameObject> getObjects(String name, boolean caseSensitive) {
        List<GameObject> found = new ArrayList<GameObject>();

        for (GameObject object : this.objects) {
            if ((caseSensitive && object.getName().equals(name)) || (!caseSensitive && object.getName().equalsIgnoreCase(name))) {
                found.add(object);
            }
        }

        return found;
    }

    /**
     * Get a specific object inside the scene by name
     * 
     * @param name The name of the object
     * 
     * @return The gameobject requested or null
     * 
     * @since 1.0.0
     */
    public GameObject getObject(String name) {
        return this.getObject(name, true);
    }

    /**
     * Get a specific object inside the scene by name
     * 
     * @param name The name of the object
     * @param caseSensitive Weather the name check is case sensitive or not
     * 
     * @return The gameobject requested or null
     * 
     * @since 1.0.0
     */
    public GameObject getObject(String name, boolean caseSensitive) {
        for (GameObject object : this.objects) {
            if ((caseSensitive && object.getName().equals(name)) || (!caseSensitive && object.getName().equalsIgnoreCase(name))) {
                return object;
            }
        }

        return null;
    }

    /**
     * Add an objects to the scene
     * 
     * @param object The objects to add
     * 
     * @since 1.0.0
     */
    public void addObjects(GameObject object) {
        if (object.getScene() == null) {
            object._setScene(this);

            this.objects.add(object);
        } else {
            throw new RuntimeException("You can't add a gameobject to multiple scenes");
        }
    }

    /**
     * Remove an objects from the scene
     * 
     * @param object The objects to remove
     * 
     * @since 1.0.0
     */
    public void removeObjects(GameObject object) {
        this.objects.remove(object);
    }
}