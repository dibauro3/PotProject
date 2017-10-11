package com.example.parkjaeha.firebasetest;

import android.graphics.drawable.Drawable;

/**
 * Created by hyeongkun on 2017-04-10.
 */
/*식물 정보리스트를 보여주기 위한 DTO 형식의 클래스이다.*/
public class PlantListItem {
    private Drawable iconDrawable ;
    private String mtitle ;
    private String mdesc ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        mtitle = title ;
    }
    public void setDesc(String desc) {
        mdesc = desc ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.mtitle ;
    }
    public String getDesc() {
        return this.mdesc ;
    }
}


