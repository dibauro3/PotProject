package com.example.hyeongkun.jsontest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends Activity {

    TextView tv;
    String url="http://192.168.20.65/Project/example2.php";                    //IP 부분은 변경될때 마다 바꿔주어야 함
    phpDown task;
    ArrayList<ListItem>listItem=new ArrayList<ListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        task=new phpDown();
        tv=(TextView)findViewById(R.id.TextView1);
        task.execute(url);
    }

    private class phpDown extends AsyncTask<String,Integer,String>{
    @Override
        protected String doInBackground(String... urls){
        StringBuilder jsonHtml= new StringBuilder();
        try{
            // 연결 url 설정
            URL url = new URL(urls[0]);
            // 커넥션 객체 생성
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            // 연결되었으면.
            if(conn != null){
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                // 연결되었음 코드가 리턴되면.
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    for(;;){
                        // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                        String line = br.readLine();
                        if(line == null) break;
                        // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                        jsonHtml.append(line + "\n");
                    }
                    br.close();
                }
                conn.disconnect();
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
        return jsonHtml.toString();
        }

        protected void onPostExecute(String str){


            String num;
            String val;
            try{

                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");

                for(int i=0; i<ja.length(); i++){

                    JSONObject jo = ja.getJSONObject(i);
                    num = jo.getString("num");
                    val = jo.getString("val");
                    listItem.add(new ListItem(num,val));

                }

            }catch(JSONException e){
                e.printStackTrace();
            }


            for(int j=0; j<6;j++){
                    tv.append("num :"+listItem.get(j).getData(0)+"-"+"val:"+ listItem.get(j).getData(1)+"\n");
            }

        }

    }
}

//(0,0)-(0,1)   (1,0)-(1,1)    (2,0)-(2,1)








