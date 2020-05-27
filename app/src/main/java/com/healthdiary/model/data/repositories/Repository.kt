package com.healthdiary.model.data.repositories

import com.healthdiary.model.data.localstorage.dbentities.query.EntityLastValueByIndicatorId
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.Note
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import java.util.*

interface Repository {
    fun getNotesByDate(date: Date): List<Note>
    suspend fun getNotesByIndicator(indicator: Indicator?): Flow<List<Note>>
    suspend fun getIndicatorById(id: Int): Indicator?
    suspend fun getIndicatorList(): List<Indicator>
    suspend fun getLastValueByIndicatorId(id: Int?): Flow<EntityLastValueByIndicatorId?>
    suspend fun saveNewNote(note: Note) : Int?
    suspend fun getNotesByIndicatorId(id: Int) : Flow<List<Note>>
}