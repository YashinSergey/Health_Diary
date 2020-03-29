package com.healthdiary.model.data.repositories

import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.Note
import java.util.*

interface Repository {
    fun getNotesByIndicatorId(id: Int?) : List<Note>
    fun getIndicatorById(id: Int?) : Indicator?
    fun getNoteByDate(date: Date): Note?
}