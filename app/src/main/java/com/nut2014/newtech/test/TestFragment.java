package com.nut2014.newtech.test;

import com.nut2014.baselibrary.base.BaseMvpFragment;
import com.nut2014.newtech.compress.CompressPresenter;
import com.nut2014.newtech.compress.CompressView;

/**
 * @author feiltel 2020/7/10 0010
 */
public class TestFragment extends BaseMvpFragment<CompressView, CompressPresenter> implements CompressView {
    @Override
    protected CompressPresenter createPresenter() {
        return null;
    }

    @Override
    public void setLogInfo(String msg) {

    }

    @Override
    public void startCompress() {

    }

    @Override
    public void endCompress() {

    }

    @Override
    public void compressProgress(int progress) {

    }
}
