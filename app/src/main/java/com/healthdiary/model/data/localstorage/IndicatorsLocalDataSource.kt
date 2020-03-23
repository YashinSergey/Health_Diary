package com.healthdiary.model.data.localstorage

import com.healthdiary.model.data.repositories.IndicatorsDataSource
import com.healthdiary.model.entities.Indicator
import java.util.*

object IndicatorsLocalDataSource : IndicatorsDataSource {

    override fun getIndicatorsFromDb(): List<Indicator> = data

    override fun saveIndicatorToDb(indicator: Indicator) {
        data.add(indicator)
    }

    private var data: MutableList<Indicator> = mutableListOf(
        Indicator(1, "Height", mutableListOf(Pair(Date(), "180")), "cm", 123),
        Indicator(2, "Weight", mutableListOf(Pair(Date(), "80")), "kg", 123),
        Indicator(3, "Sleep", mutableListOf(Pair(Date(), "8")), "h", 123),
        Indicator(4, "Some custom indicator", mutableListOf(Pair(Date(), "22")), "custom", 123),
        Indicator(5, "Some custom indicator", mutableListOf(Pair(Date(), "33")), "custom", 123),
        Indicator(6, "Some custom indicator", mutableListOf(Pair(Date(), "12")), "custom", 123),
        Indicator(7, "Some custom indicator", mutableListOf(Pair(Date(), "68")), "custom", 123)
    )

}
