package com.bwie.administrator.shoppingcar.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bwie.administrator.shoppingcar.R;
import com.bwie.administrator.shoppingcar.data.adapter.BusinessAdapter;
import com.bwie.administrator.shoppingcar.data.bean.ShoppingCarBean;
import com.bwie.administrator.shoppingcar.di.contrant.ShoppingCartContract;
import com.bwie.administrator.shoppingcar.di.presenter.ShoppingCarPresenter;
import com.bwie.administrator.shoppingcar.ui.base.BaseFragment;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShopcarFragment extends BaseFragment implements ShoppingCartContract.IView,CompoundButton.OnClickListener{

    @BindView(R.id.rv_business)
    RecyclerView rvBusiness;
    @BindView(R.id.srl_container)
    SmartRefreshLayout srlContainer;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.btn_price)
    Button btnPrice;
    @BindView(R.id.tv_count)
    TextView tvCount;
    Unbinder unbinder;
    private ShoppingCartContract.IPresenter presenter;
    private Context mContext;
    private List<ShoppingCarBean.DataBean> businessList;
    private BusinessAdapter adapter;
    private AlertDialog alertDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopcar, container, false);

        unbinder = ButterKnife.bind(this, view);

        presenter = new ShoppingCarPresenter();
        presenter.attachView(this);
        presenter.requestData();

        return view;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showData(String mCartString) {
        cbAll.setOnCheckedChangeListener(null);
        //全选 反选的处理
        cbAll.setOnClickListener(this);
        //数据解析展示
        ShoppingCarBean carBean = new Gson().fromJson(mCartString, ShoppingCarBean.class);
        //外层商家条目数的数据源
        businessList = carBean.getData();
        //设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvBusiness.setLayoutManager(manager);
        //设置适配器
        adapter = new BusinessAdapter(R.layout.recycler_business_item,businessList);
        rvBusiness.setAdapter(adapter);
        adapter.setOnBusinessItemClickLisenter(new BusinessAdapter.OnBusinessItemClickLisenter() {
            @Override
            public void onCallBack() {
                boolean result = true;
                for (int i = 0; i < businessList.size(); i++) {
                    boolean businessChecked = businessList.get(i).getBusinessChecked();
                    result = result & businessChecked;
                    for (int j = 0; j < businessList.get(i).getList().size(); j++) {
                        boolean goodsChecked = businessList.get(i).getList().get(j).getGoodsChecked();
                        result = result & goodsChecked;
                    }
                }
                cbAll.setChecked(result);
                calculateTotalCount();
            }
        });

    }
    private void calculateTotalCount(){
        //对总价进行计算
        double totalCount = 0;
        //外层条目
        for (int i = 0; i < businessList.size(); i++) {
            for (int j = 0; j < businessList.get(i).getList().size(); j++) {
                //判断条目是否勾选
                if (businessList.get(i).getList().get(j).getGoodsChecked() == true){
                    //获取商品数据  +   商品价格
                    double price = businessList.get(i).getList().get(j).getPrice();
                    int defalutNumber = businessList.get(i).getList().get(j).getDefalutNumber();
                    double goodsPrice = price * defalutNumber;
                    totalCount = totalCount + goodsPrice;
                }
            }
        }
        tvCount.setText("总价是:" + String.valueOf(totalCount));
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < businessList.size(); i++) {
            businessList.get(i).setBusinessChecked(cbAll.isChecked());
            for (int j = 0; j < businessList.get(i).getList().size(); j++) {
                businessList.get(i).getList().get(j).setGoodsChecked(cbAll.isChecked());
            }
        }
        adapter.notifyDataSetChanged();
        calculateTotalCount();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView(this);
    }

    @Override
    protected void loadData() {
        Log.i("TAG","ShopFragemnt");
    }
}
