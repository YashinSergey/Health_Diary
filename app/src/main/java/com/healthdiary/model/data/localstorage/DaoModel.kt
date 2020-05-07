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
import com.healthdiary.model.entities.Indicator

@Dao
interface DaoModel {
    @Query("SELECT * FROM indicators")
    fun getIndicatorsList()  : List<EntityIndicator>

    @Query("SELECT * FROM indicators WHERE id == :indicatorId")
    fun getIndicatorById(indicatorId : Int) : EntityIndicator

    @Query("SELECT notes.date, note_values.indicator_value_id, note_values.value FROM notes inner join note_values on notes.id = note_values.note_id and notes.indicator_id == :indicatorId")
    fun getNotesByIndicatorId(indicatorId : Int?) : List<EntityNoteForDataPoint>

    @Query("SELECT * FROM notes WHERE date == :date")
    fun getNotesByDate(date : Long) : List<EntityNote>

    @Query("SELECT id FROM indicator_parameters WHERE indicator_id == :indicatorId")
    fun getIndicatorParametersID(indicatorId : Int) : List<Int>

    @Query("SELECT id FROM indicator_values WHERE indicator_id == :indicatorId")
    fun getIdIndicatorValuesByIndicatorId(indicatorId : Int) : List<Int>

    @Query("SELECT * FROM parameter_values WHERE parameter_id == :parameterId")
    fun getParameterValuesByParametersId(parameterId : Int?) : List<EntityParameterValues>

    @Query("SELECT id FROM parameter_values WHERE parameter_id == :parameterId")
    fun getParameterValuesIdByParametersId(parameterId : Int?) : List<Int>

    @Query("SELECT notes.date, note_values.value, note_values.indicator_value_id FROM notes  inner join note_values on notes.id = note_values.note_id and notes.indicator_id == :indicatorId ORDER BY notes.date")
    fun getLastValueByIndicatorId(indicatorId : Int?) : List<EntityLastValueByIndicatorId>

    @Query("SELECT id FROM notes ORDER BY date")
    fun getNotesIdOrderByDate() : List<Int>

    @Insert()
    fun saveNote(noteEntry : EntityNote) : Long

    @Insert()
    fun saveNoteValues(noteValuesEntry : EntityNoteValues)

    @Insert()
    fun saveNoteParameters(noteParameters: EntityNoteParameters)

    @Insert()
    fun saveIndicator(indicatorEntry : EntityIndicator)

    @Insert()
    fun saveIndicatorValue(indicatorValueEntry : EntityIndicatorValues)

    @Insert()
    fun saveIndicatorParameters(indicatorParameterEntry : EntityIndicatorParameters)

    @Insert()
    fun saveParameterValues(parameterValuesEntry : EntityParameterValues)

}