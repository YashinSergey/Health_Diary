package com.healthdiary.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.healthdiary.NavGraphDirections
import com.healthdiary.R
import com.healthdiary.ui.home.adapters.HomeRVAdapter
import com.healthdiary.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope{

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    private val homeViewModel by viewModel<HomeViewModel>()

    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = view.findNavController()
        initAdapter(navController)
        tv_date.text = dateFormat.format(Date())
    }

    private  fun initAdapter(navController: NavController) {
        val adapter = HomeRVAdapter(get()) {
            val action = NavGraphDirections.actionGlobalIndicatorFragment(it)
            navController.navigate(action)
        }
        launch {
            homeViewModel.viewState.consumeEach {
                adapter.itemsList = it
            }
        }
        initRecycler(adapter)
    }

    private fun initRecycler(adapter: HomeRVAdapter) {
        rv_home.layoutManager = LinearLayoutManager(this.context)
        rv_home.adapter = adapter
    }
}
