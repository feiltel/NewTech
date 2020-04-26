package com.nut2014.baselibrary.base;

/**
 * @author feiltel 2020/4/10 0010
 */
public class BaseParam {

    //设置为统一toolbar
    private boolean haveToolbar=false;
    //设置toolbar 上标题
    private String title="";
    //toolbar是否有返回键
    private boolean haveBackAction=false;


    //设置为全屏
    private boolean isTransparent =false;




    public String getTitle() {
        return title;
    }

    public BaseParam setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isHaveBackAction() {
        return haveBackAction;
    }

    public BaseParam setHaveBackAction(boolean haveBackAction) {
        this.haveBackAction = haveBackAction;
        return this;
    }
    public BaseParam setHaveToolbar(boolean haveToolbar) {
        this.haveToolbar = haveToolbar;
        return this;
    }

    public BaseParam setTransparent(boolean transparent) {
        isTransparent = transparent;
        return this;
    }

    public boolean isHaveToolbar() {
        return haveToolbar;
    }

    public boolean isTransparent() {
        return isTransparent;
    }
}
