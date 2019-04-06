package com.bwie.administrator.shoppingcar.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bwie.administrator.shoppingcar.ui.activity.MainActivity;

public abstract class BaseFragment extends Fragment {

    /*private String mTextviewContent;
    private MainActivity mMainActivity;*/

    private boolean isViewCreated;
    private boolean isUIVisible;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyload();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            isUIVisible = true;
            lazyload();
        }else {
            isUIVisible = false;
        }
    }

    private void lazyload(){
        if (isViewCreated && isUIVisible){
            loadData();
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    protected abstract void loadData();
}
