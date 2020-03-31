package com.healthdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.Note
import com.jjoe64.graphview.series.DataPoint
import timber.log.Timber

class IndicatorViewModel(val indicatorId : Int?, private val repository: Repository) : ViewModel() {

    val viewState = MutableLiveData<Pair<Indicator?, Array<DataPoint>>>()

    fun loadNotes(indicatorId: Int?) {
        val chartSeries = getChartSeries(repository.getNotesByIndicatorId(indicatorId))
        viewState.value = Pair(repository.getIndicatorById(indicatorId), chartSeries)
    }

    private fun getChartSeries(notes: List<Note>): Array<DataPoint> {
        return Array(notes.size) {
            val currentNote = notes[it]
            DataPoint(currentNote.date, currentNote.value.toDouble())
        }
    }
}