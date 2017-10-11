package com.example.parkjaeha.firebasetest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parkjaeha on 2017-02-03.
 */
/*
    ContactAdapter는 DrawTotalGraphActivity에서 listTable을 구현하기 위해 만든 클래스로 테이블 형식으로 가져올 수 있게 도와준다.
*/

//DisplayListView Viewer row_data
public class ContactAdapter extends ArrayAdapter {
    List list = new ArrayList();                        //list를 arraylist 객체로 선언

    public ContactAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Contacts object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }       //get list size

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }   //get list position

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {     //데이터를 list에 뿌려주기

        View row;
        row = convertView;
        ContactHolder contactHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_row_layout,parent,false);            //contactholder에 row set하기
            contactHolder = new ContactHolder();
            contactHolder.tx_moi = (TextView) row.findViewById(R.id.tx_moi);
            contactHolder.tx_ill = (TextView) row.findViewById(R.id.tx_ill);
            contactHolder.tx_temp = (TextView) row.findViewById(R.id.tx_temp);
            contactHolder.tx_humi = (TextView) row.findViewById(R.id.tx_humi);
            contactHolder.tx_date = (TextView) row.findViewById(R.id.tx_date);


            row.setTag(contactHolder);
        }
        else{
            contactHolder = (ContactHolder)row.getTag();
        }
        Contacts contacts = (Contacts) this.getItem(position);          //data set하기
        contactHolder.tx_moi.setText(contacts.getMoi());
        contactHolder.tx_ill.setText(contacts.getIll());
        contactHolder.tx_temp.setText(contacts.getTemp());
        contactHolder.tx_humi.setText(contacts.getHumi());
        contactHolder.tx_date.setText(contacts.getDate());

        return row;
    }

    static class ContactHolder{                         //textview 선언

        TextView tx_moi,tx_ill,tx_temp,tx_humi,tx_date;

    }
}
