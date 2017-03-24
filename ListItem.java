package com.example.hyeongkun.jsontest;

/**
 * Created by hyeongkun on 2017-03-21.
 */

public class ListItem {

    private String[] mData;

    public ListItem(String[] data ){

        mData = data;
    }

    public ListItem(String num, String val){

        mData = new String[2];
        mData[0] = num;
        mData[1] = val;

    }

    public String[] getData(){

        return mData;
    }

    public String getData(int index){

        return mData[index];
    }

    public void setData(String[] data){

        mData = data;

    }


}
