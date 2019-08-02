package com.sensetime.scrollerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SlideView mSlideView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlideView = findViewById(R.id.slide_View);
    }

    public void click(View view) {
        mSlideView.smoothScrollTo(30,0);
    }
}
