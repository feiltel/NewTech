package com.nut2014.newtech.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.jaeger.library.StatusBarUtil
import com.nut2014.baselibrary.utils.MToast
import com.nut2014.newtech.R
import kotlinx.android.synthetic.main.nav2_fragment.*

class Nav2Fragment : Fragment() {

    companion object {
        fun newInstance() = Nav2Fragment()
    }

    private lateinit var viewModel: Nav2ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.nav2_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val string = arguments?.getString("data")
        MToast.show(activity,string);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //默认状态栏全透明
        StatusBarUtil.setColor(activity, resources.getColor(R.color.colorPrimary), 0)
        viewModel = ViewModelProviders.of(this).get(Nav2ViewModel::class.java)
        // TODO: Use the ViewModel
        back_btn.setOnClickListener(View.OnClickListener {
            findNavController().navigateUp();
        })
    }

}
