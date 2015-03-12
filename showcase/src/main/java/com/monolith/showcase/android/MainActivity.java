package com.monolith.showcase.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.monolith.api.android.MonolithActivity;
import com.monolith.showcase.R;
import com.monolith.showcase.android.examples.EngineAsFragmentActivity;
import com.monolith.showcase.android.examples.SceneSwitchingActivity;

import butterknife.InjectView;

/**
 * Created by Jakub Petriska on 13. 2. 2015.
 */
public class MainActivity extends BaseActionBarActivity {

    @InjectView(R.id.list)
    ListView mList;

    private int[] mExamples = new int[]{
            R.string.example_basic_activity,
            R.string.example_basic_fragment,
            R.string.example_scene_switching
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] examplesTitles = new String[mExamples.length];
        for (int i = 0; i < mExamples.length; i++) {
            examplesTitles[i] = getResources().getString(mExamples[i]);
        }

        mList.setAdapter(new ArrayAdapter<>(this, R.layout.list_item_simple, examplesTitles));

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (mExamples[position]) {
                    case R.string.example_basic_activity:
                        startActivity(new Intent(MainActivity.this, MonolithActivity.class));
                        return;
                    case R.string.example_basic_fragment:
                        startActivity(new Intent(MainActivity.this, EngineAsFragmentActivity.class));
                        return;
                    case R.string.example_scene_switching:
                        startActivity(new Intent(MainActivity.this, SceneSwitchingActivity.class));
                        return;
                }
            }
        });
    }
}
