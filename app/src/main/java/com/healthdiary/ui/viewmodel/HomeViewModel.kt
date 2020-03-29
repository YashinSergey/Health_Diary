package com.healthdiary.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Note
import java.util.*

class HomeViewModel(repository: Repository) : ViewModel() {

    val viewState = MutableLiveData<Note>()
    var date = Date()

    init {
        viewState.value = repository.getNoteByDate(date)
    }

}
