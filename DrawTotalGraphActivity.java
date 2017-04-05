package com.example.hyeongkun.myapplication;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

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

public class DrawTotalGraphActivity extends AppCompatActivity {

    private LineChart mChart;

    String url = "http://192.168.20.165/Project/period/1hour.php";                                    //웹서버 주소 default
    phpDown task;
    ArrayList<ListItem> listItem_temp = new ArrayList<ListItem>();
    ArrayList<ListItem> listItem_water = new ArrayList<ListItem>();                                 //JSON 객체를 저장할 배열리스트
    ArrayList<ListItem> listItem_lux = new ArrayList<ListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        task = new phpDown();
        task.execute(url);
        new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
            @Override
            public void run() {
                setData(0, 0);
            }
        }, 3000);

        // get the legend (only possible after setting data)
      /*  Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
      //  l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = mChart.getXAxis();
      //  xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = mChart.getAxisLeft();
     //  leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = mChart.getAxisRight();
      //  rightAxis.setTypeface(mTfLight);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(900);
        rightAxis.setAxisMinimum(-200);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        */

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

        public void onPostExecute(String str) {                          //가져온 데이터를 원하는 형태로 파싱 하는 부분

            String id, temp, water, lux;

            try {
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");

                for (int i = 0; i < ja.length(); i++) {

                    JSONObject jo = ja.getJSONObject(i);
                    id = jo.getString("id");
                    temp = jo.getString("temp");
                    water = jo.getString("water");
                    lux = jo.getString("lux");

                    listItem_temp.add(new ListItem(id, temp));
                    listItem_water.add(new ListItem(id, water));
                    listItem_lux.add(new ListItem(id, lux));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private void setData(int count, float range) {

        ArrayList<Entry> values1 = new ArrayList<Entry>();

        values1.clear();

        for (int i = 0; i < listItem_temp.size(); i++) {

            float myvalue = (float) (Integer.parseInt(listItem_temp.get(i).getData(1)));
            Entry mEntry = new Entry(i, myvalue);
            values1.add(mEntry);
        }


        ArrayList<Entry> values2 = new ArrayList<Entry>();
        values2.clear();


        for (int i = 0; i < listItem_water.size(); i++) {

            float myvalue2 = (float) (Integer.parseInt(listItem_water.get(i).getData(1)));
            Entry mEntry2 = new Entry(i, myvalue2);
            values2.add(mEntry2);
        }


        ArrayList<Entry> values3 = new ArrayList<Entry>();
        values3.clear();

        for (int i = 0; i < listItem_water.size(); i++) {

            float myvalue3 = (float) (Integer.parseInt(listItem_lux.get(i).getData(1)));
            Entry mEntry3 = new Entry(i, myvalue3);
            values3.add(mEntry3);
        }

        LineDataSet set1, set2, set3;

        /*if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            //set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
            set1.setValues(values1);
            set2.setValues(values2);
            //set3.setValues(yVals3);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } */
        /*else {*/
        // create a dataset and give it a type
        set1 = new LineDataSet(values1, "온도");

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(Color.RED);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        //set1.setFillFormatter(new MyFillFormatter(0f));
        //set1.setDrawHorizontalHighlightIndicator(false);
        //set1.setVisible(false);
        //set1.setCircleHoleColor(Color.WHITE);

        // create a dataset and give it a type
        set2 = new LineDataSet(values2, "수분량");
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setColor(ColorTemplate.getHoloBlue());
        set2.setCircleColor(Color.BLACK);
        set2.setLineWidth(2f);
        set2.setCircleRadius(3f);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.RED);
        set2.setDrawCircleHole(false);
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        //set2.setFillFormatter(new MyFillFormatter(900f));

        set3 = new LineDataSet(values3, "조도");
        set3.setAxisDependency(YAxis.AxisDependency.LEFT);
        set3.setColor(Color.YELLOW);
        set3.setCircleColor(Color.BLACK);
        set3.setLineWidth(2f);
        set3.setCircleRadius(3f);
        set3.setFillAlpha(65);
        set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
        set3.setDrawCircleHole(false);
        set3.setHighLightColor(Color.rgb(244, 117, 117));

        // create a data object with the datasets
        LineData data = new LineData(set1, set2, set3);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
        mChart.invalidate();
    }


}
