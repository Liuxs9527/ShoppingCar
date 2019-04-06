package com.bwie.administrator.shoppingcar.di.presenter;

import com.bwie.administrator.shoppingcar.di.model.Imodelmpl;
import com.bwie.administrator.shoppingcar.di.view.Iview;
import com.bwie.administrator.shoppingcar.di.view.MyCallBack;

public class IpresenterImp implements Ipresenter {


    private Imodelmpl imodelmpl;
    private Iview iview;

    public IpresenterImp(Iview iview) {
        imodelmpl=new Imodelmpl();
        this.iview = iview;
    }


    @Override
    public void getXBanner(String urlData,Class clazz) {
        imodelmpl.getXBannerData(urlData,clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccessData(data);
            }

            @Override
            public void onFail(Exception e) {
                iview.onFailData(e);
            }
        });
    }
    //get
    @Override
    public void get(String urlData,Class clazz) {
        imodelmpl.get(urlData,clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iview.onSuccessData(data);
            }

            @Override
            public void onFail(Exception e) {
                iview.onFailData(e);
            }
        });
    }


    public void detach(){
        imodelmpl=null;
        iview=null;
    }
}
