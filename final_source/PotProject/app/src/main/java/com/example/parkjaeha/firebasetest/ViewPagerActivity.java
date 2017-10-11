package com.example.parkjaeha.firebasetest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by hyeongkun on 2017-04-07.
 */
/*어플 사용방법을 알려주기 위한 클래스이다. */
public class ViewPagerActivity extends Activity {

    ViewPager pager;
    Button vp_cancel;
    TextView vp_tv2;
    String using1="1.자신의 기기를 등록 합니다.";
    String using2="2.기르고 싶은 식물을 선택 합니다.";
    String using3="3.식물정보를 확인한 뒤 등록버튼을 눌러 주세요.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.viewpager_main);


        vp_tv2=(TextView)findViewById(R.id.vp_tv02);
        vp_tv2.setText(using1);
        vp_cancel=(Button)findViewById(R.id.vp_cancel);
        pager= (ViewPager)findViewById(R.id.pager);
        VPCustomAdapter adapter= new VPCustomAdapter(getLayoutInflater());
        pager.setAdapter(adapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int num=pager.getCurrentItem();
                if(num== 0) vp_tv2.setText(using1);
                else if(num== 1)vp_tv2.setText(using2);
                else vp_tv2.setText(using3);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void mOnClick(View v){

        switch( v.getId() ){
            case R.id.vp_cancel:
                finish();
                break;
        }

        int num=pager.getCurrentItem();
        if(num== 0) vp_tv2.setText(using1);
        else if(num== 1)vp_tv2.setText(using2);
        else vp_tv2.setText(using3);

    }
}

