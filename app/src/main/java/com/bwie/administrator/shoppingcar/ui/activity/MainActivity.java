package com.bwie.administrator.shoppingcar.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bwie.administrator.shoppingcar.R;
import com.bwie.administrator.shoppingcar.data.adapter.ViewpagerAdapter;
import com.bwie.administrator.shoppingcar.ui.fragment.MyFragment;
import com.bwie.administrator.shoppingcar.ui.fragment.ShopcarFragment;
import com.bwie.administrator.shoppingcar.ui.fragment.ShouyeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener{

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.radio_shouye)
    RadioButton radioShouye;
    @BindView(R.id.radio_shopcar)
    RadioButton radioShopcar;
    @BindView(R.id.radio_my)
    RadioButton radioMy;
    @BindView(R.id.rads)
    RadioGroup rads;

    private ViewpagerAdapter viewpagerAdapter;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        list = new ArrayList<Fragment>();
        list.add(new ShouyeFragment());
        list.add(new ShopcarFragment());
        list.add(new MyFragment());

        viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(),list);
        viewpager.setAdapter(viewpagerAdapter);

        viewpager.addOnPageChangeListener(this);
        rads.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i){
            case 0:
                rads.check(R.id.radio_shouye);
                break;
            case 1:
                rads.check(R.id.radio_shopcar);
                break;
            case 2:
                rads.check(R.id.radio_my);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio_shouye:
                viewpager.setCurrentItem(0);
                break;
            case R.id.radio_shopcar:
                viewpager.setCurrentItem(1);
                break;
            case R.id.radio_my:
                viewpager.setCurrentItem(2);
                break;
        }
    }
}
