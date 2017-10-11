package com.example.parkjaeha.firebasetest;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by parkjaeha on 2017-02-20.
 */
/* firebase를 이용해 android의 기기에 할당되는 고유 토큰을 가져온다.*/
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
   @Override
    public void onTokenRefresh(){               //token 정보 저장

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"Refreshed token:"+token);

       registerToken(token);    //저장된 토큰 mysql에 저장
    }



    public  void registerToken(final String token){


Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {             //thread
        try{
            OkHttpClient client = new OkHttpClient();           //httpclient를 이용
            RequestBody body = new FormBody.Builder()               //request body 설정
                    .add("Token",token)
                    .build();

            Request request = new Request.Builder()                     //request url 설정
                    .url("http://211.44.136.164:7777/Project/fcm_register.php")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();          //reponse call 하여 저장

            response.body().string();
            if (!response.isSuccessful()) throw new
                    IOException("Unexpected code " + response);
        }catch(Exception e)
        {
            System.out.println("FIL FUNCTION errroros hwa "+e);
        }
    }
});

        thread.start();
    }


}
