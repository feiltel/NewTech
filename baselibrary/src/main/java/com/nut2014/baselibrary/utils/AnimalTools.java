package com.nut2014.baselibrary.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class AnimalTools {


    public static void propertyValuesHolder(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f,
                0.5f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0,
                0.5f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0,
                0.5f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(1500).start();
    }

    /**
     * 抛物线
     * @param view
     */
    public void parabola(final View view) {

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {


                PointF point = (PointF) animation.getAnimatedValue();
                view.setX(point.x);
                view.setY(point.y);
            }
        });
    }

    //顺序播放动画
    public static void playWithAfter(View view) {

        int[] location = new int[2];
       // view.getLocationOnScreen(location);
//        int x = location[0];
        //int y = location[1];
        float cx = view.getX();
        ObjectAnimator animAlpha = ObjectAnimator.ofFloat(view, "alpha",
                1.0f, 0.0f);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(view, "scaleX",
                1.0f, 2f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "scaleY",
                1.0f, 2f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(view,
                "x", cx, 0f);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(view,
                "x", 180);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2).with(animAlpha);
        animSet.play(anim2).with(anim3);
        animSet.play(anim3).after(anim4);
        animSet.setDuration(1000);
        animSet.start();
    }

    //属性动画
    public static void performAnimate(View view, int Hpx) {
        ViewWrapper wrapper = new ViewWrapper(view);
        ObjectAnimator.ofInt(wrapper, "Height", Hpx).setDuration(1000).start();
    }

    //属性动画实现类
    private static class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View target) {
            mTarget = target;
        }

        public void setHeight(int height) {
            mTarget.getLayoutParams().height = height;
            mTarget.requestLayout();
        }

        public int getHeight() {
            return mTarget.getLayoutParams().height;
        }

        public int getWidth() {
            return mTarget.getLayoutParams().width;
        }

        public void setWidth(int width) {
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }
    }
}
