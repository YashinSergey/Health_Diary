package com.healthdiary.model.data.localstorage

import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicator
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicatorValues
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityIndicatorParameters
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityParameterValues
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNote
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteParameters
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteValues
import com.healthdiary.model.data.repositories.*
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.IndicatorParameter
import com.healthdiary.model.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.random.Random

object LocalDataSource : Repository {

    private val measureTime = IndicatorParameter(1, "measure time",
        listOf("before meal", "after meal"))

    private val indicatorList: MutableList<Indicator> = mutableListOf(
        Indicator(1, "Height", "cm", 123),
        Indicator(2, "Weight", "kg", 123, listOf(measureTime)),
        Indicator(3, "Sleep", "h", 123),
        Indicator(4, "Pressure", "custom", 123),
        Indicator(5, "Indicator 5", "custom", 123),
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
        Note(7, Date(), indicatorList[0], 68f, null,"custom"),
        Note(8, Date(), indicatorList[1], 70f, listOf(Pair(measureTime, "before meal")),"custom"),
        Note(9, Date(), indicatorList[2], 69f, null, "custom")
    )

    private val notesOfIndicator: MutableList<Note> = mutableListOf(
        Note(1, GregorianCalendar(2020, 2, 1).time, indicatorList[1], 60f,  listOf(Pair(measureTime, "before meal")),"ok"),
        Note(2, GregorianCalendar(2020, 2, 2).time, indicatorList[1], 61f, listOf(Pair(measureTime, "after meal")),"everything bad, life is sucks"),
        Note(3, GregorianCalendar(2020, 2, 3).time, indicatorList[1], 60f, listOf(Pair(measureTime, "before meal"))),
        Note(4, GregorianCalendar(2020, 2, 4).time, indicatorList[1], 59f, listOf(Pair(measureTime, "before meal")))
    )

    override fun getNotesByDate(date: Date): List<Note> {
        return notesForOneDay
    }

    override fun getNotesByIndicatorId(indicatorId: Int?): List<Note> {
        return notesOfIndicator
    }



    override fun getIndicatorById(id: Int?): Indicator? {
        indicatorList.forEach { indicator ->
            if (indicator.id == id)
                return indicator
        }
        return null
    }

    override fun getIndicatorList(): List<Indicator> {
        return indicatorList
    }

    override fun saveNote(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveIndicatorValues(){
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun initMockDBContent(db : DataBase?) {
        GlobalScope.launch(Dispatchers.IO) {
            Timber.d("Start Coroutine")

            //region AddedIndicators and starting values and param for them
            val indicators = getIndicatorList()
            for (indicator in indicators) {
                val modelIndicator = EntityIndicator(
                    id = null,
                    title = indicator.title,
                    unit = indicator.unit,
                    iconRes = indicator.icon,
                    isActive = indicator.isActive
                )
                val modelIndicatorValue = EntityIndicatorValues(
                    id = null,
                    indicatorId = indicator.id,
                    title = "Some value for ${indicator.title}"
                )
                val modelIndicatorParameters = EntityIndicatorParameters(
                    id = null,
                    indicatorId = indicator.id
                )

                db?.daoModel()?.saveIndicator(modelIndicator)
                db?.daoModel()?.saveIndicatorValue(modelIndicatorValue)
                db?.daoModel()?.saveIndicatorParameters(modelIndicatorParameters)

                val modelParameterValues = EntityParameterValues(
                    id = null,
                    parameterId = db?.daoModel()?.getIndicatorParametersID(indicator.id)!! ,
                    value = "Some measurement characteristic"
                )

                db.daoModel().saveParameterValues(modelParameterValues)
            }
            //endregion

            //region Additional values and parameters

            val modelSecondIndicatorValueForPressure = EntityIndicatorValues(
                id = null,
                indicatorId = indicators[3].id,
                title = "Up value"

            )
            db?.daoModel()?.saveIndicatorValue(modelSecondIndicatorValueForPressure)

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
            db?.daoModel()?.saveParameterValues(modelSecondValueForHeight)
            db?.daoModel()?.saveParameterValues(modelSecondValueForWeight)
            db?.daoModel()?.saveParameterValues(modelSecondValueForSleep)
            //endregion

            //region Added notes and parameters for them
            val notes = getNotesByIndicatorId(1)
            for (note in notes) {
                val modelNote =
                    EntityNote(
                        id = null,
                        date = note.date,
                        indicatorId = note.indicator.id,
                        comment = note.comment
                    )
                db?.daoModel()?.saveNote(modelNote)

                val listIndicatorValues = db?.daoModel()?.getIdIndicatorValuesByIndicatorId(note.indicator.id)
                listIndicatorValues.let {
                    for(indicatorValueId in it!!){
                        val modelNoteValues = EntityNoteValues(
                            id = null,
                            noteID = note.id,
                            indicatorValueId = indicatorValueId,
                            value = Random.nextInt(100).toFloat()
                        )
                        db?.daoModel()?.saveNoteValues(modelNoteValues)
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
                db?.daoModel()?.saveNoteParameters(modelNoteParameters)
            }
            //endregion


            Timber.d("Saving mock content done")
        }
    }


}