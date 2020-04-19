package com.healthdiary.model.data.localstorage

import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.IndicatorParameter
import com.healthdiary.model.entities.Note
import java.util.*

object LocalDataSource : Repository {

    private val indicatorParameters: MutableList<IndicatorParameter> = mutableListOf(
        IndicatorParameter(1, "измерен", listOf("до еды", "после еды")),
        IndicatorParameter(2, "измерен", listOf("в состоянии покоя", "после физической нагрузки")),
        IndicatorParameter(3, "измерен", listOf("до еды", "после еды", "вне зависимости от приёма пищи")),
        IndicatorParameter(4, "прием пищи", listOf("завтрак", "обед", "перекус", "ужин")))

    private val indicatorList: MutableList<Indicator> = mutableListOf(
        Indicator(1, "Рост", "см", 123),
        Indicator(2, "Вес", "кг", 123, listOf(indicatorParameters[0])),
        Indicator(3, "Сон", "ч", 123),
        Indicator(4, "Пульс", "чсс", 123, listOf(indicatorParameters[1])),
        Indicator(5, "Глюкоза", "ммоль/л", 123, listOf(indicatorParameters[2])),
        Indicator(6, "Калории", "ккал", 123, listOf(indicatorParameters[3])),
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
        Note(8, Date(), indicatorList[1], 70f, listOf(Pair(indicatorParameters[0], "before meal")),"custom"),
        Note(9, Date(), indicatorList[2], 69f, null, "custom")
    )

    private val notesOfIndicator: MutableList<Note> = mutableListOf(
        Note(1, GregorianCalendar(2020, 2, 1).time, indicatorList[1], 60f,  listOf(Pair(indicatorParameters[0], "before meal")),"ok"),
        Note(2, GregorianCalendar(2020, 2, 2).time, indicatorList[1], 61f, listOf(Pair(indicatorParameters[0], "after meal")),"everything bad, life is sucks"),
        Note(3, GregorianCalendar(2020, 2, 3).time, indicatorList[1], 60f, listOf(Pair(indicatorParameters[0], "before meal"))),
        Note(4, GregorianCalendar(2020, 2, 4).time, indicatorList[1], 59f, listOf(Pair(indicatorParameters[0], "before meal")))
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