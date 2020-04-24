package com.nut2014.newtech.home.tab1;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.nut2014.baselibrary.utils.AnimalTools;
import com.nut2014.baselibrary.utils.WindowUtils;
import com.nut2014.newtech.MyApp;
import com.nut2014.newtech.R;

public class MHeaderView implements IHeaderView {
    private ImageView im;

    private View init() {
        View inflate = LayoutInflater.from(MyApp.context).inflate(R.layout.refresh_item_header, null);
        im = inflate.findViewById(R.id.im);
        return inflate;
    }

    @Override
    public View getView() {
        return init();
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        im.setScaleX(fraction);
        im.setScaleY(fraction);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        im.setScaleX(fraction);
        im.setScaleY(fraction);
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        AnimalTools.playWithAfter(im);
    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        animEndListener.onAnimEnd();
        im.setScaleX(1);
        im.setScaleY(1);
    }

    @Override
    public void reset() {

    }
}
