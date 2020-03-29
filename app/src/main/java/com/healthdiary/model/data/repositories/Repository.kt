package com.healthdiary.model.data.repositories

import com.healthdiary.model.entities.Note
import java.time.LocalDate

interface Repository {
    fun getNoteByDate(date: LocalDate): Note?
}