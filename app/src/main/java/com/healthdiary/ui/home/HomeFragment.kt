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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    private val homeViewModel by viewModel<HomeViewModel>()

    private val RV: RecyclerView by lazy { rv_home }
    val adapter: HomeRVAdapter by lazy {
        val navController = view?.findNavController()
        HomeRVAdapter(get()) {
            val action = NavGraphDirections.actionGlobalIndicatorFragment(it)
            navController?.navigate(action)
        }
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
        initRecycler()
        tv_date.text = dateFormat.format(Date())
    }


    private fun initRecycler() {
        homeViewModel.viewState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            HomeRVAdapter.itemsList = it
        })
        RV.layoutManager = LinearLayoutManager(this.context)
        RV.adapter = adapter

    }
}
