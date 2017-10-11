package com.example.parkjaeha.firebasetest;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by hyeongkun on 2017-04-07.
 */
/*사용법을 보여주는데 사용한 adapter이다. */
public class VPCustomAdapter extends PagerAdapter{

    LayoutInflater inflater;

    public VPCustomAdapter(LayoutInflater inflater){

        this.inflater=inflater;
    }

    @Override
    public int getCount(){return 3; }  //사용법 이미지개수

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        View view=null;
        view= inflater.inflate(R.layout.viewpager_childview, null);
        ImageView img= (ImageView)view.findViewById(R.id.img_viewpager_childimage);
        img.setImageResource(R.drawable.useimg1+position);
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View v,Object obj){
        return v==obj;
    }
}
