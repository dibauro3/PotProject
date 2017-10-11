package com.example.parkjaeha.firebasetest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hyeongkun on 2017-04-14.
 */
/*식물 관련 정보를 볼 수 있는 클래스이다. */
public class PlantDicInfoMenu extends AppCompatActivity {
    String plantName,plantState,plantNumber,plantUser;
    PlantData data = new PlantData();
    TextView tx_plant,tx_info,tv_t1,tv_t2;
    LinearLayout relativeLayout;
    TextView text_plant_info,text_plant_name;
    FrameLayout frame;
    ImageView img1_1,img1_2,img1_3,img1_4,img1_5,img2_1,img2_2,img2_3,img2_4,img2_5,img3_1,img3_2;
    String wsm[] = new String[4];
    RelativeLayout rl = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_info);

        ImageView iv = (ImageView) findViewById(R.id.imgview_plantInfo);
         text_plant_info=(TextView)findViewById(R.id.text_plantInfo);
        text_plant_name= (TextView)findViewById(R.id.text_plantName);
        Button reg_button =(Button)findViewById(R.id.reg_button);
        tx_plant = (TextView)findViewById(R.id.tx_plant);
        tx_info = (TextView)findViewById(R.id.tx_info);



        img1_1=(ImageView) findViewById(R.id.img1_1);
        img1_2=(ImageView) findViewById(R.id.img1_2);
        img1_3=(ImageView) findViewById(R.id.img1_3);
        img1_4=(ImageView) findViewById(R.id.img1_4);
        img1_5=(ImageView) findViewById(R.id.img1_5);

        img2_1=(ImageView) findViewById(R.id.img2_1);
        img2_2=(ImageView) findViewById(R.id.img2_2);
        img2_3=(ImageView) findViewById(R.id.img2_3);
        img2_4=(ImageView) findViewById(R.id.img2_4);
        img2_5=(ImageView) findViewById(R.id.img2_5);

        img3_1=(ImageView) findViewById(R.id.img3_1);
        img3_2=(ImageView) findViewById(R.id.img3_2);
        tv_t1=(TextView)findViewById(R.id.tv_t1);
        tv_t2=(TextView)findViewById(R.id.tv_t2);
        relativeLayout = (LinearLayout) findViewById(R.id.layout);                              //listview 선언

         rl = (RelativeLayout)findViewById(R.id.re_regi);
        rl.setBackgroundColor(Color.WHITE);

        frame = (FrameLayout) findViewById(R.id.frame) ;

        frame.removeView(relativeLayout);

        Intent intent=getIntent();
        plantName=intent.getStringExtra("PLANT_NAME");
        plantState=intent.getStringExtra("PLANT_STATE");
        plantNumber=intent.getStringExtra("PLANT_NUMBER");
        plantUser=intent.getStringExtra("PLANT_USER");

        if(plantState.equals("0")){
            reg_button.setVisibility(View.GONE);
        }
        else if(plantState.equals("1"))
        {
            reg_button.setVisibility(View.VISIBLE);
        }

        tx_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeView(0);

            }
        });
        tx_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            changeView(1);

            }
        });


        switch(plantName){
            case "방울토마토":
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.icontomato);
                //String info= getResources().getString(getResources().getIdentifier("tomato_info","string","com.example.parkjaeha.firebasetest"));
                text_plant_info.setText(data.tomato_info);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "10";
                wsm[3] = "30";

                break;

            case "당근(홍당무)":
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconcarrot);
                //String carrot_info= getResources().getString(getResources().getIdentifier("carrot_info","string","com.example.parkjaeha.firebasetest"));
                text_plant_info.setText(data.carrot_info);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "10";
                wsm[3] = "25";
                break;

            case "레몬":
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.lemonicon);
                text_plant_info.setText(data.lemon);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "10";
                wsm[3] = "40";
                break;

            case "스파티필름":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconpeacelily);
                text_plant_info.setText(data.peacelily);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "10";
                wsm[3] = "25";
                break;

            case "스패니쉬대거":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconspanishdagger);
                text_plant_info.setText(data.spanishdagger);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "10";
                wsm[3] = "20";
                break;

            case "올리브나무":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconolivetree);
                text_plant_info.setText(data.olivetree);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "10";
                wsm[3] = "30";
                break;

            case "바질":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconbasil);
                text_plant_info.setText(data.basil);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "10";
                wsm[3] = "30";
                break;

            case "장미":
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconrose);
                text_plant_info.setText(data.rose);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "10";
                wsm[3] = "40";
                break;

            case "금전수(돈나무)":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconzzplant);
                text_plant_info.setText(data.ZZplant);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "10";
                wsm[3] = "25";
                break;

            case "에피프렘넘":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.icongoldenpothos);
                text_plant_info.setText(data.goldenpothos);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "10";
                wsm[3] = "30";
                break;


            case "감자":
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconpotato);
                text_plant_info.setText(data.potato);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "10";
                wsm[3] = "30";
                break;

            case "오이":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconoi);
                text_plant_info.setText(data.oi);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "15";
                wsm[3] = "30";
                break;

            case "고구마":
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconipomoea_batatas);
                text_plant_info.setText(data.ipomoea_batatas);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "15";
                wsm[3] = "40";
                break;

            case "고추":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconchili_pepper);
                text_plant_info.setText(data.chili_pepper);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "15";
                wsm[3] = "30";
                break;

            case "딸기":
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconstrawberry);
                text_plant_info.setText(data.strawberry);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "7";
                wsm[3] = "35";
                break;

            case "해바라기":
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconsunflower);
                text_plant_info.setText(data.sunflower);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "15";
                wsm[3] = "40";
                break;

            case "피망":
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconpimang);
                text_plant_info.setText(data.pimang);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "15";
                wsm[3] = "35";
                break;

            case "상추":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconlactuca_sativa);
                text_plant_info.setText(data.lactuca_sativa);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "10";
                wsm[3] = "25";
                break;

            case "튤립":
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.icontulip);
                text_plant_info.setText(data.tulip);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "10";
                wsm[3] = "35";
                break;

            case "포인세티아":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconpoinsettia);
                text_plant_info.setText(data.poinsettia);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "15";
                wsm[3] = "35";
                break;


            case "양파":
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.icononion);
                text_plant_info.setText(data.onion);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "10";
                wsm[3] = "30";
                break;

            case "양상추":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconbutter_head);
                text_plant_info.setText(data.butter_head);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "10";
                wsm[3] = "25";
                break;

            case "배추":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconbechu);
                text_plant_info.setText(data.bechu);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "10";
                wsm[3] = "25";
                break;

            case "양배추":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconyangbechu);
                text_plant_info.setText(data.yangbechu);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "10";
                wsm[3] = "25";
                break;

            case "안개꽃":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconange);
                text_plant_info.setText(data.ange);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "10";
                wsm[3] = "25";
                break;

            case "토마토":
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.icontomato1);
                text_plant_info.setText(data.tomato);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "10";
                wsm[3] = "30";
                break;

            case "참외":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconchamyui);
                text_plant_info.setText(data.chamyui);
                wsm[0] = "2";
                wsm[1] = "4";
                wsm[2] = "10";
                wsm[3] = "30";
                break;

            case "가지":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.icongagi);
                text_plant_info.setText(data.gagi);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "15";
                wsm[3] = "30";
                break;

            case "무":
                //
               text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconmo);
                text_plant_info.setText(data.mo);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "10";
                wsm[3] = "25";
                break;

            case "시금치":
                //
                text_plant_name.setText(plantName);
                iv.setImageResource(R.drawable.iconsigeamchi);
                text_plant_info.setText(data.sigeamchi);
                wsm[0] = "2";
                wsm[1] = "3";
                wsm[2] = "10";
                wsm[3] = "25";
                break;

        }

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //db의 기기 number를 통해 식물을 저장한다.
                BackgroundTask backgroundTask = new BackgroundTask(PlantDicInfoMenu.this);
                String method = "device";
                backgroundTask.execute(method,plantUser,plantNumber,plantName);
                Toast.makeText(getApplicationContext(),"등록되었습니다.",Toast.LENGTH_LONG).show();
                Intent return_intent = new Intent(getApplicationContext(),PlantMain.class);

                return_intent.putExtra("PLANT_NAME",plantName);
                return_intent.putExtra("check","0");

                startActivity(return_intent);
            }
        });

    }


    private void changeView(int index) {



        // 0 번째 뷰 제거. (뷰가 하나이므로, 0 번째 뷰를 제거하면 모든 뷰가 제거됨.)
        frame.removeViewAt(0);

        // index에 해당하는 textView 표시
        switch (index) {
            case 0:
                frame.addView(text_plant_info);
                frame.removeView(relativeLayout) ;

                break;
            case 1:
                frame.addView(relativeLayout);

                if(wsm[0].equals("0")){

                }else if(wsm[0].equals("1")){
                    img1_1.setImageResource(R.drawable.water1600);
                    img1_2.setImageResource(R.drawable.water1600_2);
                    img1_3.setImageResource(R.drawable.water1600_2);
                    img1_4.setImageResource(R.drawable.water1600_2);
                    img1_5.setImageResource(R.drawable.water1600_2);
                }else if(wsm[0].equals("2")){
                    img1_1.setImageResource(R.drawable.water1600);
                    img1_2.setImageResource(R.drawable.water1600);
                    img1_3.setImageResource(R.drawable.water1600_2);
                    img1_4.setImageResource(R.drawable.water1600_2);
                    img1_5.setImageResource(R.drawable.water1600_2);
                }else if(wsm[0].equals("3")){
                    img1_1.setImageResource(R.drawable.water1600);
                    img1_2.setImageResource(R.drawable.water1600);
                    img1_3.setImageResource(R.drawable.water1600);
                    img1_4.setImageResource(R.drawable.water1600_2);
                    img1_5.setImageResource(R.drawable.water1600_2);
                }else if(wsm[0].equals("4")){
                    img1_1.setImageResource(R.drawable.water1600);
                    img1_2.setImageResource(R.drawable.water1600);
                    img1_3.setImageResource(R.drawable.water1600);
                    img1_4.setImageResource(R.drawable.water1600);
                    img1_5.setImageResource(R.drawable.water1600_2);
                }else if(wsm[0].equals("5")){
                    img1_1.setImageResource(R.drawable.water1600);
                    img1_2.setImageResource(R.drawable.water1600);
                    img1_3.setImageResource(R.drawable.water1600);
                    img1_4.setImageResource(R.drawable.water1600);
                    img1_5.setImageResource(R.drawable.water1600);                }

                if(wsm[1].equals("0")){

                }else if(wsm[1].equals("1")){
                    img2_1.setImageResource(R.drawable.sunshine);
                    img2_2.setImageResource(R.drawable.sunshine_non);
                    img2_3.setImageResource(R.drawable.sunshine_non);
                    img2_4.setImageResource(R.drawable.sunshine_non);
                    img2_5.setImageResource(R.drawable.sunshine_non);

                }else if(wsm[1].equals("2")){
                    img2_1.setImageResource(R.drawable.sunshine);
                    img2_2.setImageResource(R.drawable.sunshine);
                    img2_3.setImageResource(R.drawable.sunshine_non);
                    img2_4.setImageResource(R.drawable.sunshine_non);
                    img2_5.setImageResource(R.drawable.sunshine_non);
                }else if(wsm[1].equals("3")){
                    img2_1.setImageResource(R.drawable.sunshine);
                    img2_2.setImageResource(R.drawable.sunshine);
                    img2_3.setImageResource(R.drawable.sunshine);
                    img2_4.setImageResource(R.drawable.sunshine_non);
                    img2_5.setImageResource(R.drawable.sunshine_non);
                }else if(wsm[1].equals("4")){
                    img2_1.setImageResource(R.drawable.sunshine);
                    img2_2.setImageResource(R.drawable.sunshine);
                    img2_3.setImageResource(R.drawable.sunshine);
                    img2_4.setImageResource(R.drawable.sunshine);
                    img2_5.setImageResource(R.drawable.sunshine_non);
                }else if(wsm[1].equals("5")){
                    img2_1.setImageResource(R.drawable.sunshine);
                    img2_2.setImageResource(R.drawable.sunshine);
                    img2_3.setImageResource(R.drawable.sunshine);
                    img2_4.setImageResource(R.drawable.sunshine);
                    img2_5.setImageResource(R.drawable.sunshine);
                }

                /*
                img1_1.setImageResource(R.drawable.water);
                img1_2.setImageResource(R.drawable.water);
                img1_3.setImageResource(R.drawable.water);
                img1_4.setImageResource(R.drawable.water);
                img1_5.setImageResource(R.drawable.water);

                img2_1.setImageResource(R.drawable.sun);
                img2_2.setImageResource(R.drawable.sun);
                img2_3.setImageResource(R.drawable.sun);
                img2_4.setImageResource(R.drawable.sun);
                img2_5.setImageResource(R.drawable.sun);
                */

                img3_1.setImageResource(R.drawable.thermometer_blue);
                img3_2.setImageResource(R.drawable.thermometer_red);
                tv_t1.setText(wsm[2]+"ºc");
                tv_t2.setText(wsm[3]+"ºc");
                frame.removeView(text_plant_info) ;
                break;

        }

    }



/*
    class PlantManager{
        String tomato[] = {"2","4","10~30"};
        String potato[]= {"2","4","10~30"};
        String sweatpotato[]= {"2","4","15~40"};
        String stomato[]= {"2","4","10~30"};
        String lemon[]= {"2","4","10~40"};
        String rose[]= {"2","3","10~40"};
        String carrot[]= {"2","3","10~25"};
        String strawberry[]= {"2","4","7~35"};
        String sunflower[]= {"2","3","15~40"};
        String tulip[]= {"2","2","7~40"};
        String onion[]= {"2","4","10~30"};
        String mo[]= {"2","4","7~30"};
    }

*/

    class PlantData{

//data
        String tomato_info ="방울토마토 또는 체리토마토(cherry tomato)는 2~3cm 정도 크기의 토마토로, 페루와 칠레 북부가 기원인 것으로 간주된다.가지속에 속하는 식용 작물로서, 잎이나 열매 거의 모든 부분에서 토마토와 비슷하나 열매가 보통 2~3 cm 이며 구형을 띤다. 조그마한 방울과 같다 하여 방울토마토라고 불린다. 토마토와 같이 숙성채소이다. 토마토보다 당도가 좀 더 높으며, 먹기에 더 간편하여 간식이나 후식용으로 사람들이 즐겨 먹는다.";

        String carrot_info= "당근(문화어: 홍무)은 홍당무라고도 하며 높이는 1m 내외이다. 오늘날 흔히 재배하는 당근과 비슷한 종류는 프랑스에서 개량되어 13세기까지 유럽에 널리 보급되었다. 한국에 들어온 시기는 16세기 무렵이다. 재배용 당근은 크게 장근종(긴 뿌리), 중근종(중간 뿌리), 단근종(짧은 뿌리) 세 가지로 나눈다. 원산지는 지중해 연안이다. 비타민 B1을 비롯해 소량의 비타민 B2와 비타민 C가 들어 있다. 그뿐만 아니라 사람의 몸에서 비타민 A로 바뀌는 카로틴이라는 물질도 들어있으며 당분과 철분이 풍부하다.당근은 추운 겨울에도 살아남을 수 있으며 여름철의 심한 더위도 잘 이겨낸다.";

        String lemon=  "레몬(lemon)은 귤속에 속하는 과일이다. 레몬 과즙은 시트르산이 많아 산성을 띠고, 단맛이 적고 강한 신맛이 나며, pH가 2에서 3 정도이다. 과즙, 껍질, 과육 모두 요리에 자주 사용되는데, 과즙은 특히 고기류와 생선류의 염기성인 비린내를 제거하고 맛을 살리기 위해 사용하는 경우가 많다.";


        String peacelily="스파티필름(Peace Lily)은 상록 여러해살이풀로서 열대 아메리카·동남아시아에 30종 정도가 자란다. 높이 약 1m이다. 잎은 상록이고 뭉쳐나며 잎자루에는 긴 잎집이 있다. 꽃은 양성화로서 육수꽃차례에 달린다. 꽃자루가 길고 화피는 통 모양이며 보통 4∼6개의 화피갈래조각이 있다. 독특한 향기가 있고 흰색이며 매우 아름답다.";

        String spanishdagger="미국 동남부 원산인 실유카 속의 줄기가 짧고 잎이 두꺼운 식물이다. 잎의 길이는 약 80cm로서 끝은 딱딱하고 뾰족하다. 꽃은 폭 10cm 정도에 푸른 기가 도는 백색 또는 붉은 빛깔을 띤다.";

        String olivetree="지중해,아시아,아프리카 일부에 분포해 있으며 잎은 타원형으로 은빛을 띄는 녹색이며 길이 4~10cm, 폭 1~3cm이다. 꽃은 백색으로 작게 핀다. 과실은 작은 핵과로 길이 1~1.5cm 정도이다.\n" +
                "        야생종은 재배되는 품종에 비해 열매가 작고 과육이 적다.";

        String basil="  열대아시아 인도와 아시아원산으로 주로 태평양 섬에서 많이 자라는 한해살이풀로 높이 40cm 정도이다.7월 중순에서 9월 하순에 적자색, 백색의 꽃이 핀다.\n" +
                "        향기와 풍미가 각각 독특한데 스위트 바질이 대표적으로 알려져 있으며 주로 줄기와 잎을 이용한다. 꽃이 피기 직전의 잎이 가장 향기롭고 달콤한 향기와 약간의 쓴맛과 매운맛이 있어 좋다.\n" +
                "                종자는 갈색으로 가늘고 작으며 길이 1~2mm이다.";

        String rose="  야생종이 북반구의 한대·아한대·온대·아열대에 분포하며 약 100종 이상이 알려져 있다. 장미의 잎은 어긋나고 3∼7개의 작은잎으로 구성된 깃꼴겹잎이다. 길이 3∼9cm로서 표면은 짙은 녹색이고, 어느 정도 윤기가 있다.\n" +
                "                뒷면은 흰빛이 돌지만 어린 잎은 홍자색이며, 가장자리에 예리한 톱니가 있다. 턱잎은 가늘고 길며, 하단부가 잎자루에 붙어 있으며, 윗부분은 바늘 같다. 줄기는 녹색을 주로 띠며 가시가 있다.\n";
        String ZZplant=" 아프리카가 원산지인 천남성과의 여러해 살이 식물이다. 크기는 약 45~60cm까지 자라며 줄기는 진녹색~연녹색이며 하부는 갈색/암녹색 빛이 더해져 어두운 편이다. 잎은 장타원형으로 끝은 뾰족한 편이며 가장자리는 매끈하다.\n" +
                "                꽃은 간헐적으로 여름에 피며 길이는 3~5cm내외로 백색~미백색이다. 그늘에서 키우며 건조하게 관리해야하고 18~25도씨 내외로 관리해 주어야한다.\n" +
                "        독성이 있으므로 피부에 닿지 않도록 하여야 한다.";
        String goldenpothos="에피프렘넘은 외떡잎식물 천남성목 천남성과에 속하는 덩굴성 관엽식물이다.원산지는 태평양 솔로몬제도 등 열대지방이며 줄기가 덩굴이 지면서 10m까지 길게 자란다. 잎은 어긋나고 끝부분이 뾰족하며 밑부분은 심장 모양이다.\n" +
                "                또 두껍고 윤기가 나며 녹색바탕에 크림색의 줄무늬가 대리석 무늬처럼 들어 있다.실내에서 많이 기르는 흔한 상록 덩굴식물로 적응력이 강하고 다른 물체를 타고 올라가면서 자라기 때문에 걸이화분용이나 기둥용으로 사용한다.\n" +
                "                반음지 실내에서 잘 자라며 특별한 관리는 필요없고 수분공급을 잘 하고 얼지 않게만 하면 잘 자란다. 월동 온도는 8~10℃이며 겨울철에는 충분히 빛을 받게 하면서 물은 적게 주는 것이 좋다.\n";
        String potato="감자(영어: Potato)는 가지과의 다년생식물로, 세계에서 네 번째로 많이 생산되는 곡물이다. 원산지는 남미\n" +
                "        안데스 지역이며, 주로 온대 지방에서 재배하며, 대표적인 구황작물 중 하나로 현재 재배되고 있는\n" +
                "        식물 가운데 가장 재배 적응력이 뛰어난 식물로 알려져 있다. 비교적 서늘한 기후를 좋아하는데,\n" +
                "        자라는 데 가장 알맞은 온도는 20℃쯤이며, 삶아서 주식 또는 간식으로 하고 굽거나 기름에 튀겨\n" +
                "        먹기도 한다. 소주의 원료와 알코올의 원료로 사용되고, 감자 녹말은 당면, 공업용 원료로 이용하는\n" +
                "        외에 좋은 사료도 된다. 감자의 칼륨이 소금이나 된장의 나트륨을 배출하므로 합리적이다.\n" +
                "                특히, 된장으로 간을 하면 된장이 발효되는 과정에서 생성되는 여러 펩타이드가 항산화작용을\n" +
                "        하므로 건강에 유익하다.";

        String oi="오이는 박과의 한해살이 덩굴식물로, 많은 품종이 개발되어 전 세계적으로 채소로 재배되고 있다.\n" +
                "                인도가 원산지이고,중국이 세계에서 가장 많은 오이를 생산한다. 지름 3cm 내외이며 주름이 진다.\n" +
                "        수꽃에는 3개의 수술이 있으며 암꽃에는 가시같은 돌기가 있는 긴 씨방이 있다.\n" +
                "        오이에는 95% 이상의 수분이 있으며 각종 비타민·무기질이 조금씩 들어 있고 아스코르비니아제가\n" +
                "        다량 함유되어 있다. 비타민A 56I.U., 비타민C 15mg, 비타민B1 0.06mg, 비타민B2 0.05mg 등이\n" +
                "        풍부하여 우수한 비타민 공급체이다. 과즙이 많고 맛이 깔끔해 날로 먹거나, 오이 피클,\n" +
                "                오이 냉채, 오이소박이 등의 여러 요리에 쓰인다.";
        String ipomoea_batatas="고구마(학명: Ipomoea batatas)는 메꽃과의 한해살이 뿌리채소로, 주로 전분이 많고 단 맛이 나는 혹줄기를 가진 재배용 작물이다. 고온 작물로 자라는 데 알맞은 온도는 30-35℃이다. 땅은 너무 습한 곳이 아니면 그다지 가리지 않으며 산성 땅에서도 비교적 잘 된다.\n" +
                "                고구마에서 먹는 부분은 뿌리인데, 고구마는 뿌리에 영양분이 축적되어 둥그렇게 크기가 커지며 이런 종류의 뿌리를 덩이뿌리라고 부른다. 반면 감자에서 먹는 부분은 뿌리가 아니라 줄기이고, 그런 종류의 줄기를 덩이줄기라고 부른다. 여름에 얻는 고구마순으로는 김치를 담가 먹는다.\n" +
                "        고구마는 다른 작물에 비해 수확량이 많고 쓰이는 곳이 넓으며, 보리 등의 뒷그루로 가꿀 수 있는 장점이 있다. 반면, 저장하기 까다로운 결점도 있다.";
        String chili_pepper="고추( 문화어: 댕추, 영어: Chili pepper, 일본어: 唐辛子(T?garashi), 중국어: 辣椒(Laji?o) )는 가지과에 딸린 여러해살이 나무이다. 온대지방에서는 겨울에 죽기 때문에 한해살이 풀로 알고 있는 경우가 많다.\n" +
                "        볼리비아 안데스가 원산이며, 열대 아메리카, 멕시코 유카탄 반도에서 집중적으로 작물화되어 재배되었다. 키는 60~90 cm. 가지가 많이 갈라지고 잎은 길둥글고 끝이 뾰족하다. 열매는 장과로서 길둥근 모양이고, 처음에는 짙은 녹색이나 익어 가면서 점점 붉어지며 껍질과 씨는 매우 매운맛을 내는 것이 특징이다. 비타민 A와 비타민 C가 다량 함유되어 있어. 더위 예방 효과가 높고, 또한 살균 작용이 있어 식중독을 예방하며, 식용하거나 조미료 등으로 쓰이고, 고춧잎은 무쳐서 깻잎처럼 먹는다.\n";
        String strawberry=" 딸기(strawberry, 학명: Fragaria × ananassa)는 장미과 딸기속에 속하는 식물, 또는 그 열매를 말한다. 산딸기, 뱀딸기, 야생딸기와 재배하는 딸기로 구분된다. 꽃말은 존중, 애정, 우정, 우애다. 흔히 과일로 알려졌으나 채소다.최초의 정원 딸기는 18세기 말 프랑스 브르타뉴 반도에서 경작되었다.\n" +
                "        딸기는 호냉성 열매채소(외형으로는 과일이지만)이기 때문에 냉량한 기후에 강하다.\n" +
                "                생딸기는 딸기를 물로 씻은 후 그대로 먹거나 또는 설탕과 우유를 넣어서 먹어도 좋다. 쇼트케이크·샐러드에도 이용된다. 또한 가공원료로도 쓰이고 냉동수송에도 적합하다. 잼이나 프리즈업으로도 많이 이용된다.\n";

        String sunflower="             해바라기(영어: Helianthus 또는 sunflower)는 국화과에 속하는 일년생 식물로, 꽃은 두상화(頭狀花)이다. 해바라기속에 속하는 다른 여러해살이풀도 보통 해바라기라 부른다. 중앙아메리카가 원산지로 16세기에 유럽에서 도입되었으며 한국에서는 관상용으로 많이 기르는 한해살이풀이다. 해바라기가 속하는 해바라기속 식물은 약 60종 이상이 있다. 꽃이 태양이 있는 방향으로 향하는 성질(굴광성)이 있다. ‘꽃이 항상 해를 향한다’는 뜻이 있지만, 해바라기는 어린 시기에만 햇빛을 따라서 동서로 움직이며 꽃이 피고 나면 줄기가 굵어져서 몸을 돌리는 일이 없다. 영양번식은 가능하다고만 알려져 있을 뿐 자세히 알려져 있진 않다.한국에서는 3m까지 자랄 수 있고, 꽃의 지름은 30cm까지 자란다.\n ";
        String pimang="    피망(문화어: 사자고추)는 카프시쿰 아눔의 재배종군 가운데 하나이다. 단고추를 부르는 말에는 여러가지가 있는데, 대한민국에서는 보통 매운 맛이고 다른색깔이라도 피망이나 파프리카라고 부른다. 색은 빨강·노랑·초록 등으로 다양하고, 종 모양이다. 캡사이신을 만드는 유전자가 열성을 띄기 때문에 매운 맛이 덜하다.\n";
        String lactuca_sativa="  상추(Lactuca sativa, 문화어: 부루)는 국화과에 속하는 여러해살이풀이다. 채소식물로 여러 가지 계통·품종군으로 분화되어 있다. 재배품종은 크게 결구상추·반결구상추·잎상추·배추상추의 4가지로 분류된다.\n" +
                "        잎은 타원형이나 긴 모양으로, 생육 중기까지 줄기는 거의 자라지 않고 잎이 여러 겹으로 겹쳐 결구하는 것, 결구하지 않는 것이 있다.\n" +
                "                생육 및 싹트기에 알맞은 온도는 15 ~ 20℃이며 고온에 의해 추대가 이루어진다. 산성 토양에 약하며 충분한 수분을 필요로 한다.\n";
        String tulip="           튤립(Tulip, 문화어: 튜리프)은 백합과의 여러해살이풀로 튤립속 식물의 총칭이다. 울금향(鬱金香)이라고도 한다. 남동 유럽과 중앙아시아가 원산지이다. 4월이나 5월에 종 모양의 꽃이 핀다. 나리꽃등과 더불어 알뿌리로 번식하는 식물중 하나이다. 대한민국 경기도 고양시및 전라남도 화순군이나 일본 니가타 현 니가타 시에서는 이 식물을 특산물로 판매하고 있다.\n" +
                "                화단이나 화분용 또는 절화용으로 쓰인다. 화단에는 아주 잘 어울리며 화분 생산도 유리하다. 특히 비늘줄기 생산도 영리적으로 실시된다.\n ";
        String poinsettia="   포 인 세 티아의 원산지는 멕시코이다. 남부 시날 로아 (Sinaloa) 남부에서 멕시코 전역의 태평양 연안, 치아파스 (Chiapas), 과테말라 (Guatemala)까지 적당한 고도의 낙엽 열대 우림에서 야생에서 발견된다.\n" +
                "        Euphorbia pulcherrima(=Poinsettia )는 일반적으로 0.6-4 미터 (2 피트 0-13 피트 1 인치)의 높이에 도달 관목이나 작은 나무이다. 이 식물은 7-16 센티미터 (2.8-6.3 in) 길이의 짙은 초록색 돗자리를 가지고 있고, 색깔이있는 포엽 (가장 자주 불타지만 오렌지, 옅은 녹색, 크림색, 분홍색, 흰색 또는 차돌박이가 될 수 있음)은 그룹화와 색상 때문에 꽃 꽃잎으로 오인 되곤한다. 실제로는 나뭇잎으로 포엽의 색은 광주기를 통해 생성된다. 즉, 색을 변경하기 위해 어둠 (적어도 5 일 연속으로 12 시간)이 필요하다. 동시에, 식물은 가장 밝은 색상을 위해 낮에는 풍부한 빛을 필요로합니다.\n" +
                "  ";
        String onion="       양파는 수선화과의 부추아과 부추속에 속한 식물이다.\n" +
                "                영양 성분은 물 90.4%, 단백질 1%, 지방 0.1%, 탄수화물 7.6%이고, 양파 100g 속에 비타민C 7 mg, 칼슘 15 mg, 인 30mg이 들어 있다.\n" +
                "                양파는 매운 맛이 약한 감미종과 매운맛이 강한 신미종으로 크게 나뉘고, 다시 비늘줄기의 색깔에 따라 황색·적색·백색계로 나뉜다. 감미종은 생식하는 데 많이 이용되고, 신미종은 조리에 주로 이용된다. 한국에서 재배되는 대부분의 품종은 신미종의 황색계이며, 대표적인 종이 천주황(泉州黃)이다. 또 생육기간의 장단에 따라 조생종·중생종·만생종으로 나누는데, 조생종은 온도만 적당하면 12시간 정도의 일장(日長)에서도 알이 잘 비대하나, 만생종은 일장이 그보다 길어야 비대한다.\n ";
        String butter_head="   양상추는 '헤드레터스' 또는 '캐비지레터스' 라고도 한다. 잎은 10장 이상으로 되어 결구하고 구(球)는 지름 10 ~ 20cm이다.\n" +
                "                품종은 크게 크리습 헤드(crisp head)류와 버터 헤드(butter head)류로 나뉜다. 크리습 헤드는 현재 가장 많이 재배되는 종류로 잎 가장자리가 깊이 패어 들어간 모양이고 물결 모양을 이룬다. 버터 헤드는 반결구이고 유럽에서 주로 재배하며 잎 가장자리가 물결 모양이 아니다. 양상추는 샐러드로 많이 이용되며 수분이 전체의 94∼95%를 차지하고 그 밖의 탄수화물, 조단백질, 조섬유, 비타민 C 등이 들어 있다. 양상추의 쓴맛은 락투세린(lactucerin)과 락투신(lactucin)이라는 알칼로이드 때문인데, 이것은 최면ㆍ진통효과가 있어 양상추를 많이 먹으면 졸음이 온다.\n ";
        String bechu="   배추는 브라시카 라파의 재배종이다. 중국이 원산지로 밭에서 재배하는 두해살이 잎줄기 채소이다.\n" +
                "                뿌리에서 나오는 잎은 옅은 녹색을 띄며, 거꾸로 된 달걀 모양이고 끝이 둥글다. 고르지 않은 톱니가 있는 잎 가장자리는 물결 모양으로 주름이 진다. 잎 중앙에는 흰색을 띠는 넓은 중앙맥이 있다. 잎은 보통 서로 감싸면서 단단한 덩어리를 이루는데, 가운데 잎은 햇빛을 받지 못해 노랗게 된다. 배추는 잎이 자라는 생장 전기에는 약 20℃, 결구(結球)하는 생장 후기에는 약 15~16℃의 비교적 서늘한 기후가 필요하다. 조건이 알맞으면 싹이 난 뒤 60∼90일에 결구(結球:배추 잎이 여러 겹으로 겹쳐져 둥글게 속이 드는 상태)를 끝내는데, 결구가 끝날 때에는 땅윗부분의 전체 무게가 3∼6kg, 잎수 40∼70장이 된다. 잎수는 적으나 각 잎의 무게가 무거워서 결구를 이루는 것을 엽중형(葉重型)이라 하고, 각 잎의 무게는 가벼우나 잎수가 많아서 결구하는 것을 엽수형(葉數型)이라 한다. 엽중형은 조생종에 많고 엽수형은 중·만생종에 많다.\n";
        String yangbechu="   양배추는 쌍떡잎식물 양귀비목 겨자과의 한해살이 또는 두해살이풀이다. 잎은 두껍고 털이 없으며 분처럼 흰빛이 돌고 가장자리에 불규칙한 톱니가 있으며 주름이 있어 서로 겹쳐지고 가장 안쪽에 있는 잎은 공처럼 둥글며 단단하다.\n" +
                "        서늘한 기후에서 잘 자라며 고온기에 접어들면 꽃대가 올라오면서 꽃이 핀다. 생육한계 저온은 5℃ 정도로서 저온에 대한 내한성은 강하나 품종과 생육정도에 따라 차이가 있다. 가을 재배용 품종은 -11∼-12℃ 까지는 동해를 받지 않으며 그 이하의 저온에서도 단시간 경과 시는 상해를 입지 않는 품종도 있다.\n ";
        String ange="          안개꽃은 석죽과에 속하는 내한성 한해살이풀이다. 높이는 30∼45cm에 털이 없고, 잎은 마주나며 위쪽 것은 바소와 같은 잎의 모양으로 통통하고 끝이 뾰족하다. 많은 가지가 갈라져 여름에서 가을에 걸쳐 잘고 흰 꽃이 무리지어 핀다. 꽃잎은 5장이고, 끝이 오목하다. 담홍색이나 선홍색의 품종도 있으며, 캅카스 원산으로 화단 및 꽃꽂이용으로 재배한다. 안개꽃은 한해살이풀로 가장 대중적인 품종은 흰꽃인 코벤트 가든 마킷이다. 이 꽃은 추위에 강한 올피기의 대륜인데, 화단심기와 절화용으로 인기가 있다. 붉은꽃에는 크림손·카르미네아가 있으며, 적화종은 소륜으로 키가 40∼50cm로 약간 크게 자란다.\n";
        String tomato="          토마토는 쌍떡잎식물 통화식물목 가지과의 한해살이풀이다. 일년감이라고도 한다. 남아메리카 서부 고원지대 원산이다. 높이 약 1m이다. 가지를 많이 내고 부드러운 흰 털이 빽빽이 난다. 잎은 깃꼴겹잎이고 길이 15∼45cm이며 특이한 냄새가 있다. 작은잎은 9∼19개이고 달걀 모양이거나 긴 타원 모양이며 끝이 뾰족하고 깊이 패어 들어간 톱니가 있다. 열매를 식용하거나 민간에서 고혈압·야맹증·당뇨 등에 약으로 쓴다. 열매는 수 g인 것에서부터 200g을 넘는 것까지 있으며 품종에 따라서 다양하다. 과육의 색깔은 보통 붉은색이지만 노란색인 품종도 있다. 얇은 과피는 무색인 것과 노란색인 것이 있는데 밖에서는 각각 복숭아색과 빨간색으로 구별한다. 열매를 가장 많이 수확하는 때는 여름인데 온실에서는 여름 이외의 시기에도 출하한다. 신선한 것은 날로 먹고 샐러드·샌드위치 등으로 쓰며, 주스·퓌레·케첩과 각종 통조림 등 가공용에도 많이 쓴다. 열매는 90% 정도가 수분이며 카로틴과 비타민C가 많이 들어 있다.\n";
        String chamyui="  참외는 쌍떡잎식물 합판화군 박목 박과의 한해살이 덩굴식물이다. 인도산 야생종에서 개량된 것이라고 하며 재배 역사가 긴 식물이다. 중국에서는 기원전부터 재배하였으며 5세기경에는 현대 품종의 기본형이 생겼다고 한다. 원줄기는 길게 옆으로 벋으며 덩굴손으로 다른 물체에 기어올라간다. 잎은 어긋나고 손바닥 모양으로 얕게 갈라지며 밑은 심장저 모양이고 가장자리에 톱니가 있다.\n ";
        String gagi="  가지는 쌍떡잎식물 통화식물목 가지과의 한해살이풀이다. 높이는 60∼100cm로, 식물 전체에 별 모양의 회색털이 나고 가시가 나기도 한다. 줄기는 검은 빛이 도는 짙은 보라색이다. 잎은 어긋나고 달걀 모양이며 길이 15∼35cm로 잎자루가 있고 끝이 뾰족하다. 꽃은 6∼9월에 피는데, 줄기와 가지의 마디 사이에서 꽃대가 나와 여러 송이의 연보라색 꽃이 달리며 꽃받침은 자줏빛이다. 열매의 모양은 달걀 모양, 공 모양, 긴 모양 등 품종에 따라 다양하며 한국에서는 주로 긴 모양의 긴가지를 재배한다.\n ";
        String mo="  무는 십자화과의 먹을 수 있는 뿌리 채소로 세계 곳곳에서 재배되고, 유럽에서는 로마 제국 시대부터 재배되었다. 방언으로는 무수라고도 한다.\n" +
                "                무는 크기와 색상에 따라 여러가지의 종류로 나뉘어 있고, 각각의 품종에 따라 어느 계절에나 재배할 수 있다. 동아시아에서는 아메리카나 유럽 등지에서 재배되는 무와는 달리 상대적으로 크고 흰색 빛깔을 지닌 무를 재배하는데, 이를 한국에서는 굵기와 길이에 따라 조선무 또는 왜무라고 부른다.[1] 동아시아에서 재배되는 무의 길이는 약 20 ~ 35 cm이며, 지름은 약 5 ~ 10 cm 정도이다. 무에는 비타민 C의 함량이 20-25mg이나 되어 예로부터 겨울철 비타민 공급원으로 중요한 역할을 해왔다. 이밖에 무에는 수분이 약 94%, 단백질 1.1%, 지방 0.1%, 탄수화물 4.2%, 섬유질 0.7%가 들어 있다.\n ";
        String sigeamchi="   시금치는 쌍떡잎식물 중심자목 명아주과의 한해살이 또는 두해살이풀이다. 시금치는 식용채소로 재배하는데 높이 약 50cm까지 자란다. 뿌리는 육질이고 연한 붉은색이며 굵고 길다. 원줄기는 곧게 서고 속이 비어 있다. 잎은 어긋나기로 자라고 잎자루가 있으며 밑부분이 깊게 갈라지고 윗부분은 밋밋하다. 밑동의 잎은 긴 삼각 모양이거나 달걀 모양이고 잎자루는 위로 갈수록 점차 짧아진다.\n" +
                "        시금치 100g 중에는 철 33mg, 비타민A 2,600IU, B1 0.12mg, B2 0.03mg, C 100mg 과 비타민 K도 들어 있어, 중요한 보건식품이다.\n";

    }





}

