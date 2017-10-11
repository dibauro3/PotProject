package com.example.parkjaeha.firebasetest;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by hyeongkun on 2017-04-04.
 */
//moi 수분량
//ill 빛
//temp 온도
//humi 습도
//waterLv 수위
/*식물의 수분,습도,온도 정보를 한꺼번에 그래프와 table 형식으로 보여준다.*/
public class DrawTotalGraphActivity extends AppCompatActivity {

    private LineChart mChart;
    ////"http://211.44.136.164:7777/Project/period/1hour.php"
    String url = "http://211.44.136.164:7777/Project/period/1hour_1.php";                                   //웹서버 주소 default
    phpDown task;
    ArrayList<GraphListItem> listItem_temp = new ArrayList<GraphListItem>();
    ArrayList<GraphListItem> listItem_humi = new ArrayList<GraphListItem>();                                    //JSON 객체를 저장할 배열리스트
    ArrayList<GraphListItem> listItem_ill = new ArrayList<GraphListItem>();
    ArrayList<GraphListItem> listItem_moi = new ArrayList<GraphListItem>();

    String json_string=null,json_test=null;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapter contactAdapter;
    ListView listView;

    NetworkState networkState = new NetworkState();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        json_test = getIntent().getExtras().getString("Graph");
        json_string = getIntent().getExtras().getString("json_data");                      //intent로 넘어온 json data 가져오기

        if(json_test != null || json_string != null) {


            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_drawtotalgraph);

            mChart = (LineChart) findViewById(R.id.chart1);
            mChart.getDescription().setEnabled(false);
            // enable touch gestures
            mChart.setTouchEnabled(true);
            mChart.setDragDecelerationFrictionCoef(0.9f);
            // enable scaling and dragging
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);
            mChart.setDrawGridBackground(false);
            mChart.setHighlightPerDragEnabled(false);
            // if disabled, scaling can be done on x- and y-axis separately
            mChart.setPinchZoom(true);
            // set an alternative background color
            mChart.setBackgroundColor(Color.WHITE);
            mChart.getAxisRight().setDrawLabels(false);
            mChart.getXAxis().setDrawLabels(false);

            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.setTextColor(Color.BLACK);
            if (!networkState.isConnected(DrawTotalGraphActivity.this)) {
                networkState.buildDialog(DrawTotalGraphActivity.this).show();
            } else {

                task = new phpDown();
                task.execute(url);
                new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
                    @Override
                    public void run() {

                        setData();

                    }
                }, 3000);

            }

            listView = (ListView) findViewById(R.id.listview);                              //listview 선언
            contactAdapter = new ContactAdapter(this, R.layout.activity_row_layout);        // adapter 선언하기


                listView.setAdapter(contactAdapter);                                                //listview set
                try {
                    //json_data JSON 객체로 변환하여 contacts에  저장하기
                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("server_response");
                    int count = 0;
                    String moi, ill, temp, humi, date;
                    while (count < jsonArray.length()) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        moi = JO.getString("moi");
                        ill = JO.getString("ill");
                        temp = JO.getString("temp");
                        humi = JO.getString("humi");
                        date = JO.getString("date");

                        Contacts contacts = new Contacts(moi, ill, temp, humi, date);
                        contactAdapter.add(contacts);

                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


        }else{
            setContentView(R.layout.activity_drawtotalgraph);
            Toast.makeText(getApplicationContext(), "가져온 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }

    }


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

        public void onPostExecute(String str) {                                                     //가져온 데이터를 원하는 형태로 파싱 하는 부분

            String id, temp, humi, ill, moi;                                                //변수명 추가 및 변경

            if(str !=null){

            try {
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");

                for (int i = 0; i < ja.length(); i++) {

                    JSONObject jo = ja.getJSONObject(i);
                    id = jo.getString("id");
                    moi = jo.getString("moi"); //추가
                    temp = jo.getString("temp");
                    humi = jo.getString("humi");
                    ill = jo.getString("ill");


                    listItem_temp.add(new GraphListItem(id, temp));
                    listItem_humi.add(new GraphListItem(id, humi));
                    listItem_ill.add(new GraphListItem(id, ill));
                    listItem_moi.add(new GraphListItem(id, moi));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (NullPointerException e) {
                e.printStackTrace();
            }

        }else{
            Toast.makeText(getApplicationContext(),"그래프 데이터를 불러오지 못하였습니다.",Toast.LENGTH_SHORT).show();
        }


        }
    }

    private void setData() {

        ArrayList<Entry> values1 = new ArrayList<Entry>();

        values1.clear();

        for (int i = 0; i < listItem_temp.size(); i++) {                                            //온도데이터셋
            float myvalue = (float) (Double.parseDouble(listItem_temp.get(i).getData(1)));
            Entry mEntry = new Entry(i, myvalue);
            values1.add(mEntry);
        }


        ArrayList<Entry> values2 = new ArrayList<Entry>();
        values2.clear();


        for (int i = 0; i < listItem_humi.size(); i++) {                                            //습도데이터셋
            float myvalue2 = (float) (Double.parseDouble(listItem_humi.get(i).getData(1)));
            Entry mEntry2 = new Entry(i, myvalue2);
            values2.add(mEntry2);
        }


        ArrayList<Entry> values3 = new ArrayList<Entry>();
        values3.clear();

        for (int i = 0; i < listItem_ill.size(); i++) {                                             //조도데이터셋
            float myvalue3 = (float) (Double.parseDouble(listItem_ill.get(i).getData(1)));
            Entry mEntry3 = new Entry(i, myvalue3);
            values3.add(mEntry3);
        }

        ArrayList<Entry> values4 = new ArrayList<Entry>();
        values4.clear();

        for (int i = 0; i < listItem_moi.size(); i++) {                                             //수분량데이터셋
            float myvalue4 = (float) (Double.parseDouble(listItem_moi.get(i).getData(1)));
            float fm_myvalue4= myvalue4/30;
            Entry mEntry4 = new Entry(i, fm_myvalue4);
            values4.add(mEntry4);
        }

        LineDataSet set1, set2, set3 , set4;



        set1 = new LineDataSet(values1, "온도");

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(Color.RED);
        set1.setCircleColor(Color.RED);
        set1.setLineWidth(2f);
        set1.setCircleRadius(1f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);


        // create a dataset and give it a type
        set2 = new LineDataSet(values2, "습도");
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(ColorTemplate.getHoloBlue());
        set2.setCircleColor(ColorTemplate.getHoloBlue());
        set2.setLineWidth(2f);
        set2.setCircleRadius(1f);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.RED);
        set2.setDrawCircleHole(false);
        set2.setHighLightColor(Color.rgb(244, 117, 117));


        set3 = new LineDataSet(values3, "조도");
        set3.setAxisDependency(YAxis.AxisDependency.LEFT);
        set3.setColor(Color.YELLOW);
        set3.setCircleColor(Color.YELLOW);
        set3.setLineWidth(2f);
        set3.setCircleRadius(1f);
        set3.setFillAlpha(65);
        set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
        set3.setDrawCircleHole(false);
        set3.setHighLightColor(Color.rgb(244, 117, 117));

        set4 = new LineDataSet(values4, "수분량");
        set4.setAxisDependency(YAxis.AxisDependency.LEFT);
        set4.setColor(Color.GREEN);
        set4.setCircleColor(Color.GREEN);
        set4.setLineWidth(2f);
        set4.setCircleRadius(1f);
        set4.setFillAlpha(65);
        set4.setFillColor(ColorTemplate.colorWithAlpha(Color.GREEN, 200));
        set4.setDrawCircleHole(false);
        set4.setHighLightColor(Color.rgb(244, 117, 117));

        // create a data object with the datasets
        LineData data = new LineData(set1, set2, set3, set4);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
        mChart.invalidate();
    }


}
