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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

object LocalDataSource : Repository, CoroutineScope {


    override val coroutineContext: CoroutineContext by lazy { Dispatchers.IO }
    lateinit var db: DataBase

    private val parameterValuesList = listOf<ParameterValues>(
        ParameterValues(id = 1, value = "до еды"),
        ParameterValues(id = 2,value = "после еды"),
        ParameterValues(id = 3, value = "в состоянии покоя"),
        ParameterValues(id = 4,value = "после физической нагрузки"),
        ParameterValues(id = 5, value = "до еды"),
        ParameterValues(id = 6, value = "после еды"),
        ParameterValues(id = 7, value =  "вне зависимости от приёма пищи"),
        ParameterValues(id = 8, value = "завтрак"),
        ParameterValues(id = 9, value = "обед"),
        ParameterValues(id = 10, value = "перекус"),
        ParameterValues(id = 11, value =  "ужин")
    )

    private val indicatorParameters: MutableList<IndicatorParameter> = mutableListOf(
        IndicatorParameter(0, "измерен", listOf( parameterValuesList[0], parameterValuesList[1])),
        IndicatorParameter(1, "измерен", listOf( parameterValuesList[2], parameterValuesList[3])),
        IndicatorParameter(2, "измерен", listOf( parameterValuesList[4], parameterValuesList[5], parameterValuesList[6])),
        IndicatorParameter(3, "прием пищи", listOf(parameterValuesList[7], parameterValuesList[8], parameterValuesList[9], parameterValuesList[10], parameterValuesList[11])))

    private val measureTime = IndicatorParameter(
        1, "measure time",
        parameterValuesList
    )


    private val indicatorList: MutableList<Indicator> = mutableListOf(
        Indicator(1, "Height", "cm", 199),
        Indicator(2, "Weight", "kg", 100, listOf(measureTime)),
        Indicator(3, "Sleep", "h", 101),
        Indicator(4, "Pressure", "custom", 102),
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
        Note(8, Date(), indicatorList[1], 70f, listOf(measureTime), "custom"),
        Note(9, Date(), indicatorList[2], 69f, null, "custom")
    )

    private val notesOfIndicator: MutableList<Note> = mutableListOf(
        Note(id=1,date=GregorianCalendar(2020,2,11).time,indicator=indicatorList[1],value=50f,parameters=listOf(measureTime),comment="ok"),
        Note(id=2,date=GregorianCalendar(2020,2,12).time,indicator=indicatorList[2],value=70f,parameters=listOf(measureTime),comment="ok"),
        Note(id=3,date=GregorianCalendar(2020,2,13).time,indicator=indicatorList[3],value=80f,parameters=listOf(measureTime),comment="ok"),
        Note(id=4,date=GregorianCalendar(2020,2,11).time,indicator=indicatorList[4],value=90f,parameters=listOf(measureTime),comment="ok"),
        Note(id=5,date=GregorianCalendar(2020,2,10).time,indicator=indicatorList[5],value=54f,parameters=listOf(measureTime),comment="ok"),
        Note(id=6,date=GregorianCalendar(2020,2,13).time,indicator=indicatorList[6],value=45f,parameters=listOf(measureTime),comment="ok"),
        Note(id=7,date=GregorianCalendar(2020,2,15).time,indicator=indicatorList[7],value=58f,parameters=listOf(measureTime),comment="ok"),
        Note(id=8,date=GregorianCalendar(2020,2,12).time,indicator=indicatorList[8],value=85f,parameters=listOf(measureTime),comment="ok"),
        Note(id=9,date=GregorianCalendar(2020,2,11).time,indicator=indicatorList[9],value=43f,parameters=listOf(measureTime),comment="ok"),
        Note(id=10,date=GregorianCalendar(2020,2,15).time,indicator=indicatorList[10],value=56f,parameters=listOf(measureTime),comment="ok"),
        Note(id=11,date=GregorianCalendar(2020,2,17).time,indicator=indicatorList[11],value=75f,parameters=listOf(measureTime),comment="ok"),
        Note(id=12,date=GregorianCalendar(2020,2,13).time,indicator=indicatorList[12],value=32f,parameters=listOf(measureTime),comment="ok"),
        Note(id=13,date=GregorianCalendar(2020,2,14).time,indicator=indicatorList[13],value=56f,parameters=listOf(measureTime),comment="ok"),
        Note(id=14,date=GregorianCalendar(2020,2,15).time,indicator=indicatorList[14],value=68f,parameters=listOf(measureTime),comment="ok"),
        Note(id=15,date=GregorianCalendar(2020,2,16).time,indicator=indicatorList[15],value=93f,parameters=listOf(measureTime),comment="ok"),
        Note(id=16,date=GregorianCalendar(2020,2,12).time,indicator=indicatorList[1],value=46f,parameters=listOf(measureTime),comment="ok"),
        Note(id=17,date=GregorianCalendar(2020,2,13).time,indicator=indicatorList[2],value=75f,parameters=listOf(measureTime),comment="ok"),
        Note(id=18,date=GregorianCalendar(2020,2,15).time,indicator=indicatorList[3],value=56f,parameters=listOf(measureTime),comment="ok"),
        Note(id=19,date=GregorianCalendar(2020,2,16).time,indicator=indicatorList[4],value=59f,parameters=listOf(measureTime),comment="ok"),
        Note(id=20,date=GregorianCalendar(2020,2,15).time,indicator=indicatorList[0],value=65f,parameters=listOf(measureTime),comment="ok"),
        Note(id=21,date=GregorianCalendar(2020,2,17).time,indicator=indicatorList[0],value=70f,parameters=listOf(measureTime),comment="ok")

    )

    //mock для первичного наполнения БД
    fun getStartIndicatorList(): List<Indicator> {
        return indicatorList
    }


    override fun getNotesByDate(date: Date): List<Note> {
        return notesForOneDay
    }

    override suspend fun getNotesByIndicator(indicator: Indicator?): ReceiveChannel<List<Note>> =
        Channel<List<Note>>(Channel.CONFLATED).apply {
            val listEntityNotes = db.daoModel().getNotesByIndicatorId(indicator?.id)
            val listNotes = ArrayList<Note>()
            for (entity in listEntityNotes) {
                listNotes.add(
                    Note(
                        id = entity.id,
                        date = entity.date,
                        indicator = indicator!!,
                        value = entity.value
                    )
                )
            }
            send(listNotes)
        }


    override suspend fun getIndicatorById(id: Int): Indicator? {
        Timber.d("incoming ID is $id")
        val entityIndicator = db.daoModel().getIndicatorById(id)
        Timber.d("getting db query indicator ID is ${entityIndicator.id}")

        entityIndicator.let {
            return Indicator(
                id = entityIndicator.id!!,
                title = entityIndicator.title,
                unit = entityIndicator.unit,
                icon = entityIndicator.icon,
                isActive = entityIndicator.isActive
            )
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
                    val listValue = ArrayList<ParameterValues>().apply{
                        for(entry in db.daoModel().getParameterValuesByParametersId(parametersId)){
                            add(
                                ParameterValues(
                                    id = entry.id,
                                    value = entry.value
                            )
                            )
                        }
                    }
                    listIndicatorParameters.add(
                        IndicatorParameter(
                            parametersId,
                            "HZ CHTO",
                            listValue
                        )
                    )
                }
                indicator.parameters = listIndicatorParameters
                indicatorList.add(indicator)
                send(indicatorList)
            }
        }


    override fun saveNote(
        indicatorId: Int,
        values: List<Float>,
        parameters: List<Pair<Int, String>>?
    ): Boolean {
        return getIndicatorById(indicatorId)?.let {
            val params = ArrayList<Pair<IndicatorParameter, String>>()
            parameters?.forEach { pair ->
                params.add(Pair(indicatorParameters[pair.first], pair.second))
            }
            notesOfIndicator.add(Note(129, Date(), it, values[0], params))
        } ?: false
    }
}