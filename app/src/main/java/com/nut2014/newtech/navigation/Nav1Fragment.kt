package com.nut2014.newtech.navigation

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        viewModel = ViewModelProviders.of(this).get(Nav1ViewModel::class.java)
        // TODO: Use the ViewModel
        jump_btn.setOnClickListener(View.OnClickListener {
            jump_btn.text=">>>>>>>>>>>>>";
            findNavController().navigate(R.id.nav2Fragment)
        })
    }

}
