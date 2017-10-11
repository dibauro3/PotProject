package com.example.parkjaeha.firebasetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

/**
 * Created by parkjaeha on 2017-01-31.
 */
/*처음 화면 실행시 2초간의 딜레이를 통해 앱이 실행시 화면을 먼저 띄워주는 역할을 한다.  후에 데이터를 가져오는 상황이 생기면 로딩을 사용할 것이다.*/
//main next Activity
public class IntroActivity extends Activity {

    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sub);

        h = new Handler();
        h.postDelayed(mrnu, 2000);
    }

    Runnable mrnu = new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(IntroActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    };

    @Override
    public void onBackPressed(){
        h.removeCallbacks(mrnu);
    }
}