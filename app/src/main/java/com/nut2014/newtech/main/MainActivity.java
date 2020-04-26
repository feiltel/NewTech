package com.nut2014.newtech.main;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.nut2014.baselibrary.base.BaseActivity;
import com.nut2014.baselibrary.base.BaseParam;
import com.nut2014.baselibrary.networklibrary.NetWorkManager;
import com.nut2014.baselibrary.networklibrary.annotaion.NetWork;
import com.nut2014.baselibrary.networklibrary.type.NetType;
import com.nut2014.baselibrary.utils.FPermission;
import com.nut2014.baselibrary.utils.GlideEngine;
import com.nut2014.newtech.R;
import com.nut2014.newtech.compress.CompressActivity;
import com.nut2014.newtech.constraint.ConstraintActivity;
import com.nut2014.newtech.home.HomeActivity;
import com.nut2014.newtech.login.LoginActivity;
import com.nut2014.newtech.mvp.ContentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.list_rv)
    RecyclerView listRv;


    @Override
    protected void initView() {
        NetWorkManager.getDefault().registerObserver(this);
        listRv.setLayoutManager(new LinearLayoutManager(this));
        List<String> titleList = new ArrayList<>();
        titleList.add("1.约束布局");
        titleList.add("2.MVP架构");
        titleList.add("3.压缩图片工具");
        titleList.add("4.图片选择库");
        titleList.add("5.登录常见布局");
        titleList.add("6.主页常见布局");
        titleList.add("7.测试");
        MainListAdapter mainListAdapter = new MainListAdapter(titleList);
        listRv.setAdapter(mainListAdapter);
        mainListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (fastClick()) {
                    switch (position) {
                        case 0:
                            jumpActivity(ConstraintActivity.class, null);
                            break;
                        case 1:
                            jumpActivity(ContentActivity.class, null);
                            break;
                        case 2:
                            jumpActivity(CompressActivity.class, null);
                            break;
                        case 3:
                            PictureSelector.create(getContext())
                                    .openGallery(PictureMimeType.ofImage())
                                    .enableCrop(true)
                                    .imageSpanCount(3)
                                    .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                            break;
                        case 4:
                            jumpActivity(LoginActivity.class, null);
                            break;
                        case 5:
                            jumpActivity(HomeActivity.class, null);
                            break;
                        case 6:
                            //  jumpActivity(TestActivity.class, null);
                            break;
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.size() > 0) {
                        PictureSelector.create(this)
                                .themeStyle(R.style.picture_default_style)
                                .isNotPreviewDownload(true)
                                .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                                .openExternalPreview(0, selectList);
                    }
                    // 例如 LocalMedia 里面返回五种path
                    // 1.media.getPath(); 原图path
                    // 2.media.getCutPath();裁剪后path，需判断media.isCut();切勿直接使用
                    // 3.media.getCompressPath();压缩后path，需判断media.isCompressed();切勿直接使用
                    // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                    // 5.media.getAndroidQToPath();Android Q版本特有返回的字段，但如果开启了压缩或裁剪还是取裁剪或压缩路径；注意：.isAndroidQTransform 为false 此字段将返回空
                    // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetWorkManager.getDefault().unRegisterObserver(this);
    }

    @NetWork(netType = NetType.AUTO)
    public void netWork(NetType netType) {
        showToast(netType.name());
    }

    @Override
    protected void initEvent() {
        //请求权限
        String[] permissionStr = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        FPermission.getInstance().checkPermission(MainActivity.this, permissionStr, 1, new FPermission.FPermissionCallBack() {
            @Override
            public void granted() {
                showToast("权限：允许");
            }

            @Override
            public void refuse() {
                showToast("权限：拒绝");
            }
        });
    }

    @Override
    public BaseParam getBaseParam() {
        return new BaseParam().setHaveToolbar(true).setTitle(getString(R.string.app_name));
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FPermission.getInstance().onRequestResults(requestCode, permissions, grantResults);
    }


}
