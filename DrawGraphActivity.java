package com.example.hyeongkun.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DrawGraphActivity extends AppCompatActivity  {                                              //KPU_Wireless 확인

    String url="http://192.168.20.165/Project/period/1hour.php";                                    //웹서버 주소 default
    phpDown task;
    ArrayList<ListItem> listItem_temp= new ArrayList<ListItem>();
    ArrayList<ListItem> listItem_water = new ArrayList<ListItem>();                                 //JSON 객체를 저장할 배열리스트
    ArrayList<ListItem> listItem_lux = new ArrayList<ListItem>();

    private LineChart myChart;
    public RadioGroup myRadioGroup;
    public RadioButton rb_10min,rb_30min,rb_1hour;

    String op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawgraph);

        Intent intent=getIntent();
        op=intent.getStringExtra("OPTION");
        Log.d("선택옵션값",op);

        myChart = (LineChart) findViewById(R.id.chart1);
        switch(op){
            case "temp":
                myChart.getDescription().setText("온도");
                break;
            case "water":
                myChart.getDescription().setText("습도");
                break;
            case "lux":
                myChart.getDescription().setText("조도");
                break;
        }
//      myChart.getDescription().setText("온도");
        myChart.getAxisRight().setDrawLabels(false);
        myChart.getXAxis().setDrawLabels(false);
        //myChart.animateX(3000);

        myRadioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        rb_10min=(RadioButton)findViewById(R.id.rb_10min);
        rb_30min=(RadioButton)findViewById(R.id.rb_30min);
        rb_1hour=(RadioButton)findViewById(R.id.rb_1hour);

        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch(checkedId){
                    case R.id.rb_10min:
                        url="http://192.168.20.165/Project/period/10minute.php";
                        task = new phpDown();
                        task.execute(url);
                        new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
                            @Override
                            public void run() {
                                setData(op);}
                        },2000);
                        break;

                    case R.id.rb_30min:
                        url="http://192.168.22.35/Project/period/30minute.php";
                        task = new phpDown();
                        task.execute(url);
                        new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
                            @Override
                            public void run() {
                                setData(op);}
                        },2000);
                        break;

                    case R.id.rb_1hour:
                        url="http://192.168.20.165/Project/period/1hour.php";
                        task = new phpDown();
                        task.execute(url);
                        new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
                            @Override
                            public void run() {
                                setData(op);}
                        },2000);
                        break;

                    default:
                        url="http://192.168.20.165/Project/period/1hour.php";
                        task = new phpDown();
                        task.execute(url);
                        new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
                            @Override
                            public void run() {
                                setData(op);}
                        },2000);
                }
            }
        });

        task = new phpDown();
        task.execute(url);
        new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
            @Override
            public void run() {
                setData(op);}
        },2000);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////서버에서 데아터를 가져오기 위한 함수
    protected class phpDown extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jsonHtml.toString();

        }

        protected void onPostExecute(String str) {                          //가져온 데이터를 원하는 형태로 파싱 하는 부분

            String id,temp,water,lux;

            try {
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");

                for (int i = 0; i < ja.length(); i++) {

                    JSONObject jo = ja.getJSONObject(i);
                    id = jo.getString("id");
                    temp = jo.getString("temp");
                    water = jo.getString("water");
                    lux= jo.getString("lux");

                    listItem_temp.add(new ListItem(id,temp));
                    listItem_water.add(new ListItem(id,water));
                    listItem_lux.add(new ListItem(id,lux));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            /*for(int j=0; j<listItem_temp.size();j++){
                tv.append(listItem_temp.get(j).getData(0) + "-" + listItem_temp.get(j).getData(1) + " ");
            }*/
        }
    }

    public void setData(String option) {

        ArrayList<Entry> values = new ArrayList<Entry>();
        values.clear();

        switch(option){
            case "temp":
                for(int i = 0; i< listItem_temp.size(); i++ ){                                                   //파싱한 데이터를 엔트리에 넣어준다
                    float myvalue=(float)(Integer.parseInt(listItem_temp.get(i).getData(1)));
                    Entry mEntry= new Entry(i,myvalue);
                    values.add(mEntry);
                }
                break;

            case "water":
                for(int i = 0; i< listItem_water.size(); i++ ){                                                   //파싱한 데이터를 엔트리에 넣어준다
                    float myvalue=(float)(Integer.parseInt(listItem_water.get(i).getData(1)));
                    Entry mEntry= new Entry(i,myvalue);
                    values.add(mEntry);
                }
                break;

            case "lux":
                for(int i = 0; i< listItem_lux.size(); i++ ){                                                   //파싱한 데이터를 엔트리에 넣어준다
                    float myvalue=(float)(Integer.parseInt(listItem_lux.get(i).getData(1)));
                    Entry mEntry= new Entry(i,myvalue);
                    values.add(mEntry);
                }
                break;
        }

        /*for(int i = 0; i< listItem_temp.size(); i++ ){                                                   //파싱한 데이터를 엔트리에 넣어준다
            float myvalue=(float)(Integer.parseInt(listItem_temp.get(i).getData(1)));
            Entry mEntry= new Entry(i,myvalue);
            values.add(mEntry);
        }*/
            LineDataSet mySet;

                // create a dataset and give it a type
                mySet = new LineDataSet(values, "온도측정량");

                // set the line to be drawn like this "- - - - - -"
                mySet.enableDashedLine(10f, 3f, 0f);

        switch(option){

            case "temp":
                mySet.setColor(Color.RED);
                break;
            case "water":
                mySet.setColor(ColorTemplate.getHoloBlue());
                break;
            case "lux":
                mySet.setColor(Color.YELLOW);
                break;
        }

                mySet.setCircleColor(Color.BLACK);
                mySet.setLineWidth(2f);
                mySet.setCircleRadius(3f);
                mySet.setDrawCircleHole(false);
                mySet.setValueTextSize(9f);
                mySet.setDrawFilled(false);
                mySet.setFormSize(15.f);


                if (Utils.getSDKInt() >= 18) {
                    // fill drawable only supported on api level 18 and above
                    Drawable drawable = ContextCompat.getDrawable(this, R.drawable.mycolor);
                    mySet.setFillDrawable(drawable);
                } else {
                    mySet.setFillColor(Color.BLACK);
                }

                ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
                dataSets.clear();
                dataSets.add(mySet); // add the datasets

                // create a data object with the datasets
                LineData data = new LineData(dataSets);

                // set data
                myChart.setData(data);
                myChart.invalidate();

            //}
                listItem_temp.clear();
                listItem_water.clear();
                listItem_lux.clear();
        }


}

//널포인터 익셉션, 인덱스 아웃오브 바운드 익셉션 예외처리 할것 (-실제운용시 데이터가 없을 확률이 적으므로 추후 처리)