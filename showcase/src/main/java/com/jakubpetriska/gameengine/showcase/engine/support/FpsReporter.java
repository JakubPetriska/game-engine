package com.jakubpetriska.gameengine.showcase.engine.support;

import com.jakubpetriska.gameengine.api.Component;
import com.jakubpetriska.gameengine.showcase.Config;

/**
 * Simple {@link Component} computing FPS and sending float objects
 * with FPS values as messages out of the engine.
 */
public class FpsReporter extends Component {

    private static final int AVERAGING_FACTOR = 5;

    private int frameCount = 0;
    private float frameTimeSum = 0;

    @Override
    public void update() {
        if(!Config.SHOW_FPS) {
            return;
        }

        frameTimeSum += getApplication().getTime().getTimeDelta();
        ++frameCount;

        if(frameCount == AVERAGING_FACTOR) {
            float fps = frameCount / frameTimeSum;
            getApplication().getMessenger().sendMessage(fps);

            frameCount = 0;
            frameTimeSum = 0;
        }
    }
}
