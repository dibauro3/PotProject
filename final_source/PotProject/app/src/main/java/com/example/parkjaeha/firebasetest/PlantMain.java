package com.example.parkjaeha.firebasetest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by parkjaeha on 2017-04-14.
 */
/*어플의 메인화면으로 다른 서비스를 이용하는데 용이하도록 navigation을 이용해 구현하였다.*/
public class PlantMain extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    GoogleApiClient mGoogleApiClient;
    static final String TAG = PlantMain.class.getName();
    String json_String=null,json_Gallery=null,uid,name=null;
    private FirebaseUser mCurrentUsers;
    TextView tv_uid;
    String latest_img=null;
    Bitmap img_bitmap = null;
    TextView tv_user ;

    PlantDicInfoMenu pld = new PlantDicInfoMenu();

    ////////////////////////
    private Button button_t, button_h,button_l,button_total;
    private ImageView wlevel_img;
    ImageButton btn_Image;
    private DrawerLayout mDrawerLayout;
    String plantName;
    TextView t_plantname,tv_drawer;
    phpDown task= new phpDown();
    String waterlevel;
    String json_device=null,json_test=null;


    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_parrot);

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        String token = FirebaseInstanceId.getInstance().getToken();
        FirebaseInstanceIDService fcm= new FirebaseInstanceIDService();
        fcm.registerToken(token);
        Log.d(TAG,"Refreshed token:"+token);

        tv_uid = (TextView) findViewById(R.id.textView3);
        //btn_Image = (ImageButton) findViewById(R.id.ImageButton);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUsers = mAuth.getCurrentUser();
        uid = mCurrentUsers.getUid();

        name = mCurrentUsers.getEmail().toString();


//Gallery latest_image_data
      /*  if(getIntent().getExtras().getString("gallery_data") != null) {
            latest_img =getIntent().getExtras().getString("gallery_data");
        }else{
            btn_Image.setImageResource(R.drawable.icontomato);
        }
        */

/*        if(latest_img != null){
            img_bitmap = new GalleryImageRoader().getBitmapImg("img_load/" + latest_img);
            btn_Image.setImageBitmap(img_bitmap);
        }
*/

        //////////////////////////////
        //graph데이터 , device, 저장데이터,이미지,워터레벨
        new phpDown1().execute("http://211.44.136.164:7777/Project/period/1hour_1.php");
        new GetDevice().execute();
      new BackgroundTask().execute();
        new BackImageTask().execute();
        task.execute("http://211.44.136.164:7777/Project/period/1hour_1.php");
        //////////////////////////////

        /*
//Login Data check
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null ){
                    Intent intent = new Intent(PlantMain.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        */
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();


        t_plantname = (TextView) findViewById(R.id.text_plantname);

        SharedPreferences sf = getSharedPreferences("sf", Context.MODE_PRIVATE);



        Intent intent=getIntent();
        if(( intent.getStringExtra("check")).equals("1")){

        }else{
            plantName = intent.getStringExtra("PLANT_NAME");
        }

       /* if(pld.){
            Toast.makeText(getApplicationContext(),"가져옴",Toast.LENGTH_SHORT).show();

            plantName = intent.getStringExtra("PLANT_NAME");
        }
*/

        if(TextUtils.isEmpty(plantName)){
            String str = sf.getString("plant", "No input");
            t_plantname.setText(str.toString());
        }else{
            t_plantname.setText(plantName);
        }

        String url="http://211.44.136.164:7777/Project/get_wlevel.php";
        //task.execute(url);

//////////////////////////////////////////////////////////////////////////////////////////
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //액션바 세팅부분
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu3);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View nav_header_view = navigationView.getHeaderView(0);
        tv_user =  (TextView)nav_header_view.findViewById(R.id.tv_drawer);
        tv_user.setText(name);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);

                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.plant_dictionary:
                        Intent plant_dic_intent= new Intent(getApplicationContext(),PlantListDicActivity.class);
                        startActivity(plant_dic_intent);
                        break;

                    case R.id.using_desc:
                        Intent using_desc_i= new Intent(getApplicationContext(),ViewPagerActivity.class);
                        startActivity(using_desc_i);
                        break;

                    case R.id.plant_register:
                        Intent plant_reg_intent= new Intent(getApplicationContext(),PotRegActivity.class);
                        plant_reg_intent.putExtra("USER",name);
                        plant_reg_intent.putExtra("DEVICE",json_device);
                        startActivity(plant_reg_intent);
                        break;

                    case R.id.nav_sub_menu_item01:
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"qkrwogk110@naver.com"});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"관리자에게 건의하기");
                        emailIntent.setType("message/rfc822");
                        startActivity(Intent.createChooser(emailIntent,"Choose email client..."));
                        break;

                    case R.id.nav_sub_menu_gallery:

                        Intent intent = new Intent(PlantMain.this,GalleryActivity.class);
                        intent.putExtra("json_Gallery",json_Gallery);
                        startActivity(intent);
                        break;
                    case R.id.nav_sub_menu_signout:
                        logout();
                        break;


                }

                return true;
            }
        });


        button_t=(Button)findViewById(R.id.button_t);
        button_t.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent i= new Intent(getApplicationContext(),DrawGraphActivity.class);
                i.putExtra("GRAPH_OPTION","temp");
                i.putExtra("Graph",json_test);
                startActivity(i);
            }
        });

        button_h =(Button)findViewById(R.id.button_h);
        button_h.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent i= new Intent(getApplicationContext(),DrawGraphActivity.class);
                i.putExtra("GRAPH_OPTION","humi");
                i.putExtra("Graph",json_test);
                startActivity(i);
            }
        });

        button_l=(Button)findViewById(R.id.button_m);
        button_l.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent i= new Intent(getApplicationContext(),DrawGraphActivity.class);
                i.putExtra("GRAPH_OPTION","moi");
                i.putExtra("Graph",json_test);
                startActivity(i);
            }
        });


        button_total=(Button)findViewById(R.id.button_total);
        button_total.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {


                Intent total_graph_intent= new Intent(getApplicationContext(),DrawTotalGraphActivity.class);
                total_graph_intent.putExtra("json_data",json_String);
                total_graph_intent.putExtra("Graph",json_test);
                startActivity(total_graph_intent);
            }
        });

        wlevel_img=(ImageView) findViewById(R.id.wl_imageView);

        ImageButton stream_btn=(ImageButton)findViewById(R.id.imageButton);
        stream_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent web_i=new Intent(getApplicationContext(),WebStreamActivity.class);
                startActivity(web_i);
            }
        });


    }

    /////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sf= getSharedPreferences("sf", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sf.edit();

        String str= t_plantname.getText().toString();
        Log.d("★★★★★온스탑될때 텍스트값",str);
        editor.putString("plant",str);
        editor.commit();

    }
    ////////////////////////////////////////////////////////////////////////////////

    class BackImageTask extends AsyncTask<Void,Void,String> {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute(){
            json_url = "http://211.44.136.164:7777/Project/folder_image.php";

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while((JSON_STRING = bufferedReader.readLine()) != null){
                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return  stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (NullPointerException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected  void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result !=null) {
                json_Gallery = result;
            }

        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    class GetDevice extends AsyncTask<Void,Void,String> {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute(){
            json_url = "http://211.44.136.164:7777/Project/available.php";

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while((JSON_STRING = bufferedReader.readLine()) != null){
                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return  stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (NullPointerException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected  void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result !=null) {
                json_device = result;
            }
             }
    }
    /////////////////////////////////////////////////////////////////////////////////////////
    //Json Get
    class BackgroundTask extends AsyncTask<Void,Void,String> {

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute(){
            json_url = "http://211.44.136.164:7777/Project/json_listdata.php";

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while((JSON_STRING = bufferedReader.readLine()) != null){
                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return  stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected  void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result !=null) {
                json_String = result;
            }


        }
    }

    ////////////////////////////////////////////////////////////////////


    protected class phpDown extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);                // 연결되었으면.
                    if (conn != null) {

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

            String wlevel;
            Log.d("수위센서 값aaaaaa:", str);

            if(str !=null) {
                try {
                    JSONObject root = new JSONObject(str);
                    JSONArray ja = root.getJSONArray("result");

                    for (int i = ja.length()-1; i < ja.length(); i++) {

                        JSONObject jo = ja.getJSONObject(i);
                        wlevel = jo.getString("waterLV");

                        waterlevel = wlevel;
                        Log.d("수위센서 값:", waterlevel);
                        if (waterlevel.equals("1")) {
                            wlevel_img.setImageResource(R.drawable.waterlv1);
                        } else {
                            wlevel_img.setImageResource(R.drawable.waterlv0);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }catch(NullPointerException e){
                    e.printStackTrace();
                }

            }

        }
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // 핸들 액션바 조작 추후 설정 추가할것
            int id = item.getItemId();

            switch (id) {
                case android.R.id.home:
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    return true;

            }

            return super.onOptionsItemSelected(item);
        }

        //LogOut fun
        private void logout() {

            mAuth.signOut();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            Intent intent = new Intent(PlantMain.this, LoginActivity.class);
            startActivity(intent);
        }


        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Log.d(TAG, "onConnectionFailed:" + connectionResult);
        }

/////////////////////////////////////////////////
protected class phpDown1 extends AsyncTask<String, Integer, String> {
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


        if (str != null) {

            try {
                JSONObject root = new JSONObject(str);
                JSONArray ja = root.getJSONArray("result");
                json_test = String.valueOf(ja.length());
                if(json_test.equals("0")){
                    json_test = null;
                }
                Log.d("ja.length",String.valueOf(ja.length()));

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
           }else{
            json_test = null;
        }


        }

    }


}
