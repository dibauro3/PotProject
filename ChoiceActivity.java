package com.example.hyeongkun.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by hyeongkun on 2017-04-04.
 */

public class ChoiceActivity extends AppCompatActivity {

    private Button button_t,button_w,button_l,button_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        button_t=(Button)findViewById(R.id.button_t);
        button_t.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent i= new Intent(getApplicationContext(),DrawGraphActivity.class);
                i.putExtra("OPTION","temp");
                startActivity(i);
            }
        });

        button_w=(Button)findViewById(R.id.button_w);
        button_w.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent i= new Intent(getApplicationContext(),DrawGraphActivity.class);
                i.putExtra("OPTION","water");
                startActivity(i);
            }
        });

        button_l=(Button)findViewById(R.id.button_l);
        button_l.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent i= new Intent(getApplicationContext(),DrawGraphActivity.class);
                i.putExtra("OPTION","lux");
                startActivity(i);
            }
        });


        button_total=(Button)findViewById(R.id.button_total);
        button_total.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),DrawTotalGraphActivity.class);
                startActivity(i);
            }
        });



    }
}
