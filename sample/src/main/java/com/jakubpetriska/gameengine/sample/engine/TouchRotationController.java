package com.jakubpetriska.gameengine.sample.engine;

import com.jakubpetriska.gameengine.api.Component;
import com.jakubpetriska.gameengine.api.GameObject;
import com.jakubpetriska.gameengine.api.Touch;

import java.util.List;

/**
 * This is simple rotation controlling {@link Component}.
 * It rotates it's {@link GameObject} according to touch input.
 */
public class TouchRotationController extends Component {

    private static final float FACTOR = 0.1f;

    private int mLastTouchId = -1;
    private float mLastTouchX;
    private float mLastTouchY;

    @Override
    public void update() {
        List<Touch> touches = getApplication().getTouchInput().getTouches();
        if (touches.size() > 0) {
            Touch touch = null;
            if(mLastTouchId != -1) {
                // Try to retrieve the Touch we tracked last time
                for(int i = 0; i < touches.size(); ++i) {
                    Touch ithTouch = touches.get(i);
                    if(ithTouch.getId() == mLastTouchId) {
                        touch = ithTouch;
                        break;
                    }
                }
            }
            if(touch == null) {
                touch = touches.get(0);
            }

            float currentTouchX = touch.getX();
            float currentTouchY = touch.getY();

            if (touch.getState() != Touch.STATE_BEGAN && touch.getId() == mLastTouchId) {
                // Factor needs to be scaled according to screen pixel density
                // since touch coordinates are in screen pixels
                float countedFactor = FACTOR / getApplication().getDisplay().densityScaleFactor;
                getGameObject().transform.rotateBy(
                        -(currentTouchY - mLastTouchY) * countedFactor,
                        -(currentTouchX - mLastTouchX) * countedFactor,
                        0);
            }
            if(touch.getState() == Touch.STATE_ENDED) {
                mLastTouchId = -1;
            } else {
                mLastTouchId = touch.getId();
            }
            mLastTouchX = currentTouchX;
            mLastTouchY = currentTouchY;
        }
    }
}
