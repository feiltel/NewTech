package com.nut2014.newtech.home.tab3;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jaeger.library.StatusBarUtil;
import com.nut2014.newtech.R;

import butterknife.ButterKnife;

public class Tab3Fragment extends Fragment {

    private static final String TAG = "Tab2Fragment";

    public static Tab3Fragment newInstance() {
        return new Tab3Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab3_layout, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
