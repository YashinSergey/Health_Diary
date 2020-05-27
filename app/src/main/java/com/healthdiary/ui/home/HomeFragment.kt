package com.healthdiary.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthdiary.NavGraphDirections
import com.healthdiary.R
import com.healthdiary.ui.home.adapters.HomeRVAdapter
import com.healthdiary.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalCoroutinesApi
class HomeFragment : Fragment() {

    private val homeViewModel by viewModel<HomeViewModel>()

    val adapter: HomeRVAdapter by lazy {
        initAdapter()
    }

    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.setViewState()
        initRecycler()
        tv_date.text = dateFormat.format(Date())
    }

    fun initAdapter(): HomeRVAdapter {
        Timber.d("initAdapter")
        val navController = view?.findNavController()
        val adapter =  HomeRVAdapter(get()) {
            val action = NavGraphDirections.actionGlobalIndicatorFragment(it)
            navController?.navigate(action)
        }
        homeViewModel.viewState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.itemsList = it
        })
        return adapter
    }


    private fun initRecycler() {
        Timber.d("initRV")
        Timber.d("Adapter count is ${adapter.itemCount}")
        rv_home.layoutManager = LinearLayoutManager(this.context)
        rv_home.adapter = adapter
    }
}
