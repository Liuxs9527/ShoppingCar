package com.bwie.administrator.shoppingcar.di.model;

import com.bwie.administrator.shoppingcar.data.util.OkHttpUtils;
import com.bwie.administrator.shoppingcar.di.view.MyCallBack;
import com.google.gson.Gson;

import java.util.Map;

public class Imodelmpl implements Imodel {


    @Override
    public void getXBannerData(String urlData, final Class clazz, final MyCallBack myCallBack) {
        OkHttpUtils.getOkHttpUtils().getXBanners(urlData, new OkHttpUtils.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                if (myCallBack != null) {
                    myCallBack.onSuccess(o);
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    /**
     * get
     * @param urlData
     * @param clazz
     * @param myCallBack
     */
    @Override
    public void get(String urlData, final Class clazz, final MyCallBack myCallBack) {
        OkHttpUtils.getOkHttpUtils().get(urlData, new OkHttpUtils.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack != null){
                        myCallBack.onSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(myCallBack != null){
                        myCallBack.onFail(e);
                    }
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

}
