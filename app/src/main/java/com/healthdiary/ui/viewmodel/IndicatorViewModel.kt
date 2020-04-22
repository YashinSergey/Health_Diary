package com.healthdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.Note
import com.jjoe64.graphview.series.DataPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class IndicatorViewModel(private val repository: Repository) : ViewModel(), CoroutineScope{

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    var indicatorId = 0

    val viewState : ReceiveChannel<Pair<Indicator?, Array<DataPoint>>> = Channel<Pair<Indicator?, Array<DataPoint>>>(Channel.CONFLATED).apply {
        launch {
            var chartSeries : Array<DataPoint>
            val indicator  = repository.getIndicatorById(indicatorId)
            repository.getNotesByIndicator(indicator).consumeEach {
                chartSeries = getChartSeries(it)
                send(Pair(indicator,chartSeries))
            }


        }
    }

    private fun getChartSeries(notes: List<Note>): Array<DataPoint> {
        return Array(notes.size) {
            val currentNote = notes[it]
            DataPoint(currentNote.date, currentNote.value.toDouble())
        }
    }
}