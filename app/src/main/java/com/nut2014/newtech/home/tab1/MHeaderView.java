package com.nut2014.newtech.home.tab1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.nut2014.baselibrary.utils.WindowUtils;
import com.nut2014.newtech.MyApp;
import com.nut2014.newtech.R;

public class MHeaderView implements IHeaderView {
    private ImageView im;

    Animation rotate_anim = null;

    @Override
    public View getView() {
        return init();
    }

    private View init() {
        View inflate = LayoutInflater.from(MyApp.context).inflate(R.layout.refresh_item_header, null);
        im = inflate.findViewById(R.id.im);
        LinearLayout headerRoot = inflate.findViewById(R.id.header_root);
        headerRoot.setPadding(0, WindowUtils.getStatusBarHeight(inflate.getContext()), 0, 0);
        return inflate;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        //  im.setScaleX(fraction);
        //im.setScaleY(fraction);

        float percentage = fraction * headHeight / maxHeadHeight;
        System.out.println(percentage + ">>");
        im.setRotation(percentage * 360);
        im.setAlpha(percentage);

        // im.setTranslationY(percentage*20);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        //  im.setScaleX(fraction);
        //  im.setScaleY(fraction);
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        //  AnimationSet animationSet = new AnimationSet(true);
        im.clearAnimation();
        rotate_anim = AnimationUtils.loadAnimation(MyApp.context, R.anim.rotate_anim);
        im.startAnimation(rotate_anim);



       /* Animation rotateAnimation = AnimationUtils.loadAnimation(MyApp.context, R.anim.translate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        rotateAnimation.setRepeatCount(-1);
        im.startAnimation(rotateAnimation);*/

    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        animEndListener.onAnimEnd();
        im.setScaleX(1);
        im.setScaleY(1);
    }

    @Override
    public void reset() {
        if (rotate_anim != null) {
            rotate_anim.reset();
            im.clearAnimation();
        }
    }
}
