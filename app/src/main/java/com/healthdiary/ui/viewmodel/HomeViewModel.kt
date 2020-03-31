package com.healthdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator

class HomeViewModel(repository: Repository) : ViewModel() {

    val viewState = MutableLiveData<List<Indicator>>()

    init {
        viewState.value = repository.getIndicatorList()
    }

}
