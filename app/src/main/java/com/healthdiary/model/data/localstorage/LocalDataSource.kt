package com.healthdiary.model.data.localstorage

import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.IndicatorParameter
import com.healthdiary.model.entities.Note
import java.util.*

object LocalDataSource : Repository {

    private val measureTime = IndicatorParameter(1, "measure time",
        listOf("before meal", "after meal"))

    private val indicatorList: MutableList<Indicator> = mutableListOf(
        Indicator(1, "Height", "cm", 123),
        Indicator(2, "Weight", "kg", 123, listOf(measureTime)),
        Indicator(3, "Sleep", "h", 123),
        Indicator(4, "Indicator 1", "custom", 123),
        Indicator(5, "Indicator 2", "custom", 123),
        Indicator(6, "Indicator 3", "custom", 123, null, false),
        Indicator(7, "Indicator 4", "custom", 123)
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

    override fun getNotesByIndicatorId(id: Int?): List<Note> {
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
}