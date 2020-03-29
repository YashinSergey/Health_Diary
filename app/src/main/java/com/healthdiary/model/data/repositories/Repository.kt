package com.healthdiary.model.data.repositories

import com.healthdiary.model.entities.Note
import java.util.*

interface Repository {
    fun getNoteByDate(date: Date): Note?
}