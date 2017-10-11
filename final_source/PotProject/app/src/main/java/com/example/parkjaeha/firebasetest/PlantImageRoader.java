package com.example.parkjaeha.firebasetest;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hyeongkun on 2017-04-11.
 */
/*식물 이미지 정보를 불러오는 클래스이다.*/
public class PlantImageRoader {

    private final String serverUrl = "http://211.44.136.164:7777/Project/";

    public PlantImageRoader() {

        new ThreadPolicy();
    }

    public Bitmap getBitmapImg(String imgStr) {

        Bitmap bitmapImg = null;

        try {
            URL url = new URL(serverUrl + imgStr);
                    //URLEncoder.encode(imgStr,"utf-8"));
            // Character is converted to 'UTF-8' to prevent broken

            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            bitmapImg = BitmapFactory.decodeStream(is);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return bitmapImg;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static class ThreadPolicy {

        // For smooth networking
        public ThreadPolicy() {

            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }
    }
}
