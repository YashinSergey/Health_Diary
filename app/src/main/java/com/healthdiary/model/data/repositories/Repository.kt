package com.healthdiary.model.data.repositories

import com.healthdiary.model.data.localstorage.dbentities.query.EntityLastValueByIndicatorId
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.Note
import kotlinx.coroutines.channels.ReceiveChannel
import java.util.*

interface Repository {
    fun getNotesByDate(date: Date) : List<Note>
    suspend fun getNotesByIndicator(indicator: Indicator?) : ReceiveChannel<List<Note>>
    suspend fun getIndicatorById(id: Int?) : ReceiveChannel<Indicator?>
    suspend fun getIndicatorList() : ReceiveChannel<List<Indicator>>
    suspend fun getLastValueByIndicatorId(id : Int?) : ReceiveChannel<EntityLastValueByIndicatorId?>




}