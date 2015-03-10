package com.monolith.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import com.monolith.engine.Engine;
import com.monolith.engine.messaging.InputMessenger;

/**
 * Created by Jakub Petriska on 29. 12. 2014.
 */
public class MonolithActivity extends Activity {

    /**
     * Extra key to use to specify default scene name.
     */
    public static final String EXTRA_DEFAULT_SCENE_NAME = "DEFAULT_SCENE_NAME";

    private MyGLSurfaceView mGlSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (savedInstanceState != null
                && savedInstanceState.containsKey(Constants.KEY_ENGINE_OBJECT_STORE_KEY)) {
            Engine engine = EngineObjectStore.retrieve(savedInstanceState.getString(Constants.KEY_ENGINE_OBJECT_STORE_KEY));
            mGlSurfaceView = new MyGLSurfaceView(this, engine);
        } else {
            mGlSurfaceView = new MyGLSurfaceView(this, getIntent().getStringExtra(EXTRA_DEFAULT_SCENE_NAME));
        }
        setContentView(mGlSurfaceView);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String engineObjectKey = EngineObjectStore.store(mGlSurfaceView.getEngine());
        outState.putString(Constants.KEY_ENGINE_OBJECT_STORE_KEY, engineObjectKey);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGlSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGlSurfaceView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGlSurfaceView.getEngine().onFinish();
    }

    public InputMessenger getInputMessenger() {
        return mGlSurfaceView == null ? null : mGlSurfaceView.getEngine().getInputMessenger();
    }
}