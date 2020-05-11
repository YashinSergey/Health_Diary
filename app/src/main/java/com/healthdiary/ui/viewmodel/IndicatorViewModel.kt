package com.healthdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.IndicatorParameter
import com.healthdiary.model.entities.Note
import com.jjoe64.graphview.series.DataPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import timber.log.Timber
import java.util.*
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class IndicatorViewModel(private val repository: Repository) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    val indicatorViewState = MutableLiveData<Indicator?>()
    val chartViewState = MutableLiveData<Array<DataPoint>>()
    val rvViewState = MutableLiveData<List<Note>>()

    fun loadIndicatorInfo(indicatorId: Int) {
        launch {
            val request = async(Dispatchers.IO) {
                return@async repository.getIndicatorById(indicatorId)
            }
            indicatorViewState.value = request.await()
        }
    }

    fun loadNotes(indicatorId: Int): ReceiveChannel<List<Note>> =
        Channel<List<Note>>(Channel.CONFLATED).apply {
            launch {
                Timber.d("Start coroutine load notes")
                val fillingContent: Deferred<List<Note>> = async {
                    lateinit var result: List<Note>
                    Timber.d("Start async load notes")
                    val request = async(Dispatchers.IO) {
                        result = repository.getNotesByIndicatorId(indicatorId)
                        Timber.d("Over async.IO load notes")
                    }
                    request.await()
                    Timber.d("Loaded notes size is ${result.size}")
                    result
                }
                Timber.d("Pre await")
                val notes = fillingContent.await()
                Timber.d("Notes size is ${notes.size}")
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