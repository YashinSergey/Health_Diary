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
import com.healthdiary.model.entities.ParameterValues
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

object LocalDataSource : Repository, CoroutineScope {


    override val coroutineContext: CoroutineContext = Dispatchers.IO
    lateinit var db: DataBase

    //region mock data
    private val parameterValuesList = listOf<ParameterValues>(
        ParameterValues(id = null, value = "до еды"),
        ParameterValues(id = null, value = "после еды"),
        ParameterValues(id = null, value = "в состоянии покоя"),
        ParameterValues(id = null, value = "после физической нагрузки"),
        ParameterValues(id = null, value = "до еды"),
        ParameterValues(id = null, value = "после еды"),
        ParameterValues(id = null, value = "вне зависимости от приёма пищи"),
        ParameterValues(id = null, value = "завтрак"),
        ParameterValues(id = null, value = "обед"),
        ParameterValues(id = null, value = "перекус"),
        ParameterValues(id = null, value = "ужин")
    )

    private val indicatorParameters: MutableList<IndicatorParameter> = mutableListOf(
        IndicatorParameter(null, "измерен", listOf(parameterValuesList[0], parameterValuesList[1])),
        IndicatorParameter(null, "измерен", listOf(parameterValuesList[2], parameterValuesList[3])),
        IndicatorParameter(
            null,
            "измерен",
            listOf(parameterValuesList[4], parameterValuesList[5], parameterValuesList[6])
        ),
        IndicatorParameter(
            null,
            "прием пищи",
            listOf(
                parameterValuesList[7],
                parameterValuesList[8],
                parameterValuesList[9],
                parameterValuesList[10]
            )
        )
    )


    private val indicatorList: MutableList<Indicator> = mutableListOf(
        Indicator(1, "Height", "cm", 199, indicatorParameters),
        Indicator(2, "Weight", "kg", 100, indicatorParameters),
        Indicator(3, "Sleep", "h", 101, indicatorParameters),
        Indicator(4, "Pressure", "custom", 102, indicatorParameters),
        Indicator(5, "Indicator 17", "custom", 123, indicatorParameters),
        Indicator(6, "Indicator 6", "custom", 123, indicatorParameters),
        Indicator(7, "Indicator 7", "custom", 123, indicatorParameters),
        Indicator(8, "Indicator 8", "custom", 123, indicatorParameters),
        Indicator(9, "Indicator 9", "custom", 123, indicatorParameters),
        Indicator(10, "Indicator 10", "custom", 123, indicatorParameters),
        Indicator(11, "Indicator 11", "custom", 123, indicatorParameters),
        Indicator(12, "Indicator 12", "custom", 123, indicatorParameters),
        Indicator(13, "Indicator 13", "custom", 123, indicatorParameters),
        Indicator(14, "Indicator 14", "custom", 123, indicatorParameters),
        Indicator(15, "Indicator 15", "custom", 123, indicatorParameters),
        Indicator(16, "Indicator 16", "custom", 123, indicatorParameters)
    )

    private val notesForOneDay: MutableList<Note> = mutableListOf(
        Note(7, Date(), indicatorList[0], 68f, null, "custom"),
        Note(8, Date(), indicatorList[1], 70f, indicatorParameters, "custom"),
        Note(9, Date(), indicatorList[2], 69f, null, "custom")
    )

    private val notesOfIndicator: MutableList<Note> = mutableListOf(
        Note(
            id = 1,
            date = GregorianCalendar(2020, 2, 11).time,
            indicator = indicatorList[1],
            value = 50f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 2,
            date = GregorianCalendar(2020, 2, 12).time,
            indicator = indicatorList[2],
            value = 70f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 3,
            date = GregorianCalendar(2020, 2, 13).time,
            indicator = indicatorList[3],
            value = 80f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 4,
            date = GregorianCalendar(2020, 2, 11).time,
            indicator = indicatorList[4],
            value = 90f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 5,
            date = GregorianCalendar(2020, 2, 10).time,
            indicator = indicatorList[5],
            value = 54f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 6,
            date = GregorianCalendar(2020, 2, 13).time,
            indicator = indicatorList[6],
            value = 45f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 7,
            date = GregorianCalendar(2020, 2, 15).time,
            indicator = indicatorList[7],
            value = 58f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 8,
            date = GregorianCalendar(2020, 2, 12).time,
            indicator = indicatorList[8],
            value = 85f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 9,
            date = GregorianCalendar(2020, 2, 11).time,
            indicator = indicatorList[9],
            value = 43f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 10,
            date = GregorianCalendar(2020, 2, 15).time,
            indicator = indicatorList[10],
            value = 56f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 11,
            date = GregorianCalendar(2020, 2, 17).time,
            indicator = indicatorList[11],
            value = 75f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 12,
            date = GregorianCalendar(2020, 2, 13).time,
            indicator = indicatorList[12],
            value = 32f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 13,
            date = GregorianCalendar(2020, 2, 14).time,
            indicator = indicatorList[13],
            value = 56f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 14,
            date = GregorianCalendar(2020, 2, 15).time,
            indicator = indicatorList[14],
            value = 68f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 15,
            date = GregorianCalendar(2020, 2, 16).time,
            indicator = indicatorList[15],
            value = 93f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 16,
            date = GregorianCalendar(2020, 2, 12).time,
            indicator = indicatorList[1],
            value = 46f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 17,
            date = GregorianCalendar(2020, 2, 13).time,
            indicator = indicatorList[2],
            value = 75f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 18,
            date = GregorianCalendar(2020, 2, 15).time,
            indicator = indicatorList[3],
            value = 56f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 19,
            date = GregorianCalendar(2020, 2, 16).time,
            indicator = indicatorList[4],
            value = 59f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 20,
            date = GregorianCalendar(2020, 2, 15).time,
            indicator = indicatorList[0],
            value = 65f,
            parameters = indicatorParameters,
            comment = "ok"
        ),
        Note(
            id = 21,
            date = GregorianCalendar(2020, 2, 17).time,
            indicator = indicatorList[0],
            value = 70f,
            parameters = indicatorParameters,
            comment = "ok"
        )
    )

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

                val modelIndicatorValues = EntityIndicatorValues(
                    id = null,
                    indicatorId = indicator.id,
                    title = "1st value"
                )

                for (indParameter in indicator.parameters!!) {
                    val modelIndicatorParameters = EntityIndicatorParameters(
                        id = null,
                        indicatorId = indicator.id,
                        title = indParameter.title
                    )
                    val paramId =
                        db.daoModel().saveIndicatorParameters(modelIndicatorParameters)
                            .toInt()
                    for (paramValues in indParameter.values) {
                        val modelIndicatorValue = EntityParameterValues(
                            id = null,
                            parameterId = paramId,
                            value = paramValues.value
                        )
                        db.daoModel().saveParameterValues(modelIndicatorValue)
                    }

                }
                db.daoModel().saveIndicatorValue(modelIndicatorValues)
                db.daoModel().saveIndicator(modelIndicator)

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

                db.daoModel().getIdIndicatorValuesIdByIndicatorId(note.indicator.id).first() {
                    for (indicatorValueId in it) {
                        val modelNoteValues = EntityNoteValues(
                            id = null,
                            noteID = note.id!!,
                            indicatorValueId = indicatorValueId,
                            value = Random.nextInt(100).toFloat()
                        )
                        db.daoModel().saveNoteValues(modelNoteValues)
                    }
                    true
                }

                val mockIndicatorParameterId = 1
                val mockParameterValueId = 1
                val modelNoteParameters = EntityNoteParameters(
                    id = null,
                    noteId = note.id!!,
                    parameterId = mockIndicatorParameterId,
                    parameterValueId = mockParameterValueId
                )
                db.daoModel().saveNoteParameters(modelNoteParameters)
            }
            Timber.d("Saving mock content done")
        }
        //endregion


    }

    override fun getNotesByDate(date: Date): List<Note> {
        return notesForOneDay
    }

    fun getStartIndicatorList(): List<Indicator> {
        return indicatorList
    }
    //endregion

    override suspend fun saveNewNote(note: Note): Int? = suspendCoroutine {
        launch {
            val idSavingNote =
                db.daoModel().saveNote(
                    EntityNote(
                        id = null,
                        indicatorId = note.indicator.id,
                        date = Date(),
                        comment = note.comment
                    )
                ).toInt()
            note.parameters?.let {
                for (parameter in it) {
                    for (value in parameter.values) {
                        db.daoModel().saveNoteParameters(
                            EntityNoteParameters(
                                id = null,
                                noteId = idSavingNote,
                                parameterId = parameter.id!!,
                                parameterValueId = value.id
                            )
                        )
                    }
                }
            }
            val indicatorValuesIdList =
                db.daoModel().getIdIndicatorValuesIdByIndicatorId(note.indicator.id).collect {
                    for (indicatorValuesId in it) {
                        db.daoModel().saveNoteValues(
                            EntityNoteValues(
                                id = null,
                                noteID = idSavingNote,
                                indicatorValueId = indicatorValuesId,
                                value = note.value
                            )
                        )
                    }
                }

            it.resume(idSavingNote)
        }

    }

    override suspend fun getIndicatorById(id: Int): Indicator? {
        val entityIndicator = db.daoModel().getIndicatorById(id).first()
        val parameters = mutableListOf<IndicatorParameter>()
        db.daoModel().getIndicatorParametersByIndicatorId(entityIndicator.id).first().forEach{
            val values = mutableListOf<ParameterValues>()
            db.daoModel().getParameterValuesByParametersId(it.id).first().forEach{
                values.add(
                    ParameterValues(
                    id = it.id,
                    value = it.value
                ))
            }

            parameters.add(
                IndicatorParameter(
                    id=it.id,
                    title = it.title,
                    values = values
                ))
        }
        return Indicator(
            id = entityIndicator.id!!,
            title = entityIndicator.title,
            unit = entityIndicator.unit,
            icon = entityIndicator.icon,
            isActive = entityIndicator.isActive,
            parameters = parameters
        )
    }

    override suspend fun getLastValueByIndicatorId(id: Int?): Flow<EntityLastValueByIndicatorId?> =
        flow {
            db.daoModel().getLastValueByIndicatorId(id).collect {
                if (it.isEmpty()) {
                    emit(null)
                } else {
                    emit(it[0])
                }
            }
        }


    override suspend fun getNotesByIndicator(indicator: Indicator?): Flow<List<Note>> = flow {
        Timber.d("Start flow getNotesByIndicator")
        db.daoModel().getNotesByIndicatorId(indicator?.id).first {
            Timber.d("Received specify list with size ${it.size}")
            val listNotes = ArrayList<Note>()
            for (entity in it) {
                listNotes.add(
                    Note(
                        id = entity.id,
                        date = entity.date,
                        indicator = indicator!!,
                        value = entity.value
                    )
                )
            }
            emit(listNotes)
            Timber.d("Emit listNotes with size ${listNotes.size}")
            true
        }
    }


    override suspend fun getNotesByIndicatorId(id: Int): Flow<List<Note>> = flow{
        val listNotes = ArrayList<Note>()
        db.daoModel().getNotesByIndicatorId(id).first {
            for (entity in it) {
                listNotes.add(
                    Note(
                        id = entity.id,
                        date = entity.date,
                        indicator = getIndicatorById(id)!!,
                        value = entity.value
                    )
                )
            }
            true
        }
        Timber.d("Emit listNotes with size ${listNotes.size}")
        emit(listNotes)
    }



    override suspend fun getIndicatorList(): List<Indicator> = mutableListOf<Indicator>().apply {
        db.daoModel().getIndicatorsList().first {
            for (entityIndicator in it) {
                val indicator = Indicator(
                    id = entityIndicator.id!!,
                    title = entityIndicator.title,
                    unit = entityIndicator.unit,
                    icon = entityIndicator.icon,
                    parameters = null,
                    isActive = entityIndicator.isActive
                )
                val listIndicatorParameters = ArrayList<IndicatorParameter>()
                indicator.parameters = listIndicatorParameters
                add(indicator)
            }
            Timber.d("IndicatorList size is ${indicatorList.size}")
            true
        }
        Timber.d("Collect is over")
    }
}