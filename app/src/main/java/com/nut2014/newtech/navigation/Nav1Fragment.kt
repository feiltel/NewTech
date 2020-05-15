package com.nut2014.newtech.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.jaeger.library.StatusBarUtil
import com.nut2014.newtech.R
import kotlinx.android.synthetic.main.nav1_fragment.*

class Nav1Fragment : Fragment() {

    companion object {
        fun newInstance() = Nav1Fragment()
    }

    private lateinit var viewModel: Nav1ViewModel
    private lateinit var listAdapter: Nav1Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.nav1_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUtil.setTransparent(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this)[Nav1ViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView();
        initEvent();
    }

    private fun initEvent() {
        //数据变化监听
        viewModel.listData.observe(activity!!, Observer {
            //(list_rv.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(viewModel.scrollInfoPos.value!!, viewModel.scrollInfoY.value!!)
            listAdapter.setList(it)
            println("数据变化监听:$it")
        })
        //点击事件
        listAdapter.setOnItemClickListener(OnItemClickListener { adapter, view, position ->
            val value = viewModel.listData.value!!.toMutableList()
            value[position] = "已点击"
            viewModel.listData.value = value;
            //带参数跳转
            val args = Bundle()
            args.putString("data", "带参数跳转")
            findNavController().navigate(R.id.acton_nav1_to_nav2,args)
            //findNavController().navigate(R.id.acton_nav1_to_nav2)
        })
    }

    private fun initView() {
        list_rv.layoutManager = LinearLayoutManager(activity);
        listAdapter = Nav1Adapter(viewModel.listData.value);
        list_rv.adapter = listAdapter;
    }
}
