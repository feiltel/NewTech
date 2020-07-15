package com.nut2014.newtech.viewModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel = ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)
        viewModel.sharedName.observe(requireActivity(), Observer {
            shareBtn.text = it
        })
    }
}
