package com.example.parkjaeha.firebasetest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hyeongkun on 2017-04-10.
 */
/* 식물 정보리스트로 보여주기 위한 adapter이다. */
public class PlantListViewAdapter extends BaseAdapter implements Filterable {

    private ArrayList<PlantListItem> listViewItemList = new ArrayList<PlantListItem>() ;
    private ArrayList<PlantListItem> filteredItemList = listViewItemList ;

    Filter listFilter;

    public PlantListViewAdapter() {

    }


    @Override
    public int getCount() {
        return filteredItemList.size() ;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.plant_listview_item, parent, false);
        }


        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;


        PlantListItem listViewItem = filteredItemList.get(position);


        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());

        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return position ;
    }


    @Override
    public PlantListItem getItem(int position) {

        return filteredItemList.get(position) ;
    }


    public void addItem(Drawable icon, String title, String desc) {
        PlantListItem item = new PlantListItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);

        listViewItemList.add(item);
    }

    private class ListFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results= new FilterResults();

            if(charSequence==null|| charSequence.length()==0){
                results.values=listViewItemList;
                results.count=listViewItemList.size();
            }else{
                ArrayList<PlantListItem> platnlist= new ArrayList<PlantListItem>();

                for(PlantListItem item : listViewItemList){
                    if(item.getTitle().toUpperCase().contains(charSequence.toString().toUpperCase())
                           ){
                        platnlist.add(item);
                    }
                }
                results.values=platnlist;
                results.count=platnlist.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {

            filteredItemList=(ArrayList<PlantListItem>) results.values;

            if(results.count>0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }
        }
    }

    @Override
    public Filter getFilter(){
        if(listFilter ==null){
            listFilter= new ListFilter();
        }
        return listFilter;
    }
}
