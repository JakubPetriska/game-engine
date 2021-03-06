package com.jakubpetriska.gameengine.tests.component_lifecycle;

import com.jakubpetriska.gameengine.tests.support.BaseEngineTest;

import org.junit.After;
import org.junit.Test;

/**
 * Component lifecycle test.
 */
public class ComponentLifecycleTest extends BaseEngineTest {

    private static final String FILES_FOLDER = "component_lifecycle_test";

    /**
     * Basic test of component lifecycle.
     * <p/>
     * Uses LifecycleAssertingComponent in the scene with 3 objects.
     * The two objects are children of the first object. All objects only
     * have a single component a LifecycleAssertingComponent component.
     */
    @Test
    public void basicComponentLifecycleTest() {
        runEngine(FILES_FOLDER, "main_scene", 10);
    }

    /**
     * This tests adding and removing GameObjects and Components.
     */
    @Test
    public void manipulationTest() {
        runEngine(FILES_FOLDER, "manipulation_test_scene", 10);
    }

    @After
    public void finalLifecycleCheckAndCleanup() {
        LifecycleAssertingComponent.checkObjectsInCacheAreOk();
        LifecycleAssertingComponent.clearObjectCache();
    }
}
