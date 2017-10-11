package com.example.parkjaeha.firebasetest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by parkjaeha on 2017-02-03.
 */
/*로그인 화면 및 로그인 관련 기능을 하는 클래스이다. Google로그인, 기본로그인을 하는데 FIrebase에 저장된 데이터를 가져와 로그인 정보를 확인한다.*/
//Login class ,Google&Firebase Use
public class LoginActivity  extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

        private SignInButton mGooglebtn;
        private  static final  int RC_SIGN_IN = 1;
        private GoogleApiClient mGoogleApiClient;
       // private FirebaseAuth mAuth;
        //private FirebaseAuth.AuthStateListener mAuthListener;
       static final int RC_GOOGLE_SIGN_IN = 9001;
       SignInButton mSigninGoogleButton;
       FirebaseAuth mFirebaseAuth;
   // FirebaseUser mFirebaseUser;
    FirebaseAuth.AuthStateListener mFirebaseAuthListener;
    private static final String TAG = "LoginActivity";
    String mGname,mGemail,mUid;


    String json_data;

        private EditText et_email,et_password;
        private Button btn_register,btn_login;
        private DatabaseReference mDatabaseUser;
        private ProgressDialog mProgress;

    //network class use
    NetworkState networkState = new NetworkState();

        protected void  onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);




                setContentView(R.layout.activity_login);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                et_email = (EditText) findViewById(R.id.et_loginemail);
                et_password = (EditText) findViewById(R.id.et_loginpassword);
                btn_login = (Button) findViewById(R.id.btn_login);
                btn_register = (Button) findViewById(R.id.btn_loginregister);
                mProgress = new ProgressDialog(this);
                //test 할때만 주석 실제로는 필요


                //FirebaseUser
                mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users");           //fireabase user 데이터 읽어오기

                //Firebase
                mFirebaseAuth = FirebaseAuth.getInstance();                                         //firebase auth 허용 set
                //Google_User_data
                // mFirebaseUser = mFirebaseAuth.getCurrentUser();

                //FirebaseListener  Logining->MainActivity
                mFirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {            //Listener로 현재 user 확인
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            //Intent intent = getIntent();
                            //String name;
                            //name = intent.getExtras().getString("name");
                            Log.d(TAG, "sign in");

                            Intent mainintent = new Intent(LoginActivity.this, PlantMain.class);
                            mainintent.putExtra("check","1");

                            startActivity(mainintent);
                            finish();
                        } else {
                            Log.d(TAG, "sign out");
                        }

                    }
                };
                //GoogleLogin
                mSigninGoogleButton = (SignInButton) findViewById(R.id.sign_in_google_button);
                mSigninGoogleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {                                                            //구글 로그인 버튼 클릭
                        //network check_line
                        if(!networkState.isConnected(LoginActivity.this)){
                            networkState.buildDialog(LoginActivity.this).show();
                        }
                        else {

                            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);                       //client 허용 데이터로  보여주기
                            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);                                //client 클릭시 정보 전달

                        }
                    }
                });
                //GoogleClient select result
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)              //구글 옵션 설정 기기 내 이메일 관련 데이터 설정
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();


                //GoogleClient select menu
                mGoogleApiClient = new GoogleApiClient.Builder(this)                //구글 api 빌드 하여 허용
                        .enableAutoManage(this, this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();


                //Login
                btn_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {           //로그인 시 로그인 데이터 확인
                        if (v.isClickable()) {
                            //network check_line
                            if(!networkState.isConnected(LoginActivity.this)){
                                networkState.buildDialog(LoginActivity.this).show();
                            }
                            else {
                                checkLogin();
                            }
                        }

                    }
                });

                //register
                btn_register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {           //회원가입 화면이동
                        //network check_line
                        if(!networkState.isConnected(LoginActivity.this)){
                            networkState.buildDialog(LoginActivity.this).show();
                        }
                        else {

                            new BackgroundTask().execute();     //register 들어가기전에 미리 데이터 get

                            new Handler().postDelayed(new Runnable() {                                                  //웹에서 데이터를 가져올 시 어느정도 딜레이가 필요하기 때문에 딜레이 부여
                                @Override
                                public void run() {
                                    if(json_data !=null) {
                                        Intent regintent = new Intent(LoginActivity.this, MemberRegActivity.class);
                                        regintent.putExtra("json_user", json_data);
                                        startActivity(regintent);
                                    }else{
                                        Toast.makeText(getApplicationContext(),"회원정보 데이터를 불러오지 못하였습니다.",Toast.LENGTH_SHORT).show();
                                    }

                                    }
                            }, 3000);

                        }
                    }
                });


        }

        //login 확인
        private void checkLogin(){
            String email = et_email.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                mProgress.setMessage("checking...Login");
                mProgress.show();
                //Login email,password check
                                                                                                //로그인 데이터 firebase data와 확인하기
                mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            mProgress.dismiss();
                            checkUserExist();

                        }else{
                            mProgress.dismiss();
                            Toast.makeText(LoginActivity.this,"입력하신 정보가 올바르지 않습니다.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }else{
                Toast.makeText(LoginActivity.this,"빈칸을 채우시오",Toast.LENGTH_SHORT).show();

            }
        }
        // id 존재여부 확인
        private void checkUserExist(){

            if(mFirebaseAuth.getCurrentUser() != null) {

                final String user_id = mFirebaseAuth.getCurrentUser().getUid();                                     //user 데이터 가져와 firebase 내에 리스너로 같은 데이터 있는지 확인
                mDatabaseUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(user_id)) {
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            //로그인시 기기의 데이터를 읽어와서 기기 존재 여부 확인하여 보여준다.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            Intent mainIntent = new Intent(LoginActivity.this, PlantMain.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            mainIntent.putExtra("check","1");
                            startActivity(mainIntent);


                        } else {
                            Toast.makeText(getApplicationContext(),"Information Not Correct",Toast.LENGTH_SHORT).show();
                            //Intent setupIntent = new Intent(LoginActivity.this, GalleryActivity.class);
                            //setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //startActivity(setupIntent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, "onConnectionFailed:" + databaseError);                  //오류시
                    }
                });
            }else{
                Toast.makeText(getApplication(),"Id NOT exist"+mFirebaseAuth.getCurrentUser().toString().trim(),Toast.LENGTH_SHORT).show();
            }

        }

    ///////
    class BackgroundTask extends AsyncTask<Void,Void,String> {              //회원 등록 데이터 가져오기

        String json_url;
        String JSON_STRING;

        @Override
        protected void onPreExecute(){
            json_url = "http://211.44.136.164:7777/Project/reg_check.php";
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
            } catch (NullPointerException e){
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
            json_data = result;
        }
    }

    //뒤로가기 막기
    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(),"Login 해주세요~",Toast.LENGTH_SHORT).show();            //뒤로가기 안되게 하기
    }

//Google_Login require in Firebase
    @Override
    protected void onStart() {                                      //firebase onstart를 이용해 user login 데이터 확인
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( mFirebaseAuthListener != null )                            //마지막 데이터 저장해 놓기
            mFirebaseAuth.removeAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {         //google 로그인 api 데이터
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == RC_GOOGLE_SIGN_IN ) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if ( result.isSuccess() ) {
                String token = result.getSignInAccount().getIdToken();         // 데이터 가져와서 확인하기

                AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
                mFirebaseAuth.signInWithCredential(credential);
            }
            else {
                Log.d(TAG, "Google Login Failed." + result.getStatus());
            }
        }
    }
}
