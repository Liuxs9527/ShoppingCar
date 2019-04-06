package com.bwie.administrator.shoppingcar.di.model;

import com.bwie.administrator.shoppingcar.data.api.Api;
import com.bwie.administrator.shoppingcar.di.contrant.ShoppingCartContract;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class ShoppinCartModel implements ShoppingCartContract.IModel {
    @Override
    public void containData(final OnCallBackListener onCallBackListener) {
        OkGo.<String>get(Api.SHOPPINGCART_URL).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String resposneData = response.body().toString();
                //回传给P层
                onCallBackListener.onCallBack(resposneData);
            }
        });
    }
}
