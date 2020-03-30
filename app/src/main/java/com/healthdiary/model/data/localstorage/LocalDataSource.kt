package com.healthdiary.model.data.localstorage

import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.Note
import java.util.*

object LocalDataSource : Repository {

    private val indicators: MutableList<Indicator> = mutableListOf(
        Indicator(1, "Height", "cm", 123),
        Indicator(2, "Weight", "kg", 123),
        Indicator(3, "Sleep", "h", 123),
        Indicator(4, "Indicator 1", "custom", 123),
        Indicator(5, "Indicator 2", "custom", 123),
        Indicator(6, "Indicator 3", "custom", 123),
        Indicator(7, "Indicator 4", "custom", 123)
    )

    private val notesForOneDay: MutableList<Note> = mutableListOf(
        Note(7, Date(), indicators[0], 68f, "custom"),
        Note(8, Date(), indicators[1], 70f, "custom"),
        Note(9, Date(), indicators[2], 69f, "custom")
    )

    private val notesOfIndicator: MutableList<Note> = mutableListOf(
        Note(1, GregorianCalendar(2020, 2, 1).time, indicators[1],
            60f, "ok"),
        Note(
            2, GregorianCalendar(2020, 2, 2).time, indicators[1],
            61f, "everything bad, life is sucks"
        ),
        Note(
            3, GregorianCalendar(2020, 2, 3).time, indicators[1],
            60f, "everything bad, life is sucks"
        ),
        Note(
            4,
            GregorianCalendar(2020, 2, 4).time, indicators[1],
            59f, "everything bad, life is sucks"
        )
    )

    override fun getNotesByDate(date: Date): List<Note> {
        return notesForOneDay
    }

    override fun getNotesByIndicatorId(id: Int?): List<Note> {
        return notesOfIndicator
    }

    override fun getIndicatorById(id: Int?): Indicator? {
        indicators.forEach { indicator ->
            if (indicator.id == id)
                return indicator
        }
        return null
    }
}