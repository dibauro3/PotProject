package com.example.parkjaeha.firebasetest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by parkjaeha on 2017-02-03.
 */
/* 서버로부터 가져온 사진 데이터를 비트맵 형식으로 변환하여 갤러리 뷰를 이용해 식물의 사진을 가져와 뿌려준다.*/
//setup Activity
public class GalleryActivity extends AppCompatActivity  {


    String json_Img;
    Button btn_img_load,free;
     String[] img_path = null;
    String img_size;
    GridView gridView;
    Bitmap[] img_bitmap;
    TextView textView;
    AsyncTask<Void, Void, Void> mTask;
    private String json_String,minfo;
    String name = null;
    MyAdapter adapter = null;
String json_gallery = null;
    ProgressDialog progressDialog;
    JSONObject jsonObject = null;
    JSONArray jsonArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gridView = (GridView) findViewById(R.id.grid_view);
       // btn_img_load.setOnClickListener(this);
        json_gallery = getIntent().getExtras().getString("json_Gallery");               //intent data get

        if(json_gallery !=null){

        try {                                                                       //json 객체로 변환 하여 배열에 저장
            jsonObject= new JSONObject(json_gallery);
            jsonArray = jsonObject.getJSONArray("response_data");
            int count=0;
            img_path =new String[jsonArray.length()];
            String name=null;
            while(count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                name= JO.getString("url");
                img_path[count] = name;

                System.out.println("total:"+name);
                System.out.println("total123:"+img_path[count]);
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }

            img_bitmap = new Bitmap[img_path.length];               //길이를 가져와 객체 선언 및 비트맵 형식으로 바꾸고 이미지 가져와 저장
            for(int i =0; img_path.length>i;i++) {
                img_bitmap[i] = new GalleryImageRoader().getBitmapImg("Project/img_load/" + img_path[i]);
            }

            adapter = new MyAdapter(getApplicationContext(),R.layout.activity_rowimg,img_bitmap);       //adapter로 row에 비트맵이미지 설정하기


            gridView.setAdapter(adapter);                   //gridview 에 set하기


        }else{
        Toast.makeText(getApplicationContext(),"list 데이터를 불러오지 못하였습니다.",Toast.LENGTH_SHORT).show();
    }





        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {            //position clik시 main화면으로 이동
                Intent intent = new Intent(GalleryActivity.this,PlantMain.class);
                intent.putExtra("gallery_data",position);
                startActivity(intent);

                //textView.setText("position : " + position);
            }
        });


        //세번째 버튼은 테이블을 검색 하는 역할

    }






    class MyAdapter extends BaseAdapter                 //adapter

    {
        Context context;            //변수 선언
        int layout;
        Bitmap img[];
        LayoutInflater inf;

        public MyAdapter(Context context, int layout, Bitmap[] img) {
            this.context = context;
            this.layout = layout;
            this.img = img;
            inf = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override                                                               //이미지 길이,포지션, getview를 통해 이미지 view에  set
        public int getCount() {
            return img.length;
        }

        @Override
        public Object getItem(int position) {
            return img[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null)
                convertView = inf.inflate(layout, null);
            ImageView iv = (ImageView)convertView.findViewById(R.id.img_gridView);
            iv.setImageBitmap(img[position]);

            return convertView;
        }
    }
}
