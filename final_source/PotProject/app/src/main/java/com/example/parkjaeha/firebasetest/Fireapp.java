package com.example.parkjaeha.firebasetest;

import android.app.Application;

import com.firebase.client.Firebase;


/**
 * Created by parkjaeha on 2017-01-30.
 */

/*
firebase service를 사용하기 위해 android에서  설정해준다.
*/

//Firebase android Use
public class Fireapp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);               //firebase android 에 set하기

    }

}