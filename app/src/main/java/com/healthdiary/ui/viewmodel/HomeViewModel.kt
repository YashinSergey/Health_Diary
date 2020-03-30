package com.healthdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import java.util.*

class HomeViewModel(repository: Repository) : ViewModel() {

    val viewState = MutableLiveData<List<Indicator>>()
    var date = Date()

    init {
        viewState.value = repository.getAllIndicators()
    }

}
