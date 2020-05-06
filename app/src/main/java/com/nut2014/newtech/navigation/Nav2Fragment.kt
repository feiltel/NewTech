package com.nut2014.newtech.navigation

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nut2014.newtech.R

class Nav2Fragment : Fragment() {

    companion object {
        fun newInstance() = Nav2Fragment()
    }

    private lateinit var viewModel: Nav2ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.nav2_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Nav2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
