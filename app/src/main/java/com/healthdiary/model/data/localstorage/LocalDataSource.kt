package com.healthdiary.model.data.localstorage

import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicator
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicatorValues
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityIndicatorParameters
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityParameterValues
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNote
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteParameters
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteValues
import com.healthdiary.model.data.localstorage.dbentities.query.EntityLastValueByIndicatorId
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.IndicatorParameter
import com.healthdiary.model.entities.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

object LocalDataSource : Repository, CoroutineScope {
    override val coroutineContext: CoroutineContext by lazy { Dispatchers.IO }
    lateinit var db: DataBase

    private val measureTime = IndicatorParameter(
        1, "measure time",
        listOf("before meal", "after meal")
    )

    private val indicatorList: MutableList<Indicator> = mutableListOf(
        Indicator(1, "Height", "cm", 123),
        Indicator(2, "Weight", "kg", 123, listOf(measureTime)),
        Indicator(3, "Sleep", "h", 123),
        Indicator(4, "Pressure", "custom", 123),
        Indicator(5, "Indicator 17", "custom", 123),
        Indicator(6, "Indicator 6", "custom", 123),
        Indicator(7, "Indicator 7", "custom", 123),
        Indicator(8, "Indicator 8", "custom", 123),
        Indicator(9, "Indicator 9", "custom", 123),
        Indicator(10, "Indicator 10", "custom", 123),
        Indicator(11, "Indicator 11", "custom", 123),
        Indicator(12, "Indicator 12", "custom", 123),
        Indicator(13, "Indicator 13", "custom", 123),
        Indicator(14, "Indicator 14", "custom", 123),
        Indicator(15, "Indicator 15", "custom", 123),
        Indicator(16, "Indicator 16", "custom", 123)
    )

    private val notesForOneDay: MutableList<Note> = mutableListOf(
        Note(7, Date(), indicatorList[0], 68f, null, "custom"),
        Note(8, Date(), indicatorList[1], 70f, listOf(Pair(measureTime, "before meal")), "custom"),
        Note(9, Date(), indicatorList[2], 69f, null, "custom")
    )

    private val notesOfIndicator: MutableList<Note> = mutableListOf(
        Note(
            1,
            GregorianCalendar(2020, 2, 1).time,
            indicatorList[1],
            150f,
            listOf(Pair(measureTime, "before meal")),
            "ok"
        ),
        Note(
            2,
            GregorianCalendar(2020, 2, 2).time,
            indicatorList[0],
            70f,
            listOf(Pair(measureTime, "after meal")),
            "everything bad, life is sucks"
        ),
        Note(
            3,
            GregorianCalendar(2020, 2, 3).time,
            indicatorList[1],
            60f,
            listOf(Pair(measureTime, "before meal"))
        ),
        Note(
            4,
            GregorianCalendar(2020, 2, 4).time,
            indicatorList[2],
            59f,
            listOf(Pair(measureTime, "before meal"))
        )
    )

    //mock для первичного наполнения БД
    fun getStartIndicatorList(): List<Indicator> {
        return indicatorList
    }


    override fun getNotesByDate(date: Date): List<Note> {
        return notesForOneDay
    }

    override suspend fun getNotesByIndicator(indicator: Indicator?): ReceiveChannel<List<Note>> =  Channel<List<Note>>(Channel.CONFLATED).apply{
        val listEntityNotes = db.daoModel().getNotesByIndicatorId(indicator?.id)
        val listNotes = ArrayList<Note>()
        for(entity in listEntityNotes){
            listNotes.add(Note(
                id = entity.id!!,
                date = entity.date,
                indicator = indicator!!,
                value = entity.value
            ))
        }
        send(listNotes)
    }


    override suspend fun getIndicatorById(id: Int?): ReceiveChannel<Indicator?> = Channel<Indicator?>(Channel.CONFLATED).apply{
        val entityIndicator = db.daoModel().getIndicatorById(id!!)
        entityIndicator?.let{
            val indicator = Indicator(
                id = entityIndicator.id!!,
                title = entityIndicator.title,
                unit = entityIndicator.unit,
                icon = entityIndicator.icon,
                isActive = entityIndicator.isActive
            )
            send(indicator)
        }

    }

    override suspend fun getIndicatorList(): ReceiveChannel<List<Indicator>> =
        Channel<List<Indicator>>(Channel.CONFLATED).apply {
            val entityIndicatorList = db.daoModel().getIndicatorsList()
            val indicatorList = ArrayList<Indicator>()
            for (entityIndicator in entityIndicatorList) {
                val indicator = Indicator(
                    id = entityIndicator.id!!,
                    title = entityIndicator.title,
                    unit = entityIndicator.unit,
                    icon = entityIndicator.icon,
                    parameters = null,
                    isActive = entityIndicator.isActive
                )
                val listIndicatorParameters = ArrayList<IndicatorParameter>()
                val listParametersid = db.daoModel().getIndicatorParametersID(indicator.id)
                for (parametersId in listParametersid) {
                    val listValue = db.daoModel().getParameterValuesByParametersId(parametersId)
                    listIndicatorParameters.add(
                        IndicatorParameter(
                            parametersId,
                            "HZ CHTO",
                            listValue
                        )
                    )
                }
                indicator.parameters = listIndicatorParameters as List<IndicatorParameter>
                indicatorList.add(indicator)
                send(indicatorList)
            }
        }

    override suspend fun getLastValueByIndicatorId(id: Int?): ReceiveChannel<EntityLastValueByIndicatorId?> =
        Channel<EntityLastValueByIndicatorId?>(Channel.CONFLATED).apply {
            val listValue = db.daoModel().getLastValueByIndicatorId(id)
            if (listValue.isEmpty()) {
                send(null)
            } else {
                send(listValue[0])
            }
        }

    fun initMockDBContent() {
        launch {
            Timber.d("Start Coroutine")

            //region AddedIndicators and starting values and param for them
            val indicators = getStartIndicatorList()
            for (indicator in indicators) {
                val modelIndicator = EntityIndicator(
                    id = null,
                    title = indicator.title,
                    unit = indicator.unit,
                    icon = indicator.icon,
                    isActive = indicator.isActive
                )
                val modelIndicatorValue = EntityIndicatorValues(
                    id = null,
                    indicatorId = indicator.id,
                    title = "Some value for ${indicator.title}"
                )
                val modelIndicatorParameters = EntityIndicatorParameters(
                    id = null,
                    indicatorId = indicator.id,
                    title = "Some title"
                )

                db.daoModel().saveIndicator(modelIndicator)
                db.daoModel().saveIndicatorValue(modelIndicatorValue)
                db.daoModel().saveIndicatorParameters(modelIndicatorParameters)

                val listParametersId = db.daoModel().getIndicatorParametersID(indicator.id)
                for (parameterId in listParametersId) {
                    val modelParameterValues = EntityParameterValues(
                        id = null,
                        parameterId = parameterId,
                        value = "Some measurement characteristic"
                    )
                    db.daoModel().saveParameterValues(modelParameterValues)
                }
            }
            //endregion

            //region Additional values and parameters

            val modelSecondIndicatorValueForPressure = EntityIndicatorValues(
                id = null,
                indicatorId = indicators[3].id,
                title = "Up value"

            )
            db.daoModel().saveIndicatorValue(modelSecondIndicatorValueForPressure)

            val modelSecondValueForHeight = EntityParameterValues(
                id = null,
                parameterId = indicators[0].id,
                value = "In jump"
            )
            val modelSecondValueForWeight = EntityParameterValues(
                id = null,
                parameterId = indicators[1].id,
                value = "Morning"
            )
            val modelSecondValueForSleep = EntityParameterValues(
                id = null,
                parameterId = indicators[2].id,
                value = "After gym"
            )
            db.daoModel().saveParameterValues(modelSecondValueForHeight)
            db.daoModel().saveParameterValues(modelSecondValueForWeight)
            db.daoModel().saveParameterValues(modelSecondValueForSleep)
            //endregion

            //region Added notes and parameters for them
            val notes = notesOfIndicator
            for (note in notes) {
                val modelNote =
                    EntityNote(
                        id = null,
                        date = note.date,
                        indicatorId = note.indicator.id,
                        comment = note.comment
                    )
                db.daoModel().saveNote(modelNote)

                val listIndicatorValues =
                    db.daoModel().getIdIndicatorValuesByIndicatorId(note.indicator.id)
                listIndicatorValues.let {
                    for (indicatorValueId in it) {
                        val modelNoteValues = EntityNoteValues(
                            id = null,
                            noteID = note.id,
                            indicatorValueId = indicatorValueId,
                            value = Random.nextInt(100).toFloat()
                        )
                        db.daoModel().saveNoteValues(modelNoteValues)
                    }
                }

                val mockIndicatorParameterId = 1
                val mockParameterValueId = 1
                val modelNoteParameters = EntityNoteParameters(
                    id = null,
                    noteId = note.id,
                    parameterId = mockIndicatorParameterId,
                    parameterValueId = mockParameterValueId
                )
                db.daoModel().saveNoteParameters(modelNoteParameters)
            }
            //endregion


            Timber.d("Saving mock content done")
        }
    }
}