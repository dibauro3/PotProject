package com.example.parkjaeha.firebasetest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by parkjaeha on 2017-02-20.
 */
/* push 알림이 오면 그것을 핸드폰 상에 알림창으로 띄워주는 클래스이다. */
public class FirebaseMessaging_Service extends com.google.firebase.messaging.FirebaseMessagingService {

    private  static  final  String TAG = "FirebaseMsgService";
                                                                            //message 정보 가져올 때
        @Override
        public void onMessageReceived(RemoteMessage remoteMessage){
            super.onMessageReceived(remoteMessage);
            String from = remoteMessage.getFrom();              //string 으로 저장
            Log.d(TAG,"From:"+from);
            //Log.d(TAG,"Notification Message Body:"+remoteMessage.getNotification().getBody());

            if(remoteMessage.getNotification() != null){
                Log.d(TAG,"NOTIFICATION:"+ remoteMessage.getNotification().getBody());
                sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());         //body notification send하기
            }
            if(remoteMessage.getData().size() > 0){
                Log.d(TAG,"Data:"+remoteMessage.getData());
            }
            //sendNotification(remoteMessage.getData().get("message"));
        }


    private void sendNotification(String title,String message) {                //send 하기

        Intent i = new Intent(this,PlantMain.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);      //pending intent로


        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)         //getdata notification set body,title
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)            //icon goole icon set
                .setContentIntent(pendingIntent);

        NotificationManager manger = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);          //notification manager set

        manger.notify(0,builder.build());                   //noti push

    }

}
