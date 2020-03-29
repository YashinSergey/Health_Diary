package com.healthdiary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.healthdiary.R
import com.healthdiary.model.data.localstorage.LocalDataSource
import com.healthdiary.ui.viewmodel.HomeViewModel
import timber.log.Timber

class HomeFragment : Fragment() {

    private var viewModel = HomeViewModel(LocalDataSource)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            Timber.d(it.toString())
        })
    }
}
