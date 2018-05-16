package com.expat.com.expat_app_v02;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout slideLinearLayout;
    private SliderAdapter sliderAdapter;
    private Button buttonNext;
    private Button buttonSkip;
    private TextView[] dotstv;
    private int positionScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mSlideViewPager = findViewById(R.id.slideViewPager);
        slideLinearLayout = findViewById(R.id.slideViewLinearLayout);
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        buttonNext = findViewById(R.id.nextIntro);
        buttonSkip = findViewById(R.id.skipIntro);

        setStatusBarTransparent();
        mSlideViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                positionScroll = position;
                if(positionScroll==3) {
                    buttonNext.setText("Finish");
                }else{
                    buttonNext.setText("Next");
                }
                setDotStatus(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(positionScroll==3) {
                    startMainActivity();
                }else{
                    mSlideViewPager.setCurrentItem(mSlideViewPager.getCurrentItem()+1);
                }
            }
        });
    }

    private void startMainActivity(){
        startActivity(new Intent(IntroActivity.this, SignUpLogin.class));
        finish();
    }

    private void setDotStatus(int page){
        slideLinearLayout.removeAllViews();
        dotstv = new TextView[4];
        for(int i=0; i<4; i++){
            dotstv[i] = new TextView(this);
            dotstv[i].setText(Html.fromHtml("&#8226;"));
            dotstv[i].setTextSize(30);
            dotstv[i].setTextColor(Color.parseColor("#a9b4bb"));
            slideLinearLayout.addView(dotstv[i]);
        }
        if(dotstv.length>0){
            dotstv[page].setTextColor(Color.parseColor("#ffffff"));
        }
    }

    private void setStatusBarTransparent(){
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLUE);
        }
    }


}
