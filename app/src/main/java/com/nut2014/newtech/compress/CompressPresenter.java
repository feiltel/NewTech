package com.nut2014.newtech.compress;

import android.content.Context;

import com.nut2014.newtech.MyApp;
import com.nut2014.newtech.mvp.base.BaseMvpPresenter;

public class CompressPresenter extends BaseMvpPresenter<CompressView> {
    private CompressModel compressModel;
    private Context context = MyApp.context;
    public CompressPresenter() {
        compressModel = new CompressModel(context);
    }


    public void starCompress(String path, int quality, int maxHeight, int maxWidth) {
        getMvpView().startCompress();
        compressModel.compressPictures(path, quality, maxHeight, maxWidth, new CompressModel.CompressCallBack() {
            @Override
            public void success() {
                if (getMvpView() != null) {
                    getMvpView().endCompress();
                }
            }

            @Override
            public void compressInfo(String msg) {
                if (getMvpView() != null) {
                    getMvpView().setLogInfo(msg);
                }
            }
        });

    }


}
