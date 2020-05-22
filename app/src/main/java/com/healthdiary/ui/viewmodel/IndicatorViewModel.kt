package com.healthdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.IndicatorParameter
import com.healthdiary.model.entities.Note
import com.jjoe64.graphview.series.DataPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.util.*
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class IndicatorViewModel(private val repository: Repository) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    val indicatorViewState = MutableLiveData<Indicator?>()
    val chartViewState = MutableLiveData<Array<DataPoint>>()
    val rvViewState = MutableLiveData<List<Note>>()

    fun loadIndicatorInfo(indicatorId: Int) {
        Timber.d("Start coroutine loadIndicatorInfo")
        launch {
            val indicator = repository.getIndicatorById(indicatorId)
            indicatorViewState.postValue(indicator)
            Timber.d("Over coroutine loadIndicatorInfo")
        }
    }

    fun loadNotes(indicatorId: Int) {
        Timber.d("Start coroutine load notes")
        launch(Dispatchers.Default) {
            val notes: List<Note> = repository.getNotesByIndicatorId(indicatorId).first()
            rvViewState.postValue(notes)
            chartViewState.postValue(getChartSeries(notes))
            Timber.d("Over coroutine loadNotes and postValue")
        }
    }

    private fun getChartSeries(notes: List<Note>): Array<DataPoint> {
        return Array(notes.size) {
            Timber.d("Start getChartSeries")
            val currentNote = notes[it]
            DataPoint(currentNote.date, currentNote.value.toDouble())

        }
    }

    suspend fun saveNote(
        indicator: Indicator,
        values: List<Float>,
        parameters: List<IndicatorParameter>? = null
    ): Boolean {
        val def = async {
            val note = Note(
                id = null,
                date = Date(),
                indicator = indicator,
                value = values[0],
                parameters = parameters,
                comment = ""
            )
            val resultSuccess = repository.saveNewNote(note)
            resultSuccess?.let { return@async true } ?: false
        }
        return def.await()
    }
}