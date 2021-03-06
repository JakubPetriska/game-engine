package com.jakubpetriska.gameengine.sample.engine;

import com.jakubpetriska.gameengine.api.Component;
import com.jakubpetriska.gameengine.api.GameObject;
import com.jakubpetriska.gameengine.api.Primitives;
import com.jakubpetriska.gameengine.api.components.Model;

/**
 * {@link Component} generating a lots of cubes as a children of it's
 * {@link GameObject}.
 *
 * This is used in performance testing.
 */
public class CubesGenerator extends Component {

    private static final float CUBE_SPACE = 2;

    public int cubeLayerCount;

    @Override
    public void start() {
        boolean generateCube = false;
        for (int layer = 0; layer < cubeLayerCount; ++layer) {
            float distance = (layer + 1) * CUBE_SPACE;
            int halfLayerSizeFloor = 1 + layer;
            for (int i = -halfLayerSizeFloor; i <= halfLayerSizeFloor; ++i) {
                for (int j = -halfLayerSizeFloor; j <= halfLayerSizeFloor; ++j) {
                    String mesh = generateCube ? Primitives.CUBE : "models/diamond.obj";
                    float scale = generateCube ? 1 : 0.5f;
                    addCube(i * CUBE_SPACE, distance, j * CUBE_SPACE, mesh, scale);
                    addCube(i * CUBE_SPACE, -distance, j * CUBE_SPACE, mesh, scale);
                    generateCube = !generateCube;
                }
            }
        }
    }

    private void addCube(float x, float y, float z, String mesh, float scale) {
        GameObject newCube = new GameObject(getGameObject());
        newCube.transform.setScale(scale, scale, scale);
        Model model = new Model();
        model.meshPath = mesh;
        newCube.addComponent(model);
        newCube.transform.setPosition(x, y, z);
    }
}
