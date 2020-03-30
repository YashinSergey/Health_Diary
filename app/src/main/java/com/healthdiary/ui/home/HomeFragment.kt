package com.healthdiary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.healthdiary.R
import com.healthdiary.ui.MainActivity
import com.healthdiary.ui.indicator.IndicatorFragment
import com.healthdiary.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class HomeFragment : Fragment() {

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        message.setOnClickListener {
            IndicatorFragment.start(activity as MainActivity, 0) }
        homeViewModel.viewState.observe(viewLifecycleOwner, Observer {
            Timber.d(it.toString())
        })
    }
}
