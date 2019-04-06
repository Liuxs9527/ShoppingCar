package com.bwie.administrator.shoppingcar.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.administrator.shoppingcar.R;
import com.bwie.administrator.shoppingcar.data.api.Api;
import com.bwie.administrator.shoppingcar.data.bean.Goods;
import com.bwie.administrator.shoppingcar.data.bean.ShopCarBean;
import com.bwie.administrator.shoppingcar.data.bean.ShopParticularsBean;
import com.bwie.administrator.shoppingcar.di.presenter.IpresenterImp;
import com.bwie.administrator.shoppingcar.di.view.Iview;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ParticularsActivity extends AppCompatActivity implements Iview {

    @BindView(R.id.particulars_back)
    ImageView particularsBack;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.shopxq_price)
    TextView shopxqPrice;
    @BindView(R.id.shopxq_saleNum)
    TextView shopxqSaleNum;
    @BindView(R.id.shopxq_name)
    TextView shopxqName;
    @BindView(R.id.shopxq_xq)
    TextView shopxqXq;
    @BindView(R.id.webView)
    WebView webView;
    private String pid;
    private IpresenterImp ipresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulars);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        Toast.makeText(this, pid, Toast.LENGTH_SHORT).show();
        ipresenter = new IpresenterImp(this);

        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(ParticularsActivity.this).load(path).into(imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });
        ipresenter.getXBanner(Api.ShopXQPath + "?commodityId=" + pid, ShopParticularsBean.class);
    }

    private void addShop(List<Goods> list) {
        String string = "[";
        if (list.size() == 0) {
            list.add(new Goods(Integer.valueOf(pid), 1));
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (Integer.valueOf(pid) == list.get(i).getCommodityId()) {
                    int count = list.get(i).getCount();
                    count++;
                    list.get(i).setCount(count);
                    break;
                } else if (i == list.size() - 1) {
                    list.add(new Goods(Integer.valueOf(pid), 1));
                    break;
                }
            }
        }


        for (Goods goods : list) {
            string += "{\"commodityId\":" + goods.getCommodityId() + ",\"count\":" + goods.getCount() + "},";
        }
        String substring = string.substring(0, string.length() - 1);
        substring += "]";
        Log.i("TAG", substring);
        Map<String, String> map = new HashMap<>();
        map.put("data", substring);
    }


    @Override
    public void onSuccessData(Object data) {

        if (data instanceof ShopParticularsBean) {
            List<String> list = new ArrayList<>();
            ShopParticularsBean bean = (ShopParticularsBean) data;
            ShopParticularsBean.ResultBean result = bean.getResult();
            String[] split = result.getPicture().split(",");
            for (int i = 0; i < split.length; i++) {
                list.add(split[i]);
            }
            banner.setImages(list);
            banner.start();

            shopxqName.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
            shopxqPrice.setText("￥：" + bean.getResult().getPrice() + ".00");
            shopxqName.setText(bean.getResult().getCommodityName() + "");
            shopxqSaleNum.setText("已售" + bean.getResult().getSaleNum() + "件");
            shopxqXq.setText(bean.getResult().getDescribe());
            webView.loadDataWithBaseURL(null, bean.getResult().getDetails(), "text/html", "utf-8", null);

        } else if (data instanceof ShopCarBean) {
            ShopCarBean bean = (ShopCarBean) data;
            if (bean.getStatus().equals("0000")) {
                List<Goods> list = new ArrayList<>();
                List<ShopCarBean.ResultBean> resultBeans = bean.getResult();
                for (ShopCarBean.ResultBean resultBean : resultBeans) {
                    list.add(new Goods(resultBean.getCommodityId(), resultBean.getCount()));

                }
                addShop(list);
                Toast.makeText(ParticularsActivity.this, bean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFailData(Exception e) {

        List<Goods> list = new ArrayList<>();
        list.add(new Goods(Integer.valueOf(pid), 1));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.setVisibility(View.GONE);
            webView.removeAllViews();
            webView.destroy();
        }
    }

    @OnClick(R.id.particulars_back)
    public void onViewClicked() {

        ParticularsActivity.this.finish();

    }
}