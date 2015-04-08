package com.monolith.api;

import com.monolith.api.components.Transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an object. GameObject serves only as container for functionality.
 * GameObject itself will not be displayed in final rendered content in any way.
 * <p/>
 * Functionality is added to GameObject using components. Components need to be
 * subclasses of {@link com.monolith.api.Component} class and they can be added
 * through GameObject's methods from scripts or in xml files defining initial scene layouts.
 * <p/>
 * GameObject can also be used to only hold children objects for manipulation.
 */
public class GameObject {

    // TODO bring order to children and components lists so searching for them is in log(n)

    private Application mApplication;
    private GameObject mParent;

    /**
     * Contains all GameObject's children.
     * <p/>
     * This list is unmodifiable, use appropriate GameObject instance
     * methods to remove children.
     * <p/>
     * Adding children to this GameObject can be done through passing this
     * object as parent into child GameObject's constructor or
     * calling {@link com.monolith.api.GameObject#setParent(GameObject)}
     * on the child GameObject.
     */
    public final List<GameObject> children;
    private final List<GameObject> mChildren = new ArrayList<>();

    /**
     * Contains all GameObject's components.
     * <p/>
     * This list is unmodifiable, use appropriate GameObject instance
     * methods to add or remove components.
     */
    public final List<Component> components;
    private final List<Component> mComponents = new ArrayList<>();

    // Mandatory GameObject's components
    // None of them can be removed so make sure it is checked in removeComponent(Component) method
    public final Transform transform;

    /**
     * Creates new GameObject.
     * <p/>
     * This constructor is meant for constructing top level GameObjects.
     * Creating top level object from scripts is currently not supported.
     * if you pass null as parent appropriate lifecycle methods will not be
     * called on this object.
     */
    public GameObject(Application application, GameObject parent) {
        this.mApplication = application;
        this.mParent = parent;
        children = Collections.unmodifiableList(mChildren);
        components = Collections.unmodifiableList(mComponents);

        if (parent != null) {
            parent.mChildren.add(this);
        }

        transform = new Transform();
        addComponent(transform);
    }

    /**
     * Creates new GameObject. Use this in your scripts.
     *
     * @param parent GameObject's parent. Can never be null.
     */
    public GameObject(GameObject parent) {
        if (parent == null) {
            throw new IllegalStateException("This constructor cannot be used for objects without parent.");
        }
        this.mApplication = parent.mApplication;
        this.mParent = parent;
        children = Collections.unmodifiableList(mChildren);
        components = Collections.unmodifiableList(mComponents);

        parent.mChildren.add(this);

        transform = new Transform();
        addComponent(transform);
    }

    /**
     * Returns GameObject's parent GameObject.
     *
     * @return Parent GameObject of this GameObject.
     */
    public GameObject getParent() {
        return mParent;
    }

    /**
     * Sets new parent to this GameObject and also removes it from children of
     * previous parent.
     *
     * @param parent New parent GameObject.
     */
    public void setParent(GameObject parent) {
        if (parent == null) {
            throw new IllegalStateException("GameObject's parent cannot be set to null.");
        }
        if (getParent() == null) {
            throw new IllegalStateException("Cannot change parent of top level object.");
        }

        getParent().mChildren.remove(this);
        parent.mChildren.add(this);
        mParent = parent;
    }

    /**
     * Removes childObject from children of this object
     * if childObject is a child of this GameObject.
     * <p/>
     * After removing, childObject is no longer valid GameObject
     * and cannot be used any further. Also no references to it should be
     * kept.
     *
     * @param childObject Child GameObject to remove.
     */
    public void removeChild(GameObject childObject) {
        if (mChildren.contains(childObject)) {
            // Remove all components from this object
            for (int i = childObject.mComponents.size() - 1; i > 0; --i) {
                childObject.removeComponentInternal(childObject.mComponents.get(i));
            }
            // Remove all child objects from removed object
            for (int i = childObject.mChildren.size() - 1; i > 0; --i) {
                childObject.removeChild(childObject.mChildren.get(i));
            }
            mChildren.remove(childObject);
        }
    }

    /**
     * Adds new {@link com.monolith.api.Component} to this GameObject.
     *
     * @param component {@link com.monolith.api.Component} to add.
     */
    public void addComponent(Component component) {
        if (!mComponents.contains(component)) {
            component.setup(mApplication, this);
            // Beware that game object's mandatory components need to stay at the beginning of the list
            mComponents.add(component);
            component.start();
        }
    }

    /**
     * Used by engine. Allows removing of mandatory components.
     */
    private void removeComponentInternal(Component component) {
        if (mComponents.contains(component)) {
            component.finish();
            mComponents.remove(component);
        }
    }

    /**
     * Removes {@link com.monolith.api.Component} from this GameObject
     * if it is it's component.
     *
     * @param component {@link com.monolith.api.Component} to remove.
     */
    public void removeComponent(Component component) {
        // Make sure none of the mandatory components is removed
        if (component == transform) {
            throw new IllegalStateException("Cannot remove GameObject's mandatory component.");
        }
        removeComponentInternal(component);
    }

    public <T> T getComponent(Class<T> componentClass) {
        for (int i = 0; i < mComponents.size(); ++i) {
            Component component = mComponents.get(i);
            if(componentClass.isInstance(component)) {
                return (T) component;
            }
        }
        return null;
    }

    public <T extends Component> List<T> getComponents(Class<T> componentsClass) {
        List<T> resultList = new ArrayList<>();
        for (int i = 0; i < mComponents.size(); ++i) {
            Component component = mComponents.get(i);
            if(componentsClass.isInstance(component)) {
                resultList.add((T) component);
            }
        }
        return resultList;
    }
}
