package com.example.parkjaeha.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hyeongkun on 2017-04-14.
 */
/*식물의 목록을 리스트로 보여주는 클래스이다.*/
public class PlantListDicActivity extends AppCompatActivity {

    String plantNumber = null;
    String plantuser = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantlist);

        final TextView textView=(TextView) findViewById(R.id.textView3);
        textView.setText("식물목록");
        final EditText editText=(EditText)findViewById(R.id.editText);
        final ListView listview;
        final PlantListViewAdapter adapter;

        Intent intent=getIntent();
        plantNumber=intent.getStringExtra("PLANT_NUMBER");
        plantuser=intent.getStringExtra("PLANT_USER");


        adapter = new PlantListViewAdapter();

        listview = (ListView) findViewById(R.id.listview_plantlist);
        listview.setAdapter(adapter);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터,아이콘 정적 저장
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icontomato), "방울토마토", "토마토과,숙성채소");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconcarrot), "당근(홍당무)", "무과 채소");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.lemonicon), "레몬", "귤 과 과일");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconpeacelily), "스파티필름", "상록 여러해살이풀");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconspanishdagger), "스패니쉬대거", "실유카");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconolivetree), "올리브나무", "과실");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconbasil), "바질", "한해살이풀");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconrose), "장미", "꽃");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconzzplant), "금전수(돈나무)", "여러해 살이 식물");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icongoldenpothos), "에피프렘넘", "외떡잎식물");

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconpotato), "감자", "가지과 다년생식물");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconoi), "오이", "박과 덩굴식물");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconipomoea_batatas), "고구마", "뿌리채소");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconchili_pepper), "고추", "가지과 여러해살이 나무");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconstrawberry), "딸기", "장미과 딸기속 식물");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconsunflower), "해바라기", "국화과 일년생 식물");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconpimang), "피망", "카프시쿰 아눔의 재배종");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconlactuca_sativa), "상추", "국화과 채소");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icontulip), "튤립", "백합과 식물");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconpoinsettia), "포인세티아", "포엽 식물");

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icononion), "양파", "수선화과의 부추속 식물");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconbutter_head), "양상추", "채소");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconbechu), "배추", "라파의 재배종");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconyangbechu), "양배추", "겨자과 식물");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconange), "안개꽃", "내한성 한해살이풀");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icontomato1), "토마토", "가지과 쌍떡잎식물");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconchamyui), "참외", "덩굴식물");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icongagi), "가지", "가지과 한해살이풀");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconmo), "무", "뿌리 채소");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.iconsigeamchi), "시금치", "명아주과의 풀");

        //adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icontomato), "JapanTomato", "일본토마토 입니다.");
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String name=null;
                PlantListItem item = (PlantListItem) parent.getItemAtPosition(position) ;

                switch(position){
                    case 0:
                        name="방울토마토";
                        break;
                    case 1:
                        name="당근(홍당무)";
                        break;
                    case 2:
                        name="레몬";
                        break;
                    case 3:
                        name="스파티필름";
                        break;
                    case 4:
                        name="스패니쉬대거";
                        break;
                    case 5:
                        name="올리브나무";
                        break;
                    case 6:
                        name="바질";
                        break;
                    case 7:
                        name="장미";
                        break;
                    case 8:
                        name="금전수(돈나무)";
                        break;
                    case 9:
                        name="에피프렘넘";
                        break;
                    case 10:
                        name="감자";
                        break;
                    case 11:
                        name="오이";
                        break;
                    case 12:
                        name="고구마";
                        break;
                    case 13:
                        name="고추";
                        break;
                    case 14:
                        name="딸기";
                        break;
                    case 15:
                        name="해바라기";
                        break;
                    case 16:
                        name="피망";
                        break;
                    case 17:
                        name="상추";
                        break;
                    case 18:
                        name="튤립";
                        break;
                    case 19:
                        name="포인세티아";
                        break;
                    case 20:
                        name="양파";
                        break;
                    case 21:
                        name="양상추";
                        break;
                    case 22:
                        name="배추";
                        break;
                    case 23:
                        name="양배추";
                        break;
                    case 24:
                        name="안개꽃";
                        break;
                    case 25:
                        name="토마토";
                        break;
                    case 26:
                        name="참외";
                        break;
                    case 27:
                        name="가지";
                        break;
                    case 28:
                        name="무";
                        break;
                    case 29:
                        name="시금치";
                        break;
                }
                    Intent plant_name_intent = new Intent(getApplicationContext(), PlantDicInfoMenu.class);
                    plant_name_intent.putExtra("PLANT_NAME", name);
                if(plantNumber == null) {
                    plant_name_intent.putExtra("PLANT_STATE", "0");
                }else {
                    Toast.makeText(getApplicationContext(),plantNumber,Toast.LENGTH_LONG).show();
                    plant_name_intent.putExtra("PLANT_NUMBER", plantNumber);
                    plant_name_intent.putExtra("PLANT_STATE", "1");
                    plant_name_intent.putExtra("PLANT_USER", plantuser);
                }
                    startActivity(plant_name_intent);

            }
        }) ;

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String filterText=editable.toString();
                ((PlantListViewAdapter)listview.getAdapter()).getFilter().filter(filterText);
            }
        });



    }
}
