package com.bwie.administrator.shoppingcar.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bwie.administrator.shoppingcar.R;
import com.bwie.administrator.shoppingcar.data.adapter.ByKeyWordAdapter;
import com.bwie.administrator.shoppingcar.data.api.Api;
import com.bwie.administrator.shoppingcar.data.bean.ByKeywordBean;
import com.bwie.administrator.shoppingcar.di.presenter.IpresenterImp;
import com.bwie.administrator.shoppingcar.di.view.Iview;
import com.bwie.administrator.shoppingcar.ui.activity.ParticularsActivity;
import com.bwie.administrator.shoppingcar.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShouyeFragment extends BaseFragment implements Iview {

    @BindView(R.id.sy_menu)
    ImageView syMenu;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.sy_sousuo)
    ImageView sySousuo;
    Unbinder unbinder;
    @BindView(R.id.moreRecycle)
    RecyclerView moreRecycle;
    @BindView(R.id.morelinear)
    LinearLayout morelinear;
    private String ed;
    private int pznum = 2;

    private ByKeyWordAdapter byKeyWordAdapter;
    private IpresenterImp ipresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shouye, container, false);
        unbinder = ButterKnife.bind(this, view);

        ipresenter = new IpresenterImp(this);


        sySousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed = edit.getText().toString();
                initByKeyWordData();
            }
        });
        return view;
    }

    private void initByKeyWordData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), pznum);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        moreRecycle.setLayoutManager(gridLayoutManager);
        Log.e("cxy", edit.getText().toString());
        ipresenter.getXBanner(Api.ByKeywordPath + "?keyword=" + ed + "&page=1&count=10", ByKeywordBean.class);
        byKeyWordAdapter = new ByKeyWordAdapter(getContext());
        moreRecycle.setAdapter(byKeyWordAdapter);
        byKeyWordAdapter.setOnByKeyWordClickListenter(new ByKeyWordAdapter.ByKeyWordClickListenter() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ParticularsActivity.class);
                int pid = byKeyWordAdapter.getPid(position);
                Bundle bundle = new Bundle();
                bundle.putString("pid", pid + "");
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onSuccessData(Object data) {
        ByKeywordBean byKeywordBean= (ByKeywordBean) data;
        List<ByKeywordBean.ResultBean> result = byKeywordBean.getResult();

        if(result.size()==0){
            Toast.makeText(getActivity(),"没有您想要的内容",Toast.LENGTH_SHORT).show();

        }else {
            byKeyWordAdapter.setResultBeans(byKeywordBean.getResult());

        }
    }

    @Override
    public void onFailData(Exception e) {

    }

    @Override
    protected void loadData() {
        Log.i("TAG","shouyeFragemnt");
    }
}
