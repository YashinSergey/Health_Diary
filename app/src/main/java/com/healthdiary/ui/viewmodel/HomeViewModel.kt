package com.healthdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.localstorage.IndicatorsLocalDataSource
import com.healthdiary.model.data.repositories.IndicatorsRepository
import com.healthdiary.model.entities.Indicator

class HomeViewModel : ViewModel() {
    val viewState = MutableLiveData<List<Indicator>>()

    init {
        viewState.value = IndicatorsRepository(IndicatorsLocalDataSource).getIndicators()
    }

}
