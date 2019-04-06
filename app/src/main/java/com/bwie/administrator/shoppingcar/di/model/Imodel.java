package com.bwie.administrator.shoppingcar.di.model;

import com.bwie.administrator.shoppingcar.di.view.MyCallBack;

public interface Imodel {


    //首页轮播
    void getXBannerData(String urlData, Class clazz, MyCallBack myCallBack);
    //get
    void get(String urlData, Class clazz, MyCallBack myCallBack);

}
