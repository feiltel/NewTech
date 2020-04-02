package com.nut2014.newtech.mvp;

import androidx.annotation.NonNull;

import com.nut2014.newtech.mvp.base.BaseMvpPresenter;
import com.nut2014.newtech.retrofit.ResponseBean;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

public class ContentPresenter extends BaseMvpPresenter<ContentView> {


    public ContentPresenter(LifecycleProvider<ActivityEvent> provider) {
        super(provider);

    }

    public void login(String userName, String password) {
        if (getMvpView() != null) {
            getMvpView().showLoad();
        }

        ContentModel.getInstance().loginAct(userName, password)
                .subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .compose(getProvider().bindUntilEvent(ActivityEvent.DESTROY))// onDestroy取消订阅
                .subscribe(new DefaultObserver<ResponseBean>() {  // 订阅
                    @Override
                    public void onNext(@NonNull ResponseBean mainListBean) {
                        if (getMvpView() != null) {
                            if (mainListBean.getCode() == 1) {
                                getMvpView().jumpMain();
                            } else {
                                getMvpView().showToast();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (getMvpView() != null) {
                            getMvpView().showToast();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (getMvpView() != null) {
                            getMvpView().hideLoad();
                        }
                    }
                });
    }

}
