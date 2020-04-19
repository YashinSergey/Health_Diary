package com.healthdiary.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.localstorage.LocalDataSource
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(repository: Repository) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Default

    val viewState: ReceiveChannel<List<Indicator>> =
        Channel<List<Indicator>>(Channel.CONFLATED).apply {
            launch {
                LocalDataSource.getIndicatorList().consumeEach {
                    send(it)
                }
            }
        }

}
