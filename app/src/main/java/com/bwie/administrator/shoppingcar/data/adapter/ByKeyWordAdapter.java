package com.bwie.administrator.shoppingcar.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.administrator.shoppingcar.R;
import com.bwie.administrator.shoppingcar.data.bean.ByKeywordBean;

import java.util.ArrayList;
import java.util.List;

public class ByKeyWordAdapter extends RecyclerView.Adapter<ByKeyWordAdapter.ViewHolder> {


    private List<ByKeywordBean.ResultBean> resultBeans=new ArrayList<>();
    private Context mContext;

    public ByKeyWordAdapter(Context Context) {

        this.mContext = Context;
    }


    public void setResultBeans(List<ByKeywordBean.ResultBean> resultBeans) {
        this.resultBeans = resultBeans;
        notifyDataSetChanged();
    }

    public int getPid(int position){
        return resultBeans.get(position).getCommodityId();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(mContext, R.layout.rxxp_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        Glide.with(mContext).load(resultBeans.get(position).getMasterPic()).into(holder.sy_shopimg);
        holder.sy_shopprice.setText("￥："+resultBeans.get(position).getPrice()+".00");
        holder.sy_shopname.setText(resultBeans.get(position).getCommodityName()+"");
        holder.rxxpLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listenter!=null){
                    listenter.onClick(position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return resultBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView sy_shopimg;
        private final TextView sy_shopname;
        private final TextView sy_shopprice;
        private final LinearLayout rxxpLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            sy_shopimg = itemView.findViewById(R.id.sy_shopimg);
            sy_shopname = itemView.findViewById(R.id.sy_shopname);
            sy_shopprice = itemView.findViewById(R.id.sy_shopprice);
            rxxpLinearLayout = itemView.findViewById(R.id.rxxpLinearLayout);
        }
    }
    public ByKeyWordClickListenter listenter;

    public  void setOnByKeyWordClickListenter(ByKeyWordClickListenter clickListenter){
        listenter=clickListenter;
    }


    public interface ByKeyWordClickListenter{
        void onClick(int position);
    }
}

