package com.bwie.administrator.shoppingcar.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.administrator.shoppingcar.R;
import com.bwie.administrator.shoppingcar.ui.base.BaseFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MyFragment extends BaseFragment {

    @BindView(R.id.relative)
    RelativeLayout relative;
    @BindView(R.id.image_myhead)
    SimpleDraweeView imageMyhead;
    @BindView(R.id.text_screenname)
    TextView textScreenname;
    Unbinder unbinder;
    private TextView photo, album, dismiss;
    private PopupWindow popupWindow;
    private final String path = Environment.getExternalStorageDirectory() + "/image.png";
    private final int REQUEST_CAMEAR = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.image_myhead)
    public void onViewClicked() {
        popuhead();
    }

    //上传头像
    public void popuhead() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popu_head, null, false);
        photo = contentView.findViewById(R.id.photo);
        album = contentView.findViewById(R.id.album);
        dismiss = contentView.findViewById(R.id.dismiss);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(getActivity().getResources().getColor(R.color.coloroo));
        popupWindow.setBackgroundDrawable(dw);
        imageMyhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(v, 80, 100);
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //6.0权限
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    String[] mStatenetwork = new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.INTERNET,
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_APN_SETTINGS,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                    };
                    ActivityCompat.requestPermissions(getActivity(), mStatenetwork, 100);
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                startActivityForResult(intent, REQUEST_CAMEAR);

            }
        });
        album.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 200);
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void loadData() {
        Log.i("TAG","MyFragemnt");
    }
}
