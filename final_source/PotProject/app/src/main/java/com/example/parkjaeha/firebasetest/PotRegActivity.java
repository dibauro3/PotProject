package com.example.parkjaeha.firebasetest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by parkjaeha on 2017-02-08.
 */
/*화분의 기기를 등록하기 위해 만든 클래스이다. QR코드를 인식하여 등록하는 방식이다. */
public class PotRegActivity extends AppCompatActivity{

     ArrayList<String> items = new ArrayList<String>() ;
     ArrayAdapter adapter;
    private Button button;
    ListView listView;
    String user=null,GETDEVICE=null;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String[] device_number = new String[10];
    NetworkState networkState = new NetworkState();
    ProgressDialog progressDialog;
    Boolean  bool;

    @Override
    protected  void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_potreg);

        button = (Button) this.findViewById(R.id.btn_QR);

        user = getIntent().getExtras().getString("USER");
        GETDEVICE = getIntent().getExtras().getString("DEVICE");               //intent data get
        progressDialog = new ProgressDialog(this);

        Log.d("deviceData",GETDEVICE+user);

if(GETDEVICE !=null) {
    try {                                                                       //json 객체로 변환 하여 배열에 저장
        jsonObject = new JSONObject(GETDEVICE);
        jsonArray = jsonObject.getJSONArray("device_response");
        int count = 0;
        device_number = new String[jsonArray.length()];
        String device = null;
        while (count < jsonArray.length()) {
            JSONObject JO = jsonArray.getJSONObject(count);
            device = JO.getString("device");
            device_number[count] = device;

            System.out.println("device123:" + device_number[count]);
            count++;
        }

    } catch (JSONException e) {
        e.printStackTrace();
    } catch (NullPointerException e) {
        e.printStackTrace();
    }

}else{
    Toast.makeText(getApplicationContext(),"데이터를 불러오지 못하였습니다.",Toast.LENGTH_SHORT).show();
}

        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;
        // listview 생성 및 adapter 지정.
         ListView listview = (ListView) findViewById(R.id.listView2) ;
        listview.setAdapter(adapter) ;


        final Activity activity = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        //scan한 데이터가 리스트에 저장되었을때 선택시 리스트 데이터를 보낸다.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),PlantListDicActivity.class);
                Log.d("plantdata:",items.get(position));
                intent.putExtra("PLANT_NUMBER",items.get(position));    // 스캔정보가 넘어가면 db 데이터와 확인하여 관련 테이블에 식물을 저장한다.
                intent.putExtra("PLANT_USER",user);
                //intent.putExtra("PLANT_STATE","1");
                startActivity(intent);
            }
        });
    }
//데이터가 스캔되면 db에 저장하고 저장된 데이터를 확인해야한다.


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=null){
            if(result.getContents() == null){
                Toast.makeText(this,"cancelled",Toast.LENGTH_LONG).show();
            }else {
                bool= false;
                String device = result.getContents().toString();
                //아이디별 저장하는것 해야됨
                progressDialog.show();

                if (GETDEVICE != null) {
                for (int i = 0; i < device_number.length; i++) {
                    Log.d("devicenum123:",device_number[i]+" / " +device_number.length);
                     if (device_number[i].equals(device)) {

                        bool = true;
                         Log.d("getdevice123:",bool+"");
                    }
                }

                    Log.d("getdevice789:",user+"/"+device);
                    Log.d("getdevice456:",bool+"");
                //스캔시 데이터가 db에 저장된 고유의 기기인지 확인하는 if문 필요함 이후 등록도 되어야함 가져오고 저장하고를 동시에 해야함

                if(bool==true &&  networkState.isConnected(PotRegActivity.this)) {
                    //String method = "device";
                   // BackgroundTask backgroundTask = new BackgroundTask(PotRegActivity.this);
                    Log.d("getdevice7891:",user+"/"+device);
                    Log.d("getdevice123:",bool+"");
                    if(items.size() != 0) {
                        for (int i = 0; i < items.size(); i++) {

                            if (items.get(i).equals(device)) {
                               // Toast.makeText(getApplicationContext(), "이미 확인된 기기입니다.", Toast.LENGTH_SHORT).show();
                            } else if (!(items.get(i).equals(device))&& i==items.size()-1){
                                Log.d("getdevice789:",user+"/"+device);
                                //backgroundTask.execute(method, user,pot_number, device);
                                items.add(device);
                            }
                        }
                    }else{
                        items.add(device);
                    }

                     }else{
                    //network check_line
                    Toast.makeText(getApplicationContext(), "존재하지 않는 기기 or 네트워크를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
                    progressDialog.dismiss();

                    adapter.notifyDataSetChanged();
                    // listview 갱신


                }else{
                    Toast.makeText(getApplication(),"데이터를 확인 할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }


            }

        }else{
            super.onActivityResult(resultCode,resultCode,data);
        }
    }

}
