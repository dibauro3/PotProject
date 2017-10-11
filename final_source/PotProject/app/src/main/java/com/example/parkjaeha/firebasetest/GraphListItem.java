package com.example.parkjaeha.firebasetest;

/**
 * Created by hyeongkun on 2017-03-24.
 */
//rename to GraphListItem from ListItem 2017 04 10

public class GraphListItem {

    private String[] mData;

    public GraphListItem(String[] data ){

        mData = data;
    }

    public GraphListItem(String num, String val){

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
