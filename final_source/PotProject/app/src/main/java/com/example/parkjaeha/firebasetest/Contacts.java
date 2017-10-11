package com.example.parkjaeha.firebasetest;

/**
 * Created by parkjaeha on 2017-02-03.
 */
/*
    contacts는 DTO의 개념으로 만든 클래스이다.
*/
//contacts define
public class Contacts {

    private  String moi,ill,temp,humi,date,update;

    public  Contacts(String moi,String ill,String temp,String humi,String date){
                                                                                    //가져온 데이터 set하기
        this.setMoi(moi);
        this.setIll(ill);
        this.setTemp(temp);
        this.setHumi(humi);
        this.setDate(date);

    }
                                                    //데이터별로 분류하기
    public String getMoi() {
        return moi;
    }

    public void setMoi(String moi) {
        this.moi = moi;
    }

    public String getIll() {
        return ill;
    }

    public void setIll(String ill) {
        this.ill = ill;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumi() {
        return humi;
    }

    public void setHumi(String humi) {
        this.humi = humi;
    }

    public String getDate() {
        //int i = date.indexOf(".0000");
        //update = date.substring(0,i);
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
