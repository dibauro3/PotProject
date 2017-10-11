package com.example.parkjaeha.firebasetest;

/**
 * Created by hyeongkun on 2017-04-18.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
/* webstream을 보여주는 activity를 따로 만든 클래스이다. */
public class WebStreamActivity extends AppCompatActivity {


    NetworkState networkState = new NetworkState();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webstream);
        if(!networkState.isConnected(WebStreamActivity.this)){

            networkState.buildDialog(WebStreamActivity.this).show();
        }
        else {


            WebView webView = (WebView) findViewById(R.id.webView);
            webView.setPadding(0, 0, 0, 0);

            webView.getSettings().setBuiltInZoomControls(false);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);

            String url = "http://172.20.10.8:8081/javascript_simple.html";
            webView.loadUrl(url);

        }

        Button cls_btn= (Button)findViewById(R.id.webcls_btn);
        cls_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}