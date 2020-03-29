package com.healthdiary.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Note
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(repository: Repository) : ViewModel() {
    val viewState = MutableLiveData<Note>()
    var localDate = LocalDate.now()

    init {
        viewState.value = repository.getNoteByDate(localDate)
    }

}
