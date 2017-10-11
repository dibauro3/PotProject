package com.example.parkjaeha.firebasetest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by parkjaeha on 2017-02-01.
 */
/* 회원가입 정보를 입력하고 데이터 중복 여부 확인후 회원가입이 되도록 구현하였다.*/
//register
public class MemberRegActivity extends AppCompatActivity {


    private EditText mname, memail, mpassword, mpass_check;
    private Button btn_register, btn_check;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    String json_user;
    String emt= null;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String name = null,result=null;
    String[] data= null;
    Boolean fact = false;
    FirebaseAuth.AuthStateListener mFirebaseAuthListener;
    private static final String TAG = "MemberRegActivity";
    NetworkState networkState = new NetworkState();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase =  FirebaseDatabase.getInstance().getReference().child("Users");


        mname = (EditText) findViewById(R.id.et_name);
        memail = (EditText) findViewById(R.id.et_email);
        mpassword = (EditText) findViewById(R.id.et_password);
        mpass_check = (EditText) findViewById(R.id.et_passcheck);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_check = (Button) findViewById(R.id.btn_check);



        json_user = getIntent().getExtras().getString("json_user");

        if(json_user !=null) {
            try {
                jsonObject = new JSONObject(json_user);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                data = new String[jsonArray.length()];
                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    name = JO.getString("email");
                    data[count] = name;
                    Log.d("qkrwogk", name + "/ " + data[count]);
                    count++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (NullPointerException e)
            {
                e.printStackTrace();
            }

        }else{
            Toast.makeText(getApplicationContext(),"회원정보 데이터를 불러오지 못하였습니다.",Toast.LENGTH_SHORT).show();
        }

        //register
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!networkState.isConnected(MemberRegActivity.this)){
                    networkState.buildDialog(MemberRegActivity.this).show();
                }
                else {
                    startRegister();
                }
            }
        });

        //id_check
        btn_check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                progressDialog.setMessage("User_email checking");
                progressDialog.show();
                  emt = memail.getText().toString().trim();
                if (!TextUtils.isEmpty(emt)&& emt.contains("@") && (emt.contains(".com") || emt.contains(".net"))) {

                    for(int i=0; data.length > i; i++) {
                        if (data[i].equals(emt)) {
                            fact = true;
                            break;
                        }
                    }
                    if (fact==true) {
                        Toast.makeText(getApplicationContext(), "중복된 아이디입니다.", Toast.LENGTH_LONG).show();
                        fact=false;
                    } else {
                        Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다.", Toast.LENGTH_LONG).show();
                        result = emt;

                    }

                    progressDialog.dismiss();



                    }else {
                    Toast.makeText(getApplication(), "이메일을 써주세요.", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }
        });

    }





//register fun
    private void startRegister() {

        final String name = mname.getText().toString().trim();
        final String email = memail.getText().toString().trim();
        String password = mpassword.getText().toString().trim();
        String pass_check = mpass_check.getText().toString().trim();
        final String method = "register";

//email | password | name empty message
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(pass_check) && password.equals(pass_check) && email.equals(result)){

            progressDialog.setMessage("Signing Up");
            progressDialog.show();


            //User_id create_ exist check
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful() && !email.contains("gmail")) {


                        String user_id = mAuth.getCurrentUser().getUid();

                        DatabaseReference cureent_user_db = mDatabase.child(user_id);

                        //register_data Firebase DB save
                        cureent_user_db.child("name").setValue(name);
                        cureent_user_db.child("email").setValue(email);
                        cureent_user_db.child("uid").setValue(mAuth.getCurrentUser().getUid());

                        //mysql_DB save
                            BackgroundTask backgroundTask= new BackgroundTask(MemberRegActivity.this);
                            backgroundTask.execute(method,name,email,user_id);
                            finish();

                            progressDialog.dismiss();

                            Intent logIntent = new Intent(MemberRegActivity.this, LoginActivity.class);
                            logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(logIntent);
                        } else {
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "\n password를 6자리 이상설정하세요", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }else{
                    Toast.makeText(getApplication(),"중복,빈칸,gmail인지 확인해주세요~",Toast.LENGTH_LONG).show();

            }
        }


    }


