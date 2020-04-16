package com.healthdiary.model.data.repositories

import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.Note
import java.util.*

interface Repository {
    fun getNotesByDate(date: Date) : List<Note>
    fun getNotesByIndicatorId(indicatorId: Int?) : List<Note>
    fun getIndicatorById(id: Int?) : Indicator?
    fun getIndicatorList() : List<Indicator>

    fun saveNote(note : Note)
    fun saveIndicatorValues()




}