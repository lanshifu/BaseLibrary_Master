package com.lanshifu.demo_module.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.lanshifu.demo_module.R;

import io.flutter.facade.Flutter;
import io.flutter.view.FlutterView;

//import io.flutter.facade.Flutter;
//import io.flutter.view.FlutterView;

/**
 * 集成看这篇 https://blog.csdn.net/c10wtiybq1ye3/article/details/88415077
 *
 * */
public class DemoFlutterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_flutter);


        final FlutterView flutterView = Flutter.createView(
                this,
                getLifecycle(),
                "route1"
        );
        final FrameLayout layout = findViewById(R.id.flutter_container);
        layout.addView(flutterView);
        final FlutterView.FirstFrameListener[] listeners = new FlutterView.FirstFrameListener[1];
        listeners[0] = new FlutterView.FirstFrameListener() {
            @Override
            public void onFirstFrame() {
                layout.setVisibility(View.VISIBLE);
            }
        };
        flutterView.addFirstFrameListener(listeners[0]);
    }

}
