package com.healthdiary.model.entities

import com.healthdiary.model.data.localstorage.IndicatorsLocalDataSource
import java.util.*

class DateState(val date : Date = Date()) {
    val listIndicator : List<Indicator> = IndicatorsLocalDataSource.getIndicatorsFromDb()
}