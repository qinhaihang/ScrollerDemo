package com.sensetime.scrollerdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SlideView mSlideView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlideView = findViewById(R.id.slide_View);
        new ViewPager(this);
    }

    public void click(View view) {
        
        switch(view.getId()){
            case R.id.btn_scroll:
                mSlideView.smoothScrollTo(275,0);
                break;
            case R.id.tv_content:
                Toast.makeText(this,"content",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_right:
                Toast.makeText(this,"rigth",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        
    }
}
