package com.nut2014.newtech.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
        viewModel.btnName.observe(activity!!, Observer {
            jump_btn.text = it
        })

        jump_btn.setOnClickListener(View.OnClickListener {
            viewModel.btnName.value = "已跳转"
            findNavController().navigate(R.id.acton_nav1_to_nav2)
        })
    }

}