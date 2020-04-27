package com.nut2014.newtech.dataBinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.chad.library.BR;

/**
 * @author feiltel 2020/4/27 0027
 */
public class VmDbBean extends BaseObservable {
    public VmDbBean(int id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }

    @Bindable
    public int id;
    @Bindable
    public String name;
    @Bindable
    public String title;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
