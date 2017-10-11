package com.example.parkjaeha.firebasetest;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by parkjaeha on 2017-02-08.
 */
/*
*  BackgroundTask는 DB에 저장되는 타입에 따라 다르게 php로 정보를 보내어 저장하는 클래스이다.
* */
//Json형식  BackgroundTask register save
public class BackgroundTask extends AsyncTask<String,Void,String> {

    AlertDialog alertDialog;
    Context ctx;
    BackgroundTask(Context ctx){
        this.ctx = ctx;
    }


    @Override
    protected  void onPreExecute(){
        //super.onPreExecute();
        alertDialog =new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("register Informaiton");
    }

    //Json Get data
    @Override
    protected  String doInBackground(String... params){
        String reg_url="http://211.44.136.164:7777/Project/register.php"; // register url
        String push_url="http://211.44.136.164:7777/Project/pushconnect.php";
        String pot_url="http://211.44.136.164:7777/Project/potplant.php";
        String device_url="http://211.44.136.164:7777/Project/device.php";

        String method = params[0];                      //string register

        if(method.equals("register")) {
            String name = params[1];            //name
            String user_email = params[2];      //email
            String user_uid = params[3];        //uid
            try {
                URL url = new URL(reg_url);         //url설정
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //http연결
                httpURLConnection.setRequestMethod("POST");         //post방식으로 사용하겠다.
                httpURLConnection.setDoOutput(true);                //http output으로 set
                OutputStream OS = httpURLConnection.getOutputStream();   //http output get
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));  //buffere형식으로 저장및 데이터 encode 형식으로 저장
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(user_email, "UTF-8") + "&" +
                        URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(user_uid, "UTF-8");
                bufferedWriter.write(data);         //데이터 입력
                bufferedWriter.flush();             //사용후 flush
                bufferedWriter.close();             //buffer close
                OS.close();                         //outputstream close
                InputStream IS = httpURLConnection.getInputStream();  //http input get
                IS.close();
                return "Registeration Success";


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(method.equals("pushNotify")){
            String token = params[1];
            try {
                URL url = new URL(push_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("Token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Push Success";


            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(method.equals("device")){      //user, device 필요 php
            String user = params[1];
            String pot_number = params[2];
            String pot_plant = params[3];
            try {
                URL url = new URL(device_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8") +"&"+
                        URLEncoder.encode("pot_number", "UTF-8") + "=" + URLEncoder.encode(pot_number, "UTF-8")+"&"+
                        URLEncoder.encode("pot_plant", "UTF-8") + "=" + URLEncoder.encode(pot_plant, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Device Register Success";

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(method.equals("pot")){             //pot,device 번호 필요 php
            String pot_number = params[1];
            String pot_name = params[2];
            try {
                URL url = new URL(pot_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("pot_number", "UTF-8") + "=" + URLEncoder.encode(pot_number, "UTF-8") +
                        URLEncoder.encode("pot_name", "UTF-8") + "=" + URLEncoder.encode(pot_name, "UTF-8");        //기기번호,식물
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Pot Register Success";

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }


    @Override
    protected  void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }

    @Override
    protected  void onPostExecute(String result){               //결과 값을 가져와 결과에 따라 데이터 출력

        if(result.equals("Registeration Success")){
            Toast.makeText(ctx,"회원가입되었습니다.",Toast.LENGTH_SHORT).show();
        }else if(result.equals("Push Success")){
            Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
        }else if(result.equals("Device Register Success")){
            Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
        }else if(result.equals("Pot Register Success")){
            Toast.makeText(ctx,result,Toast.LENGTH_SHORT).show();
        }else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }

    }


}
