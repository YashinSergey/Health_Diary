package com.healthdiary.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.localstorage.LocalDataSource
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class HomeViewModel() : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    val viewState : MutableLiveData<List<Indicator>> = MutableLiveData<List<Indicator>>()

    init{
        setViewState()
    }

    fun setViewState(): Channel<List<Indicator>> =
        Channel<List<Indicator>>(Channel.CONFLATED).apply {
            launch {
                LocalDataSource.getIndicatorList().consumeEach {
                    viewState.postValue(it)
                    Timber.d("Coroutine ${it.size}")
                }
            }
        }

}
