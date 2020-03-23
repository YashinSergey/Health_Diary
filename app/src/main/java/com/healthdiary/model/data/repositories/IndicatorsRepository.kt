package com.healthdiary.model.data.repositories

import com.healthdiary.model.entities.Indicator

class IndicatorsRepository(private val indicators: IndicatorsDataSource) {

    fun getIndicators(): List<Indicator> {
        return indicators.getIndicatorsFromDb()
    }

    fun saveIndicator(indicator: Indicator) {
        indicators.saveIndicatorToDb(indicator)
    }

}