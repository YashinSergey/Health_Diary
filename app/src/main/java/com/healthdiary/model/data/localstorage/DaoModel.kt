package com.healthdiary.model.data.localstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicatorValues
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicator
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityParameterValues
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityIndicatorParameters
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNote
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteParameters
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteValues
import com.healthdiary.model.data.localstorage.dbentities.query.EntityLastValueByIndicatorId
import com.healthdiary.model.data.localstorage.dbentities.query.EntityNoteForDataPoint
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoModel {
    @Query("SELECT notes.date, note_values.indicator_value_id, note_values.value FROM notes inner join note_values on notes.id = note_values.note_id and notes.indicator_id == :indicatorId ORDER BY notes.date")
    fun getNotesByIndicatorId(indicatorId : Int?) : Flow<List<EntityNoteForDataPoint>>

    @Query("SELECT * FROM notes WHERE date == :date ORDER BY notes.date")
    fun getNotesByDate(date : Long) : Flow<List<EntityNote>>

    @Query("SELECT notes.date, note_values.value, note_values.indicator_value_id FROM notes  inner join note_values on notes.id = note_values.note_id and notes.indicator_id == :indicatorId ORDER BY notes.date")
    fun getLastValueByIndicatorId(indicatorId : Int?) : Flow<List<EntityLastValueByIndicatorId>>

    @Query("SELECT id FROM notes ORDER BY date")
    fun getNotesIdOrderByDate() : Flow<List<Int>>


    @Query("SELECT * FROM indicators")
    fun getIndicatorsList()  : Flow<List<EntityIndicator>>

    @Query("SELECT * FROM indicators WHERE id == :indicatorId")
    fun getIndicatorById(indicatorId : Int) : Flow<EntityIndicator>


    @Query("SELECT id FROM indicator_parameters WHERE indicator_id == :indicatorId")
    fun getIndicatorParametersID(indicatorId : Int) : Flow<List<Int>>


    @Query("SELECT id FROM indicator_values WHERE indicator_id == :indicatorId")
    fun getIdIndicatorValuesIdByIndicatorId(indicatorId : Int) : Flow<List<Int>>

    @Query("SELECT * FROM parameter_values WHERE parameter_id == :parameterId")
    fun getParameterValuesByParametersId(parameterId : Int?) : Flow<List<EntityParameterValues>>

    @Query("SELECT id FROM parameter_values WHERE parameter_id == :parameterId")
    fun getParameterValuesIdByParametersId(parameterId : Int?) : Flow<List<Int>>


    @Insert()
    suspend fun saveNote(noteEntry : EntityNote) : Long

    @Insert()
    suspend fun saveNoteValues(noteValuesEntry : EntityNoteValues)

    @Insert()
    suspend fun saveNoteParameters(noteParameters: EntityNoteParameters) : Long

    @Insert()
    suspend fun saveIndicator(indicatorEntry : EntityIndicator) : Long

    @Insert()
    suspend fun saveIndicatorValue(indicatorValueEntry : EntityIndicatorValues)

    @Insert()
    suspend fun saveIndicatorParameters(indicatorParameterEntry : EntityIndicatorParameters) : Long

    @Insert()
    suspend fun saveParameterValues(parameterValuesEntry : EntityParameterValues)

}