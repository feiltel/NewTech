package com.nut2014.newtech.viewModel

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.nut2014.newtech.R
import kotlinx.android.synthetic.main.share_fragment.*

class ShareFragment : Fragment() {

    companion object {
        fun newInstance() = ShareFragment()
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.share_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel = ViewModelProvider(activity!!).get(ShareViewModel::class.java)
        viewModel.sharedName.observe(activity!!, Observer {
            shareBtn.text=it
        })
    }
}
