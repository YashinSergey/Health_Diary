package com.healthdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class HomeViewModel(private val repository: Repository) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    val viewState: MutableLiveData<List<Indicator>> = MutableLiveData<List<Indicator>>()

    fun setViewState() {
        Timber.d("Start coroutine")
        launch {
            Timber.d("Coroutine has started")
            val list = repository.getIndicatorList()
            viewState.postValue(list)
            Timber.d("Coroutine ${list.size}")
        }
    }

}
