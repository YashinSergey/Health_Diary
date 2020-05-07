package com.healthdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.IndicatorParameter
import com.healthdiary.model.entities.Note
import com.jjoe64.graphview.series.DataPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class IndicatorViewModel(private val repository: Repository) : ViewModel(), CoroutineScope{

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    val indicatorViewState = MutableLiveData<Indicator?>()
    val chartViewState = MutableLiveData<Array<DataPoint>>()
    val rvViewState = MutableLiveData<List<Note>>()

    fun loadIndicatorInfo(indicatorId: Int?) {
        launch {
            indicatorViewState.value = repository.getIndicatorById(indicatorId)
        }
    }

    fun loadNotes(indicatorId: Int?) {
        launch {
            val notes = repository.getNotesByIndicatorId(indicatorId)
            chartViewState.value = getChartSeries(notes)
            rvViewState.value = notes
        }
    }

    private fun getChartSeries(notes: List<Note>): Array<DataPoint> {
        return Array(notes.size) {
            val currentNote = notes[it]
            DataPoint(currentNote.date, currentNote.value.toDouble())
        }
    }

    fun saveNote(indicator: Indicator, values: List<Float>, parameters: List<IndicatorParameter>? = null) : Boolean {
        launch {
            val note = Note(
                id = null,
                date = Date(),
                indicator = indicator,
                value = values[0],
                parameters = parameters,
                comment = ""
            )
            val resultSuccess = repository.saveNote(indicator, values, parameters)
            if (resultSuccess) {
                loadNotes(indicator.id)
            }
            return resultSuccess
        }
    }
}