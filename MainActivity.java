package com.example.hyeongkun.myapplication;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnSeekBarChangeListener {
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// JSON
    String url = "http://192.168.20.65/Project/example2.php";                                               //웹서버 주소
    phpDown task;
    ArrayList<ListItem> listItem = new ArrayList<ListItem>();                                               //JSON 객체를 저장할 배열리스트
    TextView tv;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private LineChart myChart;
    private SeekBar mySeekbar1, mySeekbar2;
    private TextView myTextview1, myTextview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        tv = (TextView) findViewById(R.id.textView1);
        Log.d("사이즈 크기1:",String.valueOf(listItem.size()));
        task = new phpDown();
        task.execute(url);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        myChart = (LineChart) findViewById(R.id.chart1);

        myTextview1 = (TextView) findViewById(R.id.tvXMax);
        myTextview2 = (TextView) findViewById(R.id.tvYMax);

        mySeekbar1 = (SeekBar) findViewById(R.id.seekBar1);
        mySeekbar2 = (SeekBar) findViewById(R.id.seekBar2);

        mySeekbar1.setProgress(5);
        mySeekbar2.setProgress(5);
        mySeekbar1.setOnSeekBarChangeListener(this);
        mySeekbar2.setOnSeekBarChangeListener(this);

        new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
            @Override
            public void run() {
                setData(0, 0);
            }
        },2500);                    //2.5초딜레이

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

            String num;
            String val;

            try {
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");

                for (int i = 0; i < ja.length(); i++) {

                    JSONObject jo = ja.getJSONObject(i);
                    num = jo.getString("num");
                    val = jo.getString("val");

                    listItem.add(new ListItem(num, val));

                }
                Log.d("사이즈 크기2:",String.valueOf(listItem.size()));


            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int j=0; j<6;j++){
                tv.append(listItem.get(j).getData(0) + "-" + listItem.get(j).getData(1) + " ");
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        myTextview1.setText("" + (mySeekbar1.getProgress() + 1));
        myTextview2.setText("" + (mySeekbar2.getProgress()));
        setData(mySeekbar1.getProgress() + 1, mySeekbar2.getProgress());    //seekbar의 이동에따라 달라지는 데이터개수

        // redraw
        myChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    public void setData(int count, int range) {          //데이터 세팅해서 받아오는 함수

        ArrayList<Entry> values = new ArrayList<Entry>();

        Log.d("사이즈 크기3:",String.valueOf(listItem.size()));

        /*for(int i=0; i<18;i++) {                                                                      //예시
            float val2[] = {22, 20, 21, 21, 19, 18, 21 ,22,23,23,24,25,27,20,21,19,19,21};
            float val = (float) (val2[i]);
            values.add(new Entry(i, val));

        }*/

        for(int i = 0; i< listItem.size(); i++ ){                                                   //파싱한 데이터를 엔트리에 넣어준다

            float myvalue=(float)(Integer.parseInt(listItem.get(i).getData(1)));
            Entry mEntry= new Entry(i,myvalue);
            values.add(mEntry);
        }

        //////////////////////////////////////////////////////////////////////////////////////////고쳐야할부분
        /*
        for(int i=0;i<6;i++){

            float val=(float)(number[i]);
            values.add(new Entry(i,val));

        }*/
        ////////////////////////////////////////////////////////////////////////////////////////////

            LineDataSet mySet;

            if (myChart.getData() != null &&
                    myChart.getData().getDataSetCount() > 0) {
                mySet = (LineDataSet) myChart.getData().getDataSetByIndex(0);
                mySet.setValues(values);
                myChart.getData().notifyDataChanged();
                myChart.notifyDataSetChanged();
            } else {
                // create a dataset and give it a type
                mySet = new LineDataSet(values, "MyDataSet");

                // set the line to be drawn like this "- - - - - -"
                mySet.enableDashedLine(10f, 5f, 0f);
                mySet.setColor(Color.BLACK);
                mySet.setCircleColor(Color.BLACK);
                mySet.setLineWidth(1f);
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
                dataSets.add(mySet); // add the datasets

                // create a data object with the datasets
                LineData data = new LineData(dataSets);

                // set data
                myChart.setData(data);
                myChart.invalidate();
            }
        }
}

