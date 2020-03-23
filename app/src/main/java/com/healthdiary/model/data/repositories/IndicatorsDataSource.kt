package com.healthdiary.model.data.repositories

import com.healthdiary.model.entities.Indicator

interface IndicatorsDataSource {
    fun getIndicatorsFromDb(): List<Indicator>
    fun saveIndicatorToDb(indicator: Indicator)
}