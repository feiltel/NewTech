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
import androidx.recyclerview.widget.RecyclerView
import com.nut2014.newtech.R
import kotlinx.android.synthetic.main.nav1_fragment.*

class Nav1Fragment : Fragment() {

    companion object {
        fun newInstance() = Nav1Fragment()
    }

    private lateinit var viewModel: Nav1ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.nav1_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this)[Nav1ViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_rv.layoutManager = LinearLayoutManager(activity);
        list_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                viewModel.scrollInfoY.value=dy;
                println("$dx>>$dy")
                val positionAndOffset = getPositionAndOffset(recyclerView)
                viewModel.scrollInfoPos.value= positionAndOffset!![0]
                viewModel.scrollInfoY.value= positionAndOffset!![1]

            }
        })
        viewModel.btnName.observe(activity!!, Observer {
            jump_btn.text = it
        })
        viewModel.listData.observe(activity!!, Observer {
            list_rv.adapter=Nav1Adapter(it);
            (list_rv.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(viewModel.scrollInfoPos.value!!,viewModel.scrollInfoY.value!!)
        })


        jump_btn.setOnClickListener(View.OnClickListener {
            viewModel.btnName.value = "已跳转"
            var listData=ArrayList<String>();
            listData.add(">>>1");
            listData.add(">>>2");
            listData.add(">>>3");
            listData.add(">>>4");
            listData.add(">>>5");
            listData.add(">>>6");
            listData.add(">>>6");
            listData.add(">>>6");
            viewModel.listData.value=listData;
            findNavController().navigate(R.id.acton_nav1_to_nav2)
        })
        initData()
    }
    private fun initData(){
        var listData=ArrayList<String>();
        for (i in 1..30) {
           listData.add("测试$i")
        }
        viewModel.listData.value=listData;
    }
    private fun getPositionAndOffset(recyclerView: RecyclerView): IntArray? {
        val posArr = IntArray(2)
        val layoutManager = recyclerView.layoutManager
        //获取可视的第一个view
        val topView = layoutManager!!.getChildAt(0)
        if (topView != null) {
            //得到该View的数组位置
            posArr[0] = layoutManager.getPosition(topView)
            //获取与该view的顶部的偏移量
            posArr[1] = topView.top
        }
        return posArr;
    }
}
