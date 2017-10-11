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

/*서버로부터 식물 사진 데이터를 가져온다. */
public class GalleryImageRoader {
                                                                                                    //  image 가져오기
        private final String serverUrl = "http://211.44.136.164:7777/";             //서버 주소

        public GalleryImageRoader() {

            new ThreadPolicy();
        }

        public Bitmap getBitmapImg(String imgStr) {             //bitmap 형식으로 데이터를 가져온다.

            Bitmap bitmapImg = null;

            try {
                URL url = new URL(serverUrl + imgStr);          //url을 통해 서버의 이미지 get
                //URLEncoder.encode(imgStr,"utf-8"));
                // Character is converted to 'UTF-8' to prevent broken

                HttpURLConnection conn = (HttpURLConnection) url            //http를 url로 open
                        .openConnection();
                conn.setDoInput(true);                                      //ouput set
                conn.connect();                                               //연결

                InputStream is = conn.getInputStream();                     //input으로 가져와
                bitmapImg = BitmapFactory.decodeStream(is);                 //데이터 비트맵 형식으로 변환 decode

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return bitmapImg;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public static class ThreadPolicy {
                                                                //class작동시 thread
            // For smooth networking
            public ThreadPolicy() {

                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);
            }
        }
    }


