package com.example.parkjaeha.firebasetest;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
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
import java.util.List;


//moi 수분량
//ill 빛
//temp 온도
//humi 습도
//waterLv 수위
/* 센서로 부터 받아온 식물의 상태를 그래프로 보여주는 클래스이다.*/
public class DrawGraphActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {//KPU_Wireless 확인


    //"http://211.44.136.164:7777/Project/"
    String url = "http://211.44.136.164:7777/Project/";                                                     //웹서버 주소 default
    phpDown task;
    ArrayList<GraphListItem> listItem_temp = new ArrayList<GraphListItem>();
    ArrayList<GraphListItem> listItem_humi = new ArrayList<GraphListItem>();                                     //JSON 객체를 저장할 배열리스트
    ArrayList<GraphListItem> listItem_moi = new ArrayList<GraphListItem>();


    private LineChart myChart;
    public RadioGroup myRadioGroup;
    public RadioButton rb_10min, rb_30min, rb_1hour;

    NetworkState networkState = new NetworkState();
    String op,json_test=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawgraph);

        json_test = getIntent().getExtras().getString("Graph");

        if(json_test != null) {

            Intent intent = getIntent();
            op = intent.getStringExtra("GRAPH_OPTION");
            Log.d("선택옵션값", op);

            myChart = (LineChart) findViewById(R.id.chart1);

            myChart.setOnChartGestureListener(this);                //추가부분 -> 마커뷰 클래스 마커뷰 레이아웃
            myChart.setOnChartValueSelectedListener(this);
            myChart.setTouchEnabled(true);
            myChart.setDragEnabled(true);
            myChart.setScaleEnabled(true);
            myChart.setPinchZoom(true);
            myChart.setBackgroundColor(Color.WHITE);


            XAxis xAxis = myChart.getXAxis();
            xAxis.enableGridDashedLine(10f, 10f, 0f);

            MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
            mv.setChartView(myChart); // For bounds control
            myChart.setMarker(mv); // Set the marker to the chart

/////////////////////////////////////////////////////////////////////////
            switch (op) {                                                                                 //옵션값변경
                case "temp":
                    myChart.getDescription().setText("온도데이터");
                    break;
                case "humi":
                    myChart.getDescription().setText("습도데이터");
                    break;
                case "moi":
                    myChart.getDescription().setText("수분량데이터");
                    break;

            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////////
            //x축
            myChart.getAxisRight().setDrawLabels(false);                                            //변경부분
            myChart.getXAxis().setDrawLabels(true);
            myChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

            myRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
            rb_10min = (RadioButton) findViewById(R.id.rb_10min);
            rb_30min = (RadioButton) findViewById(R.id.rb_30min);
            rb_1hour = (RadioButton) findViewById(R.id.rb_1hour);

            myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                    if (!networkState.isConnected(DrawGraphActivity.this)) {
                        networkState.buildDialog(DrawGraphActivity.this).show();
                    } else {

                        switch (checkedId) {
                            case R.id.rb_10min:
                                url = "http://211.44.136.164:7777/Project/period/10min.php";
                                task = new phpDown();
                                task.execute(url);
                                new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
                                    @Override
                                    public void run() {
                                        setData(op);
                                    }
                                }, 3000);
                                break;

                            case R.id.rb_30min:
                                url = "http://211.44.136.164:7777/Project/period/30min.php";
                                task = new phpDown();
                                task.execute(url);
                                new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
                                    @Override
                                    public void run() {
                                        setData(op);
                                    }
                                }, 3000);
                                break;

                            case R.id.rb_1hour:
                                url = "http://211.44.136.164:7777/Project/period/1hour_1.php";
                                task = new phpDown();
                                task.execute(url);
                                new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
                                    @Override
                                    public void run() {
                                        setData(op);
                                    }
                                }, 3000);
                                break;

                        }

                    }//networkendline

                }
            });


            task = new phpDown();
            url = url + "period/1hour_1.php";
            task.execute(url);


            new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
                @Override
                public void run() {
                    setData(op);
                }
            }, 3000);

        }else {  //if문
            Toast.makeText(getApplicationContext(),"가져온 데이터가 없습니다.",Toast.LENGTH_SHORT).show();
        }


    }


    //서버에서 데아터를 가져오기 위한 메소드
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

        protected void onPostExecute(String str) {                                                  //가져온 데이터를 원하는 형태로 파싱 하는 부분

            String id, temp, humi, moi;

            if(str !=null){

            try {
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");

                for (int i = 0; i < ja.length(); i++) {

                    JSONObject jo = ja.getJSONObject(i);
                    id = jo.getString("id");
                    temp = jo.getString("temp");
                    humi = jo.getString("humi");
                    moi = jo.getString("moi");

                    listItem_temp.add(new GraphListItem(id, temp));
                    listItem_humi.add(new GraphListItem(id, humi));
                    listItem_moi.add(new GraphListItem(id, moi));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (NullPointerException e) {
                e.printStackTrace();
            }

        }else{
            Toast.makeText(getApplicationContext(),"데이터를 불러오지 못하였습니다.",Toast.LENGTH_SHORT).show();
        }

        }
    }

    public void setData(String option) {

        ArrayList<Entry> values = new ArrayList<Entry>();
        values.clear();

        switch (option) {
            case "temp":
                if(listItem_temp !=null) {
                    for (int i = 0; i < listItem_temp.size(); i++) {                                      //파싱한 데이터를 엔트리에 넣어준다
                        float myvalue = (float) (Double.parseDouble(listItem_temp.get(i).getData(1)));
                        Entry mEntry = new Entry(i, myvalue);       //Entry(x축,y축)
                        values.add(mEntry);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"temp no",Toast.LENGTH_SHORT).show();
                }

                break;

            case "humi":
               if(listItem_humi !=null) {
                   for (int i = 0; i < listItem_humi.size(); i++) {
                       float myvalue = (float) (Double.parseDouble(listItem_humi.get(i).getData(1)));
                       Entry mEntry = new Entry(i, myvalue);
                       values.add(mEntry);
                   }
               }else{
                   Toast.makeText(getApplicationContext(),"humi no",Toast.LENGTH_SHORT).show();
               }
                break;

            case "moi":
                if(listItem_moi !=null) {
                    for (int i = 0; i < listItem_moi.size(); i++) {
                        float myvalue = (float) (Double.parseDouble(listItem_moi.get(i).getData(1)));
                        Entry mEntry = new Entry(i, myvalue);
                        values.add(mEntry);
                    }
             }else{
            Toast.makeText(getApplicationContext(),"moi no",Toast.LENGTH_SHORT).show();
        }
                break;
        }


        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        LineDataSet mySet;

        // create a dataset and give it a type
        mySet = new LineDataSet(values, "측정량");

        // set the line to be drawn like this "- - - - - -"
        mySet.enableDashedLine(10f, 3f, 0f);

        YAxis yax = myChart.getAxisLeft();
        yax.setDrawGridLines(true);

        switch (option) {

            case "temp":
                mySet.setColor(Color.RED);
                yax.setAxisMaximum(35f);
                yax.setAxisMinimum(15f);
                yax.setGranularity(0.1f);
                break;
            case "humi":
                mySet.setColor(ColorTemplate.getHoloBlue());
                yax.setAxisMaximum(100f);
                yax.setAxisMinimum(30f);
                yax.setGranularity(0.1f);
                break;
            case "moi":
                mySet.setColor(Color.GREEN);
                yax.setAxisMaximum(1000f);
                yax.setAxisMinimum(0f);
                yax.setGranularity(0.1f);
                break;
        }

        mySet.setCircleColor(Color.TRANSPARENT);
        mySet.setLineWidth(1f);
        mySet.setCircleRadius(1f);
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
        listItem_humi.clear();
        listItem_moi.clear();
    }


    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            myChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + myChart.getLowestVisibleX() + ", high: " + myChart.getHighestVisibleX());
        Log.i("MIN MAX", "xmin: " + myChart.getXChartMin() + ", xmax: " + myChart.getXChartMax() + ", ymin: " + myChart.getYChartMin() + ", ymax: " + myChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.line, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(json_test != null) {

            switch (item.getItemId()) {
                case R.id.actionToggleValues: {
                    List<ILineDataSet> sets = myChart.getData()
                            .getDataSets();

                    for (ILineDataSet iSet : sets) {

                        LineDataSet set = (LineDataSet) iSet;
                        set.setDrawValues(!set.isDrawValuesEnabled());
                    }

                    myChart.invalidate();
                    break;
                }

                case R.id.animateX: {
                    myChart.animateX(3000);
                    break;
                }

            }

        }else{
            Toast.makeText(getApplicationContext(), "데이터가 없으면 동작하지 않습니다.", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}


//널포인터 익셉션, 인덱스 아웃오브 바운드 익셉션 예외처리 할것 (-실제운용시 데이터가 없을 확률이 적으므로 추후 처리)//널포인터 익셉션, 인덱스 아웃오브 바운드 익셉션 예외처리 할것 (-실제운용시 데이터가 없을 확률이 적으므로 추후 처리)