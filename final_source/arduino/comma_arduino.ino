//DHT22로 교체함
//17.03.25 LED 우선 대충 아무 값이나 넣어서 어두우면 불켜지게만 해 놓음,
//          수위센서는 그냥 출력만 해놓음
//17.03.29 모든 센서 예외처리함
//         빠른 DB Test를 위해 delay시간 줄임
//17.04.03 delay 다 없애고 시간을 이용한 작동으로 바꿈 
//         릴레이, led 다 작동함 
//         우선 지금은 1분간격으로 저장해놓음 
//         문제점: 인풋 데이터 못가져옴, 아직 시간을 제대로 컨트롤할줄 모름, LED 저녁에는 어떻게 끌지 생각해야함 
//                DB보면 시간이 1.7초 정도 지연됨
#include "DHT.h"
#define DHTPIN 4 //온습도 센서 핀
#define DHTTYPE DHT22
DHT dht(DHTPIN, DHTTYPE);
int moisturePin = A0;    // select the input pin for the potentiometer
int illuminancePin = A1;
int pump_pin = 10;
int waterLVPin = A2; //수위센서
int led_pin = 8;

int waterLV = 0;
int cnt = 0;
double moi_add = 0, ill_add=0, temp_add=0, humi_add=0;
double moi_aver = 0, ill_aver=0, temp_aver=0, humi_aber=0;
int moistureValue = 0;
int illuminanceValue = 0; //조도값
float humi = 0, temp = 0;
//----------------------시간 제어를 위한 변수들 ------------------
unsigned long prev_time = 0; //시간을 컨트롤 하기 위한 변수 
unsigned long check_time = 0;
unsigned long print_time = 0;
unsigned long feed_time = 0;

//--------------------함수들----------------
void printOnSerial(); //시리얼에 출력하기 
void scanData(); //데이터 측정 
void scanError(); //데이터 측정중 오류 발생시 실행 
void addData(); //데이터 평균 구하기 위해 더한다 
void makeData(); //데이터를 구하기위한 함수들 한번에 실행 
void averData(); //데이터 평균 구함 
void LEDonNoff(); //led 제어 
//---------------------C++형식 CLASS 만들기 ---------------------
class onNoff
{
  int pinNo; //pinNo(LED, Pump)
  long OnTime; //milliseconds of on-time
  long OffTime; //milliseconds of off-time

  int thingsState; //LED or Pump State
  unsigned long previousMillis; //will store last time thing was updated

public:
  onNoff(int pin, long on, long off)
  {
    pinNo = pin;
    pinMode(pinNo, OUTPUT);

    OnTime = on;
    OffTime = off;

    thingsState = LOW;
    previousMillis = 0;
  }

  void Update()
  {
    unsigned long currentMillis = millis();

    if((thingsState == HIGH) && (currentMillis - previousMillis >= OnTime))
    {
      thingsState = LOW;
      previousMillis = currentMillis;
      digitalWrite(pinNo, thingsState);
    }
    else if ((moi_aver==0)&&(thingsState == LOW) && (currentMillis - previousMillis >= OffTime))//수분양 여기 기입 
    {
      thingsState = HIGH;
      previousMillis = currentMillis;
      digitalWrite(pinNo, thingsState);
    }
  }
};
//-------------------------------------------------------------------------

//-----------------------C++ CLASS---------------------------------------
class DataMaker
{
  long OnTime;
  long OffTime;

  unsigned long previousMillis;

public:
  DataMaker(long on, long off)
  {
    OnTime = on;
    OffTime = off;
    previousMillis = 0;
  }

  void Update()
  {
    unsigned long currentMillis = millis();

    if((cnt<7) && (currentMillis - previousMillis >= OnTime))
    {
      if(cnt<6) makeData();
      else if(cnt==6){
        averData();
        printOnSerial();
        cnt++;
      }
      previousMillis = currentMillis;
    }
    else if (currentMillis - previousMillis >= OffTime)
    {
      cnt=0;
      moi_add=0, ill_add=0, temp_add=0, humi_add=0;
      previousMillis = currentMillis;
    }
  }
};
//----------------------------------------------------------------------
onNoff PUMP_test(pump_pin, 1000, 59000);
DataMaker make_DATA(300, 59700);
//-------------------------------------------------

void setup() {
  // declare the ledPin as an OUTPUT:
   Serial.begin(9600); 
   pinMode(led_pin, OUTPUT);  //12121212
   //pinMode(pump_pin, OUTPUT); //12121212
   dht.begin();
}

void loop() {
  make_DATA.Update();
  PUMP_test.Update();
  //LEDonNoff();
}

//-----------------------함수들-------------------------------

void printOnSerial() //시리얼에 데이터 출력 함수 
{
  Serial.print(moi_aver);
  Serial.print(",");
  Serial.print(ill_aver);
  Serial.print(",");
  Serial.print(temp_aver);
  Serial.print(",");
  Serial.print(humi_aber);
  Serial.print(",");
  Serial.print(waterLV); //수위 출력
  Serial.println(","); 
}

void scanData()
{
  humi = dht.readHumidity();
  temp = dht.readTemperature();
  illuminanceValue = analogRead(illuminancePin);
  moistureValue = analogRead(moisturePin);
  if(analogRead(waterLVPin)<500) waterLV=0; //물이 빠진직후 파악하기위해 500으로 설정해놓음
    else waterLV=1;//수위레벨 측정
}

void scanError()
{
  while (isnan(temp) || isnan(humi) || isnan(illuminanceValue) || isnan(moistureValue) || isnan(waterLV)) {
    scanData();
    //Serial.println("Reading error..");
  }
}

void addData()
{
  moi_add+=moistureValue;
  ill_add+=illuminanceValue;
  temp_add+=temp;
  humi_add+=humi;
  cnt++;
}

void makeData()
{
  scanData();
  scanError();
  addData();
}

void averData()
{
  moi_aver=moi_add/cnt, ill_aver=ill_add/cnt, temp_aver=temp_add/cnt, humi_aber=humi_add/cnt;
}

void LEDonNoff()
{
  if(illuminanceValue<50) digitalWrite(led_pin, HIGH); //임시적으로 led 켜고 끄는거
  else if(illuminanceValue>=50) digitalWrite(led_pin, LOW); //위와 동일
}
